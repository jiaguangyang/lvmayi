package com.jk.service;

import com.jk.model.BiaoTi;
import com.jk.utils.MenuTree;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public interface LvService {


    String getTreeAll();//查询树 wzk

    List<LinkedHashMap<String, Object>> getblacklist(); //查询 黑名单 wzk

    //jgy查询标题表
    HashMap<String, Object> finBiaoti(Integer pageSize, Integer start);

    //jgy新增或修改标题
    void addBiaoti(BiaoTi bt);

    //jgy删除标题
    void deleteBiaoti(String btid);

    //根据IDcha查询标题
    BiaoTi findBtById(String btid);
}
