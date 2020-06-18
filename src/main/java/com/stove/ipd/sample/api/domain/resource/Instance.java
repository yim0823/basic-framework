package com.stove.ipd.sample.api.domain.resource;

import com.stove.ipd.sample.api.domain.BaseEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

/*
 * Project : sample
 * Class : com.stove.dcos.sample.api.domain.resource.Instance
 * Version : 2020-03-18 v0.0.1
 * Created by thyim on 2020-03-18.
 * *** 저작권 주의 ***
 */
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "INSTANCE")
@IdClass(InstanceId.class)
@Audited(withModifiedFlag = false)
public class Instance extends BaseEntity {

  @Id
  @Column(name = "INSTANCE_ID", nullable = false)
  private String instanceId;

  @Id
  @Column(name = "TENANT_ID", nullable = false)
  private String tenantId;

  @Column(name = "FLAVOR_ID")
  private String flavorId;

  @Column(name = "SITE_CD")
  private String siteCd;

  @Column(name = "IMAGE_ID")
  private String imageId;

  @Column(name = "SNAPSHOT_ID")
  private String snapshotId;

  @Column(name = "NAME")
  private String name;

  @Column(name = "UUID")
  private String uuid;

  @Column(name = "STATUS")
  private String status;

  @Column(name = "OS")
  private String os;

  @Column(name = "USER_ID")
  private long userId;

  @Column(name = "LAUNCH_DATE", columnDefinition = "TIMESTAMP")
  private LocalDateTime launchDate;

  @Column(name = "DBSTATE")
  @ColumnDefault("0")
  private String dbstate;

  @Builder
  public Instance(
      String instanceId,
      String tenantId,
      String flavorId,
      String siteCd,
      String imageId,
      String snapshotId,
      String name,
      String uuid,
      String status,
      String os,
      long userId,
      LocalDateTime launchDate,
      String dbstate,
      String createdBy,
      Date createdAt,
      String updatedBy,
      Date updatedAt) {

    this.instanceId = instanceId;
    this.tenantId = tenantId;
    this.flavorId = flavorId;
    this.siteCd = siteCd;
    this.imageId = imageId;
    this.snapshotId = snapshotId;
    this.name = name;
    this.uuid = uuid;
    this.status = status;
    this.os = os;
    this.userId = userId;
    this.launchDate = launchDate;
    this.dbstate = dbstate;
    this.setCreatedBy(createdBy);
    this.setCreatedAt(createdAt);
    this.setUpdatedBy(updatedBy);
    this.setUpdatedAt(updatedAt);
  }
}
