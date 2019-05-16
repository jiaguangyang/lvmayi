package com.jk.dao;

import com.jk.utils.MenuTree;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Repository
public interface LvMapper {
    @Select(" select * from t_tree ")
    List<MenuTree> getTreeAll();//绿蚂蚁后台管理系统 同步树 wzk

    @Select(" select * from t_blacklist limit #{start},#{pageSize} ")
    List<LinkedHashMap<String,Object>> getblacklist(@Param("pageSize") Integer pageSize,@Param("start") Integer start);//查询 黑名单 wzk

    @Select(" select count(id) from t_blacklist ")
    long getsum();

    @Delete(" delete from t_blacklist where id=#{id}")
    void deleteblack(@Param("id") String id);//删除 黑名单  wzk
}
