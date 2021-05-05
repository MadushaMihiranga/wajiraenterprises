/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wajiraenterprises.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author madusha mihiranga
 */
@Entity
@Table(name = "customeracceptence")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customeracceptence.findAll", query = "SELECT c FROM Customeracceptence c")})
public class Customeracceptence implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "acceptedpersonname")
    @Pattern(regexp = "^([A-Z][a-z]*[.]?[\\s]?)*([A-Z][a-z]*)$", message = "Invalid name")
    private String acceptedpersonname;
    @Column(name = "acceptedpersonnic")
    @Pattern(regexp = "^(([\\d]{9}[vVxX])|([\\d]{12}))$", message = "Invalid NIC")
    private String acceptedpersonnic;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Lob
    @Column(name = "note")
    private String note;
    @JoinColumn(name = "acceptencestatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Acceptencestatus acceptencestatusId;
    @JoinColumn(name = "delivery_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Delivery deliveryId;
    @JoinColumn(name = "gdn_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Gdn gdnId;
    @JoinColumn(name = "shipment_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Shipment shipmentId;

    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee employeeId;


    public Customeracceptence() {
    }

    public Customeracceptence(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAcceptedpersonname() {
        return acceptedpersonname;
    }

    public void setAcceptedpersonname(String acceptedpersonname) {
        this.acceptedpersonname = acceptedpersonname;
    }

    public String getAcceptedpersonnic() {
        return acceptedpersonnic;
    }

    public void setAcceptedpersonnic(String acceptedpersonnic) {
        this.acceptedpersonnic = acceptedpersonnic;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Acceptencestatus getAcceptencestatusId() {
        return acceptencestatusId;
    }

    public void setAcceptencestatusId(Acceptencestatus acceptencestatusId) {
        this.acceptencestatusId = acceptencestatusId;
    }

    public Delivery getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Delivery deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Gdn getGdnId() {
        return gdnId;
    }

    public void setGdnId(Gdn gdnId) {
        this.gdnId = gdnId;
    }

    public Shipment getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Shipment shipmentId) {
        this.shipmentId = shipmentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customeracceptence)) {
            return false;
        }
        Customeracceptence other = (Customeracceptence) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wajiraenterprises.entity.Customeracceptence[ id=" + id + " ]";
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }


}
