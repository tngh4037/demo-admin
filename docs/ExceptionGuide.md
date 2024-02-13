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
### 모든 예외 처리를 위한 클래스: GlobalExceptionHandler (@ControllerAdvice)
- @ControllerAdvice 를 적용한 GlobalExceptionHandler 에서 비즈니스 예외를 포함한 모든 예외를 처리합니다.
- (참고) 아래는 현재까지 구현된 예외 처리 클래스의 일부 참고이며, 개선중에 있습니다. ( [예외 처리 핸들러 바로가기](../src/main/java/com/example/demo/admin/global/error/GlobalExceptionHandler.java) )
  - 개선이 필요한 부분은 해당 파일내 주석으로 작성해 두었습니다. ( view 와 api 응답을 위한 처리를 어떻게 하면 효율적으로 처리할 수 있는지 학습 및 고민이 필요 )
```java
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String ERROR_PAGE = "error";
    private static final String AJAX_HEADER_NAME = "X-Requested-With";
    private static final String AJAX_HEADER_VALUE = "XMLHttpRequest";

    @ExceptionHandler(BusinessException.class)
    private ModelAndView handleBusinessException(HttpServletRequest request, BusinessException ex) {
        log.info("handleBusinessException :: ", ex);
        return sendError(request, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    private String handleException(Exception ex) {
        log.error("handleException :: ", ex);
        // alarm
        return ERROR_PAGE;
    }

    private ModelAndView sendError(HttpServletRequest request, String msg) {
        if (isAjaxRequest(request)) {
            return errorJson(msg);
        }
        return errorHtml(msg);
    }

    private ModelAndView errorJson(String message) {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("code", HttpStatus.BAD_REQUEST.value());
        mav.addObject("message", message);
        return mav;
    }

    private ModelAndView errorHtml(String msg) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("msg", msg);
        mav.addObject("url", "/");
        mav.setViewName("/common/redirect");
        return mav;
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader(AJAX_HEADER_NAME);
        String acceptHeader = request.getHeader(HttpHeaders.ACCEPT);
        return AJAX_HEADER_VALUE.equals(header) || MediaType.APPLICATION_JSON_VALUE.equals(acceptHeader);
    }
}
```

### 공통 응답 포맷
- 정책 정의 및 작성중