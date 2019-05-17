package com.jk.service;

import com.jk.model.common;

import java.util.HashMap;
import java.util.List;

public interface commonService {
    HashMap<String, Object> getcommon(Integer pageSize, Integer start,common common);

    void deletecommon(String id);//删除 承运商 wzk 2019-5-17 16:35:55

    //查询 详情
    HashMap<String, Object> finddetailsdialog(String id);
}
