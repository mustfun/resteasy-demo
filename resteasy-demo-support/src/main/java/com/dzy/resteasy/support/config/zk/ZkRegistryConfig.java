package com.dzy.resteasy.support.config.zk;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2017/8/30
 * @since 1.0
 */
@ConfigurationProperties(prefix = "codis.zk")
public class ZkRegistryConfig {

    /**
     * zk url
     */
    private String zkUrl;

    /**
     * zk会话超时（单位：毫秒）
     * <p>默认：30000ms</p>
     */
    private int zkSessionTimeoutInMs;

    /**
     * proxy在zk上的路径
     * <p>e.g. /zk/codis/db_xxx/proxy</p>
     */
    private String codisProxyPath;

    public ZkRegistryConfig(){
        zkSessionTimeoutInMs = 30000;
    }


    /**
     * Gets proxy在zk上的路径
     * <p>e.g. zkcodisdb_xxxproxy<p>.
     *
     * @return Value of proxy在zk上的路径
     * <p>e.g. zkcodisdb_xxxproxy<p>.
     */
    public String getCodisProxyPath() {
        return codisProxyPath;
    }

    /**
     * Gets zk会话超时（单位：毫秒）
     * <p>默认：30000ms<p>.
     *
     * @return Value of zk会话超时（单位：毫秒）
     * <p>默认：30000ms<p>.
     */
    public int getZkSessionTimeoutInMs() {
        return zkSessionTimeoutInMs;
    }

    /**
     * Sets new zk会话超时（单位：毫秒）
     * <p>默认：30000ms<p>.
     *
     * @param zkSessionTimeoutInMs New value of zk会话超时（单位：毫秒）
     *                             <p>默认：30000ms<p>.
     */
    public void setZkSessionTimeoutInMs(int zkSessionTimeoutInMs) {
        this.zkSessionTimeoutInMs = zkSessionTimeoutInMs;
    }

    /**
     * Sets new proxy在zk上的路径
     * <p>e.g. zkcodisdb_xxxproxy<p>.
     *
     * @param codisProxyPath New value of proxy在zk上的路径
     *                       <p>e.g. zkcodisdb_xxxproxy<p>.
     */
    public void setCodisProxyPath(String codisProxyPath) {
        this.codisProxyPath = codisProxyPath;
    }

    /**
     * Sets new zk url.
     *
     * @param zkUrl New value of zk url.
     */
    public void setZkUrl(String zkUrl) {
        this.zkUrl = zkUrl;
    }

    /**
     * Gets zk url.
     *
     * @return Value of zk url.
     */
    public String getZkUrl() {
        return zkUrl;
    }
}
