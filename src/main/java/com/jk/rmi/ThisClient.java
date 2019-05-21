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
    HashMap<String, Object> findUser(@RequestParam("username")String username,@RequestParam("password") String password,@RequestParam("type") Integer type);

    @RequestMapping("findDxCode")
    @ResponseBody
    HashMap<String, Object> findDxCode(@RequestParam("loginNumber") String loginNumber,@RequestParam("type")Integer type);

    @RequestMapping("getCheckCode")
    @ResponseBody
    HashMap<String, Object> getCheckCode(@RequestParam("email")String email,@RequestParam("type")Integer type);

    @RequestMapping("numlogin")
    @ResponseBody
    HashMap<String, Object> numlogin(@RequestParam("phone")String phone, @RequestParam("smsCode")String smsCode,@RequestParam("type")Integer type);

    @RequestMapping("emailLogin")
    @ResponseBody
    HashMap<String, Object> emailLogin(@RequestParam("email")String email, @RequestParam("emailCode")String emailCode,@RequestParam("type")Integer type);
}
