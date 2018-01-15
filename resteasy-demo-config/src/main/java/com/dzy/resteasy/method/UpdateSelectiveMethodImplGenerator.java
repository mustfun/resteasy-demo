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

import com.dzy.resteasy.utils.GenerateFilePackageHolder;
import com.dzy.resteasy.utils.Utils;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2018/1/12
 * @since 1.0
 */
public class UpdateSelectiveMethodImplGenerator extends AbstractJavaServiceImplMethodGenerator{

    private static final Log log= LogFactory.getLog(UpdateSelectiveMethodImplGenerator.class);


    public UpdateSelectiveMethodImplGenerator() {
        super();
    }

    @Override
    public void addInterfaceElements(TopLevelClass topLevelClass) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addAnnotation("@Override");

        String reqDomainName = GenerateFilePackageHolder.getFilePackage("req");
        FullyQualifiedJavaType reqType = new FullyQualifiedJavaType(reqDomainName);

        importedTypes.add(reqType);

        FullyQualifiedJavaType type = introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType();
        method.setReturnType(type);

        method.setName(introspectedTable.getUpdateByPrimaryKeySelectiveStatementId());
        method.addParameter(new Parameter(reqType, "req"));


        String orginalDomainName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        String mapperType = introspectedTable.getMyBatis3JavaMapperType();


        importedTypes.add(type);
        importedTypes.add(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
        importedTypes.add(new FullyQualifiedJavaType("org.springframework.beans.BeanUtils"));
        importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType()));
        importedTypes.add(new FullyQualifiedJavaType(context.getJavaModelGeneratorConfiguration().getTargetPackage()+"."+orginalDomainName));
        log.debug("now update base record is "+introspectedTable.getFullyQualifiedTable());

        //====添加方法体
        StringBuilder sb = new StringBuilder();
        sb.append(orginalDomainName).append(" ").append(Utils.lowerFirstCase(orginalDomainName)).append("=")
                .append("new ").append(orginalDomainName).append("();");
        method.addBodyLine(sb.toString());
        sb.setLength(0);
        sb.append("BeanUtils.copyProperties(").append("req").append(",").append(Utils.lowerFirstCase(orginalDomainName)).append(");");
        method.addBodyLine(sb.toString());
        sb.setLength(0);
        sb.append(Utils.extractBaseNameFromPackageAndLowerCase(mapperType)).append(".").append(introspectedTable.getUpdateByPrimaryKeySelectiveStatementId())
                .append("(").append(Utils.lowerFirstCase(orginalDomainName)).append(");");
        method.addBodyLine(sb.toString());
        sb.setLength(0);
        String javaProperty = introspectedTable.getPrimaryKeyColumns().get(0).getJavaProperty();
        log.debug("now update selective primary key property is "+javaProperty);
        sb.append("return ").append(Utils.lowerFirstCase(orginalDomainName)).append(".").append(Utils.generateGetMethod(javaProperty)).append(";");
        method.addBodyLine(sb.toString());
        //====添加方法体

        topLevelClass.addMethod(method);
        topLevelClass.addImportedTypes(importedTypes);

        context.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable);

    }

}
