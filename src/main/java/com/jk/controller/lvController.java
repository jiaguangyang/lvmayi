package com.jk.controller;

import com.jk.model.BiaoTi;
import com.jk.service.LvService;
import com.jk.utils.MenuTree;
import com.jk.utils.TreeNoteUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @RequestMapping("deleteBiaoti")
    @ResponseBody
    public void deleteBiaoti(String btid){
        lvService.deleteBiaoti(btid);
    }



    @RequestMapping("toaddBiaoti")
    public String toaddBiaoti(String btid, Model model){
        if (StringUtils.isNotEmpty(btid)){
            BiaoTi bt= lvService.findBtById(btid);
            model.addAttribute("id",bt.getId());
            model.addAttribute("text",bt.getText());
            model.addAttribute("uri",bt.getUri());
        }
        return "addBiaoti";
    }

    @RequestMapping("addOrUpBiaoti")
    @ResponseBody
    public void addBiaoti(BiaoTi bt){
        lvService.addBiaoti(bt);
    }

    @RequestMapping("finBiaoti")
    @ResponseBody
    public HashMap<String,Object> finBiaoti(Integer pageSize, Integer start){
        return lvService.finBiaoti(pageSize,start);
    }


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
    public List<LinkedHashMap<String,Object>> getblacklist(){
        return  lvService.getblacklist();
    }

    @RequestMapping("find")
    public String  find(String url){
        return url;
    }
}
