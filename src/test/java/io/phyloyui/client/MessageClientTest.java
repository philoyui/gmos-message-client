package io.phyloyui.client;

import io.phyloyui.client.domain.Message;
import org.junit.Test;

public class MessageClientTest {

    public void test_common(){

        String appKey = "appkey122fgfg112";

        String secret = "secret111222";

        String group = "default_group";

        MessageClient messageClient = new MessageClient(appKey,secret,group);

        messageClient.handleMessage(new MessageHandler(){

            @Override
            public void onTextMessage(Message message) {
                System.out.println( "--success--" + message.getContent());
            }

        });

        messageClient.connect("ws://localhost:8080/endpoints");

        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
