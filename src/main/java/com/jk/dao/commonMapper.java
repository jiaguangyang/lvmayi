package com.jk.dao;

import com.jk.model.Tixian;
import com.jk.model.common;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Repository
public interface commonMapper {
    // 查询 承运商总条数 wzk   2019-5-17 14:47:11
    @Select("select count(id) from t_commpany")
    Integer getcommonSum();


    List<common> getcommon(@Param("pageSize") Integer pageSize, @Param("start") Integer start,@Param("companyName") String companyName,@Param("companyProvince") String companyProvince,@Param("companyPhone") String companyPhone);

    /**
     * 删除 承运商 wzk
     * @param id
     */
    @Delete(" DELETE FROM T_COMMPANY WHERE id=#{id} ")
    void deletecommon(@Param("id") String id);

    /**
     * 查询详情
     * @param id
     * @return
     */
    @Select(" SELECT * FROM T_COMMPANY WHERE id=#{id}")
    HashMap<String, Object> finddetailsdialog(@Param("id") String id);

    @Select("select yuer from t_commpany where id=#{id}")
    String getSumByid(@Param("id") Integer id);

    @Update("update t_commpany set yuer=yuer-#{v}*1.001 where id=#{id}")
    void updatesum(@Param("v") double v, @Param("id") Integer commid);

    void addcaiwu(Tixian ti);

    void addtixianjilu(Tixian ti);
}
