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

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.codegen.mybatis3.model.SimpleModelGenerator;
import org.mybatis.generator.config.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2018/1/9
 * @since 1.0
 * @function 可以额外的生成 req resp dto bo 等
 */
public class ExtDomainPlugin extends PluginAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(ExtDomainPlugin.class);

    private Context context;


    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }


    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        SimpleModelGenerator repository = new SimpleModelGenerator();
        repository.setContext(context);
        repository.setIntrospectedTable(introspectedTable);
        repository.setProgressCallback(new ProgressCallback() {
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
        });
        List<CompilationUnit> units = repository.getCompilationUnits();

        List<GeneratedJavaFile> generatedFile = new ArrayList<>();
        GeneratedJavaFile gif;
        for (CompilationUnit unit : units) {
            gif = new GeneratedJavaFile(unit,context.getJavaModelGeneratorConfiguration().getTargetProject()+"/po",
                    context.getProperty("javaFileEncoding"),context.getJavaFormatter());
            generatedFile.add(gif);
        }
        return generatedFile;
    }
}
