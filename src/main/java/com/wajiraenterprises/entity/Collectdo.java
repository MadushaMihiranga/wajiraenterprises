/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wajiraenterprises.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author madusha mihiranga
 */
@Entity
@Table(name = "collectdo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Collectdo.findAll", query = "SELECT c FROM Collectdo c")})
public class Collectdo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
   /* @Column(name = "number")
    private String number;*/
    @Column(name = "issuedby")
    @Pattern(regexp = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$", message = "Name Is Invalid")
    private String issuedby;
    @JoinColumn(name = "clearingprocess_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Clearingprocess clearingprocessId;
    @JoinColumn(name = "deliveryagent_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Deliveryagent deliveryagentId;
    @JoinColumn(name = "pickby", referencedColumnName = "id" )
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee pickby;

    public Collectdo() {
    }

    public Collectdo(Clearingprocess clearingprocessId) {
        this.clearingprocessId = clearingprocessId;
    }

    public Collectdo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

   /* public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }*/

    public String getIssuedby() {
        return issuedby;
    }

    public void setIssuedby(String issuedby) {
        this.issuedby = issuedby;
    }

    public Clearingprocess getClearingprocessId() {
        return clearingprocessId;
    }

    public void setClearingprocessId(Clearingprocess clearingprocessId) {
        this.clearingprocessId = clearingprocessId;
    }

    public Deliveryagent getDeliveryagentId() {
        return deliveryagentId;
    }

    public void setDeliveryagentId(Deliveryagent deliveryagentId) {
        this.deliveryagentId = deliveryagentId;
    }

    public Employee getPickby() {
        return pickby;
    }

    public void setPickby(Employee pickby) {
        this.pickby = pickby;
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
        if (!(object instanceof Collectdo)) {
            return false;
        }
        Collectdo other = (Collectdo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wajiraenterprises.entity.Collectdo[ id=" + id + " ]";
    }
    
}
