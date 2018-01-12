/*
 * Copyright (c) 2018.  wuhandzy@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.dzy.resteasy.plugins;

import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.internal.PluginAggregator;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2018/1/10
 * @since 1.0
 * @function 生成Service、ServiceImpl的一个generator
 */
public class ControllerGenerator extends AbstractJavaGenerator {

    private static final Log logger = LogFactory.getLog(ExtDomainPlugin.class);

    private PluginAggregator plugins;

    public ControllerGenerator() {
        super();
        plugins = new PluginAggregator();
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {

        Interface inter = new Interface("com.dzy.resteasy.controller.CityController");
        inter.setVisibility(JavaVisibility.PUBLIC);
        inter.addImportedType(new FullyQualifiedJavaType
                (introspectedTable.getBaseRecordType()));

        List<CompilationUnit> answer = new ArrayList<>();
        answer.add(inter);
        return answer;
    }



    public PluginAggregator getPlugins() {
        return plugins;
    }

    public void setPlugins(PluginAggregator plugins) {
        this.plugins = plugins;
    }
}
