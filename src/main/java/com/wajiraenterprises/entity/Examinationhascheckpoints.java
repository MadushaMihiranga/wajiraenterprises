/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wajiraenterprises.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;

import java.io.Serializable;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author madusha mihiranga
 */
@Entity
@Table(name = "examinationhascheckpoints")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Examinationhascheckpoints.findAll", query = "SELECT e FROM Examinationhascheckpoints e")})
public class Examinationhascheckpoints implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
 /*   @Lob
    @Column(name = "discription")
    private String discription;
    @Lob
    @Column(name = "solution")
    private String solution;*/
   /* @JoinColumn(name = "pestatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Pestatus pestatusId;*/

    //@JsonIgnore
    @JoinColumn(name = "checkpoints_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Checkpoints checkpointsId;

    @JoinColumn(name = "panelexamination_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnore
    private Panelexamination panelexaminationId;


    @Basic(optional = false)
    @Column(name = "cpstatus")
    private boolean cpstatus;

/*    @JoinColumn(name = "cpstatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    //@Fetch(value = SELECT)
    private Cpstatus cpstatusId;*/

    public Examinationhascheckpoints() {
    }

    public Examinationhascheckpoints(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /*public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }



*/
/*    public Pestatus getPestatusId() {
        return pestatusId;
    }

    public void setPestatusId(Pestatus pestatusId) {
        this.pestatusId = pestatusId;
    }*/



    public boolean getCpstatus() {
        return cpstatus;
    }

    public void setCpstatus(boolean cpstatus) {
        this.cpstatus = cpstatus;
    }


    public Checkpoints getCheckpointsId() {
        return checkpointsId;
    }

    public void setCheckpointsId(Checkpoints checkpointsId) {
        this.checkpointsId = checkpointsId;
    }

    public Panelexamination getPanelexaminationId() {
        return panelexaminationId;
    }

    public void setPanelexaminationId(Panelexamination panelexaminationId) {
        this.panelexaminationId = panelexaminationId;
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
        if (!(object instanceof Examinationhascheckpoints)) {
            return false;
        }
        Examinationhascheckpoints other = (Examinationhascheckpoints) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wajiraenterprises.entity.Examinationhascheckpoints[ id=" + id + " ]";
    }

/*    public Cpstatus getCpstatusId() {
        return cpstatusId;
    }

    public void setCpstatusId(Cpstatus cpstatusId) {
        this.cpstatusId = cpstatusId;
    }*/

}
