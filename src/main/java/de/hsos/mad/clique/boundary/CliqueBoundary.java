/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.mad.clique.boundary;

import com.google.gson.Gson;
import de.hsos.mad.clique.controller.CliqueController;
import de.hsos.mad.clique.controller.UserCliqueController;
import de.hsos.mad.clique.controller.UserController;
import de.hsos.mad.clique.entity.Clique;
import de.hsos.mad.clique.entity.UserClique;
import de.hsos.mad.clique.entity.Users;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author zensd
 */
@Path("clique")
@RequestScoped
@Produces({MediaType.APPLICATION_JSON})
public class CliqueBoundary {
    @Inject
    CliqueController clc;
    
    @Inject
    UserCliqueController ucc;
    
    @Inject
    UserController usc;
    
    private final Gson gson = new Gson();
    
    @GET
    @Path("/create/{userid}/{name}")
    public Response newClique(@PathParam("userid")long userid, @PathParam("name")String name){
        try {
            //User Objekt holen
            Users tmpUser = new Users();
            tmpUser = usc.getUserById(userid);
            
            //Clique anlegen
            Clique tmpClique = new Clique();
            tmpClique.setCreator(tmpUser.getName());
            tmpClique.setName(name);
            clc.createClique(tmpClique);
            
            //Clique Objekt holen
            tmpClique = clc.getCliqueByName(name);
            
            //User_Clique eintrag setzen
            UserClique uc = new UserClique();
            uc.setClique(tmpClique);
            uc.setUser(tmpUser);
            ucc.createNewUserClique(uc);
            return Response.status(202).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }
    }
    
    @PUT
    @Path("/update/{cliquenid}/{newname}")
    public Response updateCliqueName(@PathParam("cliquenid")long cliquenid, @PathParam("newname")String newname){
        try {
            clc.updateCliqueNameById(cliquenid, newname);
            return Response.status(202).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }
    }
    
    @DELETE
    @Path("/delete/{cliquenid}")
    public Response deleteCliqueById(@PathParam("cliquenid")long cliquenid){
        try {
            /*ToDo:
                Löschen der Clique aus Events mit CliqueID.
                Löschen aller User_Events die mit der Clique verbunden waren.
            */
            
            //Löschen der Clique aus UserClique mit allen Usern die in der Clique waren
            Clique tmpClique = new Clique();
            tmpClique = clc.getCliqueByID(cliquenid);
            ucc.deleteUserCliqueByCliqueId(tmpClique);
            
            //Löschen der Clique aus Tabelle Clique
            clc.deleteCliqueById(cliquenid);
            return Response.status(202).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }
    }
}
