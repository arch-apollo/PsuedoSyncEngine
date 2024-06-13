package com.apollo.pseudoSyncEngine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apollo.pseudoSyncEngine.model.SyncUpdatesQueue;

public interface SyncUpdatesQueueRepository extends JpaRepository <SyncUpdatesQueue, Long> {

    List<SyncUpdatesQueue> findByStatus(String status);
    
}
