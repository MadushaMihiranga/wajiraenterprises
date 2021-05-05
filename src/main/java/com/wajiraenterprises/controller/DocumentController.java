package com.wajiraenterprises.controller;


import com.wajiraenterprises.dao.DocumentDao;
import com.wajiraenterprises.entity.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DocumentController {

    @Autowired
    private DocumentDao dao;

    @RequestMapping(value = "/document/list", method = RequestMethod.GET, produces = "application/json")
    public List<Document> document() { return dao.list(); }

}
