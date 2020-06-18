package com.stove.ipd.sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

/*
 * Project : sample
 * Class : com.stove.dcos.sample.config.AuditConfig
 * Version : 2020-03-18 v0.0.1
 * Created by thyim on 2020-03-18.
 * *** 저작권 주의 ***
 */
@Configuration
@EnableJpaAuditing(
    auditorAwareRef = "auditorProvider") // JPA Auditing - Common Entity 의 생성시간, 수정시간 자동화
public class AuditConfig {

  @Bean
  public AuditorAware<String> auditorProvider() {
    return () -> Optional.ofNullable("It was changed by JpaAuditingConfig.");
  }

  /*
   * TODO:
   *   Spring security 를 사용한다면 SecurityContextHolder 에서 Authentication 객체로부터 UserDto 정보를 가져올 수 있을 것이고
   *   Token 기반의 인증을 사용한다면 일반적으로 Token 을 파싱한 결과를 Thread local 에 담아둘 것이므로
   *   Thread local 로부터 사용자 정보를 가져오는 구현을 아래에서 구현하면 될 것이다.
   *   if you are using spring security, SecurityContextHolder.getContext().getAuthentication().getName()
   */
  //  @Bean
  //  public AuditorAware<String> auditorProvider() {
  //    return new AuditorAware<String>() {
  //      @Override
  //      public Optional<String> getCurrentAuditor() {
  //
  //        if (Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
  //          return Optional.of("Unknown");
  //        } else {
  //          return Optional.of(SecurityContextHolder.getContext().getAuthentication().getName());
  //        }
  //      }
  //    };
  //  }
}
