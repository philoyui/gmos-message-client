package io.phyloyui.client;

import io.phyloyui.client.domain.Message;

public interface MessageHandler {

    void onTextMessage(Message message);

    void onError(Message message);

}
