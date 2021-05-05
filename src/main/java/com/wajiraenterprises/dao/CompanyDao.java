package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyDao extends JpaRepository<Company,Integer> {

    @Query(value = "SELECT c FROM Company c ORDER BY c.id DESC")
    Page<Company> findAll(Pageable of);

    @Query(value = "SELECT new Company (c.id,c.name) FROM Company c")
    List<Company> list();

    @Query("SELECT c FROM Company c WHERE c.number= :number")
    Company findByNumber(@Param("number")String number);

    @Query("SELECT c FROM Company c WHERE c.name= :name")
    Company findByName(@Param("name")String number);

    @Query(value="SELECT MAX (c.number) from Company c")
    String numbermax();

}
