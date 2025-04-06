package com.gucardev.springbootstartertemplate.infrastructure.filter.pbkeyurl;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import jakarta.annotation.PostConstruct;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class RSAKeyGenerator {

    private PublicKey publicKey;
    private PrivateKey privateKey;

    @PostConstruct
    public void init() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        this.publicKey = keyPair.getPublic();
        this.privateKey = keyPair.getPrivate();
    }

    public String getEncodedPublicKey() {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }
}