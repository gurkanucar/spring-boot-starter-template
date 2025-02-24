package com.gucardev.springbootstartertemplate.infrastructure.config.firebase;

import com.gucardev.springbootstartertemplate.infrastructure.config.firebase.dto.MulticastNotificationRequest;
import com.gucardev.springbootstartertemplate.infrastructure.config.firebase.dto.NotificationRequest;
import com.gucardev.springbootstartertemplate.infrastructure.config.firebase.dto.TopicNotificationRequest;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FirebaseMessagingService {

    @Value("${app.firebase-configuration-file}")
    private String firebaseConfigPath;

    @PostConstruct
    public void initialize() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream());

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(googleCredentials)
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }

    public String sendMessageToToken(NotificationRequest request) throws FirebaseMessagingException {
        Message message = Message.builder()
                .setNotification(Notification.builder()
                        .setTitle(request.getTitle())
                        .setBody(request.getBody())
                        .build())
                .putAllData(request.getData())
                .setToken(request.getRecipientToken())
                .build();

        return FirebaseMessaging.getInstance().send(message);
    }

    public BatchResponse sendMessageToTokens(MulticastNotificationRequest request) throws FirebaseMessagingException {
        MulticastMessage message = MulticastMessage.builder()
                .setNotification(Notification.builder()
                        .setTitle(request.getTitle())
                        .setBody(request.getBody())
                        .build())
                .putAllData(request.getData())
                .addAllTokens(request.getRecipientTokens())
                .build();

        return FirebaseMessaging.getInstance().sendEachForMulticast(message);
    }

    public String sendMessageToTopic(TopicNotificationRequest request) throws FirebaseMessagingException {
        Message message = Message.builder()
                .setNotification(Notification.builder()
                        .setTitle(request.getTitle())
                        .setBody(request.getBody())
                        .build())
                .putAllData(request.getData())
                .setTopic(request.getTopic())
                .build();

        return FirebaseMessaging.getInstance().send(message);
    }

}

