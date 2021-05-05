/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wajiraenterprises.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
import javax.persistence.ManyToOne;
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
@Table(name = "servicesquotation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Servicesquotation.findAll", query = "SELECT s FROM Servicesquotation s")})
public class Servicesquotation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "value")
    private BigDecimal value;
    @JoinColumn(name = "containertype_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Containertype containertypeId;
    @JoinColumn(name = "mods_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Mods modsId;
    @JoinColumn(name = "services_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Services servicesId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "servicesquotationId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Invoicehasservice> invoicehasserviceList;

    public Servicesquotation() {
    }

    public Servicesquotation(Integer id,BigDecimal value, Containertype containertypeId, Mods modsId, Services servicesId) {
        this.id = id;
        this.value = value;
        this.containertypeId = containertypeId;
        this.modsId = modsId;
        this.servicesId = servicesId;
    }

    public Servicesquotation(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Containertype getContainertypeId() {
        return containertypeId;
    }

    public void setContainertypeId(Containertype containertypeId) {
        this.containertypeId = containertypeId;
    }

    public Mods getModsId() {
        return modsId;
    }

    public void setModsId(Mods modsId) {
        this.modsId = modsId;
    }

    public Services getServicesId() {
        return servicesId;
    }

    public void setServicesId(Services servicesId) {
        this.servicesId = servicesId;
    }

    @XmlTransient
    public List<Invoicehasservice> getInvoicehasserviceList() {
        return invoicehasserviceList;
    }

    public void setInvoicehasserviceList(List<Invoicehasservice> invoicehasserviceList) {
        this.invoicehasserviceList = invoicehasserviceList;
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
        if (!(object instanceof Servicesquotation)) {
            return false;
        }
        Servicesquotation other = (Servicesquotation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wajiraenterprises.entity.Servicesquotation[ id=" + id + " ]";
    }
    
}
