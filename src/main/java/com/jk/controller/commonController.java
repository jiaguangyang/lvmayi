package com.jk.controller;

import com.jk.model.Tixian;
import com.jk.model.common;
import com.jk.service.commonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class commonController {
    @Autowired
    private commonService commonService;

    @RequestMapping("tixian")
    @ResponseBody
    public void tixian(Tixian ti){
        commonService.updatesum(ti);
        commonService.addcaiwu(ti);
        commonService.addtixianjilu(ti);
    }

    @RequestMapping("getSumByid")
    @ResponseBody
    public String getSumByid(Integer id){
        return commonService.getSumByid(id);
    }

    /**
     * 查询 条查 分页 wzk
     * @return
     */
    @RequestMapping("getcommon")
    @ResponseBody
    public HashMap<String, Object> getcommon(Integer pageSize, Integer start,common common){
        return commonService.getcommon(pageSize,start,common);
    }
    /**
     * 承运商 删除 wzk
     */

    @RequestMapping("deletecommon")
    @ResponseBody
    public String deletecommon(String id){
        commonService.deletecommon(id);
        return null;
    }
    /**
     * 查询 详情 wzk 2019-5-17 19:41:31
     */
  @RequestMapping("finddetailsdialog")
    @ResponseBody
    public HashMap<String,Object> finddetailsdialog(String id){
        return commonService.finddetailsdialog(id);
  }
}
