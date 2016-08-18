/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.mad.clique.boundary;

import de.hsos.mad.clique.controller.UserController;
import de.hsos.mad.clique.entity.Users;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author zensd
 */
@Path("user")
@RequestScoped
@Produces({MediaType.APPLICATION_JSON})
public class UserBoundary {
    @Inject
    UserController usc;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public void newUser(){
        try {
            Users newus = new Users();
            newus.setEmail("test");
            newus.setName("test");
            newus.setPassword("test");
            usc.createUser(newus);
        } catch (Exception e) {
        }
    }
}
