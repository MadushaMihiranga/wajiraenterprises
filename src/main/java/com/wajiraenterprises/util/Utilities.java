package com.wajiraenterprises.util;

import com.wajiraenterprises.entity.*;


import javax.validation.constraints.NotNull;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

/**
 * @author Madusha Mihiranga
 * @version 1.0
 * @since 2019-10-11
 */

public class Utilities {

    /**
     * This Function Used to Create User Active Logs
     * @param user        = user who login to the system   [ eg: userDao.findByUsername(username) ]
     * @param module      = the module that the activity belongs to   [ eg: moduleDao.findByName(ModuleList.EMPLOYEE.toString() ]
     * @param type        = activity type  ( !Important. can only use these values: "ADD", "UPDATE", "DELETE")  [ eg: activitytypeDao.findByName("UPDATE") ]
     * @param description = description of the activity
     */

    public static Activitylog CreateActivityLog(User user, Module module, Activitytype type, String description) {
        try {
            //System.out.println("activitylog");
            Activitylog activitylog = new Activitylog();
            String typename;
            String descriptionvalue;
            String start;
            final Set <Character> vowels= new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u'));
            if (type.getId()==1){
                typename = type.getName()+"ED";
                descriptionvalue = "A New "+Utilities.FLtoUpperCase(module.getName())+" "+description+" was "+Utilities.FLtoUpperCase(typename);
            }else {
                if (vowels.contains(Character.toLowerCase(module.getName().toLowerCase().charAt(0)))){
                    start = "An ";
                }else {
                    start = "A ";
                }
                typename = type.getName()+"D";
                descriptionvalue = start+Utilities.FLtoUpperCase(module.getName())+" "+description+" "+" was "+Utilities.FLtoUpperCase(typename);
            }
            activitylog.setDescription(descriptionvalue);
            activitylog.setUserId(user);
            activitylog.setActivitytypeId(type);
            activitylog.setModuleId(module);
            activitylog.setDate(LocalDate.now());
            activitylog.setTime(LocalTime.now());
            return activitylog;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This Function Convert First Letter Of a String to Upper case
     * @param text = String not null
     * */

    public static String FLtoUpperCase(String text){
        StringBuilder stringBuilder = new StringBuilder(text.toLowerCase());
        stringBuilder.setCharAt(0,Character.toUpperCase(stringBuilder.charAt(0)));
        text = stringBuilder.toString();
        return text;
    }

    /**
     *This Function return unique code [ format : ###-19101101  ###--code  19-last 2 digits of the year, 10-current month, 11-current day, 01 - index ]
     * @param currentMaxNumber = current biggest value in database
     * @param code             = String Value that want to concatenate with generated number
     * @param format           = format of the code ( !Important. can only use these values: "yy-mm-dd", "yy" , "" )
     * @param length
     * */

    public static String generateNumber(String currentMaxNumber, String code, String format, Integer length){
        String num;
        Date date = new Date();
        String  getMonth =Integer.toString(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue());
        String getDate = Integer.toString(Calendar.getInstance().get(Calendar.DATE));
        String getYear = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));

        if (Integer.toString(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue()).length()==1){
            getMonth = "0"+getMonth;
        }
        if (Integer.toString(Calendar.getInstance().get(Calendar.DATE)).length()==1){
            getDate = "0"+getMonth;
        }
        if (currentMaxNumber != null){
            Scanner number1 = new Scanner(currentMaxNumber).useDelimiter("[^0-9]+");
            Scanner number2 = new Scanner(currentMaxNumber).useDelimiter("[^0-9]+");
            Scanner number3 = new Scanner(currentMaxNumber).useDelimiter("[^0-9]+");
            boolean year = Integer.toString(number1.nextInt()).substring(0,2).equals(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)).substring(2));
            boolean month = Integer.toString(number2.nextInt()).substring(2,4).equals(getMonth);
            boolean day = Integer.toString(number3.nextInt()).substring(4,6).equals(getDate);
            if (year && month && day){
                Scanner number = new Scanner(currentMaxNumber).useDelimiter("[^0-9]+");
                num = Integer.toString(number.nextInt()+1);
            }else {
                num = getYear.substring(2)+getMonth+getDate+"01";
            }
        }else {
            num = getYear.substring(2)+getMonth+getDate+"01";
        }
        return code+num;
    }


   // @org.jetbrains.annotations.Nullable
    public static Notification CreateNotification(String content, Integer type, Employee employee){

        try {
            Notification notification = new Notification();
            Notificationtype notificationtype = new Notificationtype(type);
            Notificationstatus notificationstatus = new Notificationstatus(1);

            notification.setEmployeeId(employee);
            notification.setContent(content);
            notification.setNotificationtypeId(notificationtype);
            notification.setNotificationstatusId(notificationstatus);
            notification.setDate(LocalDate.now());
            notification.setTime(LocalTime.now());

            return notification;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }




}
