/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wajiraenterprises.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wajiraenterprises.util.RegexPattern;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i")})
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Lob
    @Column(name = "description")
    @Pattern(regexp = "^.*$", message = "Item Description is invalid")
    private String description;
    @Column(name = "hscode")
    @Pattern(regexp = "^(((\\d{8})){0,1})$", message = "Harmonized code of the Item is invalid")
    private String hscode;
    @Column(name = "qty")
    @RegexPattern(regexp = "^\\d+$", message = "Item's Quantity is invalid")
    private Integer qty;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rateperunit")
    //@RegexPattern Pattern(r = "(\\d+\\.\\d{1,2})", message = "RatePer Unit of the item is invalid")
    private BigDecimal rateperunit;
    @Lob
    @Column(name = "manufacturer")
    @Pattern(regexp = "^.*$", message = "Manufacturer of the item is invalid")
    private String manufacturer;
    @JoinColumn(name = "countryoforigin", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Country countryoforigin;
    @JoinColumn(name = "tradingcountry", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Country tradingcountry;
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Currency currencyId;
    @JoinColumn(name = "shipment_id", referencedColumnName = "id")

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    //@LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private Shipment shipmentId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemId", fetch = FetchType.EAGER, orphanRemoval = true)
    //@JsonIgnore
    private List<ItemHasLicence> itemHasLicenceList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Grnhasitem> grnhasitemList;

    @JoinColumn(name = "packagetype_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Packagetype packagetypeId;

    @Column(name = "totalamount")
    private BigDecimal totalamount;

    public Item() {
    }

    public Item(Integer id, String description, Integer qty, Packagetype packagetypeId) {
        this.id = id;
        this.description = description;
        this.qty = qty;
        this.packagetypeId = packagetypeId;
    }


    public BigDecimal getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(BigDecimal totalamount) {
        this.totalamount = totalamount;
    }

    public Item(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHscode() {
        return hscode;
    }

    public void setHscode(String hscode) {
        this.hscode = hscode;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public BigDecimal getRateperunit() {
        return rateperunit;
    }

    public void setRateperunit(BigDecimal rateperunit) {
        this.rateperunit = rateperunit;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Country getCountryoforigin() {
        return countryoforigin;
    }

    public void setCountryoforigin(Country countryoforigin) {
        this.countryoforigin = countryoforigin;
    }

    public Country getTradingcountry() {
        return tradingcountry;
    }

    public void setTradingcountry(Country tradingcountry) {
        this.tradingcountry = tradingcountry;
    }

    public Currency getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Currency currencyId) {
        this.currencyId = currencyId;
    }

    public Shipment getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Shipment shipmentId) {
        this.shipmentId = shipmentId;
    }

    @XmlTransient
    public List<ItemHasLicence> getItemHasLicenceList() {
        return itemHasLicenceList;
    }

    public void setItemHasLicenceList(List<ItemHasLicence> itemHasLicenceList) {
        this.itemHasLicenceList = itemHasLicenceList;
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
        if (!(object instanceof Item)) {
            return false;
        }
        Item other = (Item) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wajiraenterprises.entity.Item[ id=" + id + " ]";
    }

    @XmlTransient
    public List<Grnhasitem> getGrnhasitemList() {
        return grnhasitemList;
    }

    public void setGrnhasitemList(List<Grnhasitem> grnhasitemList) {
        this.grnhasitemList = grnhasitemList;
    }


    public Packagetype getPackagetypeId() {
        return packagetypeId;
    }

    public void setPackagetypeId(Packagetype packagetypeId) {
        this.packagetypeId = packagetypeId;
    }


}
