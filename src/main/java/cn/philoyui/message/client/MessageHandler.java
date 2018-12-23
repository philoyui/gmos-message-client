package cn.philoyui.message.client;

import cn.philoyui.message.client.domain.Message;
import cn.philoyui.message.client.domain.MethodStatus;

public interface MessageHandler {

    void onTextMessage(Message message, MethodStatus methodStatus);

}
