package com.jk.service;

import com.jk.dao.LvMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LvServiceImpl implements LvService{
    @Autowired
    private LvMapper lvMapper;
}
