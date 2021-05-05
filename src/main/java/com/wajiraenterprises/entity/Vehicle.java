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
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 *
 * @author madusha mihiranga
 */
@Entity
@Table(name = "vehicle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vehicle.findAll", query = "SELECT v FROM Vehicle v")})
public class Vehicle implements Serializable {

    @Basic(optional = false)
    @Column(name = "number")
    private String number;
    @Basic(optional = false)
    @Column(name = "adddate")
    //@Temporal(TemporalType.DATE)
    private LocalDate adddate;

    @OneToMany(mappedBy = "vehicleId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Deliveryconfirmation> deliveryconfirmationList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "rgno")
    @Pattern(regexp = "^([0-9]{2,3})-[0-9]{4}|[A-Z]{2}[ ][A-Z]{3}-[0-9]{4}", message = "Invalid Vehicle Registration No")//^([a-zA-Z]{2,3}|((?!0*-)[0-9]{2,3}))-[0-9]{4}(?<!0{4})
    private String rgno;
    @Column(name = "engno")
    @Pattern(regexp = "^([A-Z0-9]{8,})", message = "Invalid Engine Number")
    private String engno;
    @Column(name = "chaseno")
    @Pattern(regexp = "^([A-Z0-9]{8,})", message = "Invalid Chase Number")
    private String chaseno;
    @Column(name = "manfactureyear")
    //@Temporal(TemporalType.DATE)
    private Integer manfactureyear;
    @JoinColumn(name = "addemployee", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee addemployee;
/*    @JoinColumn(name = "driveremployee", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Employee driveremployee;
    @JoinColumn(name = "drivingassistantemployee", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Employee drivingassistantemployee;*/
    @JoinColumn(name = "model_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Model modelId;
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Vehiclestatus statusId;
    @JoinColumn(name = "vehicletype_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Vehicletype vehicletypeId;



    public Vehicle() {
    }

    public Vehicle(Integer id) {
        this.id = id;
    }

    public Vehicle(String number) {  this.number = number; }

  /*  public Vehicle(Employee driveremployee) {
        this.driveremployee = driveremployee;
    }*/

    public Vehicle(Integer id, String rgno, LocalDate adddate) {
        this.id = id;
        this.rgno = rgno;
        this.adddate = adddate;
    }

    public Vehicle(Integer id,String number, Model modelId ,String rgno) {
        this.id =id;
        this.number = number;
        this.rgno = rgno;
        this.modelId = modelId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRgno() {
        return rgno;
    }

    public void setRgno(String rgno) {
        this.rgno = rgno;
    }

    public String getEngno() {
        return engno;
    }

    public void setEngno(String engno) {
        this.engno = engno;
    }

    public String getChaseno() {
        return chaseno;
    }

    public void setChaseno(String chaseno) {
        this.chaseno = chaseno;
    }

    public Integer getManfactureyear() {
        return manfactureyear;
    }

    public void setManfactureyear(Integer manfactureyear) {
        this.manfactureyear = manfactureyear;
    }


    public Employee getAddemployee() {
        return addemployee;
    }

    public void setAddemployee(Employee addemployee) {
        this.addemployee = addemployee;
    }

/*    public Employee getDriveremployee() {
        return driveremployee;
    }

    public void setDriveremployee(Employee driveremployee) {
        this.driveremployee = driveremployee;
    }

    public Employee getDrivingassistantemployee() {
        return drivingassistantemployee;
    }

    public void setDrivingassistantemployee(Employee drivingassistantemployee) {
        this.drivingassistantemployee = drivingassistantemployee;
    }*/

    public Model getModelId() {
        return modelId;
    }

    public void setModelId(Model modelId) {
        this.modelId = modelId;
    }

    public Vehiclestatus getStatusId() {
        return statusId;
    }

    public void setStatusId(Vehiclestatus statusId) {
        this.statusId = statusId;
    }

    public Vehicletype getVehicletypeId() {
        return vehicletypeId;
    }

    public void setVehicletypeId(Vehicletype vehicletypeId) {
        this.vehicletypeId = vehicletypeId;
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
        if (!(object instanceof Vehicle)) {
            return false;
        }
        Vehicle other = (Vehicle) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wajiraenterprises.entity.Vehicle[ id=" + id + " ]";
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getAdddate() {
        return adddate;
    }

    public void setAdddate(LocalDate adddate) {
        this.adddate = adddate;
    }



    @XmlTransient
    public List<Deliveryconfirmation> getDeliveryconfirmationList() {
        return deliveryconfirmationList;
    }

    public void setDeliveryconfirmationList(List<Deliveryconfirmation> deliveryconfirmationList) {
        this.deliveryconfirmationList = deliveryconfirmationList;
    }
    
}
