package io.phyloyui.client;

import io.phyloyui.client.conf.SubscribeConfig;
import io.phyloyui.client.thread.ReceiverThread;

public class MessageClient {

    private SubscribeConfig subscribeConfig;

    /**
     * 消息处理器
     */
    private MessageHandler messageHandler;

    public MessageClient(String appKey, String secret, String group) {
        subscribeConfig = new SubscribeConfig(appKey,secret,group);
    }

    public void handleMessage(MessageHandler messageHandler) {
        subscribeConfig.registerMessageHandler(messageHandler);
    }

    public void connect(String url) {
        subscribeConfig.setSubscribeUrl(url);
        new Thread(new ReceiverThread(subscribeConfig)).start();
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

}
