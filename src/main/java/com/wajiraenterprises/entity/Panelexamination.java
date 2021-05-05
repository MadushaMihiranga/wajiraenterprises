/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wajiraenterprises.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.*;

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
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author madusha mihiranga
 */
@Entity
@Table(name = "panelexamination")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Panelexamination.findAll", query = "SELECT p FROM Panelexamination p")})
public class Panelexamination implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    //@Temporal(TemporalType.DATE)
    private LocalDate date;
    @Column(name = "customsofficer")
    @Pattern(regexp = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$", message = "Name Is Invalid")
    private String customsofficer;
    @JoinColumn(name = "clearingprocess_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Clearingprocess clearingprocessId;
    @JoinColumn(name = "cusdec_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Cusdec cusdecId;
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee employeeId;
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Location locationId;
    @JoinColumn(name = "pestatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Pestatus pestatusId;
    @JoinColumn(name = "shipment_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Shipment shipmentId;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = {CascadeType.ALL, CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "panelexaminationId")
    @Fetch(value= FetchMode.SELECT)
    private List<Examinationhascheckpoints> examinationhascheckpointsList;

    public Panelexamination() {
    }

    public Panelexamination(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCustomsofficer() {
        return customsofficer;
    }

    public void setCustomsofficer(String customsofficer) {
        this.customsofficer = customsofficer;
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

    public Location getLocationId() {
        return locationId;
    }

    public void setLocationId(Location locationId) {
        this.locationId = locationId;
    }

    public Pestatus getPestatusId() {
        return pestatusId;
    }

    public void setPestatusId(Pestatus pestatusId) {
        this.pestatusId = pestatusId;
    }

    public Shipment getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Shipment shipmentId) {
        this.shipmentId = shipmentId;
    }

    @XmlTransient
    public List<Examinationhascheckpoints> getExaminationhascheckpointsList() {
        return examinationhascheckpointsList;
    }

    public void setExaminationhascheckpointsList(List<Examinationhascheckpoints> examinationhascheckpointsList) {
        this.examinationhascheckpointsList = examinationhascheckpointsList;
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
        if (!(object instanceof Panelexamination)) {
            return false;
        }
        Panelexamination other = (Panelexamination) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wajiraenterprises.entity.Panelexamination[ id=" + id + " ]";
    }
    
}
