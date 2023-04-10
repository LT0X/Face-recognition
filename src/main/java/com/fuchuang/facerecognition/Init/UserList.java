package com.fuchuang.facerecognition.Init;


import com.fuchuang.facerecognition.dao.UserDao;
import com.fuchuang.facerecognition.model.UserBean;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;


@Component
public class UserList{

    @Autowired
    private UserDao userDao;

    private Map<Integer,UserBean> map;


    @PostConstruct
    public void InitUserMap(){
        map = userDao.GetAllUSerImage();
    }

    public Map<Integer, UserBean> getMap() {
        return map;
    }

    public void addUser(Integer id,UserBean userBean){
        map.put(id,userBean);
    }

}
