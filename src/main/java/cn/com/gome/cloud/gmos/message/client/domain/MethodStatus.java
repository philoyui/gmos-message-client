package cn.com.gome.cloud.gmos.message.client.domain;

import java.io.Serializable;

public class MethodStatus implements Serializable {

    private boolean success = true;

    public boolean isSuccess() {
        return success;
    }

    public void fail() {
        success = false;
    }
}
