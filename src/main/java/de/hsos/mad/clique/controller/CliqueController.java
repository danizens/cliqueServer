/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.mad.clique.controller;

import de.hsos.mad.clique.entity.Clique;
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
public class CliqueController {
    @PersistenceContext(unitName = "de.hsos.mad_Clique_war_1.0-SNAPSHOTPU")
    protected EntityManager em;
    
    public void createClique(Clique clique){
        em.persist(clique);
    }
    
    public List<Clique> getCliqueAll(){
        TypedQuery<Clique> query = em.createQuery("SELECT c FROM Clique c", Clique.class);
        return (List<Clique>)query.getResultList();
    }
    
    public Clique getCliqueByName(String name){
        TypedQuery<Clique> query = em.createQuery("SELECT c FROM Clique c WHERE c.name = :name",Clique.class);
        query.setParameter("name", name);
        return(Clique)query.getSingleResult();
    }
    
    public void updateCliqueNameById(long id, String name){
        TypedQuery<Clique> query = em.createQuery("UPDATE Clique c SET c.name = :name WHERE c.id = :id", Clique.class);
        query.setParameter("id", id);
        query.setParameter("name", name);
        query.executeUpdate();
    }
    
    public Clique getCliqueByID(long id){
        TypedQuery<Clique> query = em.createQuery("SELECT c FROM Clique c WHERE c.id = :id",Clique.class);
        query.setParameter("id", id);
        return (Clique)query.getSingleResult();
    }
    
    public void deleteCliqueById(long id){
        TypedQuery<Clique> query = em.createQuery("DELETE FROM Clique c WHERE c.id = :id",Clique.class);
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
