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
@Table(name = "checkpoints")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Checkpoints.findAll", query = "SELECT c FROM Checkpoints c")})
public class Checkpoints implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "checkpointsId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Examinationhascheckpoints> examinationhascheckpointsList;

    public Checkpoints() {
    }

    public Checkpoints(Integer id,String name) {
        this.id  = id;
        this.name = name;
    }

    public Checkpoints(Integer id) {
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
        if (!(object instanceof Checkpoints)) {
            return false;
        }
        Checkpoints other = (Checkpoints) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wajiraenterprises.entity.Checkpoints[ id=" + id + " ]";
    }
    
}
