package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Clearingprocess;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClearingprocessDao extends JpaRepository<Clearingprocess,Integer> {

    @Query("SELECT c FROM Clearingprocess c WHERE c.shipmentId.number= :number")
    Clearingprocess findByNumber(@Param("number")String number);

    @Query(value = "SELECT c FROM Clearingprocess c ORDER BY c.id DESC")
    Page<Clearingprocess> findAll(Pageable of);

    @Query(value="SELECT new Clearingprocess (c.id,c.startdate,c.endate,c.clearingprocessstatusId,c.shipmentId) FROM Clearingprocess c where c.shipmentId.companyId.id=:companyId")
    List<Clearingprocess> listByCompanyId(@Param("companyId") Integer companyId);

}
