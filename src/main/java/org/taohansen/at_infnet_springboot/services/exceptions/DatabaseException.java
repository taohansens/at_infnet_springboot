package org.taohansen.at_infnet_springboot.services.exceptions;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String msg) {
        super(msg);
    }
}
