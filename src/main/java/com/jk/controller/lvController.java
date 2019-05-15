package com.jk.controller;

import com.jk.service.LvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class lvController {
    @Autowired
    private LvService lvService;
}
