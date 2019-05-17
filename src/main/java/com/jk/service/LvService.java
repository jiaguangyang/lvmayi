package com.jk.service;

import com.jk.model.BiaoTi;
import com.jk.model.Ossbean;
import com.jk.model.User;
import com.jk.model.common;
import com.jk.utils.MenuTree;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public interface LvService {

    //查询树 wzk
    String getTreeAll();

    //查询 黑名单 wzk
    HashMap<String, Object>  getblacklist(Integer pageSize, Integer start);


    //jgy查询标题表
    HashMap<String, Object> finBiaoti(Integer pageSize, Integer start);

    //jgy新增或修改标题
    void addBiaoti(BiaoTi bt);

    //jgy删除标题
    void deleteBiaoti(String btid,Integer type);

    //根据IDcha查询标题
    BiaoTi findBtById(String btid);

    //删除黑名单
    void deleteblack(String id);

    User findUser(User user);

    //查询轮播图
    HashMap<String, Object> findOssTable(Integer start, Integer pageSize);

    //修改路径
    void upHref(Ossbean ossbean);

    //新增轮播图
    void addLunbo(Ossbean ossbean);
}
