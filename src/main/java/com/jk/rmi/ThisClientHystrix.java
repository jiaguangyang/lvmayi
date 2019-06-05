package com.jk.rmi;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ThisClientHystrix implements  ThisClient {
    @Override
    public HashMap<String, Object> findUser(String username, String password, Integer type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("msg", "网络异常");
        map.put("status",-1);
        return map;
    }

    @Override
    public HashMap<String, Object> findDxCode(String loginNumber, Integer type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("msg", "网络异常");
        map.put("status",-1);
        return map;
    }

    @Override
    public HashMap<String, Object> getCheckCode(String email, Integer type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("msg", "网络异常");
        map.put("status",-1);
        return map;
    }

    @Override
    public HashMap<String, Object> numlogin(String phone, String smsCode, Integer type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("msg", "网络异常");
        map.put("status",-1);
        return map;
    }

    @Override
    public HashMap<String, Object> emailLogin(String email, String emailCode, Integer type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("msg", "网络异常");
        map.put("status",-1);
        return map;
    }
}
