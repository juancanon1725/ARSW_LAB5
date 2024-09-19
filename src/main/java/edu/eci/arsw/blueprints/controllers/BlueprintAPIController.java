/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {

    @Autowired
    BlueprintsServices bps;

    @GetMapping()
    public ResponseEntity<?> getAllBlueprint() throws ResourceNotFoundException{
        try{
            return new ResponseEntity<>(bps.getAllBlueprints(), HttpStatus.OK);
        }catch(BlueprintNotFoundException | BlueprintPersistenceException ex){
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{author}")
    public ResponseEntity<?> getBlueprintByAuthor(@PathVariable("author") String author) throws ResourceNotFoundException, BlueprintNotFoundException, BlueprintPersistenceException {
        Set<Blueprint> blueprints = bps.getBlueprintsByAuthor(author);
        if (blueprints.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        return new ResponseEntity<>(blueprints, HttpStatus.OK);
    }

    @GetMapping(value="/{author}/{name}")
    public ResponseEntity<?>  getBlueprintByAuthorAndName(@PathVariable("author") String author,@PathVariable("name") String name )throws ResourceNotFoundException, BlueprintNotFoundException{
        try {
            return new ResponseEntity<>(bps.getBlueprint(author,name), HttpStatus.OK);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error",HttpStatus.NOT_FOUND);
        }
    }

}

