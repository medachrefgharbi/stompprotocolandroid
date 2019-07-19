package com.mohamedachrefgharbi.stompprotocolclientlibrary.stomp;

import android.util.Log;

import org.java_websocket.WebSocket;

import java.util.Map;

import okhttp3.OkHttpClient;

/**
 * Created by agharbi on 19/07/2019.
 */

public class Stomp {

    public static StompClient over(Class clazz, String uri) {
        return over(clazz, uri, null, null);
    }

    /**
     * @param clazz              class for using as transport
     * @param uri                URI to connect
     * @param connectHttpHeaders HTTP headers, will be passed with handshake query, may be null
     * @return StompClient for receiving and sending messages. Call #StompClient.connect
     */
    public static StompClient over(Class clazz, String uri, Map<String, String> connectHttpHeaders) {
        return over(clazz, uri, connectHttpHeaders, null);
    }

    /**
     * {@code webSocketClient} can accept the following type of clients:
     * <ul>
     * <li>{@code org.java_websocket.WebSocket}: cannot accept an existing client</li>
     * <li>{@code okhttp3.WebSocket}: can accept a non-null instance of {@code okhttp3.OkHttpClient}</li>
     * </ul>
     *
     * @param clazz              class for using as transport
     * @param uri                URI to connect
     * @param connectHttpHeaders HTTP headers, will be passed with handshake query, may be null
     * @param webSocketClient    Existing client that will be used to open the WebSocket connection, may be null to use default client
     * @return StompClient for receiving and sending messages. Call #StompClient.connect
     */
    public static StompClient over(Class clazz, String uri, Map<String, String> connectHttpHeaders, Object webSocketClient) {
        try {
            if (Class.forName("org.java_websocket.WebSocket") != null && clazz == WebSocket.class) {

                if (webSocketClient != null) {
                    throw new IllegalArgumentException("You cannot pass a webSocketClient with 'org.java_websocket.WebSocket'. use null instead.");
                }

                return createStompClient(new WebSocketsConnectionProvider(uri, connectHttpHeaders));
            }
        } catch (ClassNotFoundException e) {
            Log.e("Class Not Found Excep", e.getMessage(), e);
        }
        try {
            if (Class.forName("okhttp3.WebSocket") != null && clazz == okhttp3.WebSocket.class) {

                OkHttpClient okHttpClient = getOkHttpClient(webSocketClient);

                return createStompClient(new OkHttpConnectionProvider(uri, connectHttpHeaders, okHttpClient));
            }
        } catch (ClassNotFoundException e) {
            Log.e("Class Not Found Excep", e.getMessage(), e);
        }

        throw new RuntimeException("Not supported overlay transport: " + clazz.getName());
    }

    private static StompClient createStompClient(ConnectionProvider connectionProvider) {
        return new StompClient(connectionProvider);
    }

    private static OkHttpClient getOkHttpClient(Object webSocketClient) {
        if (webSocketClient != null) {
            if (webSocketClient instanceof OkHttpClient) {
                return (OkHttpClient) webSocketClient;
            } else {
                throw new IllegalArgumentException("You must pass a non-null instance of an 'okhttp3.OkHttpClient'. Or pass null to use a default websocket client.");
            }
        } else {
            // default http client
            return new OkHttpClient();
        }
    }
}
