package com.jk.model;

import lombok.Data;

@Data
public class Carrier {
    private Integer id;
    private String account;
    private String password;
    private String companyPhone;
    private String email;
    private String remPwd;
    private String companyName;
}
