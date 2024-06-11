package com.apollo.pseudoSyncEngine.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Component
public class DownloadFileJob {

    @Scheduled(cron = "${file.download.cron}")
    public void checkDownloadQueue() {
        log.info("Check Download Queue task executed at " + System.currentTimeMillis());
    }

}
