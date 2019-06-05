package com.jk.service;

import com.jk.dao.commonMapper;
import com.jk.model.Tixian;
import com.jk.model.common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class commonServiceImpl implements commonService {
            @Autowired
            private commonMapper commonMapper;
    /**
     * 查询 分页 条件查询
     * @param pageSize
     * @param start
     * @param
     * @return
     */
    @Override
    public  HashMap<String, Object> getcommon(Integer pageSize, Integer start,common common) {
        Integer commonSum=commonMapper.getcommonSum();
        List<common> commonlist=commonMapper.getcommon(pageSize,start,common.getCompanyName(),common.getCompanyProvince(),common.getCompanyPhone());
        HashMap<String,Object> map =new HashMap<>();
        map.put("total", commonSum);
        map.put("rows", commonlist);
        return map;
    }

    /**
     * 删除 承运商 wzk  2019-5-17 16:36:18
     * @param id
     */
    @Override
    public void deletecommon(String id) {
        commonMapper.deletecommon(id);
    }

    /**
     * 查询详情
     * @param id
     * @return
     */
    @Override
    public HashMap<String, Object> finddetailsdialog(String id) {
        return commonMapper.finddetailsdialog(id);
    }

    @Override
    public String getSumByid(Integer id) {
        return commonMapper.getSumByid(id);
    }

    @Async("taskExecutor")
    @Override
    public void updatesum(Tixian ti) {
        commonMapper.updatesum(ti.getTxmoney()*1.001,ti.getCommid());
    }

    @Async("taskExecutor")
    @Override
    public void addcaiwu(Tixian ti) {
        commonMapper.addcaiwu(ti);
    }

    @Async("taskExecutor")
    @Override
    public void addtixianjilu(Tixian ti) {
        commonMapper.addtixianjilu(ti);
    }


}
