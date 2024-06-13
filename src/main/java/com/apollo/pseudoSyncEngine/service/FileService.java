package com.apollo.pseudoSyncEngine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ssl.SslProperties.Bundles.Watch.File;
import org.springframework.stereotype.Service;

import com.apollo.pseudoSyncEngine.model.SyncUpdatesQueue;
import com.apollo.pseudoSyncEngine.repository.SyncUpdatesQueueRepository;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.*;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Slf4j
@Service
public class FileService {

    @Value("${file.status.check}")
    private String statusCheck;

    @Value("${file.status.change}")
    private String statusChange;

    @Value("${file.download.location}")
    private String sourceLocation;

    @Value("${file.upload.location.event}")
    private String destinationEvent;

    @Value("${file.upload.location.inventory}")
    private String destinationInventory;

    @Value("${file.upload.location.transaction}")
    private String destinationTransaction;

    @Autowired
    SyncUpdatesQueueRepository syncUpdatesQueueRepository;

    public void copyFile(String sourcePath, String destinationPath) throws IOException {
        Path source = Path.of(sourcePath);
        Path destination = Path.of(destinationPath);
        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
    }

    public void tryUpload(){
        log.info("Checking DB for possible uploads...");
        List <SyncUpdatesQueue> syncUpdatesQueues = syncUpdatesQueueRepository.findByStatus(statusCheck);
        log.info("Files to upload: {}", syncUpdatesQueues.size());
        if(syncUpdatesQueues.size() > 0){
            for(SyncUpdatesQueue syncUpdatesQueue : syncUpdatesQueues){
                DateFormat full = new SimpleDateFormat("yyyyMMddHHmmss");
                DateFormat year = new SimpleDateFormat("yyyy");
                DateFormat yearMonth = new SimpleDateFormat("yyyy-MM-dd");
                String[] parts = syncUpdatesQueue.getFileName().split("-");
                String type = parts[0];
                String facility = parts[1];
                Date created = syncUpdatesQueue.getDateCreated();
                String fileName = full.format(created).toString() + "-" + facility + "-" + type + ".csv";
                String destination = "";
                String source = sourceLocation + syncUpdatesQueue.getFileName();
                Path path = Paths.get(source);
                if (Files.notExists(path)) {
                    // log.info("File not found: {}", syncUpdatesQueue.getFileName());
                    // log.info("Full path: {}", path);
                    continue;
                }
                log.info("File type: {}", type);
                switch(type.toLowerCase()){
                    case "event":
                        destination = destinationEvent + "event/" + type + "/" + year.format(created).toString() + "/"
                            + yearMonth.format(created) + "/" + facility;
                        break;
                    case "inventory":
                    case "facility_inventory":
                    case "inventory_detail":
                        destination = destinationEvent + "inventory/" + type + "/" + year.format(created).toString() + "/"
                        + yearMonth.format(created) + "/" + facility;
                        break;
                    case "sales_order":
                    case "sales_order_item":
                    case "sales_order_adjustment":
                        destination = destinationEvent + "transaction/" + type + "/" + year.format(created).toString() + "/"
                        + yearMonth.format(created) + "/" + facility;
                        break;
                    default:
                        log.info("Skipping {}", syncUpdatesQueue.getFileName());
                        break;
                }
                if(!destination.isEmpty()){
                    try {
                        Files.createDirectories(Paths.get(destination));
                        copyFile(source, destination + "/" + fileName);
                        log.info("File {} successfully transferred.", fileName);
                        syncUpdatesQueue.setStatus(statusChange);
                        syncUpdatesQueueRepository.saveAndFlush(syncUpdatesQueue);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        log.info("Skipping {}", syncUpdatesQueue.getFileName());
                        continue;
                    }
    
                }
            }
        }
        log.info("Checking DONE!");
    }
}

