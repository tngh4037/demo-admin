# `Package Guide`

## 1) 도메인형 구조
demo-admin 프로젝트는 크게 아래와 같은 패키지 구조로 설계되어 있습니다.
- ① `domain` : 프로젝트의 각 도메인을 관리하는 코드로 구성됩니다.
- ② `global` : 프로젝트에서 전역적으로 사용되는 객체들과 설정들로 구성됩니다.
- ③ `infra` : 외부 인프라스트럭처와 관련된 코드들로 구성됩니다.

```
└─src
    ├─main
    │  ├─java
    │  │  └─com
    │  │      └─example
    │  │          └─demo
    │  │              └─admin
    │  │                  ├─domain
    │  │                  ├─global
    │  │                  ├─infra
    │  │                  └─DemoAdminApplication.java
    │  └─resources
    │      ├─mybatis
    │      ├─static
    │      ├─templates
    │      └─application.yml
    └─test
```
- (참고) `resources`
  - `mybatis`: mybatis mapper files (*.xml)
  - `static`: static resources (css, js, ..)
  - `templates`: thymeleaf template html
  - `application.yml`: environment config


## 2) 상세 설명
### ① `domain` : 프로젝트의 각 도메인을 관리

```
├─domain
│  ├─customer
│  │  ├─controller
│  │  ├─define
│  │  ├─domain
│  │  ├─dto
│  │  ├─exception
│  │  ├─repository
│  │  ├─service
│  │  └─validator
│  ├─event
│  ├─goods
│  ├─admin
│  ├─home
│  └─login
├─global
└─infra
```

domain 내부에는 프로젝트의 각 도메인을 관리하는 패키지(customer, event, goods, ..)들이 존재합니다. 각 패키지 내부의 전체적인 구조는 다음과 같습니다.

- `controller`: 컨트롤러 역할의 클래스들을 관리합니다.
- `define`: 해당 도메인과 관련한 공통 코드를 관리합니다. (ex. Enum)
- `domain`: 도메인 엔티티 성격의 클래스로 구성됩니다.
- `dto`: 클라이언트로부터 요청 데이터를 받는 용도의 클래스와 API 응답데이터를 위한 클래스들을 관리합니다.
- `exception`: 해당 도메인이 발생시키는 예외 클래스들을 관리합니다.
- `repository`: 데이터 엑세스 처리를 위한 클래스들을 관리합니다.
- `service`: 비즈니스 로직, 트랜잭션 처리 등을 담당하는 서비스 클래스들을 관리합니다.
- `validator`: 클라이언트의 요청값을 검증 처리하기 위한 클래스들을 관리합니다.


### ② `global`: 프로젝트의 전체적인 설정을 관리

```
├─domain
├─global
│  ├─common
│  │  ├─constant
│  │  ├─dto
│  │  └─define
│  ├─config
│  │  ├─argumentresolver
│  │  ├─converter
│  │  ├─define
│  │  ├─interceptor
│  │  └─WebMvcConfig.java
│  ├─error
│  │  └─exception
│  └─util
└─infra
```

global은 프로젝트에서 전역적으로 사용되는 객체들과 설정들로 구성됩니다.

- `common`: 프로젝트에서 전역적으로 사용되는 객체, 상수들로 구성됩니다. (ex. 페이징 처리를 위한 객체, 공통 상수 등)
- `config`: 프로젝트의 전체적인 설정을 관리합니다. (ex. 스프링과 관련된 설정 등)
- `error`: API 예외 처리를 담당하는 핸들러 클래스와 응답 객체, 그리고 공통적인 성격의 exception 클래스들로 구성됩니다.
- `util`: 유틸성 클래스들을 관리합니다.
- (참고) 위 패키지 외에도 전역적으로 사용되는 성격의 요소들이 있다면 global 패키지 내부에서 관리합니다.


### ③ `infra`: 외부 인프라스트럭처를 관리

```
├─domain
├─global
└─infra
    └─notify
        ├─email
        ├─slack
        └─sms
            └─dto
```

infra는 인프라스트럭처 관련된 코드들로 구성됩니다. (ex. EMAIL, SMS, SLACK 알람 등과 같은 외부 서비스 이용에 대한 코드)
- 프로젝트에서 공통, 전역적으로 사용될 수 있는 성격이라는 점에서 해당 패키지와 global 패키지의 성격이 겹치는 것 같지만, 공통이라는 추상적인 개념을 모두 global에 위치시키기 보다, 공통적인 성격 중에서도 인프라적인 부분을 분리할 필요성이 있어보였습니다. 이 부분은 프로젝트를 점진적으로 확장시켜 보면서 해당 패키지의 필요성에 대해서 필자의 주관적 견해를 정리해보도록 하겠습니다.
