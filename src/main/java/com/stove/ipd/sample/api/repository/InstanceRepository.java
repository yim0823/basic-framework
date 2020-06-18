package com.stove.ipd.sample.api.repository;

import com.stove.ipd.sample.api.domain.resource.Instance;
import com.stove.ipd.sample.api.domain.resource.InstanceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Project : sample
 * Class : com.stove.dcos.sample.api.repository.InstanceRepository
 * Version : 2020-03-18 v0.0.1
 * Created by thyim on 2020-03-18.
 * *** 저작권 주의 ***
 */
@Repository
public interface InstanceRepository extends JpaRepository<Instance, InstanceId> {
}
