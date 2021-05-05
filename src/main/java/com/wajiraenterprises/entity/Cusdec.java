/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wajiraenterprises.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
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
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author madusha mihiranga
 */
@Entity
@Table(name = "cusdec")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cusdec.findAll", query = "SELECT c FROM Cusdec c")})
public class Cusdec implements Serializable {




    @Column(name = "submitetdate")
   // @Temporal(TemporalType.DATE)
    private LocalDate submitetdate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cusdecId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Paytaxes> paytaxesList;

    @JoinColumn(name = "shipment_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Shipment shipmentId;



    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cusdecId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Panelexamination> panelexaminationList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "customrefno")
    @Pattern(regexp = "^[0-9]{5,7}$", message = "Customs Reference Number is Invalid")
    private String customrefno;
    @Column(name = "manifestnumber")

    private String manifestnumber;
    @Column(name = "assessmentnumber")//
    @Pattern(regexp = "^[A-Z0-9 ]{7,9}$", message = "Assessment Number is Invalid")
    private String assessmentnumber;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "totaltaxvalue")
    private BigDecimal totaltaxvalue;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cusdecId")
    private List<Cusdechastax> cusdechastaxList;
    @JoinColumn(name = "clearingprocess_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Clearingprocess clearingprocessId;
    @JoinColumn(name = "customesoffice_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Customesoffice customesofficeId;
    @JoinColumn(name = "submitby", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee submitby;

    public Cusdec() {
    }

    public Cusdec(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getCustomrefno() {
        return customrefno;
    }

    public void setCustomrefno(String customrefno) {
        this.customrefno = customrefno;
    }

    public String getManifestnumber() {
        return manifestnumber;
    }

    public void setManifestnumber(String manifestnumber) {
        this.manifestnumber = manifestnumber;
    }

    public String getAssessmentnumber() {
        return assessmentnumber;
    }

    public void setAssessmentnumber(String assessmentnumber) {
        this.assessmentnumber = assessmentnumber;
    }

    public BigDecimal getTotaltaxvalue() {
        return totaltaxvalue;
    }

    public void setTotaltaxvalue(BigDecimal totaltaxvalue) {
        this.totaltaxvalue = totaltaxvalue;
    }

    @XmlTransient
    public List<Cusdechastax> getCusdechastaxList() {
        return cusdechastaxList;
    }

    public void setCusdechastaxList(List<Cusdechastax> cusdechastaxList) {
        this.cusdechastaxList = cusdechastaxList;
    }

    public Clearingprocess getClearingprocessId() {
        return clearingprocessId;
    }

    public void setClearingprocessId(Clearingprocess clearingprocessId) {
        this.clearingprocessId = clearingprocessId;
    }

    public Customesoffice getCustomesofficeId() {
        return customesofficeId;
    }

    public void setCustomesofficeId(Customesoffice customesofficeId) {
        this.customesofficeId = customesofficeId;
    }

    public Employee getSubmitby() {
        return submitby;
    }

    public void setSubmitby(Employee submitby) {
        this.submitby = submitby;
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
        if (!(object instanceof Cusdec)) {
            return false;
        }
        Cusdec other = (Cusdec) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wajiraenterprises.entity.Cusdec[ id=" + id + " ]";
    }

    public LocalDate getSubmitetdate() {
        return submitetdate;
    }

    public void setSubmitetdate(LocalDate submitetdate) {
        this.submitetdate = submitetdate;
    }

    @XmlTransient
    public List<Paytaxes> getPaytaxesList() {
        return paytaxesList;
    }

    public void setPaytaxesList(List<Paytaxes> paytaxesList) {
        this.paytaxesList = paytaxesList;
    }

    public Shipment getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Shipment shipmentId) {
        this.shipmentId = shipmentId;
    }

    @XmlTransient
    public List<Panelexamination> getPanelexaminationList() {
        return panelexaminationList;
    }

    public void setPanelexaminationList(List<Panelexamination> panelexaminationList) {
        this.panelexaminationList = panelexaminationList;
    }


}
