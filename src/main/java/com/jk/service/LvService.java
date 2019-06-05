package com.jk.service;

import com.jk.model.*;
import com.jk.utils.MenuTree;

import javax.servlet.http.HttpSession;
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

    //手机号登录
    HashMap<String, Object> numlogin(String phone, String smsCode);

    HashMap<String, Object> getPoster(Integer start, Integer pageSize);

    void addPoster(Ossbean ossbean);

    void updatePosterStatus(String id,Integer type);

    List<Black> findBlackListByid(String ids);

    HashMap<String, Object> findstu(Integer page, Integer limit, Student stu);

    List<LinkedHashMap<String,Object>> findstuByid(String ids);
}
