# `Exception Guide`

## 1) 예외 전략
### 비즈니스 예외를 위한 최상위 예외클래스: BusinessException
- demo-admin 애플리케이션의 비즈니스 예외를 위한 최상위 클래스를 BusinessException 으로 정의합니다.
- 따라서, 각 도메인별 모든 예외 클래스와 global 패키지 내부에 정의한 공통 예외 클래스는 모두 BusinessException을 상속받습니다.
![](../docs/images/BusinessException.png)
  - (참고) 위와 같이 애플리케이션에서 정의하는 모든 예외가 BusinessException을 바로 상속받는 것이 아니라, InvalidValueException or DataNotFoundException 과 같은 범용 및 추상적인 개념의 예외가 BusinessException을 상속받고, 각 도메인에서 정의한 예외 클래스는 해당 추상적 개념의 클래스를 상속받는 구조를 고민했으나 아래와 같은 이유로 좀 더 고민이 필요하다고 판단했습니다.
    - 도메인별로 예외 처리 클래스가 불필요하게 많아질 것에 대한 우려
    - 범용/추상적 개념의 예외클래스를 어느 정도의 범위까지 생성할 것인가에 대한 고민
    - 예외 처리 핸들러에서 BusinessException 외 애플리케이션에서 정의한 다른 예외를 별도로 처리하는 경우가 많지 않을 것 같다고 생각했습니다. 따라서 BusinessException 하위 레벨의 구조까지 맞추는 것이 필요할까 라는 생각이 들었습니다.


## 2) 예외 처리
### API 예외 처리를 위한 클래스: GlobalExceptionHandler(@RestControllerAdvice)
- API 예외의 경우 GlobalExceptionHandler(@RestControllerAdvice)에서 공통으로 응답 처리하고, 그 외의 경우(화면 처리)는 스프링 부트 기본 오류 처리 매커니즘(BasicErrorController)를 따릅니다.
```java
@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    log.error("handleMethodArgumentNotValidException", e);
    final ErrorResponse response = ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), e.getBindingResult());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
    log.error("handleMethodArgumentTypeMismatchException", e);
    final ErrorResponse response = ErrorResponse.of(HttpStatus.BAD_REQUEST.value());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BindException.class)
  protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
    log.error("handleBindException", e);
    final ErrorResponse response = ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), e.getBindingResult());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
    log.error("handleHttpRequestMethodNotSupportedException", e);
    final ErrorResponse response = ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED.value());
    return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
  }

  @ExceptionHandler(BusinessException.class)
  protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
    log.error("handleBusinessException", e);
    final ErrorResponse response = ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ErrorResponse> handleException(Exception e) {
    log.error("handleException", e);
    // 개발자 알람 전송
    final ErrorResponse response = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.value());
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
```

### API 공통 응답 포맷: ErrorResponse
- 작성중