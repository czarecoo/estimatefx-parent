package com.czareg.service.shared;

import java.io.IOException;
import java.net.SocketException;

public final class NetworkUtils {
    private NetworkUtils() {
    }

    public static boolean exceptionCausedBySocketClosing(Throwable throwable) {
        if (throwable == null) {
            return false;
        }
        return throwable instanceof SocketException && "Socket closed".equalsIgnoreCase(throwable.getMessage());
    }

    public static boolean exceptionCausedByCancelingEventSource(Throwable throwable) {
        if (throwable == null) {
            return false;
        }
        return throwable instanceof IOException && "Canceled".equalsIgnoreCase(throwable.getMessage());
    }
}