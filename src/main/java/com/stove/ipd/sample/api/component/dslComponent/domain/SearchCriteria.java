package com.stove.ipd.sample.api.component.dslComponent.domain;

import com.stove.ipd.sample.utils.enums.SearchOperation;
import lombok.Getter;
import lombok.Setter;

/*
 * Project : sample
 * Class : com.stove.dcos.sample.api.component.dslComponent.domain.SearchCriteria
 * Version : 2020-03-18 v0.0.1
 * Created by thyim on 2020-03-18.
 * *** 저작권 주의 ***
 */
@Setter
@Getter
public class SearchCriteria {

  private String key;
  private SearchOperation operation;
  private Object value;
  private boolean orPredicate;

  public SearchCriteria() {}

  public SearchCriteria(final String key, final SearchOperation operation, final Object value) {
    super();
    this.key = key;
    this.operation = operation;
    this.value = value;
  }

  public SearchCriteria(
      final String orPredicate,
      final String key,
      final SearchOperation operation,
      final Object value) {
    super();
    this.orPredicate = orPredicate != null && orPredicate.equals(SearchOperation.OR_PREDICATE_FLAG);
    this.key = key;
    this.operation = operation;
    this.value = value;
  }

  public SearchCriteria(String key, String operation, String prefix, String value, String suffix) {
    SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
    if (op != null) {
      if (op == SearchOperation.EQUALITY) { // the operation may be complex operation
        final boolean startWithAsterisk =
            prefix != null && prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
        final boolean endWithAsterisk =
            suffix != null && suffix.contains(SearchOperation.ZERO_OR_MORE_REGEX);

        if (startWithAsterisk && endWithAsterisk) {
          op = SearchOperation.CONTAINS;
        } else if (startWithAsterisk) {
          op = SearchOperation.ENDS_WITH;
        } else if (endWithAsterisk) {
          op = SearchOperation.STARTS_WITH;
        }
      }
    }
    this.key = key;
    this.operation = op;
    this.value = value;
  }
}
