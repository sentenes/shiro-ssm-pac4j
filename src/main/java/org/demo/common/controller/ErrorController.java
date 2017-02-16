package org.demo.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("error")
public class ErrorController {

    @RequestMapping("404")
    public ModelAndView error404() {
        System.out.println("404");
        return new ModelAndView("/error/404");
    }

    @RequestMapping("401")
    public ModelAndView error401() {
        return new ModelAndView("/error/401");
    }

    @RequestMapping("500")
    public ModelAndView error500() {
        return new ModelAndView("/error/500");
    }

    @RequestMapping("kickout")
    public ModelAndView errorKickout() {
        return new ModelAndView("/error/kickout");
    }

    @RequestMapping("index")
    public ModelAndView demo() {
        return new ModelAndView("/index.jsp");
    }
}
