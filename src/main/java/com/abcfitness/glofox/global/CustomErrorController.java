package com.abcfitness.glofox.global;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("/error")
public class CustomErrorController implements ErrorController {

    @RequestMapping("")
    public String handleError() {
        return "error";
    }

    public String getErrorPath() {
        return "/error";
    }
}

