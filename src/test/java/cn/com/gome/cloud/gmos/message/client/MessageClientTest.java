package cn.com.gome.cloud.gmos.message.client;

import cn.com.gome.cloud.gmos.message.client.domain.Message;
import cn.com.gome.cloud.gmos.message.client.domain.MethodStatus;
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
            public void onTextMessage(Message message, MethodStatus methodStatus) {
                try {
                    System.out.println("--success--" + message.getContent());
                }catch (Exception e){
                    methodStatus.fail();
                }
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
