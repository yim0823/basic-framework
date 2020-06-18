package com.stove.ipd.sample.api.component.dslComponent.dsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import com.stove.ipd.sample.api.component.dslComponent.domain.SearchCriteria;
import com.stove.ipd.sample.utils.enums.SearchOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/*
 * Project : sample
 * Class : com.stove.dcos.sample.api.component.dslComponent.dsl.GenericSpecificationsBuilder
 * Version : 2020-03-18 v0.0.1
 * Created by thyim on 2020-03-18.
 * *** 저작권 주의 ***
 */
public class GenericSpecificationsBuilder {

  private final List<SearchCriteria> params;

  public GenericSpecificationsBuilder() {
    this.params = new ArrayList<>();
  }

  public final GenericSpecificationsBuilder with(
      final String key, final String operation, final Object value) {
    return with(null, key, operation, value);
  }

  public final GenericSpecificationsBuilder with(
      final String precedenceIndicator,
      final String key,
      final String operation,
      final Object value) {
    params.add(
        new SearchCriteria(precedenceIndicator, key, SearchOperation.valueOf(operation), value));
    return this;
  }

  public final GenericSpecificationsBuilder with(SearchCriteria searchCriteria) {
    if (!ObjectUtils.isEmpty(searchCriteria)) {
      params.add(searchCriteria);
    }
    return this;
  }

  public final GenericSpecificationsBuilder with(List<SearchCriteria> params) {
    if (!CollectionUtils.isEmpty(params)) {
      this.params.addAll(params);
    }
    return this;
  }

  public BooleanExpression build(Class<? extends EntityPathBase> type, String name) {

    if (params.size() == 0) {
      return null;
    }

    final List<BooleanExpression> predicates =
        params.stream()
            .map(
                param -> {
                  AbstractPredicate predicate = new AbstractPredicate(type, name, param);
                  return predicate.getPredicate();
                })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

    BooleanExpression result = Expressions.asBoolean(true).isTrue();

    for (int idx = 0; idx < predicates.size(); idx++) {
      result =
          params.get(idx).isOrPredicate()
              ? result.or(predicates.get(idx))
              : result.and(predicates.get(idx));
    }

    return result;
  }
}
