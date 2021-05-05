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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author madusha mihiranga
 */
@Entity
@Table(name = "country")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Country.findAll", query = "SELECT c FROM Country c")})
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "numericcode")
    private String numericcode;
    @Basic(optional = false)
    @Column(name = "twodigitcode")
    private String twodigitcode;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "countryoforigin", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Item> itemList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tradingcountry", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Item> itemList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "countryId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Placeofloadingordischarge> placeofloadingordischargeList;

    public Country() {
    }

    public Country(Integer id) {
        this.id = id;
    }

    public Country(Integer id, String name, String numericcode, String twodigitcode) {
        this.id = id;
        this.name = name;
        this.numericcode = numericcode;
        this.twodigitcode = twodigitcode;
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

    public String getNumericcode() {
        return numericcode;
    }

    public void setNumericcode(String numericcode) {
        this.numericcode = numericcode;
    }

    public String getTwodigitcode() {
        return twodigitcode;
    }

    public void setTwodigitcode(String twodigitcode) {
        this.twodigitcode = twodigitcode;
    }

    @XmlTransient
    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    @XmlTransient
    public List<Item> getItemList1() {
        return itemList1;
    }

    public void setItemList1(List<Item> itemList1) {
        this.itemList1 = itemList1;
    }

    @XmlTransient
    public List<Placeofloadingordischarge> getPlaceofloadingordischargeList() {
        return placeofloadingordischargeList;
    }

    public void setPlaceofloadingordischargeList(List<Placeofloadingordischarge> placeofloadingordischargeList) {
        this.placeofloadingordischargeList = placeofloadingordischargeList;
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
        if (!(object instanceof Country)) {
            return false;
        }
        Country other = (Country) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wajiraenterprises.entity.Country[ id=" + id + " ]";
    }
    
}
