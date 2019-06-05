package com.jk.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Black {
 private Integer id;
 private String phone;

 @DateTimeFormat(pattern = "yyyy-MM-dd")
 @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
 private Date blacktime;
}
