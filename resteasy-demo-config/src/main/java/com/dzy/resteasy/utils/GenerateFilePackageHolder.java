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

package com.dzy.resteasy.utils;

import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

import java.util.HashMap;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2018/1/11
 * @since 1.0
 */
public class GenerateFilePackageHolder {

    private static final Log log = LogFactory.getLog(GenerateFilePackageHolder.class);
    /**
     * 插件是单线程的，应该不会有问题
     */
    private static HashMap<String,String> hashMap =new HashMap<>();

    public static void init(){
        log.debug("fuck package success size="+hashMap.size());
    }

    public static void setPackageName(String key,String value){
        log.debug("store package success size="+hashMap.size());
        hashMap.put(key,value);
        log.debug("store package success size="+hashMap.size());
    }

    public static  String getFilePackage(String name){
        log.debug("get package name size="+hashMap.size());
        return hashMap.get(name);
    }
}
