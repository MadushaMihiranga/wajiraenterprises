package com.wajiraenterprises.controller;


import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/ui/report")
public class ReportUiController {

    @RequestMapping("/systemaccessanalysis")
    public ModelAndView login(){
        ModelAndView model = new ModelAndView();
        model.setViewName("systemaccessanalysis.html");
        return model;
    }

    @RequestMapping("/customershipment")
    public ModelAndView customershipment(){
        ModelAndView model = new ModelAndView();
        model.setViewName("customershipment.html");
        return model;
    }

    @RequestMapping("/shipmentreport")
    public ModelAndView shipmentreport(){
        ModelAndView model = new ModelAndView();
        model.setViewName("shipmentreport.html");
        return model;
    }

    @RequestMapping("/deliverybyvehicle")
    public ModelAndView deliverybyvehicle(){
        ModelAndView model = new ModelAndView();
        model.setViewName("deliveryByVehicle.html");
        return model;
    }

    @RequestMapping("/deliverybydriver")
    public ModelAndView deliverybydriver(){
        ModelAndView model = new ModelAndView();
        model.setViewName("deliverybydriver.html");
        return model;
    }

    @RequestMapping("/shipmentbyclearingclerk")
    public ModelAndView shipmentbyclearingclerk(){
        ModelAndView model = new ModelAndView();
        model.setViewName("shipmentbyclearingclerk.html");
        return model;
    }

    @RequestMapping("/shipmentbycustomer")
    public ModelAndView shipmentbycustomer(){
        ModelAndView model = new ModelAndView();
        model.setViewName("shipmentbycustomer.html");
        return model;
    }

    @RequestMapping("/shipmentbystatus")
    public ModelAndView shipmentbystatus(){
        ModelAndView model = new ModelAndView();
        model.setViewName("shipmentbystatus.html");
        return model;
    }
@RequestMapping("/shipmenttimerange")
public ModelAndView shipmenttimerange(){
    ModelAndView model = new ModelAndView();
    model.setViewName("shipmenttimerange.html");
    return model;
}


}