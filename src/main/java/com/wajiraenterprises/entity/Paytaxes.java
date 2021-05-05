/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wajiraenterprises.entity;

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
@Table(name = "paytaxes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paytaxes.findAll", query = "SELECT p FROM Paytaxes p")})
public class Paytaxes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "payedvalue")
    private BigDecimal payedvalue;
    @Column(name = "payordernumber")//
    @Pattern(regexp = "^[0-9 ]{15,30}$", message = "Pay order Number Invalid")
    private String payordernumber;
    @Column(name = "date")
    //@Temporal(TemporalType.DATE)
    private LocalDate date;
    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Branch branchId;
    @JoinColumn(name = "clearingprocess_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Clearingprocess clearingprocessId;
    @JoinColumn(name = "cusdec_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Cusdec cusdecId;
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee employeeId;
    @JoinColumn(name = "paymenttype_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Paymenttype paymenttypeId;

    public Paytaxes() {
    }

    public Paytaxes(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getPayedvalue() {
        return payedvalue;
    }

    public void setPayedvalue(BigDecimal payedvalue) {
        this.payedvalue = payedvalue;
    }

    public String getPayordernumber() {
        return payordernumber;
    }

    public void setPayordernumber(String payordernumber) {
        this.payordernumber = payordernumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Branch getBranchId() {
        return branchId;
    }

    public void setBranchId(Branch branchId) {
        this.branchId = branchId;
    }

    public Clearingprocess getClearingprocessId() {
        return clearingprocessId;
    }

    public void setClearingprocessId(Clearingprocess clearingprocessId) {
        this.clearingprocessId = clearingprocessId;
    }

    public Cusdec getCusdecId() {
        return cusdecId;
    }

    public void setCusdecId(Cusdec cusdecId) {
        this.cusdecId = cusdecId;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }

    public Paymenttype getPaymenttypeId() {
        return paymenttypeId;
    }

    public void setPaymenttypeId(Paymenttype paymenttypeId) {
        this.paymenttypeId = paymenttypeId;
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
        if (!(object instanceof Paytaxes)) {
            return false;
        }
        Paytaxes other = (Paytaxes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wajiraenterprises.entity.Paytaxes[ id=" + id + " ]";
    }
    
}
