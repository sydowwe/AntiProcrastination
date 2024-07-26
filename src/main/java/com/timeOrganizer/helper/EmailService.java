package com.timeOrganizer.helper;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailService implements IEmailService {
    private final JavaMailSender javaMailSender;
    @Value("${page.url}")
    private String PAGE_URL;
    @Value("${app.name}")
    private String APP_NAME;

	public void sendSimpleEmail(String to, String subject, String text)
	{
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		javaMailSender.send(message);
	}
    @Override
    public void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);

            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String generateForgottenPasswordEmail(String tempPassword) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Forgotten Password</title>\n" +
                "</head>\n" +
                "<body style=\"font-family: Arial, sans-serif;user-select: none;\">\n" +
                "    <div style=\"max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ccc; border-radius: 5px;\">\n\n" +
                "        <h2 style=\"color: #333;\">Forgotten Password</h2>\n" +
                "        <p>Hello,</p>\n" +
                "        <p>We received a request to reset your password. Please use this temporary password to log in and update your password as soon as possible. If you didn't request a password reset, please ignore this email.</p>\n" +
                "       <div style=\" margin-bottom: 25px; padding: 10px;\">\n" +
                "           <span><b>Your temporary password is:</b></span>\n" +
                "           <span style=\"user-select: all;background-color: #d5d5d5; padding: 10px; width: fit-content; border-radius: 5px; font-size: 16px; font-weight: bold; color: #2478ff;\">\n" +
                            tempPassword + "</span>\n" +
                "       </div>" +
                "       <div style=\"text-align: center; width: 100%\"><a href=\"" + PAGE_URL + "\" style=\"background-color: #5d07df; padding: 10px; border-radius: 5px; font-size: 16px; font-weight: bold; color: #EEE;\">" +
                "Go to " + APP_NAME + " login" +
                "   </a></div>\n" +
                "        <p>Best regards,</p>\n" +
                "       <p>&emsp;Your <b>" + APP_NAME + "</b> team</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }
}
