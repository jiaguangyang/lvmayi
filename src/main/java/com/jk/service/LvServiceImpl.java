package com.jk.service;

import com.alibaba.fastjson.JSON;
import com.jk.dao.LvMapper;
import com.jk.model.*;
import com.jk.utils.MenuTree;
import com.jk.utils.TreeNoteUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpSession;
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
            resource.close();
            return tree;
        }else{
            List<MenuTree> list= lvMapper.getTreeAll();
            List<MenuTree> tree = TreeNoteUtil.getFatherNode(list);
            String jsonString = JSON.toJSONString(tree);
            resource.setex("tree", 20,jsonString);
            resource.close();
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
       Integer total= lvMapper.getOssTableTotal();
        List<Ossbean> list =lvMapper.findOssTable(start,pageSize);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows", list);
        return map;
    }

    @Override
    public HashMap<String, Object> getPoster(Integer start, Integer pageSize) {
        Integer total= lvMapper.getPosterTableTotal();
        List<Ossbean> list =lvMapper.getPoster(start,pageSize);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows", list);
        return map;
    }

    @Override
    public void addPoster(Ossbean ossbean) {
        lvMapper.addPoster(ossbean);
    }

    @Override
    public void updatePosterStatus(String id,Integer type) {
        lvMapper.updatePosterStatus(id);
        lvMapper.updateOrtherPoster(id,type);
    }

    @Override
    public List<Black> findBlackListByid(String ids) {

        return  lvMapper.findBlackListByid(ids);
    }

    @Override
    public HashMap<String, Object> findstu(Integer page, Integer limit, Student stu) {
        Integer total=lvMapper.findStuCount();
        List<Student> list=lvMapper.findstu((page-1)*limit,limit,stu);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("count",total);
        hashMap.put("data",list);
        hashMap.put("code",0);
        return hashMap;
    }

    @Override
    public List<LinkedHashMap<String,Object>> findstuByid(String ids) {
        return lvMapper.findstuByid(ids);
    }

    //修改路径
    @Override
    public void upHref(Ossbean ossbean) {
        Jedis resource = jedisPool.getResource();
        resource.del("luobo");
        resource.close();
        lvMapper.upHref(ossbean);
    }

    //新增轮播图
    @Override
    public void addLunbo(Ossbean ossbean) {
        Jedis resource = jedisPool.getResource();
        resource.del("luobo");
        resource.close();
        lvMapper.addLunbo(ossbean);
    }


    //手机号登录
    @Override
    public HashMap<String, Object> numlogin(String phone, String smsCode) {
        Jedis jedis = jedisPool.getResource();
        String code = jedis.get(phone);
        HashMap<String, Object> hashMap = new HashMap<>();
        jedis.close();
        if (StringUtils.isNotEmpty(code)){
            if (code.equals(smsCode)){
                hashMap.put("status",0);
                hashMap.put("msg","登陆成功");
            }else{
                hashMap.put("status",1);
                hashMap.put("msg","验证码错误");
            }
        }{
            hashMap.put("status",1);
            hashMap.put("msg","验证码已过期");
        }
        return hashMap;
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
        Jedis resource = jedisPool.getResource();
        resource.del("biaoti");
        resource.close();
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
        Jedis resource = jedisPool.getResource();


     if (type==1){
         resource.del("biaoti");
         lvMapper.deleteBiaoti(btid);
     }
       if (type==2){
           resource.del("luobo");
           lvMapper.deleteLunbo(btid);
       }
        if (type==3){
            lvMapper.deletePoster(btid);
        }
        resource.close();
    }

    //根据ID查询标题
    @Override
    public BiaoTi findBtById(String btid) {
        BiaoTi bt= lvMapper.findBtById(btid);
        return bt;
    }


}
