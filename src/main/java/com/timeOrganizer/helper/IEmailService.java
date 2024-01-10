package com.timeOrganizer.helper;

public interface IEmailService {
    void sendEmail(String to, String subject, String body);
}
