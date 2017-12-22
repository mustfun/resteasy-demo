package com.dzy.resteasy.support.filter;

import org.apache.commons.io.output.TeeOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author : itar
 * @date : 2017-12-17
 * @time : 13:59
 * @Version: 1.0
 * @since: JDK 1.8
 */
public class BranchResponseWrapper extends HttpServletResponseWrapper {

    /**
     * 我们的分支流
     */
    private ByteArrayOutputStream output;
    private ServletOutputStream filterOutput;

    private OutputStream bufferOutputStream;

    public BranchResponseWrapper(HttpServletResponse response) {
        super(response);
        /*try {
            bufferOutputStream = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        output = new ByteArrayOutputStream();
    }

    /**
     * 巧妙将ServletOutputStream放到公共变量，解决不能多次读写问题
     * 还只能用super.getOutputStream来写啊，奇怪
     * @return
     * @throws IOException
     */
    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (filterOutput == null) {
            filterOutput = new ServletOutputStream() {

                //替换构造方法
                //拿父类的response，初始化的时候，里面还没有数据，只有一些request信息和response信息,但是调用了创建outputStream,
                // 但是 但是 但是 ===》 创建了之后就不能修改了，无法达到修改response的结果
                //private TeeOutputStream teeOutputStream = new TeeOutputStream(bufferOutputStream,output);
                private TeeOutputStream teeOutputStream = new TeeOutputStream(BranchResponseWrapper.super.getOutputStream(),output);

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setWriteListener(WriteListener writeListener) {

                }

                @Override
                public void write(int b) throws IOException {
                    teeOutputStream.write(b);
                }
            };
        }
        return filterOutput;
    }

    public byte[] toByteArray() {
        return output.toByteArray();
    }
}
