package com.fuchuang.facerecognition.dao;


import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

@Repository
public interface WarningDao {


    @Insert("insert into warning values(null,#{context},null)")
    int AddWarning(String context);


}
