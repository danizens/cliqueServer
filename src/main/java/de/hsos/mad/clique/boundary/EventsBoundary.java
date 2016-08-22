/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.mad.clique.boundary;

import com.google.gson.Gson;
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
@Path("events")
@RequestScoped
@Produces({MediaType.APPLICATION_JSON})
public class EventsBoundary {
    @Inject
    EventsController evc;
    
    @Inject
    CliqueController clc;
    
    @Inject
    UserCliqueController ucc;
    
    @Inject
    UserEventController uec;
    
    @Inject
    UserController usc;
    
    private final Gson gson = new Gson();
    
    @GET
    @Path("create/{cliquenid}/{name}/{street}/{plz}/{place}/{description}/{date}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response createNewEvent(@PathParam("cliquenid")long id, @PathParam("name")String name,
            @PathParam("street")String street, @PathParam("plz")String plz, @PathParam("place")String place,
            @PathParam("description")String description, @PathParam("date")String date){
        try {
            //Cliquen Objekt fürs Event holen
            Clique tmpClique = new Clique();
            tmpClique = clc.getCliqueByID(id);
            
            //Event erstellen
            Events tmpEvent = new Events();
            tmpEvent.setClique(tmpClique);
            tmpEvent.setCreateDate(date);
            tmpEvent.setDescription(description);
            tmpEvent.setName(name);
            tmpEvent.setPlace(place);
            tmpEvent.setPlz(plz);
            tmpEvent.setStreet(street);
            evc.createNewEvent(tmpEvent);
            
            //EventId holen
            tmpEvent = evc.getEventIdByName(tmpEvent.getName());
            
            //Event in UserEvent eintragen
            List<UserClique>tmpUCList = ucc.getUserByCliqueId(tmpClique);
            for(int i = 0; i < tmpUCList.size();i++){
                UserEvent tmpUserEvent = new UserEvent();
                //User aus UserClique holen
                Users tmpUser = new Users();
                tmpUser = usc.getUserById(tmpUCList.get(i).getUser().getId());
                
                tmpUserEvent.setUser(tmpUser);
                tmpUserEvent.setEvent(tmpEvent);
                tmpUserEvent.setAbsagen(false);
                tmpUserEvent.setOffen(true);
                tmpUserEvent.setZusagen(false);
                uec.createUserEvent(tmpUserEvent);
            }
            
            return Response.status(202).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }
    }
    
    @DELETE
    @Path("delete/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteEventById(@PathParam("id")long id){
        /*
            ToDo:
            EventID aus UserEvent löschen
            Event aus Events löschen
        */
        try {
            //Event holen das gelöscht werden soll
            Events tmpEvents = new Events();
            tmpEvents = evc.getEventsById(id);
        
            //Event aus UserEvent löschen
            uec.deleteUserEventsByEventId(tmpEvents);
            
            //Event aus Events löschen
            evc.deleteEventsById(id);
            return Response.status(202).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }  
    }
    
    @PUT
    @Path("update/{userid}/{eventid}/{status}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response updateEventStatus(@PathParam("userid")long userid, @PathParam("eventid")long eventid, @PathParam("status")boolean status){
        try {
            Users tmpUser = new Users();
            tmpUser = usc.getUserById(userid);
            
            Events tmpEvent = new Events();
            tmpEvent = evc.getEventsById(eventid);
            
            uec.updateEventStatus(tmpEvent, tmpUser, status );
            return Response.status(202).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }
    }
    
    @GET
    @Path("get/{userid}/{cliquenid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllEventsByUserAndClique(@PathParam("userid")long userid, @PathParam("cliquenid")long cliquenid){
        try {
            //User holen
            Users tmpUser = new Users();
            tmpUser = usc.getUserById(userid);
            
            //Clique holen in denen der User drin ist
            List<UserClique> tmpUserCliqueList = ucc.getCliqueByUserId(tmpUser);
            
            //CliquenObjekte durchgehen
            List<Clique> tmpCliqueList = new ArrayList<>();
            for(int i = 0; i < tmpUserCliqueList.size(); i++){
                Clique cl = tmpUserCliqueList.get(i).getClique();
                //System.out.println(cl.getId());
                tmpCliqueList.add(clc.getCliqueByID(cl.getId()));
                //tmpCliqueList.add(cl);
            }
            
            List<Events> tmpEventList = new ArrayList<>();
            for(int i = 0; i < tmpCliqueList.size();i++){
                tmpEventList.add(evc.getEventsByCliqueId(tmpCliqueList.get(i)));
            }
            
            return Response.accepted(gson.toJson(tmpUserCliqueList)).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }
    }
}
