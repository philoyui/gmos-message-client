package cn.com.gome.cloud.gmos.message.client;

import cn.com.gome.cloud.gmos.message.client.domain.Message;
import cn.com.gome.cloud.gmos.message.client.domain.MethodStatus;

public interface MessageHandler {

    void onTextMessage(Message message, MethodStatus methodStatus);

}
