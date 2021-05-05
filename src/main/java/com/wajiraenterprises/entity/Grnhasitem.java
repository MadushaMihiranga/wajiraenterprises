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
@Table(name = "grnhasitem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grnhasitem.findAll", query = "SELECT g FROM Grnhasitem g")})
public class Grnhasitem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "recivedqty")
    private Integer recivedqty;
    @Column(name = "note")
    private String note;
    @JoinColumn(name = "grnitemstatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Grnitemstatus grnitemstatusId;

    @JoinColumn(name = "grn_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnore
    private Grn grnId;

    @JoinColumn(name = "item_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    //@JsonIgnore
    private Item itemId;

    @JoinColumn(name = "packagetype_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Packagetype packagetypeId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grnhasitemId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Inventry> inventryList;


    public Grnhasitem() {
    }

    public Grnhasitem(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRecivedqty() {
        return recivedqty;
    }

    public void setRecivedqty(Integer recivedqty) {
        this.recivedqty = recivedqty;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Grnitemstatus getGrnitemstatusId() {
        return grnitemstatusId;
    }

    public void setGrnitemstatusId(Grnitemstatus grnitemstatusId) {
        this.grnitemstatusId = grnitemstatusId;
    }

    public Grn getGrnId() {
        return grnId;
    }

    public void setGrnId(Grn grnId) {
        this.grnId = grnId;
    }

    public Item getItemId() {
        return itemId;
    }

    public void setItemId(Item itemId) {
        this.itemId = itemId;
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
        if (!(object instanceof Grnhasitem)) {
            return false;
        }
        Grnhasitem other = (Grnhasitem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wajiraenterprises.entity.Grnhasitem[ id=" + id + " ]";
    }

    public Packagetype getPackagetypeId() {
        return packagetypeId;
    }

    public void setPackagetypeId(Packagetype packagetypeId) {
        this.packagetypeId = packagetypeId;
    }


    @XmlTransient
    public List<Inventry> getInventryList() {
        return inventryList;
    }

    public void setInventryList(List<Inventry> inventryList) {
        this.inventryList = inventryList;
    }

    
}
