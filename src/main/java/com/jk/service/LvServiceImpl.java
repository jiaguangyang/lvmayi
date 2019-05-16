package com.jk.service;

import com.alibaba.fastjson.JSON;
import com.jk.dao.LvMapper;
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
     * 查询 黑名单 wzk
     * @return
     */
    @Override
    public HashMap<String, Object> getblacklist(Integer pageSize,Integer start) {
       long count=lvMapper.getsum();
       List<LinkedHashMap<String,Object>> find=lvMapper.getblacklist(pageSize,start);
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
}
