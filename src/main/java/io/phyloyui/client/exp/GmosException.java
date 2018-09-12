package io.phyloyui.client.exp;

public class GmosException extends RuntimeException{

    public GmosException(String message, Exception e) {
        super(message,e);
    }

    public GmosException(String message) {
        super(message);
    }
}
