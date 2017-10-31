package com.dzy.resteasy.test;

import com.alibaba.fastjson.JSON;
import com.dzy.resteasy.BootLauncher;
import com.dzy.resteasy.facade.CityController;
import com.dzy.resteasy.facade.impl.CityControllerImpl;
import com.dzy.resteasy.model.City;
import com.dzy.resteasy.model.UserInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/9/22
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootLauncher.class)
@ActiveProfiles("dev")
public class CityControllerMockTest {
    private transient final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CityControllerImpl cityController;


    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(cityController).defaultRequest(get("/")).alwaysExpect(forwardedUrl(null)).build();
    }

    /**
     * mock前可以设置一些默认值什么的
     * @MockBean模式， 这里有个问题： mockBean和mockMvc什么关系
     */
    /*@Before
    public void prepareMock(){
        BaseResult result=new BaseResult();
        result.setStatus(0);

        BDDMockito.given(cityController.getCity(1)).willReturn(result);
        //BDDMockito.given(cityController.testException("测试异常.....")).willThrow(new exception());
    }*/

    @Test
    public void testInsertCity() throws Exception {
        City city = new City();
        city.setName("sydney");
        city.setCountry("australia");
        city.setState("australia");
        String resp = mockMvc.perform(post("/city/addOneCity")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSON.toJSONString(city))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        logger.info("response is {}",resp);
        Assert.assertTrue(resp.contains("\"status\""));
    }

    @Test
    public void testInsertUserInfo() throws Exception{
        String version  =  "V2";
        String resp = mockMvc.perform(get("/city/addUserInfo?version="+version))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        logger.info("response is {}",resp);
        Assert.assertTrue(resp.contains("\"status\""));

    }

}
