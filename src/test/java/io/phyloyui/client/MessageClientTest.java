package io.phyloyui.client;

import io.phyloyui.client.domain.Message;
import org.junit.Test;

public class MessageClientTest {

    @Test
    public void test_common(){

        String appKey = "appkey";

        String secret = "secret";

        String group = "group";

        MessageClient messageClient = new MessageClient(appKey,secret,group);

        messageClient.handleMessage(new MessageHandler(){

            @Override
            public void onTextMessage(Message message) {

            }

            @Override
            public void onError(Message message) {

            }

        });

        messageClient.connect("ws://gw.gome.com.cn");

    }


}
