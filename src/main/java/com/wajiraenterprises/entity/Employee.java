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
@Table(name = "employee")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e")})
public class Employee implements Serializable {

    @Lob
    @Column(name = "photo")
    private byte[] photo;
    @Column(name = "dobirth")
    private LocalDate dobirth;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Payment> paymentList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "issuedby", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Gdn> gdnList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createemployee", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Invoice> invoiceList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recivedby", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Grn> grnList;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Deliveryconfirmation> deliveryconfirmationList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Vehiclerentelcompany> vehiclerentelcompanyList;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "requestedby", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Deliveryrequest> deliveryrequestList;

    @Column(name = "doassignment")
    private LocalDate doassignment;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Panelexamination> panelexaminationList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Paytaxes> paytaxesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "submitby", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Cusdec> cusdecList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pickby", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Collectdo> collectdoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "addemployee", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Shipment> shipmentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "addemployee", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Vehicle> vehicleList;
    /*@OneToMany(mappedBy = "driveremployee", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Vehicle> vehicleList1;
    @OneToMany(mappedBy = "drivingassistantemployee", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Vehicle> vehicleList2;*/
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Messagereply> messagereplyList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createemployee", fetch = FetchType.LAZY)  //Employee who create customer
    @JsonIgnore
    private List<Company> companyList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clearingclerk", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Shipment> shipmentList1;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "number")
    //@Pattern(regexp = "^\\d{4}$", message = "Invalid Number")
    private String number;
    @Column(name = "fullname")
    @Pattern(regexp = "^([A-Z][a-z]*[.]?[\\s]?)*([A-Z][a-z]*)$", message = "Invalid Fullname")
    private String fullname;
    @Column(name = "callingname")
    @Pattern(regexp = "^([A-Z][a-z]*[.]?[\\s]?)*([A-Z][a-z]*)$", message = "Invalid Calligname")
    private String callingname;
    @Column(name = "nic")
    @Pattern(regexp = "^(([\\d]{9}[vVxX])|([\\d]{12}))$", message = "Invalid NIC")
    private String nic;
    @Lob
    @Column(name = "address")
    @Pattern(regexp = "^([\\w\\/\\-,\\s]{2,})$", message = "Invalid Address")
    private String address;
    @Column(name = "mobile")
    @Pattern(regexp = "^0\\d{9}$", message = "Invalid Mobilephone Number")
    private String mobile;
    @Column(name = "land")
    @Pattern(regexp = "^(((0\\d{9})){0,1})$", message = "Invalid Landphone Number")
    private String land;
    @Lob
    @Column(name = "description")
    @Pattern(regexp = "^.*$", message = "Invalid Description")
    private String description;
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Gender genderId;
    @JoinColumn(name = "designation_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Designation designationId;
    @JoinColumn(name = "civilstatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Civilstatus civilstatusId;
    @JoinColumn(name = "employeestatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employeestatus employeestatusId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Operationlog> operationlogList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeCreatedId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<User> userList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<User> userList1;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Customeracceptence> customeracceptenceList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "driver", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Deliveryconfirmation> deliveryconfirmationList1;

    @OneToMany(mappedBy = "assistant", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Deliveryconfirmation> deliveryconfirmationList2;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Notification> notificationList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Note> noteList;



    public Employee() {
    }

    public Employee(Integer id) {
        this.id = id;
    }

    public Employee(String number) {
        this.number = number;
    }

    public Employee(Integer id,String callingname) {
        this.id = id;
        this.callingname=callingname;
    }

    public Employee(Designation designationId) {
        this.designationId = designationId;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCallingname() {
        return callingname;
    }

    public void setCallingname(String callingname) {
        this.callingname = callingname;
    }

    @XmlTransient
    public List<Shipment> getShipmentList1() {
        return shipmentList1;
    }

    public void setShipmentList1(List<Shipment> shipmentList1) {
        this.shipmentList1 = shipmentList1;
    }



    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Gender getGenderId() {
        return genderId;
    }

    public void setGenderId(Gender genderId) {
        this.genderId = genderId;
    }

    public Designation getDesignationId() {
        return designationId;
    }

    public void setDesignationId(Designation designationId) {
        this.designationId = designationId;
    }

    public Civilstatus getCivilstatusId() {
        return civilstatusId;
    }

    public void setCivilstatusId(Civilstatus civilstatusId) {
        this.civilstatusId = civilstatusId;
    }

    public Employeestatus getEmployeestatusId() {
        return employeestatusId;
    }

    public void setEmployeestatusId(Employeestatus employeestatusId) {
        this.employeestatusId = employeestatusId;
    }

    @XmlTransient
    public List<Operationlog> getOperationlogList() {
        return operationlogList;
    }

    public void setOperationlogList(List<Operationlog> operationlogList) {
        this.operationlogList = operationlogList;
    }

    @XmlTransient
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @XmlTransient
    public List<User> getUserList1() {
        return userList1;
    }

    public void setUserList1(List<User> userList1) {
        this.userList1 = userList1;
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
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.earth.bitproject.entity.Employee[ id=" + id + " ]";
    }


    @XmlTransient
    public List<Company> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<Company> companyList) {
        this.companyList = companyList;
    }


    @XmlTransient
    public List<Messagereply> getMessagereplyList() {
        return messagereplyList;
    }

    public void setMessagereplyList(List<Messagereply> messagereplyList) {
        this.messagereplyList = messagereplyList;
    }



    @XmlTransient
    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    /*@XmlTransient
    public List<Vehicle> getVehicleList1() {
        return vehicleList1;
    }

    public void setVehicleList1(List<Vehicle> vehicleList1) {
        this.vehicleList1 = vehicleList1;
    }

    @XmlTransient
    public List<Vehicle> getVehicleList2() {
        return vehicleList2;
    }

    public void setVehicleList2(List<Vehicle> vehicleList2) {
        this.vehicleList2 = vehicleList2;
    }*/


    @XmlTransient
    public List<Shipment> getShipmentList() {
        return shipmentList;
    }

    public void setShipmentList(List<Shipment> shipmentList) {
        this.shipmentList = shipmentList;
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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public LocalDate getDobirth() {
        return dobirth;
    }

    public void setDobirth(LocalDate dobirth) {
        this.dobirth = dobirth;
    }

    public LocalDate getDoassignment() {
        return doassignment;
    }

    public void setDoassignment(LocalDate doassignment) {
        this.doassignment = doassignment;
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


    @XmlTransient
    public List<Deliveryrequest> getDeliveryrequestList() {
        return deliveryrequestList;
    }

    public void setDeliveryrequestList(List<Deliveryrequest> deliveryrequestList) {
        this.deliveryrequestList = deliveryrequestList;
    }


    @XmlTransient
    public List<Deliveryconfirmation> getDeliveryconfirmationList() {
        return deliveryconfirmationList;
    }

    public void setDeliveryconfirmationList(List<Deliveryconfirmation> deliveryconfirmationList) {
        this.deliveryconfirmationList = deliveryconfirmationList;
    }

    @XmlTransient
    public List<Vehiclerentelcompany> getVehiclerentelcompanyList() {
        return vehiclerentelcompanyList;
    }

    public void setVehiclerentelcompanyList(List<Vehiclerentelcompany> vehiclerentelcompanyList) {
        this.vehiclerentelcompanyList = vehiclerentelcompanyList;
    }


    @XmlTransient
    public List<Grn> getGrnList() {
        return grnList;
    }

    public void setGrnList(List<Grn> grnList) {
        this.grnList = grnList;
    }

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

    @XmlTransient
    public List<Customeracceptence> getCustomeracceptenceList() {
        return customeracceptenceList;
    }

    public void setCustomeracceptenceList(List<Customeracceptence> customeracceptenceList) {
        this.customeracceptenceList = customeracceptenceList;
    }

    @XmlTransient
    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }


    @XmlTransient
    public List<Deliveryconfirmation> getDeliveryconfirmationList1() {
        return deliveryconfirmationList1;
    }

    public void setDeliveryconfirmationList1(List<Deliveryconfirmation> deliveryconfirmationList1) {
        this.deliveryconfirmationList1 = deliveryconfirmationList1;
    }

    @XmlTransient
    public List<Deliveryconfirmation> getDeliveryconfirmationList2() {
        return deliveryconfirmationList2;
    }

    public void setDeliveryconfirmationList2(List<Deliveryconfirmation> deliveryconfirmationList2) {
        this.deliveryconfirmationList2 = deliveryconfirmationList2;
    }

    @XmlTransient
    public List<Notification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    @XmlTransient
    public List<Note> getNoteList() {
        return noteList;
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
    }



}
