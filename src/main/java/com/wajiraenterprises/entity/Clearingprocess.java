/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wajiraenterprises.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author madusha mihiranga
 */
@Entity
@Table(name = "clearingprocess")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clearingprocess.findAll", query = "SELECT c FROM Clearingprocess c")})
public class Clearingprocess implements Serializable {

    @Column(name = "startdate")
    //@Temporal(TemporalType.DATE)
    //@Temporal(TemporalType.DATE)
    //@Temporal(TemporalType.DATE)
    private LocalDate startdate;
    @Column(name = "endate")
    //@Temporal(TemporalType.DATE)
    private LocalDate endate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clearingprocessId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Panelexamination> panelexaminationList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clearingprocessId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Paytaxes> paytaxesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clearingprocessId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Cusdec> cusdecList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clearingprocessId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Collectdo> collectdoList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "clearingprocessstatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Clearingprocessstatus clearingprocessstatusId;
    @JoinColumn(name = "shipment_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER
    )
    private Shipment shipmentId;

    public Clearingprocess() {
    }

    public Clearingprocess(Integer id,LocalDate startdate, LocalDate endate, Clearingprocessstatus clearingprocessstatusId, Shipment shipmentId) {
        this.startdate = startdate;
        this.endate = endate;
        this.clearingprocessstatusId = clearingprocessstatusId;
        this.shipmentId = shipmentId;
    }

    public Clearingprocess(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Clearingprocessstatus getClearingprocessstatusId() {
        return clearingprocessstatusId;
    }

    public void setClearingprocessstatusId(Clearingprocessstatus clearingprocessstatusId) {
        this.clearingprocessstatusId = clearingprocessstatusId;
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
        if (!(object instanceof Clearingprocess)) {
            return false;
        }
        Clearingprocess other = (Clearingprocess) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wajiraenterprises.entity.Clearingprocess[ id=" + id + " ]";
    }



    @XmlTransient
    public List<Collectdo> getCollectdoList() {
        return collectdoList;
    }

    public void setCollectdoList(List<Collectdo> collectdoList) {
        this.collectdoList = collectdoList;
    }


    @XmlTransient
    public List<Cusdec> getCusdecList() {
        return cusdecList;
    }

    public void setCusdecList(List<Cusdec> cusdecList) {
        this.cusdecList = cusdecList;
    }

    public LocalDate getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDate startdate) {
        this.startdate = startdate;
    }

    public LocalDate getEndate() {
        return endate;
    }

    public void setEndate(LocalDate endate) {
        this.endate = endate;
    }

    @XmlTransient
    public List<Paytaxes> getPaytaxesList() {
        return paytaxesList;
    }

    public void setPaytaxesList(List<Paytaxes> paytaxesList) {
        this.paytaxesList = paytaxesList;
    }

    @XmlTransient
    public List<Panelexamination> getPanelexaminationList() {
        return panelexaminationList;
    }

    public void setPanelexaminationList(List<Panelexamination> panelexaminationList) {
        this.panelexaminationList = panelexaminationList;
    }


}
