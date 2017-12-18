package com.dzy.resteasy.support.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author : itar
 * @date : 2017-12-17
 * @time : 13:59
 * @Version: 1.0
 * @since: JDK 1.8
 */
public class ResponseWrapper extends HttpServletResponseWrapper {

    ByteArrayOutputStream output;
    ServletOutputStream filterOutput;

    public ResponseWrapper(HttpServletResponse response) {
        super(response);
        output = new ByteArrayOutputStream();
    }

    /**
     * 巧妙将ServletOutputStream放到公共变量，解决不能多次读写问题
     * 你确定这多线程下不会出问题？？？
     * @return
     * @throws IOException
     */
    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (filterOutput == null) {
            filterOutput = new ServletOutputStream() {
                @Override
                public void write(int b) throws IOException {
                    output.write(b);
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setWriteListener(WriteListener writeListener) {
                }
            };
        }
        return filterOutput;
    }

    public byte[] toByteArray() {
        return output.toByteArray();
    }
}
