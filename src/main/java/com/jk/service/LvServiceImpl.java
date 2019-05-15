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
          List<MenuTree> list= lvMapper.getTreeAll();
        List<MenuTree> treeList = TreeNoteUtil.getFatherNode(list);
        Jedis resource = jedisPool.getResource();
        String jsonString = JSON.toJSONString(treeList);
        resource.set("tree",jsonString);
        return jsonString;
    }

    /**
     * 查询 黑名单
     * @return
     */
    @Override
    public List<LinkedHashMap<String, Object>> getblacklist() {
        return lvMapper.getblacklist();
    }
}
