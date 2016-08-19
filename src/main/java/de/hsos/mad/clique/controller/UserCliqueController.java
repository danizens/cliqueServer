/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.mad.clique.controller;

import de.hsos.mad.clique.entity.Clique;
import de.hsos.mad.clique.entity.UserClique;
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
public class UserCliqueController {
    @PersistenceContext(unitName = "de.hsos.mad_Clique_war_1.0-SNAPSHOTPU")
    protected EntityManager em;
    
    public void createNewUserClique(UserClique uc){
        em.persist(uc);
    }
    
    public void deleteUserCliqueByCliqueId(Clique cl){
        TypedQuery<UserClique> query = em.createQuery("DELETE FROM UserClique uc WHERE uc.clique = :cl", UserClique.class);
        query.setParameter("cl", cl);
        query.executeUpdate();
    }
    
    public void deleteUserCliqueByUser(Users user){
        TypedQuery<UserClique> query = em.createQuery("DELETE FROM UserClique uc WHERE uc.user = :user", UserClique.class);
        query.setParameter("user", user);
        query.executeUpdate();
    }
    
    public List<UserClique> getUserByCliqueId(Clique cl){
        TypedQuery<UserClique> query = em.createQuery("SELECT uc FROM UserClique uc WHERE uc.clique = :clique",UserClique.class);
        query.setParameter("clique", cl);
        return (List<UserClique>)query.getResultList();
    }
}
