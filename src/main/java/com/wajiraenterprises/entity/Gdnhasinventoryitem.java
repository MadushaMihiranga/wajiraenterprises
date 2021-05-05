/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wajiraenterprises.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author madusha mihiranga
 */
@Entity
@Table(name = "gdnhasinventoryitem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gdnhasinventoryitem.findAll", query = "SELECT g FROM Gdnhasinventoryitem g")})
public class Gdnhasinventoryitem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "qty")
    private Integer qty;
    @JoinColumn(name = "gdn_id", referencedColumnName = "id")
    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Gdn gdnId;
    @JoinColumn(name = "inventry_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Inventry inventryId;

    @Lob
    @Column(name = "note")
    private String note;
    @JoinColumn(name = "packagetype_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Packagetype packagetypeId;

    public Gdnhasinventoryitem() {
    }

    public Gdnhasinventoryitem(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Gdn getGdnId() {
        return gdnId;
    }

    public void setGdnId(Gdn gdnId) {
        this.gdnId = gdnId;
    }

    public Inventry getInventryId() {
        return inventryId;
    }

    public void setInventryId(Inventry inventryId) {
        this.inventryId = inventryId;
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
        if (!(object instanceof Gdnhasinventoryitem)) {
            return false;
        }
        Gdnhasinventoryitem other = (Gdnhasinventoryitem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wajiraenterprises.entity.Gdnhasinventoryitem[ id=" + id + " ]";
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Packagetype getPackagetypeId() {
        return packagetypeId;
    }

    public void setPackagetypeId(Packagetype packagetypeId) {
        this.packagetypeId = packagetypeId;
    }



}
