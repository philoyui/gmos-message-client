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

    private WebSocket webSocket;

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

        String webSocketUrl = url + "?appKey=" + appKey;

        try {
            webSocket = new WebSocketFactory()
                    .setConnectionTimeout(5000)
                    .createSocket(webSocketUrl)
                    .addListener(new WebSocketAdapter() {

                        @Override
                        public void onTextMessage(WebSocket websocket, String response) {
                            Message message = new Message();
                            message.setContent(response);
                            messageHandler.onTextMessage(message);
                        }

                        @Override
                        public void onError(WebSocket websocket, WebSocketException cause) {
                            Message message = new Message();
                            message.setContent("竟然报错了");
                            messageHandler.onError(message);
                        }

                        @Override
                        public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
                            Message message = new Message();
                            message.setContent("服务器重连");
                            messageHandler.onError(message);
                        }

                        @Override
                        public void onConnectError(WebSocket websocket, WebSocketException exception) throws Exception {
                            super.onConnectError(websocket, exception);
                        }

                    })
                    .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE)
                    .connectAsynchronously();

            SubscribeRequest request = new SubscribeRequest(appKey,secret,group);

            webSocket.sendText(gson.toJson(request));

        } catch (Exception e){
            e.printStackTrace();
        }

    }

//    public void reconnect() {
//        if (webSocket != null && !webSocket.isOpen() && getConnectStatus() != ConnectStatus.CONNECTING) {
//            TimerTask mReconnectTimerTask = new TimerTask() {
//                @Override
//                public void run() {
//                    connect();
//                }
//            };
//            mReconnectTimer.schedule(mReconnectTimerTask, DEFAULT_SOCKET_RECONNECTINTERVAL);
//        }
//    }


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
