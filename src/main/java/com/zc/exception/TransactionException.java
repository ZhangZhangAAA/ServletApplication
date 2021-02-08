package com.zc.exception;

public class TransactionException extends Exception {

    static final long serialVersionUID = 7818375896285090177L;

    public TransactionException() {
        this("commit操作未设置rollback!");
    }

    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionException(Throwable cause) {
        super(cause);
    }
}
