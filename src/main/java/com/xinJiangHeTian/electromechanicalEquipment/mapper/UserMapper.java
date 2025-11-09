package com.xinJiangHeTian.electromechanicalEquipment.mapper;

import com.xinJiangHeTian.electromechanicalEquipment.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM users WHERE id = #{id}")
    User findById(Long id);

    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(String username);

    @Select("SELECT * FROM users WHERE email = #{email}")
    User findByEmail(String email);

    @Select("SELECT * FROM users")
    List<User> findAll();

    @Insert("INSERT INTO users (username, email, password, role, avatar_url, bio) " +
            "VALUES (#{username}, #{email}, #{password}, #{role}, #{avatarUrl}, #{bio})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Update("UPDATE users SET email = #{email}, avatar_url = #{avatarUrl}, bio = #{bio}, updated_at = NOW() WHERE id = #{id}")
    int update(User user);

    @Update("UPDATE users SET password = #{password} WHERE id = #{id}")
    int updatePassword(@Param("id") Long id, @Param("password") String password);

    @Delete("DELETE FROM users WHERE id = #{id}")
    int delete(Long id);

    /**
     * 用户头像更新
     * @param userId
     * @param avatarUrl
     * @param avatarThumbUrl
     * @param avatarOriginalName
     * @param avatarFileSize
     * @return
     */
    @Update("UPDATE users SET " +
            "avatar_url = #{avatarUrl}, " +
            "avatar_thumb_url = #{avatarThumbUrl}, " +
            "avatar_original_name = #{avatarOriginalName}, " +
            "avatar_file_size = #{avatarFileSize}, " +
            "avatar_updated_at = NOW(), " +
            "updated_at = NOW() " +
            "WHERE id = #{userId}")
    int updateAvatar(@Param("userId") Long userId,
                     @Param("avatarUrl") String avatarUrl,
                     @Param("avatarThumbUrl") String avatarThumbUrl,
                     @Param("avatarOriginalName") String avatarOriginalName,
                     @Param("avatarFileSize") Long avatarFileSize);
}
