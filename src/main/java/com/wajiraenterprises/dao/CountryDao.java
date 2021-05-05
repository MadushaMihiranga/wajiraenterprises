package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CountryDao extends JpaRepository<Country,Integer> {

    @Query(value = "SELECT new Country (c.id,c.name,c.numericcode,c.twodigitcode) FROM Country c order by c.name asc")
    List<Country> list();

    @Query("SELECT c FROM Country c WHERE c.name= :name  ")
    Country findByName(@Param("name")String name);

    @Query("SELECT c FROM Country c WHERE c.numericcode= :numericcode")
    Country findByNumericCode(@Param("numericcode")String numericcode);

    @Query("SELECT c FROM Country c WHERE c.twodigitcode= :twodigitcode")
    Country findByTwoDigitCode(@Param("twodigitcode")String twodigitcode);
}
