package com.jk.model;


import lombok.Data;

import java.io.Serializable;

/**
 * 业精于勤荒于嬉,行成于思毁于随
 *
 * @Date 2019/4/28 9:12
 * @Created by wuzhuang
 */

@Data
public class User implements Serializable {

    private  String userLogin;
    private String username;
    private Integer type;
    private String email;

    private String password;

    private String remPwd;

    private String yzm;

    private Integer age;
}
