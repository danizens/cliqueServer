/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.mad.clique.controller;

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
public class UserController {
    @PersistenceContext(unitName = "de.hsos.mad_Clique_war_1.0-SNAPSHOTPU")
    protected EntityManager em;
    
    public void createUser(Users newUser){
        em.persist(newUser);
    }
    
    public List<Users> getAllUsers(){
        TypedQuery<Users> query = em.createQuery("SELECT u FROM Users u", Users.class);
        return (List<Users>)query.getResultList();
    }
    
    public List<Users> getUserLogin(String username, String password){
        TypedQuery<Users> query = em.createQuery("SELECT u FROM Users u WHERE u.name = :username AND u.password = :password",Users.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        return (List<Users>)query.getResultList();
    }
    
    public List<Users> getUsersByName(String name){
        TypedQuery<Users> query = em.createQuery("SELECT u FROM Users u WHERE u.name = :username",Users.class);
        query.setParameter("username", name);
        return (List<Users>)query.getResultList();
    }
    
    public Users getUsersByEmail(String email){
        TypedQuery<Users> query = em.createQuery("SELECT u FROM Users u WHERE u.email = :email",Users.class);
        query.setParameter("email", email);
        return (Users)query.getSingleResult();
    }
    
    public Users getUserById(long id){
        TypedQuery<Users> query = em.createQuery("SELECT u FROM Users u WHERE u.id = :id",Users.class);
        query.setParameter("id", id);
        return (Users)query.getSingleResult();
    }
    
    public long getIdByEmailPassword(String password, String email){
        TypedQuery<Users> query = em.createQuery("SELECT u FROM Users u WHERE u.email = :email AND u.password = :password", Users.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        return (long)query.getSingleResult().getId();
    }
    
    public void updateUsersEmail(String oldEmail, String newEmail){
        TypedQuery<Users> query = em.createQuery("UPDATE Users u SET u.email = :newemail WHERE u.email = :oldemail",Users.class);
        query.setParameter("oldemail", oldEmail);
        query.setParameter("newemail", newEmail);
        query.executeUpdate();
    }
    
    public void updateUsersName(long id, String newName){
        TypedQuery<Users> query = em.createQuery("UPDATE Users u SET u.name = :newname WHERE u.id = :id",Users.class);
        query.setParameter("id", id);
        query.setParameter("newname", newName);
        query.executeUpdate();
    }
    
    public void updatePassword(long id, String password){
        TypedQuery<Users> query = em.createQuery("UPDATE Users u SET u.password = :newpassword WHERE u.id = :id",Users.class);
        query.setParameter("id", id);
        query.setParameter("newpassword", password);
        query.executeUpdate();
    }
    
    public void deleteUser(long id){
        TypedQuery<Users> query = em.createQuery("DELETE FROM Users u WHERE u.id = :id",Users.class);
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
