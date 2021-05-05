package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Servicesquotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServicesquotationDao extends JpaRepository<Servicesquotation,Integer> {

    @Query(value = "SELECT new Servicesquotation (s.id,s.value,s.containertypeId,s.modsId,s.servicesId) FROM Servicesquotation s")
    List<Servicesquotation> list();

    @Query(value="SELECT new Servicesquotation (s.id,s.value,s.containertypeId,s.modsId,s.servicesId) FROM Servicesquotation s WHERE s.modsId.id= :modid AND s.containertypeId.id= :containerid AND s.servicesId.id = :serviceid")
    Servicesquotation findQuation( @Param("modid") Integer modid, @Param("containerid") Integer containerid,@Param("serviceid") Integer serviceid);

}
