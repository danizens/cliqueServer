/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.mad.clique.controller;

import de.hsos.mad.clique.entity.Clique;
import de.hsos.mad.clique.entity.Events;
import de.hsos.mad.clique.entity.UserEvent;
import de.hsos.mad.clique.entity.Users;
import java.util.List;
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
    
    public void updateEventStatus(Events event, Users user, boolean status){
        if(status == true){
            TypedQuery<Events> query1 = em.createQuery("UPDATE UserEvent ue SET ue.zusagen = :accepted  WHERE ue.event = :event AND ue.user = :user", Events.class);
            query1.setParameter("accepted", true);
            query1.setParameter("event", event);
            query1.setParameter("user", user);
            query1.executeUpdate();
            TypedQuery<Events> query2 = em.createQuery("UPDATE UserEvent ue SET ue.absagen = :declined  WHERE ue.event = :event1 AND ue.user = :user1", Events.class);
            query2.setParameter("declined", false);
            query2.setParameter("event1", event);
            query2.setParameter("user1", user);
            query2.executeUpdate();
            TypedQuery<Events> query3 = em.createQuery("UPDATE UserEvent ue SET ue.offen = :open  WHERE ue.event = :event2 AND ue.user = :user2", Events.class);
            query3.setParameter("open", false);
            query3.setParameter("event2", event);
            query3.setParameter("user2", user);
            query3.executeUpdate();
        }else{
            TypedQuery<Events> query1 = em.createQuery("UPDATE UserEvent ue SET ue.zusagen = :accepted  WHERE ue.event = :event AND ue.user = :user", Events.class);
            query1.setParameter("accepted", false);
            query1.setParameter("event", event);
            query1.setParameter("user", user);
            query1.executeUpdate();
            TypedQuery<Events> query2 = em.createQuery("UPDATE UserEvent ue SET ue.absagen = :declined  WHERE ue.event = :event1 AND ue.user = :user1", Events.class);
            query2.setParameter("declined", true);
            query2.setParameter("event1", event);
            query2.setParameter("user1", user);
            query2.executeUpdate();
            TypedQuery<Events> query3 = em.createQuery("UPDATE UserEvent ue SET ue.offen = :open  WHERE ue.event = :event2 AND ue.user = :user2", Events.class);
            query3.setParameter("open", false);
            query3.setParameter("event2", event);
            query3.setParameter("user2", user);
            query3.executeUpdate();
        }
    }
}
