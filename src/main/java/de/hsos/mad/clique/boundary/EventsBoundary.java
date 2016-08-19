/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.mad.clique.boundary;

import com.google.gson.Gson;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author zensd
 */
@Path("events")
@RequestScoped
@Produces({MediaType.APPLICATION_JSON})
public class EventsBoundary {
    private final Gson gson = new Gson();
}
