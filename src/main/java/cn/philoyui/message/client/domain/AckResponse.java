package cn.philoyui.message.client.domain;

import java.io.Serializable;

public class AckResponse implements Serializable {

    private String id;

    private String type = "success";


    public AckResponse() {
        //
    }

    public AckResponse(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
