package com.jk.controller;

import com.jk.service.LvService;
import com.jk.utils.MenuTree;
import com.jk.utils.TreeNoteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class lvController {
    @Autowired
    private LvService lvService;


    /**
     * 绿蚂蚁后台管理系统
     * 同步树  王战坤
     * @return
     */
    @RequestMapping("findTree")
    @ResponseBody
    public String getTreeAll(){

        return lvService.getTreeAll();
    }
    /**
     * 查询黑名单
     * id
     * phone
     */
    @RequestMapping("getblacklist")
    @ResponseBody
    public HashMap<String, Object> getblacklist(Integer pageSize, Integer start){

        return  lvService.getblacklist(pageSize,start);
    }

    /**
     * 删除  黑名单  wzk
     * @param
     * @return
     */
    @RequestMapping("deleteblack")
    @ResponseBody
    public  String  deleteblack(String id){
        lvService.deleteblack(id);
        return null;
    }

    @RequestMapping("find")
    public String  find(String url){
        return url;
    }
}
