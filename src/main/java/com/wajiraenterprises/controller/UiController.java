package com.wajiraenterprises.controller;


import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/ui")
public class UiController {


    @RequestMapping("/config")
    public ModelAndView config() {
        ModelAndView model = new ModelAndView();
        model.setViewName("config.html");
        return model;
    }

    @RequestMapping("/employeelogin")
    public ModelAndView loginEmployee() {
        ModelAndView model = new ModelAndView();
        model.setViewName("employeelogin.html");
        return model;
    }

    @RequestMapping("/customerlogin")
    public ModelAndView loginCustomer(){
        ModelAndView model = new ModelAndView();
        model.setViewName("customerlogin.html");
        return model;
    }

    /*@RequestMapping("/test")
    public ModelAndView test(){
        ModelAndView model = new ModelAndView();
        model.setViewName("comboaddtest.html");
        return model;
    }*/


    @RequestMapping("/mainwindow")
    public ModelAndView mainwindow(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("mainwindow.html", username, password);
    }


    @RequestMapping("/home")
    public ModelAndView home(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("employee-home.html", username, password);
    }


    @RequestMapping("/employee")
    public ModelAndView employee(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("employee.html", username, password);
    }


    @RequestMapping("/user")
    public ModelAndView user(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("user.html", username, password);
    }

    @RequestMapping("/previlage")
    public ModelAndView previlage(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("previlage.html", username, password);
    }


    @RequestMapping("/changepassword")
    public ModelAndView changepassword(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("changepassword.html", username, password);
    }


    @RequestMapping("/designation")
    public ModelAndView designation(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("designation.html", username, password);
    }

    @RequestMapping("/customer")
    public ModelAndView customer(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("customer.html", username, password);
    }

    @RequestMapping("/vehicle")
    public ModelAndView vehicle(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("vehicle.html", username, password);
    }

    @RequestMapping("/dashboard")
    public ModelAndView dashboard(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("dashboard.html", username, password);
    }

    @RequestMapping("/shipment")
    public ModelAndView addnewshipment(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("shipment.html", username, password);
    }

    @RequestMapping("/shipmentadd")
    public ModelAndView addshipment(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("add-shipment.html", username, password);
    }


    @RequestMapping("/shipment-documentdetail")
    public ModelAndView documentdetail(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("shipment-document-detail.html", username, password);
    }

    @RequestMapping("/hscodeandlicence")
    public ModelAndView hscodeandlicence(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("hscodeandlic.html", username, password);
    }

    @RequestMapping("/shippingcompany")
    public ModelAndView shippingcompany(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("shippingcompany.html", username, password);
    }

    @RequestMapping("/deliveryagent")
    public ModelAndView deliveryagent(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("deliveryagent.html", username, password);
    }

    @RequestMapping("/clearingprocess")
    public ModelAndView clearingprocess(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("clearingprocess.html", username, password);
    }

    @RequestMapping("/collectdo")
    public ModelAndView collectdo(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("collectdo.html", username, password);
    }

    @RequestMapping("/cusdec")
    public ModelAndView cesdec(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("cusdec.html", username, password);
    }

    @RequestMapping("/viewdeliveryorder")
    public ModelAndView viewdeliveryorder(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("viewdeliveryorder.html", username, password);
    }

    @RequestMapping("/viewcusdec")
    public ModelAndView viewcusdec(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("viewcusdec.html", username, password);
    }

    @RequestMapping("/paytaxes")
    public ModelAndView paytaxes(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("paytaxes.html", username, password);
    }

    @RequestMapping("/viewpaytaxes")
    public ModelAndView viewtaxpay(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("viewpaytaxes.html", username, password);
    }

    @RequestMapping("/panelexamination")
    public ModelAndView panelexamination(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("panelexamination.html", username, password);
    }

    @RequestMapping("/viewpanelexamination")
    public ModelAndView viewpanelexamination(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("viewpanelexamination.html", username, password);
    }

    @RequestMapping("/requestdelivery")
    public ModelAndView requestdelivery(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("requestdelivery.html", username, password);
    }

    @RequestMapping("/confirmdelivery")
    public ModelAndView confirmdelivery(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("confirmdelivery.html", username, password);
    }

    @RequestMapping("/vehiclerentelcompany")
    public ModelAndView vehiclerentelcompany(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("vehiclerentelcompany.html", username, password);
    }

    @RequestMapping("/delivery")
    public ModelAndView delivery(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("delivery.html", username, password);
    }

    @RequestMapping("/grn")
    public ModelAndView grn(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("grn.html", username, password);
    }


    @RequestMapping("/inventory")
    public ModelAndView inventory(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("inventory.html", username, password);
    }

    @RequestMapping("/gdn")
    public ModelAndView gdn(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("gdn.html", username, password);
    }

    @RequestMapping("/customeracceptence")
    public ModelAndView customeracceptence(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("customeracceptence.html", username, password);
    }

    @RequestMapping("/invoice")
    public ModelAndView invoice(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("invoice.html", username, password);
    }

    @RequestMapping("/payment")
    public ModelAndView payment(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("payment.html", username, password);
    }

    @RequestMapping("/cheque")
    public ModelAndView cheque(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("cheque.html", username, password);
    }

    @RequestMapping("/credit")
    public ModelAndView credit(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("credit.html", username, password);
    }

    @RequestMapping("/activitylog")
    public ModelAndView activitylog(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        return getModelView("activitylog.html", username, password);
    }



    @RequestMapping("/customerhome")
    public ModelAndView customerhome() {
        ModelAndView model = new ModelAndView();
        model.setViewName("customerhome.html");
        return model;
    }

    @RequestMapping("/customerhomenew")
    public ModelAndView customerhomenew() {
        ModelAndView model = new ModelAndView();
        model.setViewName("customerhomenew.html");
        return model;
    }


    public ModelAndView getModelView(String page, String username, String password) {

        ModelAndView model = new ModelAndView();

        if (AuthProvider.isUser(username, password)) {

            model.setViewName(page);
        } else {
            model.setViewName("noprivilage.html");

        }
        return model;

    }


}