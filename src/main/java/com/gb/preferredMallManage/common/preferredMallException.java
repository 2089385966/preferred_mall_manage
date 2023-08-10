package com.gb.preferredMallManage.common;

public class preferredMallException extends RuntimeException{

    public preferredMallException() {
    }

    public preferredMallException(String message) {
        super(message);
    }

    public static void fail(String message){
        throw new preferredMallException(message);
    }
}
