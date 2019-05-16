package com.jk.service;

import com.jk.utils.MenuTree;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public interface LvService {


    String getTreeAll();//查询树 wzk

    HashMap<String, Object> getblacklist(Integer pageSize, Integer start); //查询 黑名单 wzk

    void deleteblack(String id);//删除 黑名单
}
