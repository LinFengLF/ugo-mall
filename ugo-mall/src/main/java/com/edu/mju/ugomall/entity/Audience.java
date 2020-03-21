package com.edu.mju.ugomall.entity;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 67072
 */
@ConfigurationProperties(prefix = "audience")
@Component
public class Audience {
    private String clientId;
    private String name;
    private String base64Secret;
    private int expiresSecond;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBase64Secret() {
        return base64Secret;
    }

    public void setBase64Secret(String base64Secret) {
        this.base64Secret = base64Secret;
    }

    public int getExpiresSecond() {
        return expiresSecond;
    }

    public void setExpiresSecond(int expiresSecond) {
        this.expiresSecond = expiresSecond;
    }

    public Audience() {
    }

    @Override
    public String toString() {
        return "Audience{" +
                "clientId='" + clientId + '\'' +
                ", name='" + name + '\'' +
                ", base64Secret='" + base64Secret + '\'' +
                ", expiresSecond=" + expiresSecond +
                '}';
    }
}
