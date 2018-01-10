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

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.internal.util.StringUtility;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

import java.util.List;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2018/1/10
 * @since 1.0
 * @将什么getByExample之类的重新命名
 */
public class RenameMethodPlugin extends PluginAdapter {

    private Log log = LogFactory.getLog(this.getClass());

    // mybatis中的部分方法的后缀
    private String mybatisMethodSuffix;
    // 持久层对象的后缀
    private String persistenceObjectSuffix;
    // mybaits查询对象的后缀
    private String queryObjectSuffix;

    // mybatis中的部分方法的默认后缀
    private final String mybatisMethodDefaultSuffix = "Example";
    // 持久层对象的默认后缀
    private final String persistenceObjectDefaultSuffix = "Mapper";
    // mybaits查询对象的默认后缀
    private String queryObjectDefaultSuffix = "Example";

    public RenameMethodPlugin() {
        log.debug("initialized");
    }

    @Override
    public boolean validate(List<String> warnings) {
        mybatisMethodSuffix = properties.getProperty("mybatisMethodSuffix");
        persistenceObjectSuffix = properties.getProperty("persistenceObjectSuffix");
        queryObjectSuffix = properties.getProperty("queryObjectSuffix");

        log.debug("mybatisMethodSuffix = " + mybatisMethodSuffix);
        log.debug("persistenceObjectSuffix = " + persistenceObjectSuffix);
        log.debug("queryObjectSuffix = " + queryObjectSuffix);

        return true;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        if (StringUtility.stringHasValue(mybatisMethodSuffix)) {
            introspectedTable.setCountByExampleStatementId(introspectedTable.getCountByExampleStatementId().replace(mybatisMethodDefaultSuffix, mybatisMethodSuffix));
            introspectedTable.setDeleteByExampleStatementId(introspectedTable.getDeleteByExampleStatementId().replace(mybatisMethodDefaultSuffix, mybatisMethodSuffix));
            introspectedTable.setSelectByExampleStatementId(introspectedTable.getSelectByExampleStatementId().replace(mybatisMethodDefaultSuffix, mybatisMethodSuffix));
            introspectedTable.setSelectByExampleWithBLOBsStatementId(introspectedTable.getSelectByExampleWithBLOBsStatementId().replace(mybatisMethodDefaultSuffix, mybatisMethodSuffix));
            introspectedTable.setUpdateByExampleStatementId(introspectedTable.getUpdateByExampleStatementId().replace(mybatisMethodDefaultSuffix, mybatisMethodSuffix));
            introspectedTable.setUpdateByExampleSelectiveStatementId(introspectedTable.getUpdateByExampleSelectiveStatementId().replace(mybatisMethodDefaultSuffix, mybatisMethodSuffix));
            introspectedTable.setUpdateByExampleWithBLOBsStatementId(introspectedTable.getUpdateByExampleWithBLOBsStatementId().replace(mybatisMethodDefaultSuffix, mybatisMethodSuffix));
            introspectedTable.setExampleWhereClauseId(introspectedTable.getExampleWhereClauseId().replace(mybatisMethodDefaultSuffix, mybatisMethodSuffix));
            introspectedTable.setMyBatis3UpdateByExampleWhereClauseId(introspectedTable.getMyBatis3UpdateByExampleWhereClauseId().replace(mybatisMethodDefaultSuffix, mybatisMethodSuffix));
        }

        if (StringUtility.stringHasValue(persistenceObjectSuffix)) {
            String myBatis3JavaMapperType = introspectedTable.getMyBatis3JavaMapperType();
            introspectedTable.setMyBatis3JavaMapperType(myBatis3JavaMapperType.replace(persistenceObjectDefaultSuffix, persistenceObjectSuffix));
        }

        if (StringUtility.stringHasValue(queryObjectSuffix)) {
            String exampleType = introspectedTable.getExampleType();
            introspectedTable.setExampleType(exampleType.replace(queryObjectDefaultSuffix, queryObjectSuffix));
        }

        super.initialized(introspectedTable);
    }

}