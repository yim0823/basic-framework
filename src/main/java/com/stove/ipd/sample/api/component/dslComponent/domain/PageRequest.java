package com.stove.ipd.sample.api.component.dslComponent.domain;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Sort;

/*
 * Project : sample
 * Class : com.stove.dcos.sample.api.component.dslComponent.domain.PageRequest
 * Version : 2020-03-18 v0.0.1
 * Created by thyim on 2020-03-18.
 * *** 저작권 주의 ***
 */
@Setter
@Getter
public class PageRequest {

  @ApiParam(value = "가져올 페이지")
  private int page;

  @ApiParam(value = "페이지의 크기")
  private int limit;

  @ApiParam(value = "정렬의 기준")
  private Sort.Direction sort;

  private String sortBy = "createdAt";

  public PageRequest() {
    this(0, 10, Sort.Direction.ASC);
  }

  public PageRequest(int page, int limit, Sort.Direction sort) {
    setPage(page);
    setLimit(limit);
    setSort(sort);
  }

  public void setPage(int page) {
    this.page = page <= 0 ? 1 : page;
  }

  public void setLimit(int limit) {
    int defaultSize = 10;
    int maxSize = 50;
    this.limit = limit > maxSize ? defaultSize : limit;
  }

  public void setSort(Sort.Direction sort) {
    if (ObjectUtils.isEmpty(sort)) {
      this.sort = Sort.Direction.ASC;
    } else {
      this.sort = sort;
    }
  }

  public org.springframework.data.domain.PageRequest of() {
    return org.springframework.data.domain.PageRequest.of(page - 1, limit, sort, sortBy);
  }
}
