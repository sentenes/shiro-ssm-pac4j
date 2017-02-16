package org.demo.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DemoController {
    
    @RequestMapping("/u/online")
    public ModelAndView online(){
        return new ModelAndView("/cas/online");
    }
    
    @RequestMapping("/news/latest")
    public ModelAndView latestNews(){
        return new ModelAndView("/cas/latestNews");
    }
}
