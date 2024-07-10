package com.timeOrganizer.security.config;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.util.io.pem.PemWriter;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Logger;

public class ECDSAKeyGenerator {
    private static final String PRIVATE_KEY_FILEPATH = "ec-private-key.pem";
    private static final String PUBLIC_KEY_FILEPATH = "ec-public-key.pem";

    public static PrivateKey readPrivateKey() {
        try (FileReader fileReader = new FileReader(PRIVATE_KEY_FILEPATH);
            PemReader pemReader = new PemReader(fileReader)) {

            PemObject pemObject = pemReader.readPemObject();
            byte[] keyBytes = pemObject.getContent();

            KeyFactory keyFactory;
            keyFactory = KeyFactory.getInstance("ECDSA", "BC");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            return keyFactory.generatePrivate(keySpec);
        } catch (FileNotFoundException e) {

            LOGGER.severe("File Not Found Exception: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            LOGGER.severe("IO Exception: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.severe("No Such Algorithm Exception: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            LOGGER.severe("No Such Provider Exception: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            LOGGER.severe("Invalid Key SpecException: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static PublicKey readPublicKey()  {
        try (FileReader fileReader = new FileReader(PUBLIC_KEY_FILEPATH);
             PemReader pemReader = new PemReader(fileReader)) {

            PemObject pemObject = pemReader.readPemObject();
            byte[] keyBytes = pemObject.getContent();

            KeyFactory keyFactory = KeyFactory.getInstance("ECDSA", "BC");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            return keyFactory.generatePublic(keySpec);
        } catch (FileNotFoundException e) {
            LOGGER.severe("File Not Found Exception: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            LOGGER.severe("IO Exception: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.severe("No Such Algorithm Exception: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            LOGGER.severe("No Such Provider Exception: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            LOGGER.severe("Invalid Key SpecException: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void generateAuthKeys() {
        Security.addProvider(new BouncyCastleProvider());
        String curveName = "secp384r1";
        KeyPair keyPair;
        createDirectoriesIfNotExist();
        try {
            keyPair = generateECDSAKeyPair(curveName);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) {
            LOGGER.severe("Error generating ECDSA key pair: " + e.getMessage());
            throw new RuntimeException(e);
        }
        try {
            ClassPathResource privateKeyResource = new ClassPathResource(PRIVATE_KEY_FILEPATH);
            ClassPathResource publicKeyResource = new ClassPathResource(PUBLIC_KEY_FILEPATH);
            writePemFile(keyPair.getPrivate(), "SECRET KEY", privateKeyResource.getFile().getPath());
            LOGGER.info("Private key written successfully.");
            writePemFile(keyPair.getPublic(), "PUBLIC KEY", publicKeyResource.getFile().getPath());
            LOGGER.info("Public key written successfully.");
        } catch (Exception e) {
            LOGGER.severe("Error writing keys to PEM files: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static KeyPair generateECDSAKeyPair(String curveName) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec(curveName);
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");
        keyPairGenerator.initialize(ecSpec, new SecureRandom());
        return keyPairGenerator.generateKeyPair();
    }

    private static void writePemFile(Key key, String objectName, String fileName) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(fileName);
             OutputStreamWriter osw = new OutputStreamWriter(fos)) {
            PemWriter writer = new PemWriter(osw);
            PemObject pemObject = new PemObject(objectName, key.getEncoded());
            writer.writeObject(pemObject);
            writer.flush();
            writer.close();
        }
    }
    private static void createDirectoriesIfNotExist() {
        File privateKeyFile = new File(PRIVATE_KEY_FILEPATH);
        File publicKeyFile = new File(PUBLIC_KEY_FILEPATH);

        if (!privateKeyFile.getParentFile().exists()) {
            privateKeyFile.getParentFile().mkdirs();
        }

        if (!publicKeyFile.getParentFile().exists()) {
            publicKeyFile.getParentFile().mkdirs();
        }
    }
    private static final Logger LOGGER = Logger.getLogger(ECDSAKeyGenerator.class.getName());
}
