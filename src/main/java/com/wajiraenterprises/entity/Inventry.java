/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wajiraenterprises.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author madusha mihiranga
 */
@Entity
@Table(name = "inventry")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inventry.findAll", query = "SELECT i FROM Inventry i")})
public class Inventry implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "number")
    private String number;
    @Basic(optional = false)
    @Column(name = "storedqty")
    private Integer storedqty;
    @Column(name = "storedate")
   // @Temporal(TemporalType.DATE)
    private LocalDate storedate;
    @Column(name = "discpatcheddate")
   // @Temporal(TemporalType.DATE)
    private LocalDate discpatcheddate;


    @JoinColumn(name = "grnhasitem_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Grnhasitem grnhasitemId;

    @JoinColumn(name = "invetryitemstatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Invetryitemstatus invetryitemstatusId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inventryId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Gdnhasinventoryitem> gdnhasinventoryitemList;

/*    @JoinColumn(name = "shipment_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnore
    private Shipment shipmentId;*/

    public Inventry(Integer id,String number, Integer storedqty, LocalDate storedate, LocalDate discpatcheddate, Grnhasitem grnhasitemId, Invetryitemstatus invetryitemstatusId) {
        this.id = id;
        this.number = number;
        this.storedqty = storedqty;
        this.storedate = storedate;
        this.discpatcheddate = discpatcheddate;
        this.grnhasitemId = grnhasitemId;
        this.invetryitemstatusId = invetryitemstatusId;
    }

    public Inventry() {
    }

    public Inventry(Integer id) {
        this.id = id;
    }

    public Inventry(Integer id, String number, int storedqty) {
        this.id = id;
        this.number = number;
        this.storedqty = storedqty;
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

    public Integer getStoredqty() {
        return storedqty;
    }

    public void setStoredqty(Integer storedqty) {
        this.storedqty = storedqty;
    }

    public LocalDate getStoredate() {
        return storedate;
    }

    public void setStoredate(LocalDate storedate) {
        this.storedate = storedate;
    }

    public LocalDate getDiscpatcheddate() {
        return discpatcheddate;
    }

    public void setDiscpatcheddate(LocalDate discpatcheddate) {
        this.discpatcheddate = discpatcheddate;
    }

    public Grnhasitem getGrnhasitemId() {
        return grnhasitemId;
    }

    public void setGrnhasitemId(Grnhasitem grnhasitemId) {
        this.grnhasitemId = grnhasitemId;
    }

    public Invetryitemstatus getInvetryitemstatusId() {
        return invetryitemstatusId;
    }

    public void setInvetryitemstatusId(Invetryitemstatus invetryitemstatusId) {
        this.invetryitemstatusId = invetryitemstatusId;
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
        if (!(object instanceof Inventry)) {
            return false;
        }
        Inventry other = (Inventry) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wajiraenterprises.entity.Inventry[ id=" + id + " ]";
    }

  /*  public Shipment getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Shipment shipmentId) {
        this.shipmentId = shipmentId;
    }*/

    @XmlTransient
    public List<Gdnhasinventoryitem> getGdnhasinventoryitemList() {
        return gdnhasinventoryitemList;
    }

    public void setGdnhasinventoryitemList(List<Gdnhasinventoryitem> gdnhasinventoryitemList) {
        this.gdnhasinventoryitemList = gdnhasinventoryitemList;
    }
    
}
