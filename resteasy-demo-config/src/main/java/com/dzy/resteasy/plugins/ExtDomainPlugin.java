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

import com.dzy.resteasy.constants.LineConstants;
import com.dzy.resteasy.model.BaseDomain;
import com.dzy.resteasy.utils.GenerateFilePackageHolder;
import com.dzy.resteasy.utils.Utils;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.PluginAggregator;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2018/1/9
 * @since 1.0
 * @function 可以额外的生成 req resp dto bo 等
 */
public class ExtDomainPlugin extends PluginAdapter {

    private static final Log logger = LogFactory.getLog(ExtDomainPlugin.class);

    private Context context;

    private List<BaseDomain> domainList;

    public ExtDomainPlugin() {
        domainList = new ArrayList<>();
    }

    @Override
    public boolean validate(List<String> warnings) {
        List<String> domainNameList = Arrays.asList("dto", "req", "resp", "bo");
        for (String s : domainNameList) {
            BaseDomain baseDomain = new BaseDomain();
            String dto = properties.getProperty(s,"false");
            baseDomain.setName(s);
            baseDomain.setValue(dto);
            domainList.add(baseDomain);
        }
        return true;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        logger.debug("start generate req or resp");
        List<CompilationUnit> units = new ArrayList<>();
        for (BaseDomain baseDomain : domainList) {
            //设置需要生成的dto
            checkNeedToGenerate(introspectedTable, units, baseDomain, context);
        }
        List<GeneratedJavaFile> generatedFile = new ArrayList<>();
        GeneratedJavaFile gif;
        for (CompilationUnit unit : units) {
            //设置类名
            gif = new GeneratedJavaFile(unit,
                    context.getJavaModelGeneratorConfiguration().getTargetProject(),
                    context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                    context.getJavaFormatter());
            generatedFile.add(gif);
        }
        return generatedFile;
    }

    private void checkNeedToGenerate(IntrospectedTable introspectedTable, List<CompilationUnit> units, BaseDomain dto, Context context) {
        if (LineConstants.TRUE.equalsIgnoreCase(dto.getValue())) {
            ComplexModelGenerator repository = new ComplexModelGenerator();
            repository.setContext(context);
            repository.setIntrospectedTable(introspectedTable);
            if (dto.getName().equalsIgnoreCase("req")||dto.getName().equalsIgnoreCase("resp")||dto.getName().equalsIgnoreCase("dto")){
                PluginAggregator plugins = new PluginAggregator();
                plugins.addPlugin(new SwaggerPlugin());
                repository.setPlugins(plugins);
            }
            setIntrospectedTableType(introspectedTable,dto.getName());
            repository.setProgressCallback(new DomainExtProgressCallback());
            List<CompilationUnit> dtoUnits = repository.getCompilationUnits();
            units.addAll(dtoUnits);
        }
    }

    /**
     * 根据配置的参数决定是否生成bo 、req、resp、dto等
     */
    private IntrospectedTable setIntrospectedTableType(IntrospectedTable introspectedTable,String type){
        //到po那里
        String fullPath = context.getJavaModelGeneratorConfiguration().getTargetPackage();
        logger.debug("before rename po "+fullPath);
        String prefixPath = fullPath.substring(0, fullPath.lastIndexOf("."));
        String updatedFullPath = prefixPath+"."+type+"."+introspectedTable.getFullyQualifiedTable().getDomainObjectName()+ Utils.upperFirstCase(type);
        logger.debug("after rename po "+updatedFullPath);
        GenerateFilePackageHolder.init();
        GenerateFilePackageHolder.setPackageName(type,updatedFullPath);
        introspectedTable.setBaseRecordType(updatedFullPath);
        return introspectedTable;
    }


    private  class DomainExtProgressCallback implements ProgressCallback {
        @Override
        public void introspectionStarted(int totalTasks) {

        }

        @Override
        public void generationStarted(int totalTasks) {

        }

        @Override
        public void saveStarted(int totalTasks) {

        }

        @Override
        public void startTask(String taskName) {

        }

        @Override
        public void done() {

        }

        @Override
        public void checkCancel() throws InterruptedException {

        }
    }
}
