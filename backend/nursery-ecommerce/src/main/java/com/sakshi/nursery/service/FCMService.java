package com.sakshi.nursery.service;

import com.google.firebase.messaging.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sakshi.nursery.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.concurrent.ExecutionException;

@Service
public class FCMService {

    private final Logger logger = LoggerFactory.getLogger(FCMService.class);

    // Public method to call from your controller or event handler
    public void sendMessageToToken(String token, Product product) throws InterruptedException, ExecutionException {
        Message message = buildMessageToToken(token, product);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(message);
        String response = FirebaseMessaging.getInstance().sendAsync(message).get();
        logger.info("Sent message to token. Device token: " + token + ", Response: " + response + ", Payload: " + jsonOutput);
    }

    // Build the Firebase message
    private Message buildMessageToToken(String token, Product product) {
        String title = "New Product Added: " + product.getName();
        String body = "About:"+product.getDescription()+","+"Category:"+product.getCategory().getName();
        String topic = "New Product Added: " + product.getName();// Optional: used for collapseKey/threadId

        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        return Message.builder()
                .setToken(token)
                .setNotification(notification)
                .setAndroidConfig(getAndroidConfig(topic))
                .setApnsConfig(getApnsConfig(topic))
                .build();
    }

    // Android notification config
    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis())
                .setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder()
                        .setTag(topic)
                        .build())
                .build();
    }

    // iOS (APNs) notification config
    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder()
                .setAps(Aps.builder()
                        .setCategory(topic)
                        .setThreadId(topic)
                        .build())
                .build();
    }
}
