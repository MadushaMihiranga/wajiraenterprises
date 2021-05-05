package com.wajiraenterprises.dao;

import com.wajiraenterprises.entity.Item;
import com.wajiraenterprises.entity.ItemHasLicence;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ItemHasLicenceDao extends JpaRepository<ItemHasLicence,Integer> {
   // void delete(Item itm);
}
