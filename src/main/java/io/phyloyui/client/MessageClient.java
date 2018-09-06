package io.phyloyui.client;

public class MessageClient {

    /**
     * 应用键值
     */
    private final String appKey;

    /**
     * 应用秘钥
     */
    private final String secret;

    /**
     * 分组信息
     */
    private final String group;

    /**
     * 网关地址
     */
    private String url;

    /**
     * 消息处理器
     */
    private MessageHandler messageHandler;

    public MessageClient(String appKey, String secret, String group) {
        this.appKey = appKey;
        this.secret = secret;
        this.group = group;
    }

    public void handleMessage(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public void connect(String url) {
        this.url = url;
    }

    public String getAppKey() {
        return appKey;
    }

    public String getSecret() {
        return secret;
    }

    public String getGroup() {
        return group;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }


}
