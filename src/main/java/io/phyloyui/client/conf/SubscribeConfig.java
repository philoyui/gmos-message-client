package io.phyloyui.client.conf;

import io.phyloyui.client.MessageHandler;
import io.phyloyui.client.common.GlobalSettings;

/**
 *
 * 订阅配置
 *
 * Created by yangyu-ds on 2018/9/12.
 */
public class SubscribeConfig {

    /**
     * 应用键值
     */
    private String appKey;

    /**
     * 应用秘钥
     */
    private String secret;

    /**
     * 分组信息
     */
    private String group;

    /**
     * 订阅地址
     */
    private String subscribeUrl;

    /**
     * 版本
     */
    private String version = GlobalSettings.VERSION;

    private MessageHandler messageHandler;

    public SubscribeConfig(String appKey, String secret, String group) {
        this.appKey = appKey;
        this.secret = secret;
        this.group = group;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getSubscribeUrl() {
        return subscribeUrl;
    }

    public void setSubscribeUrl(String subscribeUrl) {
        this.subscribeUrl = subscribeUrl;
    }

    public void registerMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
