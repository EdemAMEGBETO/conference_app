package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.Session;
import com.pluralsight.conferencedemo.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/sessions")
public class SessionsController {
    @Autowired
    SessionRepository sessionRepository;

    @GetMapping
    public List<Session> list() {
        return sessionRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Session get(@PathVariable Long id) {
        return sessionRepository.getOne(id);
       // return sessionRepository.find(id);
    }

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public Session create(@RequestBody final Session session){
        return sessionRepository.saveAndFlush(session);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        // Vous devez également vérifier les enregistrements des enfants avant de les supprimer.
        sessionRepository.deleteById(id);
    }

    //RequestMethod.PUT permet de modifier toutes les attributs de la table, et les attributs qui ne sont pas fournis seronnt remplacés par "null"
    //RequestMethod.PATCH, lui permet de modifier certains attributs de la table que l'on veut modifier
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Session update(@PathVariable Long id, @RequestBody Session session) {
        //comme il s'agit d'un PUT, nous nous attendons à ce que tous les attributs soient transmis. Un PATCH n'aurait besoin que de ce qui a changé.
        //TODO: Ajoutez une validation indiquant que tous les attributs sont transmis, sinon renvoyez une charge utile incorrecte de 400
        Session existingSession = sessionRepository.getOne(id);
        BeanUtils.copyProperties(session, existingSession, "session_id");
        return sessionRepository.saveAndFlush(existingSession);
    }

  /*  @GetMapping
    public List<Session> list(@RequestParam(required = false) String name) {
        if(name != null) {
            return sessionRepository.getSessionsThatHaveName(name);
        } else {
            return sessionRepository.list();
        }
    }

    @DeleteMapping
    public void delete(@PathVariable Long id) {
        sessionRepository.delete(id);
    }
*/
}
