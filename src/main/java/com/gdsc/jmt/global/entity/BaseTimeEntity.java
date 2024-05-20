package com.gdsc.jmt.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseTimeEntity {
    @Column(name = "created_time", nullable = false, updatable = false)
    public LocalDateTime createdTime;

    @Column(name = "modified_time", nullable = false, updatable = true)
    public LocalDateTime modifiedTime;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdTime = now;
        this.modifiedTime = now;
    }

    @PreUpdate
    public void preUpdate() {
        LocalDateTime now = LocalDateTime.now();
        this.modifiedTime = now;
    }
}
