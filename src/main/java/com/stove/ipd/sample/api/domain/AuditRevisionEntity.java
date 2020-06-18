package com.stove.ipd.sample.api.domain;

import com.querydsl.core.annotations.QueryExclude;
import com.stove.ipd.sample.config.listener.AuditRevisionListener;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.*;

/*
 * Project : sample
 * Class : com.stove.dcos.sample.api.domain.AuditRevisionEntity
 * Version : 2020-03-18 v0.0.1
 * Created by thyim on 2020-03-18.
 * *** 저작권 주의 ***
 */
@Entity
@Table(name = "revinfo", schema = "audit")
@RevisionEntity(AuditRevisionListener.class)
@AttributeOverrides({
  @AttributeOverride(name = "timestamp", column = @Column(name = "REVTSTMP")),
  @AttributeOverride(name = "id", column = @Column(name = "REV"))
})
@Getter
@Setter
@QueryExclude
public class AuditRevisionEntity extends DefaultRevisionEntity {

  @Column(name = "username", nullable = false)
  private String userName;
}
