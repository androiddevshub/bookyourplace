package com.app.bookyourplace.Model;

public class APIError {

    private int code;
    private boolean status;
    private String error;

    public APIError() {
    }

    public int getCode() {
        return code;
    }

    public boolean isStatus() {
        return status;
    }

    public String getError() {
        return error;
    }
}
