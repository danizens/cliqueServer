/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.mad.clique.controller;

import de.hsos.mad.clique.entity.Events;
import de.hsos.mad.clique.entity.UserEvent;
import de.hsos.mad.clique.entity.Users;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author zensd
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserEventController {
    @PersistenceContext(unitName = "de.hsos.mad_Clique_war_1.0-SNAPSHOTPU")
    protected EntityManager em;
    
    public void deleteUserEventsByEventId(Events event){
        TypedQuery<UserEvent> query = em.createQuery("DELETE FROM UserEvent ue WHERE ue.event = :event", UserEvent.class);
        query.setParameter("event", event);
        query.executeUpdate();
    }
    
    public void deltetUserEventsByUserId(Users user){
        TypedQuery<UserEvent> query = em.createQuery("DELETE FROM UserEvent ue WHERE ue.user = :users", UserEvent.class);
        query.setParameter("users", user);
        query.executeUpdate();
    }
    
    public void createUserEvent(UserEvent ue){
        em.persist(ue);
    }
}
