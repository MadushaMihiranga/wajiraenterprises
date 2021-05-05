/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wajiraenterprises.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
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
@Table(name = "branch")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Branch.findAll", query = "SELECT b FROM Branch b")})
public class Branch implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branchId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Paytaxes> paytaxesList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branchId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Cheque> chequeList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;
    @JoinColumn(name = "bank_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Bank bankId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branchId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Letterofcredit> letterofcreditList;

    public Branch() {
    }

    public Branch(Integer id, String code, String name, Bank bankId) {
        this.id= id;
        this.name = name;
        this.code = code;
        this.bankId = bankId;
    }

    public Branch(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Bank getBankId() {
        return bankId;
    }

    public void setBankId(Bank bankId) {
        this.bankId = bankId;
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
        if (!(object instanceof Branch)) {
            return false;
        }
        Branch other = (Branch) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wajiraenterprises.entity.Branch[ id=" + id + " ]";
    }

    @XmlTransient
    public List<Paytaxes> getPaytaxesList() {
        return paytaxesList;
    }

    public void setPaytaxesList(List<Paytaxes> paytaxesList) {
        this.paytaxesList = paytaxesList;
    }

    @XmlTransient
    public List<Cheque> getChequeList() {
        return chequeList;
    }

    public void setChequeList(List<Cheque> chequeList) {
        this.chequeList = chequeList;
    }


}
