package com.dselector.dselector_app.http.request;

import java.io.Serializable;

public class FirewallIPRequest implements Serializable {

    private String ip;

    public FirewallIPRequest(){}

    public FirewallIPRequest(String ip){
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}
