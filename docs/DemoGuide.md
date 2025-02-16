# `Demo Guide`
임베디드 모드로 실행하기 위한 과정

- (1) application.yml
  ```yaml
    spring:
      config:
        activate:
          on-profile: demo
      datasource:
        driver-class-name: org.h2.Driver
        url: jdbc:h2:mem:testdb;MODE=MySQL;DB_CLOSE_DELAY=-1
        username: sa
        password: password
    ```
- (2) `src/main/resources` 내부에 `schema.sql` 작성
- (3) `build.gradle` 
  - `implementation 'com.h2database:h2'`
  - 참고) 프로필에 따라 분기처리 하도록 할 것.
