package com.jk.controller;

import com.jk.service.LvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class lvController {
    @Autowired
    private LvService lvService;

    @RequestMapping("find")
    public String  find(String url){
        return url;
    }
}
