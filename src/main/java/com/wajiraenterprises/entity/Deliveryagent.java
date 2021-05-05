/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wajiraenterprises.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author madusha mihiranga
 */
@Entity
@Table(name = "deliveryagent")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deliveryagent.findAll", query = "SELECT d FROM Deliveryagent d")})
public class Deliveryagent implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deliveryagentId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Collectdo> collectdoList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    @Pattern(regexp = "^[A-Z]([a-zA-Z0-9]|[- @\\.#&!()])*$", message = "Invalid Company Name")
    private String name;
    @Lob
    @Column(name = "address")
    @Pattern(regexp = "^([\\w\\/\\-,\\s]{2,})$", message = "Invalid Address")
    private String address;
    @Column(name = "contactno")
    @Pattern(regexp = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$", message = "Invalid Contact Number")
    private String contactno;
    @Column(name = "email")
    @Pattern(regexp = "^([a-zA-Z0-9]|[- @\\.#&!().])*$", message = "Invalid Email Address")
    private String email;
    @Column(name = "fax")
    @Pattern(regexp = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$", message = "Invalid Fax Number")
    private String fax;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deliveryagentId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Shipment> shipmentList;

    public Deliveryagent() {
    }

    public Deliveryagent(Integer id,String name, String address, String contactno) {
        this.id= id;
        this.name = name;
        this.address = address;
        this.contactno = contactno;
    }

    public Deliveryagent(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @XmlTransient
    public List<Shipment> getShipmentList() {
        return shipmentList;
    }

    public void setShipmentList(List<Shipment> shipmentList) {
        this.shipmentList = shipmentList;
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
        if (!(object instanceof Deliveryagent)) {
            return false;
        }
        Deliveryagent other = (Deliveryagent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wajiraenterprises.entity.Deliveryagent[ id=" + id + " ]";
    }

    @XmlTransient
    public List<Collectdo> getCollectdoList() {
        return collectdoList;
    }

    public void setCollectdoList(List<Collectdo> collectdoList) {
        this.collectdoList = collectdoList;
    }
    
}
