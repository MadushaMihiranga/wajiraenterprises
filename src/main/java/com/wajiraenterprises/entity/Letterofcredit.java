/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wajiraenterprises.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wajiraenterprises.util.RegexPattern;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author madusha mihiranga
 */
@Entity
@Table(name = "letterofcredit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Letterofcredit.findAll", query = "SELECT l FROM Letterofcredit l")})
public class Letterofcredit implements Serializable {

    @Column(name = "lcnumber")
    private String lcnumber;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "lcapplicantname")
    @Pattern(regexp = "^[A-Z]([a-zA-Z0-9]|[- @\\.#&!()])*$", message = "Applicant name of the LC is incorrect")
    private String lcapplicantname;
    @Column(name = "lcbenificiaryname")
    @Pattern(regexp = "^[A-Z]([a-zA-Z0-9]|[- @\\.#&!()])*$", message = "Beneficiary name of the LC is incorrect")
    private String lcbenificiaryname;
    @Column(name = "lcissueddate")
    //@Temporal(TemporalType.DATE)
    private LocalDate lcissueddate;
    @Column(name = "exportersbank")
    @Pattern(regexp = "^[A-Z]([a-zA-Z0-9]|[- @\\.#&!()])*$", message = "Exporter's bank name is incorrect")
    private String exportersbank;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    //@Pattern(regexp = "(\\d+\\.\\d{1,2})", message = "Amount of the LC is invalid")
    private BigDecimal amount;
    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Branch branchId;
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Currency currencyId;
    @JoinColumn(name = "shipment_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnore
    private Shipment shipmentId;
    @JoinColumn(name = "termsofpayment_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Termsofpayment termsofpaymentId;

    public Letterofcredit() {
    }

    public Letterofcredit(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLcapplicantname() {
        return lcapplicantname;
    }

    public void setLcapplicantname(String lcapplicantname) {
        this.lcapplicantname = lcapplicantname;
    }

    public String getLcbenificiaryname() {
        return lcbenificiaryname;
    }

    public void setLcbenificiaryname(String lcbenificiaryname) {
        this.lcbenificiaryname = lcbenificiaryname;
    }

    public LocalDate getLcissueddate() {
        return lcissueddate;
    }

    public void setLcissueddate(LocalDate lcissueddate) {
        this.lcissueddate = lcissueddate;
    }

    public String getExportersbank() {
        return exportersbank;
    }

    public void setExportersbank(String exportersbank) {
        this.exportersbank = exportersbank;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Branch getBranchId() {
        return branchId;
    }

    public void setBranchId(Branch branchId) {
        this.branchId = branchId;
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

    public Termsofpayment getTermsofpaymentId() {
        return termsofpaymentId;
    }

    public void setTermsofpaymentId(Termsofpayment termsofpaymentId) {
        this.termsofpaymentId = termsofpaymentId;
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
        if (!(object instanceof Letterofcredit)) {
            return false;
        }
        Letterofcredit other = (Letterofcredit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wajiraenterprises.entity.Letterofcredit[ id=" + id + " ]";
    }

    public String getLcnumber() {
        return lcnumber;
    }

    public void setLcnumber(String lcnumber) {
        this.lcnumber = lcnumber;
    }
    
}
