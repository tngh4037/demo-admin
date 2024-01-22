package web.commerce.domain.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import web.commerce.global.constant.ViewConstant;

@Controller
public class HomeController {

    /**
     * 메인 화면 이동
     */
    @GetMapping("/")
    public String getHome() {
        return ViewConstant.MAIN;
    }

    /**
     * (참고) 관리자 공통 레이아웃 - 리스트 페이지
     */
    @GetMapping("/layout")
    public String getLayout() {
        return ViewConstant.LAYOUT;
    }

    /**
     * (참고) 관리자 공통 레이아웃 - 상세보기 페이지
     */
    @GetMapping("/layout/detail")
    public String getLayoutDetail() {
        return ViewConstant.LAYOUT_DETAIL;
    }
}