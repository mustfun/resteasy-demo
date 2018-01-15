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

import com.dzy.resteasy.method.controller.AbstractControllerImplMethodGenerator;
import com.dzy.resteasy.method.controller.InsertSelectiveControllerMethodGenerator;
import com.dzy.resteasy.utils.GenerateFilePackageHolder;
import com.dzy.resteasy.utils.Utils;
import org.mybatis.generator.api.dom.java.*;
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
        TopLevelClass topLevelClass = new TopLevelClass(introspectedTable.getBaseRecordType());
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        topLevelClass.addImportedType(introspectedTable.getBaseRecordType());
        topLevelClass.addAnnotation("@Controller");
        topLevelClass.addAnnotation("@RequestMapping(\"/"+introspectedTable.getFullyQualifiedTable().getDomainObjectName()+"/\")");
        topLevelClass.addImportedType("org.springframework.stereotype.Controller");
        topLevelClass.addImportedType("org.springframework.web.bind.annotation.RequestMapping");
        topLevelClass.addImportedType("org.springframework.web.bind.annotation.RequestMethod");
        //添加Filed=====start
        String serviceType = GenerateFilePackageHolder.getFilePackage("service");
        topLevelClass.addImportedType(serviceType);
        Field field = new Field(Utils.lowerFirstCase(serviceType.substring(serviceType.lastIndexOf(".")+1,serviceType.length())),new FullyQualifiedJavaType(serviceType));
        field.addAnnotation("@Autowired");
        topLevelClass.addImportedType("org.springframework.beans.factory.annotation.Autowired ");
        field.setVisibility(JavaVisibility.PRIVATE);
        //添加Filed=====end
        topLevelClass.addField(field);
        addInsertSelectiveMethodImpl(topLevelClass);
        List<CompilationUnit> answer = new ArrayList<>();
        answer.add(topLevelClass);
        return answer;
    }

    protected void addInsertSelectiveMethodImpl(TopLevelClass interfaze) {
        //如果有primary key
        if (introspectedTable.getRules().generateSelectByPrimaryKey()) {
            AbstractControllerImplMethodGenerator methodGenerator = new InsertSelectiveControllerMethodGenerator();
            initializeAndExecuteImplGenerator(methodGenerator, interfaze);
        }
    }

    protected void initializeAndExecuteImplGenerator(
            AbstractControllerImplMethodGenerator methodGenerator,
            TopLevelClass topLevelClass) {
        methodGenerator.setIntrospectedTable(introspectedTable);
        methodGenerator.setContext(context);
        methodGenerator.setProgressCallback(progressCallback);
        methodGenerator.setWarnings(warnings);
        methodGenerator.addInterfaceElements(topLevelClass);
    }



    public PluginAggregator getPlugins() {
        return plugins;
    }

    public void setPlugins(PluginAggregator plugins) {
        this.plugins = plugins;
    }
}
