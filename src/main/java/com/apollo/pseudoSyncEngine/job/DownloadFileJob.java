package com.apollo.pseudoSyncEngine.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.apollo.pseudoSyncEngine.service.FileService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Component
public class DownloadFileJob {

    @Autowired
    private FileService fileService;

    @Scheduled(cron = "${file.download.cron}")
    public void checkDownloadQueue() {
        log.info("Check Download Queue task executed at " + System.currentTimeMillis());
        fileService.tryUpload();
    }

}
