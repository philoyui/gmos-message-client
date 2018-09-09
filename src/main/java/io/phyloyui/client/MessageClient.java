package io.phyloyui.client;

import com.neovisionaries.ws.client.*;
import io.phyloyui.client.domain.ConnectStatus;
import io.phyloyui.client.domain.Message;

import java.io.IOException;
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

    private ConnectStatus mConnectStatus = ConnectStatus.CONNECT_DISCONNECT;

    private void setConnectStatus(ConnectStatus connectStatus) {
        mConnectStatus = connectStatus;
    }

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
                        public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
                            setConnectStatus(ConnectStatus.CONNECT_SUCCESS);
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
                            System.out.println("On Error...");
                        }

                        @Override
                        public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
                            setConnectStatus(ConnectStatus.CONNECT_DISCONNECT);
                            System.out.println("连接断开...");
                            startToConnect();
                        }

                        @Override
                        public void onConnectError(WebSocket websocket, WebSocketException exception) throws Exception {
                            setConnectStatus(ConnectStatus.CONNECT_FAIL);
                            super.onConnectError(websocket, exception);
                        }
                    })
                    .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE);


        } catch (Exception e){
            System.out.println("异常中...");
        }

        startToConnect();
        setConnectStatus(ConnectStatus.CONNECTING);

    }

    private void startToConnect() {

        while(true){
            try {
                webSocket.recreate().connect();
                break;
            } catch (IOException e) {
                System.out.println("IO异常中...");
            } catch (WebSocketException e) {
                System.out.println("重新连接中...");
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public ConnectStatus getConnectStatus() {
        return mConnectStatus;
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
