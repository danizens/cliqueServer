/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.mad.clique.controller;

import de.hsos.mad.clique.entity.Clique;
import de.hsos.mad.clique.entity.Events;
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
public class EventsController {
    @PersistenceContext(unitName = "de.hsos.mad_Clique_war_1.0-SNAPSHOTPU")
    protected EntityManager em;
    
    public List<Events> getEventsByClique(Clique clique){
        TypedQuery<Events> query = em.createQuery("SELECT e FROM Events e WHERE e.clique = :clique ", Events.class);
        query.setParameter("clique", clique);
        return (List<Events>)query.getResultList();
    }
    
    public void deleteEventsByClique(Clique clique){
        TypedQuery<Events> query = em.createQuery("DELETE FROM Events e WHERE e.clique = :clique",Events.class);
        query.setParameter("clique", clique);
        query.executeUpdate();
    }
    
    public void deleteEventsById(long id){
        TypedQuery<Events> query = em.createQuery("DELETE FROM Events e WHERE e.id = :id",Events.class);
        query.setParameter("id", id);
        query.executeUpdate();
    }
    
    public void createNewEvent(Events event){
        em.persist(event);
    }
    
    public Events getEventIdByName(String name){
        TypedQuery<Events> query = em.createQuery("SELECT e FROM Events e WHERE e.name = :name ", Events.class);
        query.setParameter("name", name);
        return (Events)query.getSingleResult();
    }
    
    public Events getEventsById(long id){
        TypedQuery<Events> query = em.createQuery("SELECT e FROM Events e WHERE e.id = :id ", Events.class);
        query.setParameter("id", id);
        return (Events)query.getSingleResult();
    }
}
