/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wajiraenterprises.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author madusha mihiranga
 */
@Entity
@Table(name = "gdn")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gdn.findAll", query = "SELECT g FROM Gdn g")})
public class Gdn implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "number")
    private String number;
    @Column(name = "date")
    //@Temporal(TemporalType.DATE)
    private LocalDate date;
    @JoinColumn(name = "issuedby", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee issuedby;
    @JoinColumn(name = "shipment_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Shipment shipmentId;

    //@LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gdnId",fetch = FetchType.EAGER,orphanRemoval = true)
    private List<Gdnhasinventoryitem> gdnhasinventoryitemList;
    @OneToMany(mappedBy = "gdnId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Deliveryrequest> deliveryrequestList;

    @OneToMany(mappedBy = "gdnId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Customeracceptence> customeracceptenceList;

    public Gdn() {
    }

    public Gdn(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Employee getIssuedby() {
        return issuedby;
    }

    public void setIssuedby(Employee issuedby) {
        this.issuedby = issuedby;
    }

    public Shipment getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Shipment shipmentId) {
        this.shipmentId = shipmentId;
    }

    @XmlTransient
    public List<Gdnhasinventoryitem> getGdnhasinventoryitemList() {
        return gdnhasinventoryitemList;
    }

    public void setGdnhasinventoryitemList(List<Gdnhasinventoryitem> gdnhasinventoryitemList) {
        this.gdnhasinventoryitemList = gdnhasinventoryitemList;
    }

    @XmlTransient
    public List<Deliveryrequest> getDeliveryrequestList() {
        return deliveryrequestList;
    }

    public void setDeliveryrequestList(List<Deliveryrequest> deliveryrequestList) {
        this.deliveryrequestList = deliveryrequestList;
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
        if (!(object instanceof Gdn)) {
            return false;
        }
        Gdn other = (Gdn) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wajiraenterprises.entity.Gdn[ id=" + id + " ]";
    }

    @XmlTransient
    public List<Customeracceptence> getCustomeracceptenceList() {
        return customeracceptenceList;
    }

    public void setCustomeracceptenceList(List<Customeracceptence> customeracceptenceList) {
        this.customeracceptenceList = customeracceptenceList;
    }
    
}
