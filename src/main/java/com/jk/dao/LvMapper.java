package com.jk.dao;

import com.jk.utils.MenuTree;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

@Repository
public interface LvMapper {
    @Select(" select * from t_tree ")
    List<MenuTree> getTreeAll();//绿蚂蚁后台管理系统 同步树 wzk

    @Select(" select * from t_blacklist ")
    List<LinkedHashMap<String, Object>> getblacklist();//查询 黑名单 wzk
}
