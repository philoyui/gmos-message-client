package io.phyloyui.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neovisionaries.ws.client.*;
import io.phyloyui.client.domain.Message;
import io.phyloyui.client.domain.SubscribeRequest;

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

    private Gson gson = new GsonBuilder().create();


    public MessageClient(String appKey, String secret, String group) {
        this.appKey = appKey;
        this.secret = secret;
        this.group = group;
    }

    public void handleMessage(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public void connect(String url) {

        try {
            WebSocket webSocket = new WebSocketFactory()
                    .setConnectionTimeout(5000)
                    .createSocket(url)
                    .addListener(new WebSocketAdapter() {
                        public void onTextMessage(WebSocket websocket, String message) {
                            Message message1 = new Message();
                            messageHandler.onTextMessage(message1);
                        }

                        @Override
                        public void onError(WebSocket websocket, WebSocketException cause) throws Exception {
                            Message message1 = new Message();
                            messageHandler.onError(message1);
                        }
                    })
                    .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE)
                    .connect();

            SubscribeRequest request = new SubscribeRequest(appKey,secret,group);

            webSocket.sendText(gson.toJson(request));

        }catch (OpeningHandshakeException e) {
            // A violation against the WebSocket protocol was detected
            // during the opening handshake.
        } catch (HostnameUnverifiedException e) {
            // The certificate of the peer does not match the expected hostname.
        } catch (WebSocketException e) {
            // Failed to establish a WebSocket connection.
        } catch (Exception e){

        }

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
