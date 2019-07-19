package com.mohamedachrefgharbi.stompprotocolclientlibrary.stomp;

import java.util.TreeMap;

/**
 * Created by agharbi on 19/07/2019.
 */

public class LifecycleEvent {

    public enum Type {
        OPENED, CLOSED, ERROR
    }

    private final LifecycleEvent.Type mType;

    //Nullable
    private Exception mException;

    //Nullable
    private String mMessage;

    private TreeMap<String, String> handshakeResponseHeaders = new TreeMap<>();

    public LifecycleEvent(LifecycleEvent.Type type) {
        mType = type;
    }

    public LifecycleEvent(LifecycleEvent.Type type, Exception exception) {
        mType = type;
        mException = exception;
    }

    public LifecycleEvent(LifecycleEvent.Type type, String message) {
        mType = type;
        mMessage = message;
    }

    public LifecycleEvent.Type getType() {
        return mType;
    }

    public Exception getException() {
        return mException;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setHandshakeResponseHeaders(TreeMap<String, String> handshakeResponseHeaders) {
        this.handshakeResponseHeaders = handshakeResponseHeaders;
    }

    public TreeMap<String, String> getHandshakeResponseHeaders() {
        return handshakeResponseHeaders;
    }
}
