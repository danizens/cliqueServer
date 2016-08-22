/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.mad.clique.boundary;

import com.google.gson.Gson;
import de.hsos.mad.clique.communication.CustomResponse;
import de.hsos.mad.clique.controller.CliqueController;
import de.hsos.mad.clique.controller.EventsController;
import de.hsos.mad.clique.controller.UserCliqueController;
import de.hsos.mad.clique.controller.UserController;
import de.hsos.mad.clique.controller.UserEventController;
import de.hsos.mad.clique.entity.Clique;
import de.hsos.mad.clique.entity.Events;
import de.hsos.mad.clique.entity.UserClique;
import de.hsos.mad.clique.entity.UserEvent;
import de.hsos.mad.clique.entity.Users;
import java.util.ArrayList;
import java.util.List;
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
    
    @Inject
    EventsController evc;
    
    @Inject
    UserEventController uev;
    
    private final Gson gson = new Gson();
    
    @GET
    @Path("/create/{userid}/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response newClique(@PathParam("userid")long userid, @PathParam("name")String name){
        try {
            CustomResponse tmpCr = new CustomResponse(true);
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
            return Response.accepted(gson.toJson(tmpCr)).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }
    }
    
    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllClique(){
        try {
            List<Clique> tmpList = clc.getCliqueAll();
            return Response.accepted(gson.toJson(tmpList)).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }
    }
    
    @PUT
    @Path("/update/{cliquenid}/{newname}")
    @Produces({MediaType.APPLICATION_JSON})
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
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteCliqueById(@PathParam("cliquenid")long cliquenid){
        try {
            /*ToDo:
                Löschen der Clique aus Events mit CliqueID.
                Löschen aller User_Events die mit der Clique verbunden waren.
            */
            //Get CliqueById
            Clique tmpClique = new Clique();
            tmpClique = clc.getCliqueByID(cliquenid);
            
            //Alle EventIDs holen die gelöscht werden sollen.
            List<Events> tmpEventList = evc.getEventsByClique(tmpClique);
            
            //Löschen der Events aus UserEvent
            for(int i = 0; i < tmpEventList.size(); i++){
                uev.deleteUserEventsByEventId(tmpEventList.get(i));
            }
            
            //Alle Events löschen mit CliqueID
            evc.deleteEventsByClique(tmpClique);
            
            //Löschen der Clique aus UserClique mit allen Usern die in der Clique waren
            ucc.deleteUserCliqueByCliqueId(tmpClique);
            
            //Löschen der Clique aus Tabelle Clique
            clc.deleteCliqueById(cliquenid);
            return Response.status(202).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }
    }
    
    @GET
    @Path("/id/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCliqueIdByName(@PathParam("name")String name){
        try {
            Clique tmpClique = clc.getCliqueByName(name);
            return Response.accepted(gson.toJson(tmpClique.getId())).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }
    }
    
    @GET
    @Path("/add/{userid}/{cliquename}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response addUserToClique(@PathParam("userid")long userid, @PathParam("cliquename")String cliquename){
        try {
            //User Objekt holen
            Users tmpUser = new Users();
            tmpUser = usc.getUserById(userid);
            //Cliquen Objekt holen
            Clique tmpClique = new Clique();
            tmpClique = clc.getCliqueByName(cliquename);
            //UserCliquen Objekt anlegen und hinzufügen
            UserClique tmpUserClique = new UserClique();
            tmpUserClique.setClique(tmpClique);
            tmpUserClique.setUser(tmpUser);
            ucc.addUserToCliqueUser(tmpUserClique);
            
            //Events der Clique laden und in UserEvents eintragen
            List<Events> tmpEventList = evc.getEventsByClique(tmpClique);
            for(int i = 0; i < tmpEventList.size();i++){
                UserEvent tmpUserEvent = new UserEvent();
                tmpUserEvent.setUser(tmpUser);
                tmpUserEvent.setEvent(tmpEventList.get(i));
                tmpUserEvent.setOffen(true);
                tmpUserEvent.setZusagen(false);
                tmpUserEvent.setAbsagen(false);
                uev.createUserEvent(tmpUserEvent);
            }
            
            return Response.status(202).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }
    }
    
    @GET
    @Path("user/{userid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserInCliqueByUserId(@PathParam("userid")long id){
        try {
            //User Objekt holen
            Users tmpUser = usc.getUserById(id);
            
            //List mit Ciquen holen
            List<UserClique> tmpUserCLiqueList = ucc.getCliqueByUserId(tmpUser);
            List<Clique> tmpCliqueList = new ArrayList();
            //Cliquen aus Clique holen
            for(int i = 0; i < tmpUserCLiqueList.size(); i++){
                UserClique tmpUserClique = tmpUserCLiqueList.get(i);
                tmpCliqueList.add(clc.getCliqueByID(tmpUserClique.getClique().getId()));
            }
            return Response.accepted(gson.toJson(tmpCliqueList)).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }
    }
}
