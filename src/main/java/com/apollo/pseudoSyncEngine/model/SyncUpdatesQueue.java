package com.apollo.pseudoSyncEngine.model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sync_updates_queue")
public class SyncUpdatesQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "facility_id", nullable = false)
    private String facilityId;

    @Column(name = "server_id", nullable = true)
    private String serverId;

    @Column(name = "file_type", nullable = false)
    private String fileType;

    @Column(name = "exception", nullable = true)
    private String exception;

    @Column(name = "date_created", nullable = true)
    private Date dateCreated;

    @Column(name = "last_updated", nullable = true)
    private Date lastUpdated;
}
