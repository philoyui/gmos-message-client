package cn.com.gome.cloud.gmos.message.client.domain;

import cn.com.gome.cloud.gmos.message.client.common.GlobalSettings;
import cn.com.gome.cloud.gmos.message.client.common.SignUtils;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class SubscribeRequest implements Serializable {

    /**
     * 键值
     */
    private String appKey;

    /**
     * 分组
     */
    private String groupName;

    /**
     * 签名
     */
    private String sign;

    /**
     * 版本
     */
    private String version = GlobalSettings.VERSION;

    /**
     * 请求时间
     */
    private long timestamp = System.currentTimeMillis();

    public SubscribeRequest(String appKey, String secret, String groupName) {

        this.appKey = appKey;
        this.groupName = groupName;

        Map<String,String> parameters = new TreeMap<String,String>();
        parameters.put("appKey",appKey);
        parameters.put("groupName",groupName);
        parameters.put("version",version);
        parameters.put("timestamp",String.valueOf(timestamp));

        this.sign = SignUtils.sign(parameters,secret);

    }


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
