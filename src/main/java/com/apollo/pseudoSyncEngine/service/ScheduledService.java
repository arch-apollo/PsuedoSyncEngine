package com.apollo.pseudoSyncEngine.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Component
public class ScheduledService {

    @Scheduled(cron = "${file.download.cron}")
    public void checkDownloadQueue() {
        System.out.println("Scheduled task executed at " + System.currentTimeMillis());
    }

}
