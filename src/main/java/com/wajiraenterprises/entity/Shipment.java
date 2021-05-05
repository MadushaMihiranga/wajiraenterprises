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
import javax.persistence.Lob;
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
@Table(name = "shipment")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Shipment.findAll", query = "SELECT s FROM Shipment s")})
public class Shipment implements Serializable {


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shipmentId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Panelexamination> panelexaminationList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shipmentId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Deliveryrequest> deliveryrequestList;

    @Column(name = "dateofregisterd")
   // @Temporal(TemporalType.DATE)
    private LocalDate dateofregisterd;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shipmentId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Clearingprocess> clearingprocessList;

    @OneToMany(mappedBy = "shipmentId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Cusdec> cusdecList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "number")
    private String number;
    @Column(name = "coorefno")
    @Pattern(regexp = "^[A-Z0-9]{10,35}$", message = "CO Reference Number Invalid")
    private String coorefno;
    @Column(name = "cminvoiceno")
    @Pattern(regexp = "^[A-Za-z0-9/-]{5,35}$", message = "Commercial Invoice Number is Invalid")
    private String cminvoiceno;
    @Column(name = "airwaybillbno")
    @Pattern(regexp = "^[A-Z0-9]{10,35}$", message = "Air Way Bill Number is Invalid")
    private String airwaybillbno;
    @Column(name = "billofloadingno")
    @Pattern(regexp = "^[A-Z0-9]{10,35}$", message = "Bill of lading Number is Invalid")
    private String billofloadingno;
    @Column(name = "exportername")
    @Pattern(regexp = "^[A-Za-z() -]{3,50}$", message = "Exporter's Name is Invalid")
    private String exportername;
    @Lob
    @Column(name = "exporteraddress")
    @Pattern(regexp = "^([\\w\\/\\-,\\s]{2,})$", message = "Exporter's Address is invalid")
    private String exporteraddress;

    @LazyCollection(LazyCollectionOption.FALSE)  // remove [, fetch = FetchType.LAZY] this annotation use to stop [org.hibernate.loader.MultipleBagFetchException: cannot simultaneously fetch multiple bags]
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shipmentId", orphanRemoval = true)
    private List<Item> itemList;

    @JoinColumn(name = "deliveryagent_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Deliveryagent deliveryagentId;
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Company companyId;
    @JoinColumn(name = "containertype_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Containertype containertypeId;
    @JoinColumn(name = "deliverymethod_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Deliverymethod deliverymethodId;
    @JoinColumn(name = "deliveryterms_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Deliveryterms deliverytermsId;
    @JoinColumn(name = "addemployee", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee addemployee;
    @JoinColumn(name = "clearingclerk", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee clearingclerk;
    @JoinColumn(name = "mods_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Mods modsId;
    @JoinColumn(name = "placeofloading", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Placeofloadingordischarge placeofloading;
    @JoinColumn(name = "placeofdischarge", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Placeofloadingordischarge placeofdischarge;
    @JoinColumn(name = "shippingcompany_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Shippingcompany shippingcompanyId;
    @JoinColumn(name = "tradeagreement_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Tradeagreement tradeagreementId;

    @Basic(optional = false)
    @Lob
    @Column(name = "deliverylocation")
    private String deliverylocation;

    @Column(name = "shipmenttotalvalue")
    private BigDecimal shipmenttotalvalue;


    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shipmentId",orphanRemoval = true)
    // @JsonIgnore
    private List<Letterofcredit> letterofcreditList;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shipmentId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Customeracceptence> customeracceptenceList;


 /*   @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shipmentId")
    @JsonIgnore
    private List<Inventry> inventryList;*/

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shipmentId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Gdn> gdnList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shipmentId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Invoice> invoiceList;



    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shipmentId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Grn> grnList;

    public Shipment() {
    }

    public Shipment(String number) {
        this.number = number;
    }

    public Shipment(Integer id) {
        this.id = id;
    }

    public Shipment(Integer id, String number) {
        this.id = id;
        this.number = number;
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

    public String getCoorefno() {
        return coorefno;
    }

    public void setCoorefno(String coorefno) {
        this.coorefno = coorefno;
    }

    public String getCminvoiceno() {
        return cminvoiceno;
    }

    public void setCminvoiceno(String cminvoiceno) {
        this.cminvoiceno = cminvoiceno;
    }

    public String getAirwaybillbno() {
        return airwaybillbno;
    }

    public void setAirwaybillbno(String airwaybillbno) {
        this.airwaybillbno = airwaybillbno;
    }

    public String getBillofloadingno() {
        return billofloadingno;
    }

    public void setBillofloadingno(String billofloadingno) {
        this.billofloadingno = billofloadingno;
    }

    public String getExportername() {
        return exportername;
    }

    public void setExportername(String exportername) {
        this.exportername = exportername;
    }

    public String getExporteraddress() {
        return exporteraddress;
    }

    public void setExporteraddress(String exporteraddress) {
        this.exporteraddress = exporteraddress;
    }


    public BigDecimal getShipmenttotalvalue() {
        return shipmenttotalvalue;
    }

    public void setShipmenttotalvalue(BigDecimal shipmenttotalvalue) {
        this.shipmenttotalvalue = shipmenttotalvalue;
    }


    @XmlTransient
    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public Deliveryagent getDeliveryagentId() {
        return deliveryagentId;
    }

    public void setDeliveryagentId(Deliveryagent deliveryagentId) {
        this.deliveryagentId = deliveryagentId;
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }

    public Containertype getContainertypeId() {
        return containertypeId;
    }

    public void setContainertypeId(Containertype containertypeId) {
        this.containertypeId = containertypeId;
    }

    public Deliverymethod getDeliverymethodId() {
        return deliverymethodId;
    }

    public void setDeliverymethodId(Deliverymethod deliverymethodId) {
        this.deliverymethodId = deliverymethodId;
    }

    public Deliveryterms getDeliverytermsId() {
        return deliverytermsId;
    }

    public void setDeliverytermsId(Deliveryterms deliverytermsId) {
        this.deliverytermsId = deliverytermsId;
    }

    public Employee getAddemployee() {
        return addemployee;
    }

    public void setAddemployee(Employee addemployee) {
        this.addemployee = addemployee;
    }

    public Employee getClearingclerk() {
        return clearingclerk;
    }

    public void setClearingclerk(Employee clearingclerk) {
        this.clearingclerk = clearingclerk;
    }

    public Mods getModsId() {
        return modsId;
    }

    public void setModsId(Mods modsId) {
        this.modsId = modsId;
    }

    public Placeofloadingordischarge getPlaceofloading() {
        return placeofloading;
    }

    public void setPlaceofloading(Placeofloadingordischarge placeofloading) {
        this.placeofloading = placeofloading;
    }

    public Placeofloadingordischarge getPlaceofdischarge() {
        return placeofdischarge;
    }

    public void setPlaceofdischarge(Placeofloadingordischarge placeofdischarge) {
        this.placeofdischarge = placeofdischarge;
    }

    public Shippingcompany getShippingcompanyId() {
        return shippingcompanyId;
    }

    public void setShippingcompanyId(Shippingcompany shippingcompanyId) {
        this.shippingcompanyId = shippingcompanyId;
    }

    public Tradeagreement getTradeagreementId() {
        return tradeagreementId;
    }

    public void setTradeagreementId(Tradeagreement tradeagreementId) {
        this.tradeagreementId = tradeagreementId;
    }

    @XmlTransient
    public List<Letterofcredit> getLetterofcreditList() {
        return letterofcreditList;
    }

    public void setLetterofcreditList(List<Letterofcredit> letterofcreditList) {
        this.letterofcreditList = letterofcreditList;
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
        if (!(object instanceof Shipment)) {
            return false;
        }
        Shipment other = (Shipment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wajiraenterprises.entity.Shipment[ id=" + id + " ]";
    }

    public LocalDate getDateofregisterd() {
        return dateofregisterd;
    }

    public void setDateofregisterd(LocalDate dateofregisterd) {
        this.dateofregisterd = dateofregisterd;
    }

    @XmlTransient
    public List<Clearingprocess> getClearingprocessList() {
        return clearingprocessList;
    }

    public void setClearingprocessList(List<Clearingprocess> clearingprocessList) {
        this.clearingprocessList = clearingprocessList;
    }

    @XmlTransient
    public List<Cusdec> getCusdecList() {
        return cusdecList;
    }

    public void setCusdecList(List<Cusdec> cusdecList) {
        this.cusdecList = cusdecList;
    }

    @XmlTransient
    public List<Panelexamination> getPanelexaminationList() {
        return panelexaminationList;
    }

    public void setPanelexaminationList(List<Panelexamination> panelexaminationList) {
        this.panelexaminationList = panelexaminationList;
    }

    @XmlTransient
    public List<Deliveryrequest> getDeliveryrequestList() {
        return deliveryrequestList;
    }

    public void setDeliveryrequestList(List<Deliveryrequest> deliveryrequestList) {
        this.deliveryrequestList = deliveryrequestList;
    }

    @XmlTransient
    public List<Grn> getGrnList() {
        return grnList;
    }

    public void setGrnList(List<Grn> grnList) {
        this.grnList = grnList;
    }

/*    @XmlTransient
    public List<Inventry> getInventryList() {
        return inventryList;
    }

    public void setInventryList(List<Inventry> inventryList) {
        this.inventryList = inventryList;
    }*/

    @XmlTransient
    public List<Gdn> getGdnList() {
        return gdnList;
    }

    public void setGdnList(List<Gdn> gdnList) {
        this.gdnList = gdnList;
    }

    @XmlTransient
    public List<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }

    public String getDeliverylocation() {
        return deliverylocation;
    }

    public void setDeliverylocation(String deliverylocation) {
        this.deliverylocation = deliverylocation;
    }

    @XmlTransient
    public List<Customeracceptence> getCustomeracceptenceList() {
        return customeracceptenceList;
    }

    public void setCustomeracceptenceList(List<Customeracceptence> customeracceptenceList) {
        this.customeracceptenceList = customeracceptenceList;
    }





}
