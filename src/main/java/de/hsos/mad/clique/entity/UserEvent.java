/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.mad.clique.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author zensd
 */
@Entity
public class UserEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private boolean zusagen;
    private boolean absagen;
    private boolean offen;
    
    @ManyToOne
    private Events event;
    
    @ManyToOne
    private Users user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isZusagen() {
        return zusagen;
    }

    public void setZusagen(boolean zusagen) {
        this.zusagen = zusagen;
    }

    public boolean isAbsagen() {
        return absagen;
    }

    public void setAbsagen(boolean absagen) {
        this.absagen = absagen;
    }

    public boolean isOffen() {
        return offen;
    }

    public void setOffen(boolean offen) {
        this.offen = offen;
    }

    public Events getEvent() {
        return event;
    }

    public void setEvent(Events event) {
        this.event = event;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
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
        if (!(object instanceof UserEvent)) {
            return false;
        }
        UserEvent other = (UserEvent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.hsos.mad.clique.entity.UserEvent[ id=" + id + " ]";
    }
    
}
