package io.phyloyui.client;

import io.phyloyui.client.domain.Message;
import io.phyloyui.client.domain.MethodStatus;

public interface MessageHandler {

    void onTextMessage(Message message, MethodStatus methodStatus);

}
