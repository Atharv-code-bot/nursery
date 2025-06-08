package com.sakshi.nursery.service;

import com.sakshi.nursery.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PushNotificationService {

    private Logger logger = LoggerFactory.getLogger(PushNotificationService.class);
    @Autowired
    private FCMService fcmService;
    public PushNotificationService(FCMService fcmService) {
        this.fcmService = fcmService;
    }
    public void sendPushNotificationToToken(String token, Product product) {
        try {
            fcmService.sendMessageToToken(token,product);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

}