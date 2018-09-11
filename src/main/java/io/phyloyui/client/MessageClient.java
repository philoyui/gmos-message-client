package io.phyloyui.client;

import com.neovisionaries.ws.client.*;
import io.phyloyui.client.domain.Message;

import java.util.List;
import java.util.Map;

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

    private WebSocket webSocket;

    /**
     * 上次链接时间
     */
    private long lastConnectedTime;

    public MessageClient(String appKey, String secret, String group) {
        this.appKey = appKey;
        this.secret = secret;
        this.group = group;
    }

    public void handleMessage(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public void connect(String url) {

        final String webSocketUrl = url + "?appKey=" + appKey;

        try {
            webSocket = new WebSocketFactory()
                    .setConnectionTimeout(5000)
                    .createSocket(webSocketUrl)
                    .addListener(new WebSocketAdapter() {
                        @Override
                        public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
                            super.onConnected(websocket, headers);
                        }

                        @Override
                        public void onTextMessage(WebSocket websocket, String response) {
                            Message message = new Message();
                            message.setContent(response);
                            messageHandler.onTextMessage(message);
                        }

                        @Override
                        public void onError(WebSocket websocket, WebSocketException cause) {
                            cause.printStackTrace();
                            System.out.println("On Error..." + cause.getLocalizedMessage());
                        }

                        @Override
                        public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
                            System.out.println("建立连接失败...");
                            reconnect();
                        }

                        @Override
                        public void onConnectError(WebSocket websocket, WebSocketException exception) throws Exception {
                            super.onConnectError(websocket, exception);
                            reconnect();
                        }
                    })
                    .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE);

        } catch (Exception e){
            System.out.println("异常中...");
        }

        webSocket.connectAsynchronously();

        lastConnectedTime = System.currentTimeMillis();
    }


    private void reconnect() {

        while(true){
            try {
                if (System.currentTimeMillis() - lastConnectedTime > 20000){
                    this.webSocket.recreate().connect();
                    lastConnectedTime = System.currentTimeMillis();
                    break;
                }
            } catch (Exception e) {
                System.out.println("重新连接中...");
                lastConnectedTime = System.currentTimeMillis();
            }
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
