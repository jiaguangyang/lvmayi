package com.jk.service;

import com.alibaba.fastjson.JSON;
import com.jk.dao.LvMapper;
import com.jk.model.BiaoTi;
import com.jk.model.Ossbean;
import com.jk.model.User;
import com.jk.utils.MenuTree;
import com.jk.utils.TreeNoteUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class LvServiceImpl implements LvService{
    @Autowired
    private LvMapper lvMapper;
    @Autowired
    private JedisPool jedisPool;

    /**
     * 查询树
     * 缓存到 redis  wzk
     * @return
     */
    @Override
    public String getTreeAll() {
        Jedis resource = jedisPool.getResource();
        if(StringUtils.isNotEmpty(resource.get("tree"))){
            String tree = resource.get("tree");
            return tree;
        }else{
            List<MenuTree> list= lvMapper.getTreeAll();
            List<MenuTree> tree = TreeNoteUtil.getFatherNode(list);
            String jsonString = JSON.toJSONString(tree);
            resource.setex("tree", 20,jsonString);
            return jsonString;
        }

    }
    /**
     * 查询 黑名单 wzk
     * @return
     */
    @Override
    public HashMap<String, Object> getblacklist(Integer pageSize,Integer start) {
       long count=lvMapper.getgetblacklistsum();
       List<LinkedHashMap<String,Object>> find=lvMapper.getblacklist(start,pageSize);
        HashMap<String,Object> map =new HashMap<>();
        map.put("total", count);
        map.put("rows", find);
        return map;

    }
    /**
     * 删除 黑名单 wzk
     * @param id
     */
    @Override
    public void deleteblack(String id) {
        lvMapper.deleteblack(id);
    }

    //用户登录
    @Override
    public User findUser(User user) {
        User user1=  lvMapper.findUser(user);
        return user1;
    }

    //查询轮播图
    @Override
    public HashMap<String, Object> findOssTable(Integer start, Integer pageSize) {
        List<Ossbean> list =lvMapper.findOssTable(start,pageSize);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", list.size());
        map.put("rows", list);
        return map;
    }

    //修改路径
    @Override
    public void upHref(Ossbean ossbean) {
        lvMapper.upHref(ossbean);
    }

    //新增轮播图
    @Override
    public void addLunbo(Ossbean ossbean) {
        lvMapper.addLunbo(ossbean);
    }

    //jgy查询标题表
    @Override
    public HashMap<String, Object> finBiaoti(Integer pageSize, Integer start) {
       Integer total= lvMapper.getBtTotal();
      List<LinkedHashMap<String,Object>> list= lvMapper.getBtList(start,pageSize);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total",total);
        hashMap.put("rows",list);
        return hashMap;
    }

    //jgy新增或修改标题
    @Override
    public void addBiaoti(BiaoTi bt) {
        if (bt.getId()!=null){
            //修改标题
            lvMapper.updateBiaoti(bt);
        }else{
            //新增标题
            lvMapper.addBiaoti(bt);
        }

    }

    //jgy删除标题
    @Override
    public void deleteBiaoti(String btid,Integer type) {
     if (type==1){
         lvMapper.deleteBiaoti(btid);
     }
       if (type==2){
           lvMapper.deleteLunbo(btid);
       }
    }

    //根据ID查询标题
    @Override
    public BiaoTi findBtById(String btid) {
        BiaoTi bt= lvMapper.findBtById(btid);
        return bt;
    }


}
