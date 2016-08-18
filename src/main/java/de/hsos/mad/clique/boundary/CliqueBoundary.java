/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.mad.clique.boundary;

import com.google.gson.Gson;
import de.hsos.mad.clique.controller.CliqueController;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author zensd
 */
@Path("clique")
@RequestScoped
@Produces({MediaType.APPLICATION_JSON})
public class CliqueBoundary {
    @Inject
    CliqueController csc;
    
    private final Gson gson = new Gson();
}
