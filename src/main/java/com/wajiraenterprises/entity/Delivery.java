/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wajiraenterprises.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author madusha mihiranga
 */
@Entity
@Table(name = "delivery")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Delivery.findAll", query = "SELECT d FROM Delivery d")})
public class Delivery implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "number")
    private String number;
    @JoinColumn(name = "deliveryconfirmation_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Deliveryconfirmation deliveryconfirmationId;
    @JoinColumn(name = "deliverystatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Deliverystatus deliverystatusId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deliveryId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Grn> grnList;

    @OneToMany(mappedBy = "deliveryId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Customeracceptence> customeracceptenceList;

    public Delivery() {
    }

    public Delivery(Integer id) {
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

    public Deliveryconfirmation getDeliveryconfirmationId() {
        return deliveryconfirmationId;
    }

    public void setDeliveryconfirmationId(Deliveryconfirmation deliveryconfirmationId) {
        this.deliveryconfirmationId = deliveryconfirmationId;
    }

    public Deliverystatus getDeliverystatusId() {
        return deliverystatusId;
    }

    public void setDeliverystatusId(Deliverystatus deliverystatusId) {
        this.deliverystatusId = deliverystatusId;
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
        if (!(object instanceof Delivery)) {
            return false;
        }
        Delivery other = (Delivery) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wajiraenterprises.entity.Delivery[ id=" + id + " ]";
    }

    @XmlTransient
    public List<Customeracceptence> getCustomeracceptenceList() {
        return customeracceptenceList;
    }

    public void setCustomeracceptenceList(List<Customeracceptence> customeracceptenceList) {
        this.customeracceptenceList = customeracceptenceList;
    }
    
}
