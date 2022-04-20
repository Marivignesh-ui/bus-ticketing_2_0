package com.mari.bus_ticketing2.domain;

import com.google.gson.annotations.Expose;

public class WebResponse<T> {
    @Expose
    private boolean ok;
    @Expose
    private String message;
    @Expose
    private T responseObject;

    public WebResponse(boolean ok, String message) {
        this.ok = ok;
        this.message = message;
        this.responseObject = null;
    }

    public WebResponse(boolean ok, String message, T responseObject) {
        this.ok = ok;
        this.message = message;
        this.responseObject = responseObject;
    }

    @Override
    public String toString() {
        return "WebResponse [message=" + message + ", ok=" + ok + ", responseObject=" + responseObject + "]";
    }

    
}
