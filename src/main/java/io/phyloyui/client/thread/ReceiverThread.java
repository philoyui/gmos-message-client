package io.phyloyui.client.thread;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neovisionaries.ws.client.*;
import io.phyloyui.client.common.SignUtils;
import io.phyloyui.client.conf.SubscribeConfig;
import io.phyloyui.client.domain.Message;
import io.phyloyui.client.domain.ResponseEntity;
import io.phyloyui.client.exp.GmosException;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by yangyu-ds on 2018/9/12.
 */
public class ReceiverThread implements Runnable{

    /**
     * WebSocket链接器
     */
    private WebSocket webSocket;

    /**
     * 上次链接时间
     */
    private long lastConnectedTime;

    /**
     * 订阅配置
     */
    private SubscribeConfig subscribeConfig;


    private Gson gson = new GsonBuilder().create();

    public ReceiverThread(SubscribeConfig subscribeConfig) {
        this.subscribeConfig = subscribeConfig;
    }

    @Override
    public void run() {

        try {

            String token = fetchToken(subscribeConfig);
            String webSocketUrl = buildWebSocketUrl(token);

            webSocket = new WebSocketFactory()
                    .setConnectionTimeout(5000)
                    .createSocket(webSocketUrl)
                    .addListener(new WebSocketAdapter() {

                        @Override
                        public void onTextMessage(WebSocket websocket, String response) {
                            Message message = new Message();
                            message.setContent(response);
                            subscribeConfig.getMessageHandler().onTextMessage(message);
                        }

                        @Override
                        public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
                            System.err.println("订阅平台消息网关连接已断开...");
                            reconnect();
                        }

                        @Override
                        public void onConnectError(WebSocket websocket, WebSocketException exception) throws Exception {
                            super.onConnectError(websocket, exception);
                            reconnect();
                        }


                    })
                    .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE)
                    .connect();

                    lastConnectedTime = System.currentTimeMillis();

        } catch (GmosException e){
            System.err.println(e.getMessage());
            reconnect();
        }catch (Exception e){
            reconnect();
        }

    }

    private String buildWebSocketUrl(String token) {
        return subscribeConfig.getSubscribeUrl() + "?token=" + token;
    }


    /**
     *
     * 使用appkey和secret请求换取token
     *
     * @param subscribeConfig
     * @return
     */
    private String fetchToken(SubscribeConfig subscribeConfig) {

        String tokenObtainUrl = buildTokenUrl(subscribeConfig);

        String responseContent = requestToken(subscribeConfig, tokenObtainUrl);

        ResponseEntity<String> responseEntity = gson.fromJson(responseContent, ResponseEntity.class);
        if(responseEntity.isSuccessResponse()){
            return responseEntity.getObject();
        }else{
            throw new GmosException(responseEntity.getMessage());
        }

    }


    /**
     * 从消息网关获取Token
     * @param subscribeConfig
     * @return
     */
    private String requestToken(SubscribeConfig subscribeConfig, String tokenObtainUrl) {
        String responseContent = "";

        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(tokenObtainUrl);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("appKey", subscribeConfig.getAppKey()));
            nameValuePairs.add(new BasicNameValuePair("groupName", subscribeConfig.getGroup()));
            nameValuePairs.add(new BasicNameValuePair("version", subscribeConfig.getVersion()));
            nameValuePairs.add(new BasicNameValuePair("timestamp", String.valueOf(System.currentTimeMillis())));
            nameValuePairs.add(new BasicNameValuePair("sign", signRequest(subscribeConfig)));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            CloseableHttpResponse response = httpclient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            responseContent= EntityUtils.toString(httpEntity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseContent;
    }


    /**
     *
     * 构建Token获取链接
     *
     * @param subscribeConfig
     * @return
     */
    private String buildTokenUrl(SubscribeConfig subscribeConfig) {
        String subscribeUrl = subscribeConfig.getSubscribeUrl();

        if(subscribeUrl==null || "".equalsIgnoreCase(subscribeUrl)){
            throw new GmosException("请输入subscribeUrl");
        }

        return subscribeUrl.replace("ws://", "http://").replace("/endpoints", "/router/ws/token");
    }

    /**
     * 为请求签名
     * @param subscribeConfig
     * @return
     */
    private String signRequest(SubscribeConfig subscribeConfig) {
        Map<String,String> parameters = new TreeMap<String,String>();
        parameters.put("appKey",subscribeConfig.getAppKey());
        parameters.put("groupName",subscribeConfig.getGroup());
        parameters.put("version",subscribeConfig.getVersion());
        parameters.put("timestamp",String.valueOf(System.currentTimeMillis()));
        return SignUtils.sign(parameters,subscribeConfig.getSecret());
    }


    /**
     * 重新连接
     */
    private void reconnect() {

        while(true){
            try {
                if (System.currentTimeMillis() - lastConnectedTime > 20000){
                    this.webSocket.recreate().connect();
                    lastConnectedTime = System.currentTimeMillis();
                    break;
                }
            } catch (Exception e) {
                System.err.println("订阅平台消息网关失败，请确认应用参数是否正确...");
                lastConnectedTime = System.currentTimeMillis();
            }
        }

    }

}
