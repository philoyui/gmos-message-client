package cn.com.gome.cloud.gmos.message.client.exp;

public class GmosException extends RuntimeException{

    public GmosException(String message, Exception e) {
        super(message,e);
    }

    public GmosException(String message) {
        super(message);
    }
}
