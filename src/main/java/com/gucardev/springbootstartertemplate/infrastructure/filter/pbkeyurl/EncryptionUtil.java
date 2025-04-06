package com.gucardev.springbootstartertemplate.infrastructure.filter.pbkeyurl;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class EncryptionUtil {

    public String decrypt(String encryptedData, PrivateKey privateKey) {
        try {
            // Handle URL safe Base64 characters
            encryptedData = encryptedData.replace(" ", "+");

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);

            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting data", e);
        }
    }
}