package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Placeofloadingordischarge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceofloadingordischargeDao extends JpaRepository<Placeofloadingordischarge,Integer> {

    @Query(value="SELECT new Placeofloadingordischarge(p.id,p.name,p.countryId,p.modsId) FROM Placeofloadingordischarge p")
    List<Placeofloadingordischarge> list();

    @Query(value="SELECT new Placeofloadingordischarge (p.id,p.name,p.countryId,p.modsId) FROM Placeofloadingordischarge p where p.modsId.id=:modsId and p.countryId.id =:countryId")
    List<Placeofloadingordischarge> listByModIdAndCountryId( @Param("modsId") Integer modsId,@Param("countryId") Integer countryId);

    @Query("SELECT p FROM Placeofloadingordischarge p WHERE p.name= :name")
    Placeofloadingordischarge findByName(@Param("name")String name);



}
