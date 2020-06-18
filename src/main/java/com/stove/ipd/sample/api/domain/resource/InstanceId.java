package com.stove.ipd.sample.api.domain.resource;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/*
 * Project : sample
 * Class : com.stove.dcos.sample.api.domain.resource.InstanceId
 * Version : 2020-03-18 v0.0.1
 * Created by thyim on 2020-03-18.
 * *** 저작권 주의 ***
 */
@Getter
@Setter
public class InstanceId implements Serializable {

  private static final long serialVersionUID = 1646705716749720949L;

  private String instanceId;
  private String tenantId;

  public InstanceId() {}

  public InstanceId(String instanceId, String farmId) {
    this.instanceId = instanceId;
    this.tenantId = tenantId;
  }

  @Override
  public boolean equals(Object o) {
    return ((o instanceof InstanceId)
        && instanceId == ((InstanceId) o).getInstanceId()
        && tenantId == ((InstanceId) o).getTenantId());
  }

  @Override
  public int hashCode() {
    return instanceId.hashCode() + tenantId.hashCode();
  }
}
