package com.wajiraenterprises.controller;

import com.wajiraenterprises.dao.ItemDao;
import com.wajiraenterprises.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemController {

    @Autowired
    private ItemDao itemDao;

    @RequestMapping(value = "/item/listByShipmentNumber", method = RequestMethod.GET,params = "number",produces = "application/json")
    public List<Item> items(@RequestParam("number") String number) { return itemDao.listByShipmentNumber(number); }



}
