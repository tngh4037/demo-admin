package web.commerce.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import web.commerce.constant.ViewConstant;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getHome() {
        return ViewConstant.MAIN;
    }

    @GetMapping("/layout")
    public String getLayout() {
        return ViewConstant.LAYOUT;
    }
}