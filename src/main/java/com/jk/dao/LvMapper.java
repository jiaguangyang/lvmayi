package com.jk.dao;

import com.jk.model.BiaoTi;
import com.jk.model.common;
import com.jk.model.Ossbean;
import com.jk.model.User;
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
    //绿蚂蚁后台管理系统 同步树 wzk
    @Select(" SELECT * FROM T_TREE ")
    List<MenuTree> getTreeAll();

    //黑名单的 查询 分页 wzk
    @Select(" SELECT * FROM T_BLACKLIST ORDER BY BLACKTIME DESC limit #{start},#{pageSize}")
    List<LinkedHashMap<String, Object>> getblacklist(@Param("start") Integer start,@Param("pageSize") Integer pageSize);//查询 黑名单 wzk

    //查询 黑名单总条数 wzk
    @Select(" SELECT COUNT(ID) FROM T_BLACKLIST ")
    long getgetblacklistsum();

    //查询标题表总条数
    @Select("select count(*) from t_biaoti ")
    Integer getBtTotal();

    //jgy查询标题表
    @Select("SELECT *  FROM T_BIAOTI  LIMIT #{start},#{pageSize}")
    List<LinkedHashMap<String, Object>> getBtList(@Param("start") Integer start, @Param("pageSize") Integer pageSize);

    //新增标题
    void addBiaoti(BiaoTi bt);

    //jgy删除标题
    @Delete("delete from t_biaoti where id in(${btid})")
    void deleteBiaoti(@Param("btid")String btid);

    //根据ID查询标题
    @Select("select * from t_biaoti where id=#{btid}")
    BiaoTi findBtById(@Param("btid") String btid);
    //修改标题
    void updateBiaoti(BiaoTi bt);


    //删除黑名单 wzk
    @Delete(" DELETE FROM T_BLACKLIST WHERE ID=#{id} ")
    void deleteblack(@Param("id") String id);

    //用户登录
    @Select("select * from t_admin where username=#{username} and password=#{password}")
    User findUser(User user);

    //查询轮播图
    @Select("select * from t_oss limit #{start},#{pageSize}")
    List<Ossbean> findOssTable(@Param("start") Integer start,@Param("pageSize") Integer pageSize);

    //删除轮播图
    @Delete("delete from t_oss where id in(${btid})")
    void deleteLunbo(String btid);

    void upHref(Ossbean ossbean);

    void addLunbo(Ossbean ossbean);


}
