package com.jk.service;

import com.jk.model.BiaoTi;
import com.jk.utils.MenuTree;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public interface LvService {


    String getTreeAll();//查询树 wzk

    List<LinkedHashMap<String, Object>> getblacklist(); //查询 黑名单 wzk

    HashMap<String, Object> finBiaoti(Integer pageSize, Integer start);

    void addBiaoti(BiaoTi bt);

    void deleteBiaoti(String btid);

    BiaoTi findBtById(String btid);
}
