# `Security Guide`

## 보안 정책
- `인증`
  - 인증 방식
    - Form 인증
  - 인증 정책
    - 관리자 상태가 "정상" 이 아닌 경우, 로그인할 수 없다.
    - 마지막 로그인 날짜로 부터 90일간 로그인 이력이 없는 경우, 서비스 이용 불가한 잠금 대상으로 로그인할 수 없다. ( 만약 계정 생성된 후 한 번도 로그인 하지 않았다면, 기준 날짜는 계정 생성일로 한다. )
    - 비밀번호 실패 횟수가 5회 이상인 경우 로그인할 수 없다.
    - 참고) 위 로그인할 수 없는 조건에 해당하는 경우, 마스터 관리자의 초기화 작업이 이뤄져야 한다.
  - 패스워드 암호화 알고리즘
    - Bcrypt
- `인가`
  - 인가 방식
    - 인증 여부 및 권한에 따른 메뉴 접근 제어(URL) 처리
    - 참고) 자원에 따른 권한 정보는 DB 에서 관리하지 않고, 보안 설정 클래스에서 코드로 명시하여 정적으로 관리한다.
  - 참고) 관리자 권한 정보는 아래와 같이 6가지로 분류되며, 각 권한별 접근 가능한 메뉴는 다음과 같다.
    - MASTER(마스터 관리자) : 전체 메뉴 접근 가능 ( 회원관리, 상품관리, 매출관리, 고객센터, 어드민 관리 )
    - MANAGER(운영 담당자) : 회원관리, 상품관리, 매출관리, 고객센터
    - DEVELOPER(개발 담당자) : 회원관리, 상품관리, 매출관리, 고객센터
    - CUSTOMER(고객 담당자) : 회원관리, 고객센터
    - SALES(영업 담당자) : 매출관리
    - FINANCIAL(회계 담당자) : 매출관리
- `정책 정의 및 적용중`
  - 세션 정책
  - 접근 가능 IP
  - 보안 강화를 위한 2단계 로그인 설정
  - 패스워드 정책