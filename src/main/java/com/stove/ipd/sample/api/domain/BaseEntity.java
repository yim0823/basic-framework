package com.stove.ipd.sample.api.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

/*
 * Project : sample
 * Class : com.stove.dcos.sample.api.domain.BaseEntity
 * Version : 2020-03-18 v0.0.1
 * Created by thyim on 2020-03-18.
 * *** 저작권 주의 ***
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
public abstract class BaseEntity {

  @CreatedBy
  @Column(name = "CREATED_BY", nullable = false, updatable = false)
  private String createdBy;

  @LastModifiedBy
  @Column(name = "UPDATED_BY", nullable = false)
  private String updatedBy;

  /*
   * 나라에 따라 시간이 달리 적용되어야 할 경우,
   */
  @CreatedDate
  @Column(nullable = false)
  private Date createdAt;

  @LastModifiedDate
  @Column(nullable = false)
  private Date updatedAt;

  @PrePersist
  public void onPrePersist() {
    this.createdAt = Date.from(ZonedDateTime.now(ZoneOffset.UTC).toInstant());
    this.updatedAt = this.createdAt;
  }

  @PreUpdate
  public void onPreUpdate() {
    this.updatedAt = Date.from(ZonedDateTime.now(ZoneOffset.UTC).toInstant());
  }
}
