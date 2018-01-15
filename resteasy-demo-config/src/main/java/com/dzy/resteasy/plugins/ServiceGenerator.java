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

import com.dzy.resteasy.method.*;
import com.dzy.resteasy.utils.GenerateFilePackageHolder;
import com.dzy.resteasy.utils.Utils;
import org.mybatis.generator.api.IntrospectedTable;
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
public class ServiceGenerator extends AbstractJavaGenerator {

    private static final Log logger = LogFactory.getLog(ExtDomainPlugin.class);

    private PluginAggregator plugins;

    public ServiceGenerator() {
        super();
        plugins = new PluginAggregator();
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {

        //建立这个接口，service
        Interface inter = new Interface(introspectedTable.getBaseRecordType());
        GenerateFilePackageHolder.setPackageName("service",introspectedTable.getBaseRecordType());
        inter.setVisibility(JavaVisibility.PUBLIC);
        inter.addImportedType(new FullyQualifiedJavaType
                (introspectedTable.getBaseRecordType()));
        addSelectByPrimaryKeyMethod(inter);
        addInsertSelectiveMethod(inter);
        addUpdateSelectiveMethod(inter);
        deleteByIdMethod(inter);

        //建立这个接口的实现类 serviceImpl
        TopLevelClass topLevelClass = new TopLevelClass(generateServiceImplPath(introspectedTable.getBaseRecordType(),introspectedTable));
        GenerateFilePackageHolder.setPackageName("serviceImpl",generateServiceImplPath(introspectedTable.getBaseRecordType(),introspectedTable));
        topLevelClass.addSuperInterface(new FullyQualifiedJavaType(introspectedTable.getFullyQualifiedTable().getDomainObjectName()+"Service"));
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        topLevelClass.addImportedType(introspectedTable.getBaseRecordType());
        topLevelClass.addAnnotation("@Service");
        topLevelClass.addImportedType("org.springframework.stereotype.Service");
        //添加Filed=====start
        String mapperType = introspectedTable.getMyBatis3JavaMapperType();
        Field field = new Field(Utils.lowerFirstCase(mapperType.substring(mapperType.lastIndexOf(".")+1,mapperType.length())),new FullyQualifiedJavaType(mapperType));
        field.addAnnotation("@Autowired");
        field.setVisibility(JavaVisibility.PRIVATE);
        //添加Filed=====end
        topLevelClass.addField(field);
        addSelectByPrimaryKeyMethodImpl(topLevelClass);
        addInsertSelectiveMethodImpl(topLevelClass);
        addUpdateSelectiveMethodImpl(topLevelClass);
        deleteByIdMethodImpl(topLevelClass);
        List<CompilationUnit> answer = new ArrayList<>();
        answer.add(topLevelClass);
        answer.add(inter);
        return answer;
    }

    private String generateServiceImplPath(String servicePath, IntrospectedTable introspectedTable){
        String prefixPath = servicePath.substring(0, servicePath.lastIndexOf("."));
        return prefixPath+".impl."+introspectedTable.getFullyQualifiedTable().getDomainObjectName()+"ServiceImpl";
    }



    public PluginAggregator getPlugins() {
        return plugins;
    }

    public void setPlugins(PluginAggregator plugins) {
        this.plugins = plugins;
    }

    protected void addSelectByPrimaryKeyMethod(Interface interfaze) {
        //如果有primary key
        if (introspectedTable.getRules().generateSelectByPrimaryKey()) {
            AbstractJavaServiceMethodGenerator methodGenerator = new SelectByIdMethodGenerator();
            initializeAndExecuteGenerator(methodGenerator, interfaze);
        }
    }

    protected void addInsertSelectiveMethod(Interface interfaze) {
        //如果有primary key
        if (introspectedTable.getRules().generateSelectByPrimaryKey()) {
            AbstractJavaServiceMethodGenerator methodGenerator = new InsertSelectiveMethodGenerator();
            initializeAndExecuteGenerator(methodGenerator, interfaze);
        }
    }

    protected void addUpdateSelectiveMethod(Interface interfaze) {
        //如果有primary key
        if (introspectedTable.getRules().generateSelectByPrimaryKey()) {
            AbstractJavaServiceMethodGenerator methodGenerator = new UpdateSelectiveMethodGenerator();
            initializeAndExecuteGenerator(methodGenerator, interfaze);
        }
    }

    protected void deleteByIdMethod(Interface interfaze) {
        //如果有primary key
        if (introspectedTable.getRules().generateSelectByPrimaryKey()) {
            AbstractJavaServiceMethodGenerator methodGenerator = new DeleteByIdMethodGenerator();
            initializeAndExecuteGenerator(methodGenerator, interfaze);
        }
    }

    protected void deleteByIdMethodImpl(TopLevelClass topLevelClass) {
        //如果有primary key
        if (introspectedTable.getRules().generateSelectByPrimaryKey()) {
            AbstractJavaServiceImplMethodGenerator methodGenerator = new DeleteByIdMethodImplGenerator();
            initializeAndExecuteImplGenerator(methodGenerator, topLevelClass);
        }
    }

    protected void addUpdateSelectiveMethodImpl(TopLevelClass topLevelClass) {
        //如果有primary key
        if (introspectedTable.getRules().generateSelectByPrimaryKey()) {
            AbstractJavaServiceImplMethodGenerator methodGenerator = new UpdateSelectiveMethodImplGenerator();
            initializeAndExecuteImplGenerator(methodGenerator, topLevelClass);
        }
    }


    protected void addSelectByPrimaryKeyMethodImpl(TopLevelClass topLevelClass) {
        //如果有primary key
        if (introspectedTable.getRules().generateSelectByPrimaryKey()) {
            AbstractJavaServiceImplMethodGenerator methodGenerator = new SelectByIdMethodImplGenerator();
            initializeAndExecuteImplGenerator(methodGenerator, topLevelClass);
        }
    }

    protected void addInsertSelectiveMethodImpl(TopLevelClass topLevelClass) {
        //如果有primary key
        if (introspectedTable.getRules().generateSelectByPrimaryKey()) {
            AbstractJavaServiceImplMethodGenerator methodGenerator = new InsertSelectiveMethodImplGenerator();
            initializeAndExecuteImplGenerator(methodGenerator, topLevelClass);
        }
    }


    /**
     * 接口
     * @param methodGenerator
     * @param interfaze
     */
    protected void initializeAndExecuteGenerator(
            AbstractJavaServiceMethodGenerator methodGenerator,
            Interface interfaze) {
        methodGenerator.setContext(context);
        methodGenerator.setIntrospectedTable(introspectedTable);
        methodGenerator.setProgressCallback(progressCallback);
        methodGenerator.setWarnings(warnings);
        methodGenerator.addInterfaceElements(interfaze);
    }

    /**
     * 实现类
     * @param methodGenerator
     * @param topLevelClass
     */
    protected void initializeAndExecuteImplGenerator(
            AbstractJavaServiceImplMethodGenerator methodGenerator,
            TopLevelClass topLevelClass) {
        methodGenerator.setContext(context);
        methodGenerator.setIntrospectedTable(introspectedTable);
        methodGenerator.setWarnings(warnings);
        methodGenerator.setProgressCallback(progressCallback);
        methodGenerator.addInterfaceElements(topLevelClass);
    }
}
