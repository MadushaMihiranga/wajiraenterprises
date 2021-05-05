package com.wajiraenterprises.controller;



import com.wajiraenterprises.entity.ReportEnitity1;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ReportProvider {

    public static List<ReportEntitySystemAccessAnalysis> getSystemAccessAnalysisReport(LocalDateTime startdate, LocalDateTime enddate) {


        try {
            AuthProvider.setConnection();
            Statement st = AuthProvider.connection.createStatement();
            String query = "SELECT d.name as label, count(*) as value FROM wajiraenterprises.sessionlog as s, wajiraenterprises.user as u, wajiraenterprises.employee as e, wajiraenterprises.designation as d where s.user_id=u.id and u.employee_id=e.id and e.designation_id=d.id and s.logintime between '"+startdate+"' and '"+enddate+"' group by d.id ;";
            ResultSet rs = st.executeQuery(query);

            List<ReportEntitySystemAccessAnalysis> list = new ArrayList<>();

            while (rs.next()) {
                ReportEntitySystemAccessAnalysis report = new ReportEntitySystemAccessAnalysis(rs.getString("label"), rs.getInt("value"));
                list.add(report);
            }
            return list;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }


    public static List<ReportEnitity1> getcustomershipmentreport() {
        try {
            AuthProvider.setConnection();
            Statement st = AuthProvider.connection.createStatement();
            String query = "SELECT c.name as label, count(*) as value from wajiraenterprises.company as c,wajiraenterprises.shipment as s where s.company_id = c.id group by c.id;";
            ResultSet rs = st.executeQuery(query);

            List<ReportEnitity1> list = new ArrayList<>();

            while (rs.next()) {
                ReportEnitity1 report = new ReportEnitity1(rs.getString("label"), rs.getInt("value"));
                list.add(report);
            }
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public static List<ReportEnitity1> getShipmentModes() {
        try {
            AuthProvider.setConnection();
            Statement st = AuthProvider.connection.createStatement();
            String query = "SELECT m.name as label, count(*) as value from wajiraenterprises.mods as m,wajiraenterprises.shipment as s where s.mods_id = m.id group by m.id;";
            ResultSet rs = st.executeQuery(query);

            List<ReportEnitity1> list = new ArrayList<>();
            while (rs.next()) {
                ReportEnitity1 report = new ReportEnitity1(rs.getString("label"), rs.getInt("value"));
                list.add(report);
            }
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            return null;
        }
    }



    public static List<ReportEnitity1> getShipmentModesadnCustomer(Integer companyId ) {
        try {
            AuthProvider.setConnection();
            Statement st = AuthProvider.connection.createStatement();
            String query = "SELECT m.name as label, count(*) as value from wajiraenterprises.mods as m,wajiraenterprises.shipment as s where s.mods_id = m.id and s.company_id = "+companyId+" group by m.id;";
            ResultSet rs = st.executeQuery(query);

            List<ReportEnitity1> list = new ArrayList<>();
            while (rs.next()) {
                ReportEnitity1 report = new ReportEnitity1(rs.getString("label"), rs.getInt("value"));
                list.add(report);
            }
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            return null;
        }
    }





    public static List<ReportEnitity1> getShipmentByClearingProcessStatus() {
        try {
            AuthProvider.setConnection();
            Statement st = AuthProvider.connection.createStatement();
            String query = "select clearingprocessstatus.name as label,count(clearingprocess.clearingprocessstatus_id) as value from clearingprocessstatus left join clearingprocess on clearingprocessstatus.id = clearingprocess.clearingprocessstatus_id group by clearingprocessstatus.id;";
            ResultSet rs = st.executeQuery(query);

            List<ReportEnitity1> list = new ArrayList<>();
            while (rs.next()) {
                ReportEnitity1 report = new ReportEnitity1(rs.getString("label"), rs.getInt("value"));
                list.add(report);
            }
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            return null;
        }
    }

    //select concat(vehicle.number,"  -  ",vehicle.rgno) as label,count(wajiraenterprises.deliveryconfirmation.vehicle_id) as value from vehicle left join deliveryconfirmation on vehicle.id = deliveryconfirmation.vehicle_id group by vehicle.id;
    public static List<ReportEnitity1> getDeliveryByVehicle() {
        try {
            AuthProvider.setConnection();
            Statement st = AuthProvider.connection.createStatement();
            String query = "select concat(vehicle.number,\"  -  \",vehicle.rgno) as label,count(wajiraenterprises.deliveryconfirmation.vehicle_id) as value from vehicle left join deliveryconfirmation on vehicle.id = deliveryconfirmation.vehicle_id group by vehicle.id;";
            ResultSet rs = st.executeQuery(query);

            List<ReportEnitity1> list = new ArrayList<>();
            while (rs.next()) {
                ReportEnitity1 report = new ReportEnitity1(rs.getString("label"), rs.getInt("value"));
                list.add(report);
            }
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            return null;
        }
    }

    //SELECT c.fullname as label, count(*) as value from wajiraenterprises.employee as c,wajiraenterprises.deliveryconfirmation as s where s.driver = c.id group by c.id;
    public static List<ReportEnitity1> getDeliveryByDriver() {
        try {
            AuthProvider.setConnection();
            Statement st = AuthProvider.connection.createStatement();
            String query = "SELECT concat(c.number,\" - \",c.fullname) as label, count(*) as value from wajiraenterprises.employee as c,wajiraenterprises.deliveryconfirmation as s where s.driver = c.id group by c.id;";
            ResultSet rs = st.executeQuery(query);

            List<ReportEnitity1> list = new ArrayList<>();
            while (rs.next()) {
                ReportEnitity1 report = new ReportEnitity1(rs.getString("label"), rs.getInt("value"));
                list.add(report);
            }
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            return null;
        }
    }

    //SELECT concat(c.number," - ",c.fullname) as label, count(*) as value from wajiraenterprises.employee as c,wajiraenterprises.shipment as s where s.clearingclerk = c.id group by c.id;
    public static List<ReportEnitity1> getShipmentByClearingClerk() {
        try {
            AuthProvider.setConnection();
            Statement st = AuthProvider.connection.createStatement();
            String query = "SELECT concat(c.number,\" - \",c.fullname) as label, count(*) as value from wajiraenterprises.employee as c,wajiraenterprises.shipment as s where s.clearingclerk = c.id group by c.id;";
            ResultSet rs = st.executeQuery(query);

            List<ReportEnitity1> list = new ArrayList<>();
            while (rs.next()) {
                ReportEnitity1 report = new ReportEnitity1(rs.getString("label"), rs.getInt("value"));
                list.add(report);
            }
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public static List<shipmenttimerange> getshipmetndaterange(LocalDateTime startdate, LocalDateTime enddate) {


        try {
            AuthProvider.setConnection();
            Statement st = AuthProvider.connection.createStatement();
            String query = "select number,dateofregisterd from wajiraenterprises.shipment where shipment.dateofregisterd between '"+startdate+"' and '"+enddate+"';";
            ResultSet rs = st.executeQuery(query);

            List<shipmenttimerange> list = new ArrayList<>();

            while (rs.next()) {
                shipmenttimerange report = new shipmenttimerange(rs.getString("number"), rs.getString("dateofregisterd"));
                list.add(report);
            }
            return list;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

}



