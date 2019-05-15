package com.jk.service;

import com.jk.utils.MenuTree;

import java.util.LinkedHashMap;
import java.util.List;

public interface LvService {


    String getTreeAll();//查询树 wzk

    List<LinkedHashMap<String, Object>> getblacklist(); //查询 黑名单 wzk
}
