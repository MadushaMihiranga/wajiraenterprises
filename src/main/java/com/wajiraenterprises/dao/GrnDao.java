package com.wajiraenterprises.dao;


import com.wajiraenterprises.entity.Clearingprocess;
import com.wajiraenterprises.entity.Cusdec;
import com.wajiraenterprises.entity.Grn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface GrnDao extends JpaRepository<Grn,Integer> {

    @Query("SELECT g FROM Grn g ORDER BY g.id DESC")
    Page<Grn> findAll(Pageable of);

    @Query(value="SELECT MAX (g.number) from Grn g")
    String numbermax();

    @Query("SELECT g FROM Grn g WHERE g.deliveryId.number= :number")
    Grn findByDeliveryNo(@Param("number") String number);





}
