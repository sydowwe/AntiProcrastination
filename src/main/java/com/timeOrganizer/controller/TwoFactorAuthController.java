package com.timeOrganizer.controller;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.timeOrganizer.helper.Validation;
import com.timeOrganizer.model.dto.request.TwoFactorAuthRequest;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.google.zxing.BarcodeFormat;

@RequestMapping("/code")
public class TwoFactorAuthController extends MyController{
    private final GoogleAuthenticator gAuth;
    @Autowired
    public TwoFactorAuthController(GoogleAuthenticator gAuth) {
        this.gAuth = gAuth;
    }

    @SneakyThrows
    @GetMapping("/generate/{email}")
    public void generate(@PathVariable String email, HttpServletResponse response) {
        final GoogleAuthenticatorKey key = gAuth.createCredentials(email);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("my-demo", email, key);
        BitMatrix bitMatrix = qrCodeWriter.encode(otpAuthURL, BarcodeFormat.QR_CODE, 200, 200);
        ServletOutputStream outputStream = response.getOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        outputStream.close();
    }

    @PostMapping("/validate/key")
    public Validation validateKey(@RequestBody TwoFactorAuthRequest request) {
        return new Validation(gAuth.authorizeUser(request.getEmail(), request.getCode()),"2FA validation successful");
    }
}
