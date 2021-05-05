package com.wajiraenterprises.entity;

public class ReportEnitity1 {

    String label;
    Integer value;

    public ReportEnitity1(String label, Integer value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
