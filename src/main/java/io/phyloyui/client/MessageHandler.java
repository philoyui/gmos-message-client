package io.phyloyui.client;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;

public interface MessageHandler {

    void onTextMessage(WebSocket websocket, String text);

    void onError(WebSocket websocket, WebSocketException cause);

}
