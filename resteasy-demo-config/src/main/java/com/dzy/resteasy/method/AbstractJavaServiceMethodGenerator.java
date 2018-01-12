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

package com.dzy.resteasy.method;

import com.dzy.resteasy.plugins.ExtDomainPlugin;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.codegen.AbstractGenerator;
import org.mybatis.generator.config.GeneratedKey;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

import static org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities.getRenamedColumnNameForResultMap;
import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2018/1/12
 * @since 1.0
 * @function 抽象生成service层代码
 */
public abstract class AbstractJavaServiceMethodGenerator extends
        AbstractGenerator {

    private static final Log logger = LogFactory.getLog(ExtDomainPlugin.class);

    public abstract void addInterfaceElements(Interface interfaze);

    public AbstractJavaServiceMethodGenerator() {
        super();
    }

    protected String getResultAnnotation(Interface interfaze, IntrospectedColumn introspectedColumn,
                                         boolean idColumn, boolean constructorBased) {
        StringBuilder sb = new StringBuilder();
        if (constructorBased) {
            interfaze.addImportedType(introspectedColumn.getFullyQualifiedJavaType());

            sb.append("@Arg(column=\"");
            sb.append(getRenamedColumnNameForResultMap(introspectedColumn));
            //$NON-NLS-1$
            sb.append("\", javaType=");
            sb.append(introspectedColumn.getFullyQualifiedJavaType().getShortName());
            //$NON-NLS-1$
            sb.append(".class");
        } else {
            sb.append("@Result(column=\"");
            sb.append(getRenamedColumnNameForResultMap(introspectedColumn));
            sb.append("\", property=\"");
            sb.append(introspectedColumn.getJavaProperty());
            sb.append('\"');
        }

        if (stringHasValue(introspectedColumn.getTypeHandler())) {
            FullyQualifiedJavaType fqjt =
                    new FullyQualifiedJavaType(introspectedColumn.getTypeHandler());
            interfaze.addImportedType(fqjt);
            sb.append(", typeHandler=");
            sb.append(fqjt.getShortName());
            sb.append(".class");
        }

        sb.append(", jdbcType=JdbcType.");
        sb.append(introspectedColumn.getJdbcTypeName());
        if (idColumn) {
            sb.append(", id=true");
        }
        sb.append(')');

        return sb.toString();
    }

    protected void addGeneratedKeyAnnotation(Method method, GeneratedKey gk) {
        StringBuilder sb = new StringBuilder();
        IntrospectedColumn introspectedColumn = introspectedTable.getColumn(gk.getColumn());
        if (introspectedColumn != null) {
            if (gk.isJdbcStandard()) {
                sb.append("@Options(useGeneratedKeys=true,keyProperty=\"");
                sb.append(introspectedColumn.getJavaProperty());
                sb.append("\")");
                method.addAnnotation(sb.toString());
            } else {
                FullyQualifiedJavaType fqjt = introspectedColumn.getFullyQualifiedJavaType();
                sb.append("@SelectKey(statement=\"");
                sb.append(gk.getRuntimeSqlStatement());
                sb.append("\", keyProperty=\"");
                sb.append(introspectedColumn.getJavaProperty());
                sb.append("\", before=");
                sb.append(gk.isIdentity() ? "false" : "true");
                sb.append(", resultType=");
                sb.append(fqjt.getShortName());
                sb.append(".class)");
                method.addAnnotation(sb.toString());
            }
        }
    }

    protected void addGeneratedKeyImports(Interface interfaze, GeneratedKey gk) {
        IntrospectedColumn introspectedColumn = introspectedTable.getColumn(gk.getColumn());
        if (introspectedColumn != null) {
            if (gk.isJdbcStandard()) {
                interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Options"));
            } else {
                interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.SelectKey"));
                FullyQualifiedJavaType fqjt = introspectedColumn.getFullyQualifiedJavaType();
                interfaze.addImportedType(fqjt);
            }
        }
    }
}
