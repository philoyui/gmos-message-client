package io.phyloyui.client;

import io.phyloyui.client.domain.Message;
import org.junit.Test;

public class MessageClientTest {

    @Test
    public void test_common(){

        String appKey = "appkey122112";

        String secret = "secret111222";

        String group = "default_group";

        MessageClient messageClient = new MessageClient(appKey,secret,group);

        messageClient.handleMessage(new MessageHandler(){

            @Override
            public void onTextMessage(Message message) {
                System.out.println( "--success--" + message.getContent());
            }

            @Override
            public void onError(Message message) {
                System.out.println( "--error--" + message.getContent());
            }

        });

        messageClient.connect("ws://localhost:8080/endpoints");

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
