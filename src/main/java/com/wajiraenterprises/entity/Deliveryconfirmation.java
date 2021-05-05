/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wajiraenterprises.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author madusha mihiranga
 */
@Entity
@Table(name = "deliveryconfirmation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deliveryconfirmation.findAll", query = "SELECT d FROM Deliveryconfirmation d")})
public class Deliveryconfirmation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Lob
    @Column(name = "vehdescription")
    private String vehdescription;
    @Column(name = "vehregno")
    private String vehregno;
    @Column(name = "drivername")
    private String drivername;
    @Column(name = "driverlicenceno")
    private String driverlicenceno;
    @JoinColumn(name = "deliveryrequest_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Deliveryrequest deliveryrequestId;
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee employeeId;
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Vehicle vehicleId;

    @JoinColumn(name = "vehiclerentelcompany_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Vehiclerentelcompany vehiclerentelcompanyId;

    @JoinColumn(name = "vehownership_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Vehownership vehownershipId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deliveryconfirmationId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Delivery> deliveryList;

    @JoinColumn(name = "driver", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee driver;

    @JoinColumn(name = "assistant", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Employee assistant;



    public Deliveryconfirmation() {
    }

    public Deliveryconfirmation(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVehdescription() {
        return vehdescription;
    }

    public void setVehdescription(String vehdescription) {
        this.vehdescription = vehdescription;
    }

    public String getVehregno() {
        return vehregno;
    }

    public void setVehregno(String vehregno) {
        this.vehregno = vehregno;
    }

    public String getDrivername() {
        return drivername;
    }

    public void setDrivername(String drivername) {
        this.drivername = drivername;
    }

    public String getDriverlicenceno() {
        return driverlicenceno;
    }

    public void setDriverlicenceno(String driverlicenceno) {
        this.driverlicenceno = driverlicenceno;
    }

    public Deliveryrequest getDeliveryrequestId() {
        return deliveryrequestId;
    }

    public void setDeliveryrequestId(Deliveryrequest deliveryrequestId) {
        this.deliveryrequestId = deliveryrequestId;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }

    public Vehicle getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Vehicle vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Vehiclerentelcompany getVehiclerentelcompanyId() {
        return vehiclerentelcompanyId;
    }

    public void setVehiclerentelcompanyId(Vehiclerentelcompany vehiclerentelcompanyId) {
        this.vehiclerentelcompanyId = vehiclerentelcompanyId;
    }

    public Vehownership getVehownershipId() {
        return vehownershipId;
    }

    public void setVehownershipId(Vehownership vehownershipId) {
        this.vehownershipId = vehownershipId;
    }


    public Employee getDriver() {
        return driver;
    }

    public void setDriver(Employee driver) {
        this.driver = driver;
    }

    public Employee getAssistant() {
        return assistant;
    }

    public void setAssistant(Employee assistant) {
        this.assistant = assistant;
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
        if (!(object instanceof Deliveryconfirmation)) {
            return false;
        }
        Deliveryconfirmation other = (Deliveryconfirmation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wajiraenterprises.entity.Deliveryconfirmation[ id=" + id + " ]";
    }



    @XmlTransient
    public List<Delivery> getDeliveryList() {
        return deliveryList;
    }

    public void setDeliveryList(List<Delivery> deliveryList) {
        this.deliveryList = deliveryList;
    }
    
}
