package com.jk.service;

import com.alibaba.fastjson.JSON;
import com.jk.dao.LvMapper;
import com.jk.model.BiaoTi;
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
            resource.set("tree", jsonString);
            return jsonString;
        }
    }

    /**
     * 查询 黑名单
     * @return
     */
    @Override
    public List<LinkedHashMap<String, Object>> getblacklist() {
        return lvMapper.getblacklist();
    }

    @Override
    public HashMap<String, Object> finBiaoti(Integer pageSize, Integer start) {
       Integer total= lvMapper.getBtTotal();
      List<LinkedHashMap<String,Object>> list= lvMapper.getBtList(start,pageSize);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total",total);
        hashMap.put("rows",list);
        return hashMap;
    }

    @Override
    public void addBiaoti(BiaoTi bt) {
        if (StringUtils.isNotEmpty(bt.getId().toString())){
            lvMapper.updateBiaoti(bt);
        }else{
            lvMapper.addBiaoti(bt);
        }

    }

    @Override
    public void deleteBiaoti(String btid) {
        lvMapper.deleteBiaoti(btid);
    }

    @Override
    public BiaoTi findBtById(String btid) {
        BiaoTi bt= lvMapper.findBtById(btid);
        return bt;
    }
}
