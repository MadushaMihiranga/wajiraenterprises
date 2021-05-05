/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wajiraenterprises.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.wajiraenterprises.util.RegexPattern;

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
@Table(name = "deliveryrequest")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deliveryrequest.findAll", query = "SELECT d FROM Deliveryrequest d")})
public class Deliveryrequest implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "gatepassno")
    @Pattern(regexp = "^[A-Za-z0-9/-]{5,35}$", message = "Gate Pass Number Number is Invalid")
    private String gatepassno;
    @Lob
    @Column(name = "pickuplocation")
    private String pickuplocation;
    @Lob
    @Column(name = "deliverylocation")
    private String deliverylocation;

    @JoinColumn(name = "vehicletype_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Vehicletype vehicletypeId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deliveryrequestId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Deliveryconfirmation> deliveryconfirmationList;
//@Pattern(regexp = "^([A-Z][a-z]*[.]?[\\s]?)*([A-Z][a-z]*)$", message = "Invalid Fullname")
    @Column(name = "cargovolume")
    @RegexPattern(regexp = "^(|[1-9][0-9]*)$" , message = "Invalid Cargo volume")
    private Integer cargovolume;


    @Column(name = "date")
    //@Temporal(TemporalType.DATE)
    private LocalDate date;

    @Column(name = "time")
   // @JsonFormat(pattern = "HH:mm:ss");
    private String time;
    // @JsonDeserialize(using = LocalTimeDeserializer.class)
    // @JsonSerialize(using = LocalTimeSerializer.class)

    @JoinColumn(name = "deliverytype_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Deliverytype deliverytypeId;
    @JoinColumn(name = "requestedby", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee requestedby;
    @JoinColumn(name = "deliveryrequeststatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Deliveryrequeststatus deliveryrequeststatusId;
    @JoinColumn(name = "shipment_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Shipment shipmentId;

    @JoinColumn(name = "gdn_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Gdn gdnId;

    @Column(name = "number")
    private String number;

    public Deliveryrequest() {
    }

    public Deliveryrequest(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGatepassno() {
        return gatepassno;
    }

    public void setGatepassno(String gatepassno) {
        this.gatepassno = gatepassno;
    }

    public String getPickuplocation() {
        return pickuplocation;
    }

    public void setPickuplocation(String pickuplocation) {
        this.pickuplocation = pickuplocation;
    }

    public String getDeliverylocation() {
        return deliverylocation;
    }

    public void setDeliverylocation(String deliverylocation) {
        this.deliverylocation = deliverylocation;
    }



    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Deliverytype getDeliverytypeId() {
        return deliverytypeId;
    }

    public void setDeliverytypeId(Deliverytype deliverytypeId) {
        this.deliverytypeId = deliverytypeId;
    }

    public Employee getRequestedby() {
        return requestedby;
    }

    public void setRequestedby(Employee requestedby) {
        this.requestedby = requestedby;
    }

    public Deliveryrequeststatus getDeliveryrequeststatusId() {
        return deliveryrequeststatusId;
    }

    public void setDeliveryrequeststatusId(Deliveryrequeststatus deliveryrequeststatusId) {
        this.deliveryrequeststatusId = deliveryrequeststatusId;
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
        if (!(object instanceof Deliveryrequest)) {
            return false;
        }
        Deliveryrequest other = (Deliveryrequest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wajiraenterprises.entity.Deliveryrequest[ id=" + id + " ]";
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Vehicletype getVehicletypeId() {
        return vehicletypeId;
    }

    public void setVehicletypeId(Vehicletype vehicletypeId) {
        this.vehicletypeId = vehicletypeId;
    }

    @XmlTransient
    public List<Deliveryconfirmation> getDeliveryconfirmationList() {
        return deliveryconfirmationList;
    }

    public void setDeliveryconfirmationList(List<Deliveryconfirmation> deliveryconfirmationList) {
        this.deliveryconfirmationList = deliveryconfirmationList;
    }

    public Gdn getGdnId() {
        return gdnId;
    }

    public void setGdnId(Gdn gdnId) {
        this.gdnId = gdnId;
    }

    public Integer getCargovolume() {
        return cargovolume;
    }

    public void setCargovolume(Integer cargovolume) {
        this.cargovolume = cargovolume;
    }



}
