/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wajiraenterprises.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "invoice")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Invoice.findAll", query = "SELECT i FROM Invoice i")})
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "number")
    private String number;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "subtotal")
    private BigDecimal subtotal;
    @Column(name = "totaldiscount")
    private BigDecimal totaldiscount;
    @Column(name = "grandtotal")
    private BigDecimal grandtotal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invoiceId", fetch = FetchType.EAGER)
    private List<Invoicehasservice> invoicehasserviceList;
    @JoinColumn(name = "createemployee", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee createemployee;
    @JoinColumn(name = "invoicestatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Invoicestatus invoicestatusId;
    @JoinColumn(name = "shipment_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Shipment shipmentId;

/*    @Column(name = "paidtotal")
    private BigDecimal paidtotal;*/




    public Invoice() {
    }

    public Invoice(Integer id) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTotaldiscount() {
        return totaldiscount;
    }

    public void setTotaldiscount(BigDecimal totaldiscount) {
        this.totaldiscount = totaldiscount;
    }

    public BigDecimal getGrandtotal() {
        return grandtotal;
    }

    public void setGrandtotal(BigDecimal grandtotal) {
        this.grandtotal = grandtotal;
    }

    @XmlTransient
    public List<Invoicehasservice> getInvoicehasserviceList() {
        return invoicehasserviceList;
    }

    public void setInvoicehasserviceList(List<Invoicehasservice> invoicehasserviceList) {
        this.invoicehasserviceList = invoicehasserviceList;
    }

    public Employee getCreateemployee() {
        return createemployee;
    }

    public void setCreateemployee(Employee createemployee) {
        this.createemployee = createemployee;
    }

    public Invoicestatus getInvoicestatusId() {
        return invoicestatusId;
    }

    public void setInvoicestatusId(Invoicestatus invoicestatusId) {
        this.invoicestatusId = invoicestatusId;
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
        if (!(object instanceof Invoice)) {
            return false;
        }
        Invoice other = (Invoice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wajiraenterprises.entity.Invoice[ id=" + id + " ]";
    }

/*
    public BigDecimal getPaidtotal() {
        return paidtotal;
    }

    public void setPaidtotal(BigDecimal paidtotal) {
        this.paidtotal = paidtotal;
    }
*/




}
