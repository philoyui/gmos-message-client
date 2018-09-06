package io.phyloyui.client;

import com.neovisionaries.ws.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientTest {

    public static void main(String[] args) throws Exception {
        WebSocket ws = connect();

        BufferedReader in = getInput();

        String text;

        while ((text = in.readLine()) != null) {
            if (text.equals("exit")) {
                break;
            }

            ws.sendText(text);
        }

        ws.disconnect();
    }


    private static WebSocket connect() throws IOException, WebSocketException {
        return new WebSocketFactory()
                .setConnectionTimeout(5000)
                .createSocket("ws://echo.websocket.org")
                .addListener(new WebSocketAdapter() {
                    public void onTextMessage(WebSocket websocket, String message) {
                        System.out.println(message);
                    }
                })
                .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE)
                .connect();
    }


    private static BufferedReader getInput() throws IOException {
        return new BufferedReader(new InputStreamReader(System.in));
    }


}
