package apap.ti.tugas1_iris_2006464266.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {
    @GetMapping("/")
    private String Home() {
        return "home";
    }
}
