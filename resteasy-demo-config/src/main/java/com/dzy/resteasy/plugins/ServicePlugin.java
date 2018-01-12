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

import com.dzy.resteasy.utils.Utils;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2018/1/11
 * @since 1.0
 */
public class ServicePlugin extends PluginAdapter {

    private static final Log logger = LogFactory.getLog(ExtDomainPlugin.class);


    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }


    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        List<CompilationUnit> units;
        ServiceGenerator serviceGenerator = new ServiceGenerator();
        serviceGenerator.setContext(context);
        setIntrospectedTableType(introspectedTable,"service");
        serviceGenerator.setIntrospectedTable(introspectedTable);
        units = serviceGenerator.getCompilationUnits();
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


    private void setIntrospectedTableType(IntrospectedTable introspectedTable,String type){
        //到po那里
        String fullPath = context.getJavaModelGeneratorConfiguration().getTargetPackage();
        logger.debug("before rename service "+fullPath);
        String prefixPath = fullPath.substring(0, fullPath.lastIndexOf("."));
        prefixPath = prefixPath.substring(0, prefixPath.lastIndexOf("."));
        String updatedFullPath = prefixPath+"."+type+"."+introspectedTable.getFullyQualifiedTable().getDomainObjectName()+ Utils.upperFirstCase(type);
        logger.debug("after rename service "+updatedFullPath);
        introspectedTable.setBaseRecordType(updatedFullPath);
    }
}
