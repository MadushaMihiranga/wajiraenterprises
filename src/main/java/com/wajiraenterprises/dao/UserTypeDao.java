package com.wajiraenterprises.dao;


import com.wajiraenterprises.entity.Civilstatus;
import com.wajiraenterprises.entity.Usertype;
import org.hibernate.usertype.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserTypeDao extends JpaRepository<Usertype, Integer>
{

    @Query(value="SELECT new Usertype(ut.id,ut.name) FROM Usertype ut")
    List<Usertype> list();


}