package com.fuchuang.facerecognition.dao;


import com.fuchuang.facerecognition.model.User;
import com.fuchuang.facerecognition.model.UserBean;
import com.fuchuang.facerecognition.model.UserFace;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface UserDao {

    @MapKey("id")
    @Select("select id,name,face_image from user")
    Map<Integer,UserBean> GetAllUSerImage();


    @Insert("insert into user value (null,#{name},#{faceImage},null,#{image})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int  AddUser(User user);


    @Select("select id,name,image from user")
    List<UserFace> GetUserFace();

    @Delete("delete from user where id = #{id}")
    int DeleteUserFace(Integer id);


    @Select("select image from user where id = #{id}")
    String GetUserImage(Integer id);


}
