package com.dzy.resteasy.support.config.web;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.dzy.resteasy.support.filter.LoggingRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用fastjson解析数据
 */
@Configuration()
public class WebConfig extends WebMvcConfigurerAdapter {
    @Bean
    public HttpMessageConverter httpMessageConverter(){
        FastJsonConfig fastJsonConfig =new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue);

        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteNullNumberAsZero
                , SerializerFeature.WriteNullStringAsEmpty
                , SerializerFeature.WriteNullBooleanAsFalse
                , SerializerFeature.WriteNullListAsEmpty
                , SerializerFeature.SortField
        );
        fastJsonConfig.setFeatures(Feature.AllowUnQuotedFieldNames, Feature.AllowSingleQuotes);
        FastJsonHttpMessageConverter4 fastJsonHttpMessageConverter4 =  new FastJsonHttpMessageConverter4();
        fastJsonHttpMessageConverter4.setFastJsonConfig(fastJsonConfig);
        fastJsonHttpMessageConverter4.setDefaultCharset(Charset.forName("UTF-8"));
        return fastJsonHttpMessageConverter4;
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        List<HttpMessageConverter<?>>  converterList = restTemplate.getMessageConverters();
        HttpMessageConverter<?> converterTarget = null;
        for (HttpMessageConverter<?> item : converterList) {
            if (item.getClass() == StringHttpMessageConverter.class) {
                converterTarget = item;
                break;
            }
        }
        if (converterTarget != null) {
            converterList.remove(converterTarget);
        }
        HttpMessageConverter<?> converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        converterList.add(1,converter);
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(new LoggingRequestInterceptor());
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

    @Bean
    public AsyncRestTemplate asyncRestTemplate(){
        return new AsyncRestTemplate();
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        AntPathMatcher matcher = new AntPathMatcher();
        matcher.setCaseSensitive(false);
        configurer.setPathMatcher(matcher);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(httpMessageConverter());
        super.configureMessageConverters(converters);
    }

}

