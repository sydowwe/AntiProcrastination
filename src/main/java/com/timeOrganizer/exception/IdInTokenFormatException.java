package com.timeOrganizer.exception;

public class IdInTokenFormatException extends NumberFormatException{
    public IdInTokenFormatException(String wrongId) {
        super("Id in token is missing or cant be converted to number" + wrongId);
    }
}
