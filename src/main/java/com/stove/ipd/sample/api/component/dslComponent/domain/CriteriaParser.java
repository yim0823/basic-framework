package com.stove.ipd.sample.api.component.dslComponent.domain;

import com.google.common.base.Joiner;
import com.stove.ipd.sample.utils.enums.SearchOperation;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Project : sample
 * Class : com.stove.dcos.sample.api.component.dslComponent.domain.CriteriaParser
 * Version : 2020-03-18 v0.0.1
 * Created by thyim on 2020-03-18.
 * *** 저작권 주의 ***
 */
@Component
public class CriteriaParser {

  private static Map<String, Operator> ops;

  private static Pattern SpecCriteraRegex =
      Pattern.compile(
          "^(\\w+?)("
              + Joiner.on("|").join(SearchOperation.SIMPLE_OPERATION_SET)
              + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?)$");

  static {
    ops = Map.of("&", Operator.AND, "|", Operator.OR);
  }

  public List<SearchCriteria> parse(String searchParam) {

    List<SearchCriteria> searchCriterias = new ArrayList<>();

    Arrays.stream(searchParam.split("\\|"))
        .forEach(
            token -> {
              List<String> andString = Arrays.asList(token.split("&"));
              for (int i = 0; i < andString.size(); i++) {
                Matcher matcher = SpecCriteraRegex.matcher(andString.get(i));
                while (matcher.find()) {
                  SearchCriteria searchCriteria =
                      new SearchCriteria(
                          matcher.group(1),
                          matcher.group(2),
                          matcher.group(3),
                          matcher.group(4),
                          matcher.group(5));
                  if (i == 0) {
                    searchCriteria.setOrPredicate(true);
                  }
                  searchCriterias.add(searchCriteria);
                }
              }
            });

    return searchCriterias;
  }

  private enum Operator {
    OR(1),
    AND(2);
    final int precedence;

    Operator(int p) {
      precedence = p;
    }
  }
}
