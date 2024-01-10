package com.timeOrganizer.exception;

import java.io.IOException;

public class QrCode2FAGenerationException extends IOException {
    public QrCode2FAGenerationException(Exception e) {
        super(e.getMessage(), e.getCause());
    }
}
