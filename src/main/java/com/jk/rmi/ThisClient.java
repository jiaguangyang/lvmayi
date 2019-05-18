package com.jk.rmi;


import com.jk.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@FeignClient("loginservice")
public interface ThisClient {
    @RequestMapping("userLogin")
    @ResponseBody
    User findUser(@RequestBody User user);

    @RequestMapping("findDxCode")
    @ResponseBody
    HashMap<String, Object> findDxCode(@RequestParam("loginNumber") String loginNumber);
}
