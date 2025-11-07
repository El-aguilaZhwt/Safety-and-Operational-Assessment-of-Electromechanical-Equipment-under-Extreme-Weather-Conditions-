package com.xinJiangHeTian.electromechanicalEquipment.mapper;

import com.xinJiangHeTian.electromechanicalEquipment.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(String username);

    @Insert("INSERT INTO users(username, password, identity) VALUES(#{username}, #{password}, #{identity})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Select("SELECT COUNT(*) FROM users WHERE username = #{username}")
    int existsByUsername(String username);
}
