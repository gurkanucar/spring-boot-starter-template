package com.gucardev.springbootstartertemplate.infrastructure.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class EncryptionService {

    private final SecretKey encryptionKey;
    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 16;

    public EncryptionService(@Value("${encryption.secret}") String encryptionKeyString) {
        // The encryption key should be a Base64 encoded 256-bit key (32 bytes)
        byte[] decodedKey = Base64.getDecoder().decode(encryptionKeyString);
        this.encryptionKey = new SecretKeySpec(decodedKey, "AES");
    }

    public String encryptToken(String token) {
        try {
            // Generate a random IV
            byte[] iv = new byte[GCM_IV_LENGTH];
            java.security.SecureRandom secureRandom = new java.security.SecureRandom();
            secureRandom.nextBytes(iv);

            // Initialize Cipher for encryption
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
            cipher.init(Cipher.ENCRYPT_MODE, encryptionKey, parameterSpec);

            // Perform encryption
            byte[] tokenBytes = token.getBytes(StandardCharsets.UTF_8);
            byte[] cipherText = cipher.doFinal(tokenBytes);

            // Combine IV and ciphertext
            byte[] encryptedToken = new byte[iv.length + cipherText.length];
            System.arraycopy(iv, 0, encryptedToken, 0, iv.length);
            System.arraycopy(cipherText, 0, encryptedToken, iv.length, cipherText.length);

            // Encode with Base64
            return Base64.getEncoder().encodeToString(encryptedToken);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting JWT token", e);
        }
    }

    public String decryptToken(String encryptedToken) {
        try {
            // Decode from Base64
            byte[] decodedToken = Base64.getDecoder().decode(encryptedToken);

            // Extract IV and ciphertext
            byte[] iv = new byte[GCM_IV_LENGTH];
            byte[] cipherText = new byte[decodedToken.length - GCM_IV_LENGTH];
            System.arraycopy(decodedToken, 0, iv, 0, iv.length);
            System.arraycopy(decodedToken, iv.length, cipherText, 0, cipherText.length);

            // Initialize Cipher for decryption
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
            cipher.init(Cipher.DECRYPT_MODE, encryptionKey, parameterSpec);

            // Perform decryption
            byte[] decryptedBytes = cipher.doFinal(cipherText);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting JWT token", e);
        }
    }
}