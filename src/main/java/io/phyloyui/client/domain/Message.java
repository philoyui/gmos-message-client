package io.phyloyui.client.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class Message implements Serializable {

    /**
     * 消息的ID
     */
    private Long id;

    /**
     * 消息所属的主题
     */
    private String topic;

    /**
     * 消息发布者的AppKey
     */
    private String pubAppKey;

    /**
     * 消息的发布时间
     */
    private Date pubTime;

    /**
     * 消息所属的用户编号
     */
    private Long userId;

    /**
     * 用户的昵称
     */
    private String userNick;

    /**
     * 消息的详细内容
     */
    private String content;

    /**
     * 消息信息
     */
    private Map<String, Object> keyValues;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getPubAppKey() {
        return pubAppKey;
    }

    public void setPubAppKey(String pubAppKey) {
        this.pubAppKey = pubAppKey;
    }

    public Date getPubTime() {
        return pubTime;
    }

    public void setPubTime(Date pubTime) {
        this.pubTime = pubTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, Object> getKeyValues() {
        return keyValues;
    }

    public void setKeyValues(Map<String, Object> keyValues) {
        this.keyValues = keyValues;
    }
}
