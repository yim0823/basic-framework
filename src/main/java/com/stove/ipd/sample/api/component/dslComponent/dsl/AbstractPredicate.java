package com.stove.ipd.sample.api.component.dslComponent.dsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;
import com.stove.ipd.sample.api.component.dslComponent.domain.SearchCriteria;
import com.stove.ipd.sample.utils.enums.SearchOperation;

/*
 * Project : sample
 * Class : com.stove.dcos.sample.api.component.dslComponent.dsl.AbstractPredicate
 * Version : 2020-03-18 v0.0.1
 * Created by thyim on 2020-03-18.
 * *** 저작권 주의 ***
 */
public class AbstractPredicate {

  private final Class<? extends EntityPathBase> type;
  private final SearchCriteria searchCriteria;
  private String name;

  public AbstractPredicate(
      Class<? extends EntityPathBase> type, String name, SearchCriteria searchCriteria) {
    this.searchCriteria = searchCriteria;
    this.type = type;
    this.name = name;
  }

  public BooleanExpression getPredicate() {

    PathBuilder<?> entityPath = new PathBuilder<Object>(type, name);
    StringPath path = entityPath.getString(searchCriteria.getKey());

    return getBooleanExpression(path, searchCriteria.getOperation(), searchCriteria.getValue());
  }

  private BooleanExpression getBooleanExpression(
          StringPath stringPath, SearchOperation operation, Object value) {

    switch (operation) {
      case EQUALITY:
        return stringPath.equalsIgnoreCase(String.valueOf(value));
      case NEGATION:
        return stringPath.notEqualsIgnoreCase(String.valueOf(value));
      case GREATER_THAN:
        return stringPath.gt(String.valueOf(value));
      case LESS_THAN:
        return stringPath.lt(String.valueOf(value));
      case LIKE:
        return stringPath.likeIgnoreCase(String.valueOf(value));
      case STARTS_WITH:
        return stringPath.startsWithIgnoreCase(String.valueOf(value));
      case ENDS_WITH:
        return stringPath.endsWithIgnoreCase(String.valueOf(value));
      case CONTAINS:
        return stringPath.containsIgnoreCase(String.valueOf(value));
      default:
        return null;
    }
  }
}
