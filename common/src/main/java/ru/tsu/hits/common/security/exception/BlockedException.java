package ru.tsu.hits.common.security.exception;

public class BlockedException extends RuntimeException {

    public BlockedException() {
    }

    public BlockedException(String message) {
        super(message);
    }
}
