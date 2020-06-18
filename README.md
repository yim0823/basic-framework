# Java Common Framework
***인프라플랫폼개발팀에서 사용할 공통 플레임워크*** 로 Web 기반 Java application framework 이다. 

## Intellij common plugins
 - Lombok
 - .ignore
 - RequestMapper
 - Grazie
 - QAPlug-Checkstyle
 - QAPlug-PMD
 - QAPlug-FindBugs
 - google-java-formatter

## Required Settings On Intellij
 - Code header : Settings > File and Code Templates > Includes > File Header 상에 다음을 기입한다.
 ```
 /*
  * Project : ${PROJECT_NAME}
  * Class : ${PACKAGE_NAME}.${NAME}
  * Version : ${DATE} v0.0.1
  * Created by ${USER} on ${DATE}.
  * *** 저작권 주의 ***
  */
 ```
 - Querydsl : 간혹 querydsl 도메인(Q*)를 못 찾는 경우가 발생, 다음을 설정한다.
   - File > Project Structure > Modules > 해당 프로젝트 하위에 main 클릭 > generated folder 우클릭 > Sources 선택

## Environments 
 - Java 11
 - Spring-boot-gradle.2.2.6.RELEASE
 - Gradle 6.x
 - JPA 2.2.5.RELEASE
 - QueryDSL
 - Hikari cp
 - MariaDB 10.5
 - Undertow
 - Http Client - RestTemplate
 - (Zipkin & Seuth 2.1.2.RELEASE)
 - (Kafka 2.2.7.RELEASE)
 - Swagger
 - Spring boot admin & Actuator
 - Prometheus 
 
## Applied Functions


