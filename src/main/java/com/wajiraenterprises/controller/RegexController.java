package com.wajiraenterprises.controller;

import com.wajiraenterprises.entity.*;
import com.wajiraenterprises.util.RegexPattern;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;

@RestController
@RequestMapping("/regexes")
public class RegexController {

    @RequestMapping(value = "/employee", produces = "application/json")
    public HashMap<String, HashMap<String, String>> employee() {
        return getRegex(new Employee());
    }

    @RequestMapping(value = "/user", produces = "application/json")
    public HashMap<String, HashMap<String, String>> user() {
        return getRegex(new User());
    }

    @RequestMapping(value = "/designation", produces = "application/json")
    public HashMap<String, HashMap<String, String>> designation() {
        return getRegex(new Designation());
    }

    @RequestMapping(value = "/company", produces = "application/json")
    public HashMap<String, HashMap<String, String>> customer() {
        return getRegex(new Company());
    }

    @RequestMapping(value = "/vehicle", produces = "application/json")
    public HashMap<String, HashMap<String, String>> vehicle() { return getRegex(new Vehicle()); }

    @RequestMapping(value = "/shippingcompany", produces = "application/json")
    public HashMap<String, HashMap<String, String>> shippingcompany() { return getRegex(new Shippingcompany()); }

    @RequestMapping(value = "/deliveryagent", produces = "application/json")
    public HashMap<String, HashMap<String, String>> deliveragent() { return getRegex(new Deliveryagent()); }

    @RequestMapping(value = "/shipment", produces = "application/json")
    public HashMap<String, HashMap<String, String>> shipment() { return getRegex(new Shipment()); }

    @RequestMapping(value = "/item", produces = "application/json")
    public HashMap<String, HashMap<String, String>> item() { return getRegex(new Item()); }

    @RequestMapping(value = "/letterifcredit", produces = "application/json")
    public HashMap<String, HashMap<String, String>> letterofcredit() { return getRegex(new Letterofcredit()); }

    @RequestMapping(value = "/customeracceptenc", produces = "application/json")
    public HashMap<String, HashMap<String, String>> customeracceptence() { return getRegex(new Customeracceptence()); }

    @RequestMapping(value = "/collectdo", produces = "application/json")
    public HashMap<String, HashMap<String, String>> collectdo() { return getRegex(new Collectdo()); }

    @RequestMapping(value = "/cusdec", produces = "application/json")
    public HashMap<String, HashMap<String, String>> cusdec() { return getRegex(new Cusdec()); }

    @RequestMapping(value = "/paytaxes", produces = "application/json")
    public HashMap<String, HashMap<String, String>> paytaxes() { return getRegex(new Paytaxes()); }

    @RequestMapping(value = "/panelexamination", produces = "application/json")
    public HashMap<String, HashMap<String, String>> panelexamination() { return getRegex(new Panelexamination()); }

    @RequestMapping(value = "/deliveryrequest", produces = "application/json")
    public HashMap<String, HashMap<String, String>> deliveryrequest() { return getRegex(new Deliveryrequest()); }




    public static <T> HashMap<String, HashMap<String, String>> getRegex(T t) {
        try {
            Class<? extends Object> aClass = t.getClass();
            HashMap<String, HashMap<String, String>> regex = new HashMap<>();

            for (Field field : aClass.getDeclaredFields()) {

                Annotation[] annotations = field.getDeclaredAnnotations();

                for (Annotation annotation : annotations) {

                    if (annotation instanceof Pattern) {
                        Pattern myAnnotation = (Pattern) annotation;
                        HashMap<String, String> map = new HashMap<>();
                        map.put("regex", myAnnotation.regexp());
                        map.put("message", myAnnotation.message());
                        regex.put(field.getName(), map);
                    }

                    if (annotation instanceof RegexPattern) {
                        RegexPattern myAnnotation2 = (RegexPattern) annotation;
                        HashMap<String, String> map2 = new HashMap<>();
                        map2.put("regex", myAnnotation2.regexp());
                        map2.put("message", myAnnotation2.message());
                        regex.put(field.getName(), map2);
                    }
                }
            }
            return regex;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
