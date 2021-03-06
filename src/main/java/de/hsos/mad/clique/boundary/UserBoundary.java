/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.mad.clique.boundary;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.hsos.mad.clique.communication.CustomResponse;
import de.hsos.mad.clique.controller.CliqueController;
import de.hsos.mad.clique.controller.UserCliqueController;
import de.hsos.mad.clique.controller.UserController;
import de.hsos.mad.clique.controller.UserEventController;
import de.hsos.mad.clique.entity.Clique;
import de.hsos.mad.clique.entity.UserClique;
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
import jdk.nashorn.internal.parser.JSONParser;

/**
 *
 * @author zensd
 */
@Path("users")
@RequestScoped
@Produces({MediaType.APPLICATION_JSON})
public class UserBoundary {
    @Inject
    UserController usc;
    
    @Inject
    UserEventController uec;
    
    @Inject
    UserCliqueController ucc;
    
    @Inject
    CliqueController clc;
    
    private final Gson gson = new Gson();
    private final JsonParser parser = new JsonParser();
    
    @GET
    @Path("/new/{email}/{name}/{password}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response newUser(@PathParam("email")String email, @PathParam("name")String name, @PathParam("password")String password){
        try {
            CustomResponse tmpCr = new CustomResponse(true);
            List<Users> tmpUser = usc.getDuplicatedUsers(email);
            //Duplikat Prüfung
            if(tmpUser.size() < 1){
                Users newus = new Users();
                newus.setEmail(email);
                newus.setName(name);
                newus.setPassword(password);
                usc.createUser(newus);
            }else{
                tmpCr.setSuccess(false);
            }
            return Response.accepted(gson.toJson(tmpCr)).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }
    }
    
    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllUsers(){
        try {
            List<Users> tmpList = usc.getAllUsers();
            return Response.accepted(gson.toJson(tmpList)).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }
    }
    
    @GET
    @Path("/login/{email}/{password}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserLogin(@PathParam("email")String email, @PathParam("password")String password){
        CustomResponse tmpCr = new CustomResponse(true);
        try {
            List<Users> tmpList = usc.getUserLogin(email, password);
            if(tmpList.size() < 1){
                tmpCr.setSuccess(false);
            }
            return Response.accepted(gson.toJson(tmpCr)).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }
    }
    
    @GET
    @Path("/name/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserByName(@PathParam("name")String name){
        try {
            List<Users>tmpList = usc.getUsersByName(name);
            return Response.accepted(gson.toJson(tmpList)).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }
    }
    
    @GET
    @Path("/email/{email}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserByEmail(@PathParam("email")String email){
        try {
            Users user = usc.getUsersByEmail(email);
            return Response.accepted(gson.toJson(user)).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }
    }
    
    @GET
    @Path("/id/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserById(@PathParam("id")long id){
        try {
            Users user = usc.getUserById(id);
            return Response.accepted(gson.toJson(user)).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }
    }
    
    @GET
    @Path("/id/{email}/{password}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getIdByEmailPassword(@PathParam("email")String email, @PathParam("password")String password){
        try {
            long id = usc.getIdByEmailPassword(password, email);
            return Response.accepted(gson.toJson(id)).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }
    }
    
    @PUT
    @Path("/email/{oldemail}/{newemail}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response updateUsersEmail(@PathParam("oldemail")String oldemail, @PathParam("newemail")String newemail){
        try {
            usc.updateUsersEmail(oldemail, newemail);
            return Response.status(202).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }
    }
    
    @PUT
    @Path("/name/{id}/{newname}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response updateUsersNameById(@PathParam("id")long id, @PathParam("newname")String newname){
        try {
            usc.updateUsersName(id, newname);
            return Response.status(202).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }
    }
    
    @PUT
    @Path("/password/{id}/{newpassword}")
    public Response updateUsersPasswordById(@PathParam("id")long id, @PathParam("newpassword")String newpassword){
        try {
            usc.updatePassword(id, newpassword);
            return Response.status(202).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }
    }
    
    @DELETE
    @Path("/delete/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteUserById(@PathParam("id")long id){
        try {
            /*
                ToDo:
                User aus UserClique löschen
                User aus UserEvent löschen
                User aus User löschen
            */
            //User Objekt holen mit userid
            Users tmpUser = new Users();
            tmpUser = usc.getUserById(id);
            
            //User aus UserEvent löschen
            uec.deltetUserEventsByUserId(tmpUser);
            
            //User aus UserClique löschen
            ucc.deleteUserCliqueByUser(tmpUser);
            
            //User aus Users löschen
            usc.deleteUser(id);
            return Response.status(202).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }
    }
}
