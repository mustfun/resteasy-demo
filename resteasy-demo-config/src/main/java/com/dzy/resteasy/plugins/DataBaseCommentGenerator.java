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
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.PropertyRegistry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

/**
 * @author itar
 * @version 1.0
 * @date 2018/1/9
 * @since 1.0
 */
public class DataBaseCommentGenerator implements CommentGenerator {


    /**
     * XMl配置信息
     */
    private Properties properties;
    /**
     * 系统配置信息，拿到一些用户名什么的
     */
    private Properties sysProperties;


    private Boolean suppressDate;

    private Boolean suppressAllComments;

    private String now;


    public DataBaseCommentGenerator() {
        this.properties = new Properties();
        this.sysProperties = System.getProperties();
        this.suppressAllComments = false;
        this.suppressDate = false;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        now = sdf.format(new Date());
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        //不一定要这么put，也可以直接引用
        this.properties.putAll(properties);

        suppressDate = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));

        suppressAllComments = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
    }

    /**
     * java字段上生成db字段注释
     * @param field
     * @param introspectedTable
     * @param introspectedColumn
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        field.addJavaDocLine(LineConstants.COMMENT_HEAD);
        field.addJavaDocLine(LineConstants.COMMENT_NORMAL_STAR+introspectedColumn.getRemarks());
        field.addJavaDocLine(LineConstants.COMMENTS_TAIL);
    }

    /**
     * 生成表注释
     * @param field
     * @param introspectedTable
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        field.addJavaDocLine(LineConstants.COMMENT_HEAD);
        field.addJavaDocLine(LineConstants.COMMENT_NORMAL_STAR+introspectedTable.getFullyQualifiedTable());
        field.addJavaDocLine(LineConstants.COMMENTS_TAIL);
    }

    /**
     * Adds a comment for a model class
     * @param topLevelClass
     * @param introspectedTable
     */
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        addCommentWithAuthor(topLevelClass, introspectedTable);
    }

    /**
     * 给内部类添加注释
     * @param innerClass
     * @param introspectedTable
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        innerClass.addJavaDocLine(LineConstants.COMMENT_HEAD);
        innerClass.addJavaDocLine(LineConstants.COMMENT_NORMAL_STAR+introspectedTable.getFullyQualifiedTable());
        innerClass.addJavaDocLine(now);
        innerClass.addJavaDocLine(LineConstants.COMMENTS_TAIL);
    }

    /**
     * 给内部类添加注释
     * @param innerClass
     * @param introspectedTable
     * @param b
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean b) {
        addCommentWithAuthor(innerClass, introspectedTable);
    }

    /**
     * 添加有作者的注释，公共
     * @param innerClass
     * @param introspectedTable
     */
    private void addCommentWithAuthor(InnerClass innerClass, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

        innerClass.addJavaDocLine(LineConstants.COMMENT_HEAD);

        innerClass.addJavaDocLine(LineConstants.COMMENT_NORMAL_STAR+"@tableName: "+introspectedTable.getFullyQualifiedTable().getRemarks());
        innerClass.addJavaDocLine(" * @author "+sysProperties.getProperty("user.name"));
        innerClass.addJavaDocLine(LineConstants.COMMENT_NORMAL_STAR+"@date "+now);
        innerClass.addJavaDocLine(LineConstants.COMMENT_NORMAL_STAR+"@version 1.0");
        innerClass.addJavaDocLine(LineConstants.COMMENT_NORMAL_STAR+"@since JDK1.8");
        innerClass.addJavaDocLine(LineConstants.COMMENTS_TAIL);
    }

    /**
     * 给内部的枚举添加注释
     * @param innerEnum
     * @param introspectedTable
     */
    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

        innerEnum.addJavaDocLine(LineConstants.COMMENT_HEAD);
        innerEnum.addJavaDocLine(LineConstants.COMMENT_NORMAL_STAR+introspectedTable.getFullyQualifiedTable());
        innerEnum.addJavaDocLine(now);
        innerEnum.addJavaDocLine(LineConstants.COMMENTS_TAIL);
    }

    /**
     * 给getter方法加
     * @param method
     * @param introspectedTable
     * @param introspectedColumn
     */
    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }

        method.addJavaDocLine(LineConstants.COMMENT_HEAD);
        method.addJavaDocLine(LineConstants.COMMENT_NORMAL_STAR+"get"+introspectedColumn.getRemarks());
        method.addJavaDocLine(" * @return "+introspectedColumn.getActualColumnName()+" "+introspectedColumn.getRemarks());
        method.addJavaDocLine(LineConstants.COMMENTS_TAIL);
    }

    /**
     * 给setter方法加
     * @param method
     * @param introspectedTable
     * @param introspectedColumn
     */
    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }

        method.addJavaDocLine(LineConstants.COMMENT_HEAD);
        method.addJavaDocLine(LineConstants.COMMENT_NORMAL_STAR+"set "+introspectedColumn.getRemarks());
        Parameter parm = method.getParameters().get(0);
        method.addJavaDocLine(" * @param "+parm.getName()+" "+introspectedColumn.getRemarks());
        method.addJavaDocLine(LineConstants.COMMENTS_TAIL);

    }

    /**
     * 给普通方法加注释
     * @param method
     * @param introspectedTable
     */
    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        method.addJavaDocLine(LineConstants.COMMENT_HEAD);
        method.addJavaDocLine(LineConstants.COMMENT_NORMAL_STAR+introspectedTable.getRemarks());
        for (Parameter param : method.getParameters()) {
            method.addJavaDocLine(" * @param "+param.getName()+introspectedTable.getRemarks());
        }
        method.addJavaDocLine(LineConstants.COMMENTS_TAIL);

    }

    /**
     * 设置copyRight之类的，java File级别的
     * @param compilationUnit
     */
    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        compilationUnit.addFileCommentLine(LineConstants.MIT_LISCENCE);
    }


    @Override
    public void addComment(XmlElement xmlElement) {
        return ;
    }

    @Override
    public void addRootComment(XmlElement xmlElement) {
        return ;
    }
}
