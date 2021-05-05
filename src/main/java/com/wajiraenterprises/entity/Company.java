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
@Table(name = "company")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Company.findAll", query = "SELECT c FROM Company c")})
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "number")
    private String number;
    @Column(name = "name")
    @Pattern(regexp = "^[A-Za-z() -]{3,50}$", message = "Invalid Company Name")
    private String name;
    @Column(name = "companyregno")
    @Pattern(regexp = "^[a-zA-Z0-9-]*$", message = "Company registration number Number is Invalid")
    private String companyregno;
    @Column(name = "companyregdate")
   // @Temporal(TemporalType.DATE)
    private LocalDate companyregdate;
    @Column(name = "tin")
    @Pattern(regexp = "^[0-9-]{8,20}$", message = "Invalid TIN")
    private String tin;

    /*@Column(name = "importlicenseno")
    @Pattern(regexp = "^[A-Z]([a-zA-Z0-9]|[- @\\.#&!()])*$", message = "Invalid Import License Number")*/
    //private String importlicenseno;

    @Column(name = "telephoneno")
    @Pattern(regexp = "^(((0\\d{9})){0,1})$", message = "Invalid Contact Number")
    private String telephoneno;
    @Lob
    @Column(name = "address")
    @Pattern(regexp = "^([\\w\\/\\-,\\s]{2,})$", message = "Invalid Address")
    private String address;
    @Column(name = "emailaddress")
    @Pattern(regexp = "^(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])",message = "Invalid E-mail Address as Company E-mail")
    private String emailaddress;
    @Lob
    @Column(name = "logo")
    private byte[] logo;
    @Lob
    @Column(name = "description")
    @Pattern(regexp = "^.*$", message = "Invalid Description")
    private String description;
    @Column(name = "contactpersonname")
    @Pattern(regexp = "^([A-Z][a-z]*[.]?[\\s]?)*([A-Z][a-z]*)$", message = "Invalid Name")
    private String contactpersonname;
    @Column(name = "contactpersonteleno")
    @Pattern(regexp = "^(((0\\d{9})){0,1})$", message = "Invalid Contact Number")
    private String contactpersonteleno;
    @Column(name = "adddate")
    //@Temporal(TemporalType.DATE)
    private LocalDate adddate;
    @JoinColumn(name = "createemployee", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee createemployee;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Shipment> shipmentList;
    @OneToMany(mappedBy = "companyId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<User> userList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Credit> creditList;

    @JoinColumn(name = "customerstatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Customerstatus customerstatusId;

    /*@JoinColumn(name = "credit_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Credit creditId;*/

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Payment> paymentList;



    public Company() {
    }

    public Company(Integer id,String name){
        this.id =id;
        this.name=name;
    }

    public Company(Integer id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyregno() {
        return companyregno;
    }

    public void setCompanyregno(String companyregno) {
        this.companyregno = companyregno;
    }

    public LocalDate getCompanyregdate() {
        return companyregdate;
    }

    public void setCompanyregdate(LocalDate companyregdate) {
        this.companyregdate = companyregdate;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

/*    public String getImportlicenseno() {
        return importlicenseno;
    }

    public void setImportlicenseno(String importlicenseno) {
        this.importlicenseno = importlicenseno;
    }*/

    public String getTelephoneno() {
        return telephoneno;
    }

    public void setTelephoneno(String telephoneno) {
        this.telephoneno = telephoneno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactpersonname() {
        return contactpersonname;
    }

    public void setContactpersonname(String contactpersonname) {
        this.contactpersonname = contactpersonname;
    }

    public String getContactpersonteleno() {
        return contactpersonteleno;
    }

    public void setContactpersonteleno(String contactpersonteleno) {
        this.contactpersonteleno = contactpersonteleno;
    }

    public LocalDate getAdddate() {
        return adddate;
    }

    public void setAdddate(LocalDate adddate) {
        this.adddate = adddate;
    }

    public Employee getCreateemployee() {
        return createemployee;
    }

    public void setCreateemployee(Employee createemployee) {
        this.createemployee = createemployee;
    }

    @XmlTransient
    public List<Shipment> getShipmentList() {
        return shipmentList;
    }

    public void setShipmentList(List<Shipment> shipmentList) {
        this.shipmentList = shipmentList;
    }

    @XmlTransient
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @XmlTransient
    public List<Credit> getCreditList() { return creditList; }

    public void setCreditList(List<Credit> creditList) { this.creditList = creditList; }

    public Customerstatus getCustomerstatusId() { return customerstatusId; }

    public void setCustomerstatusId(Customerstatus customerstatusId) { this.customerstatusId = customerstatusId; }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Company)) {
            return false;
        }
        Company other = (Company) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wajiraenterprises.entity.Company[ id=" + id + " ]";
    }

/*    public Credit getCreditId() {
        return creditId;
    }

    public void setCreditId(Credit creditId) {
        this.creditId = creditId;
    }*/

    @XmlTransient
    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }



}
