package com.yyt.secondkill.dao;

import com.yyt.secondkill.entity.SecondKillUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SecondKillUserDao {

    @Select("select * from second_kill_user where id = #{id}")
    SecondKillUser getById(@Param("id") long id);

    @Update("update second_kill_user set password = #{password} where id = #{id}")
    void update(SecondKillUser secondKillUser);
}