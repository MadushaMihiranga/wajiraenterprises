package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BranchDao extends JpaRepository<Branch,Integer> {

    @Query(value="SELECT new Branch (b.id,b.code,b.name,b.bankId) FROM Branch b")
    List<Branch> list();

    @Query(value="SELECT new Branch (b.id,b.code,b.name,b.bankId) FROM Branch b where b.bankId.id=:bankId")
    List<Branch> listByBank( @Param("bankId") Integer bankId);

    @Query("SELECT b FROM Branch b WHERE b.name= :name")
    Branch findByName(@Param("name")String name);

    @Query("SELECT b FROM Branch b WHERE b.code= :code")
    Branch findByCode(@Param("code")String code);

}
