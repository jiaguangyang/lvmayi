package com.jk.dao;

import com.jk.model.BiaoTi;
import com.jk.utils.MenuTree;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
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

    @Select("select count(*) from t_biaoti ")
    Integer getBtTotal();

    @Select("select *  from t_biaoti LIMIT #{start},#{pageSize}")
    List<LinkedHashMap<String, Object>> getBtList(@Param("start") Integer start, @Param("pageSize") Integer pageSize);

    void addBiaoti(BiaoTi bt);

    @Delete("delete from t_biaoti where id in(${btid})")
    void deleteBiaoti(@Param("btid")String btid);

    @Select("select * from t_biaoti where id=#{btid}")
    BiaoTi findBtById(@Param("btid") String btid);

    void updateBiaoti(BiaoTi bt);
}
