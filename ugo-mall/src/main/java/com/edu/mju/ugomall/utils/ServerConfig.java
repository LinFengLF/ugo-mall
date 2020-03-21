package com.edu.mju.ugomall.utils;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 获取项目IP、端口号
 * @author 67072
 */
public class ServerConfig implements ApplicationListener<WebServerInitializedEvent> {
    private static int SERVER_PORT = 8080;

    public String getUrl() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "http://" + address.getHostAddress() + ":" + SERVER_PORT;
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        SERVER_PORT = event.getWebServer().getPort();
    }
}
