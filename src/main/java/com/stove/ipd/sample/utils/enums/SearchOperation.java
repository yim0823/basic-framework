package com.stove.ipd.sample.utils.enums;

/*
 * Project : sample
 * Class : com.stove.dcos.sample.utils.enums.SearchOperation
 * Version : 2020-03-18 v0.0.1
 * Created by thyim on 2020-03-18.
 * *** 저작권 주의 ***
 */
public enum SearchOperation {
  EQUALITY,
  NEGATION,
  GREATER_THAN,
  LESS_THAN,
  LIKE,
  STARTS_WITH,
  ENDS_WITH,
  CONTAINS;

  public static final String[] SIMPLE_OPERATION_SET = {":", "!", ">", "<", "~"};

  public static final String OR_PREDICATE_FLAG = "|";
  public static final String AND_PREDICATE_FLAG = "&";

  public static final String ZERO_OR_MORE_REGEX = "*";

  public static SearchOperation getSimpleOperation(final char input) {
    switch (input) {
      case ':':
        return EQUALITY;
      case '!':
        return NEGATION;
      case '>':
        return GREATER_THAN;
      case '<':
        return LESS_THAN;
      case '~':
        return LIKE;
      default:
        return null;
    }
  }
}
