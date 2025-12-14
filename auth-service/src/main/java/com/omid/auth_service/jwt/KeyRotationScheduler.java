package com.omid.auth_service.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Configuration
public class KeyRotationScheduler {
    @Autowired
    private KeyManager keyManager;

    @Scheduled(cron = "0 0 */12 * * *")  // هر 12 ساعت یکبار
    public void scheduleRotation() throws Exception {
        keyManager.rotateKeys();
        System.out.println("Rotated RSA keys successfully!");
    }

}
