package com.mohamedachrefgharbi.stompprotocolclientlibrary.stomp;

import io.reactivex.Flowable;

/**
 * Created by agharbi on 19/07/2019.
 */

public interface ConnectionProvider {
    /**
     * Subscribe this for receive stomp messages
     */
    Flowable<String> messages();

    /**
     * Sending stomp messages via you ConnectionProvider.
     * onError if not connected or error detected will be called, or onCompleted id sending started
     */
    Flowable<Void> send(String stompMessage);

    /**
     * Subscribe this for receive #LifecycleEvent events
     */
    Flowable<LifecycleEvent> getLifecycleReceiver();

    /**
     * Disconnects from server. This is basically a Callable.
     * Automatically emits Lifecycle.CLOSE
     */
    void disconnect();
}
