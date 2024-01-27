package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.Session;
import com.pluralsight.conferencedemo.models.Speaker;
import com.pluralsight.conferencedemo.repositories.SessionRepository;
import com.pluralsight.conferencedemo.repositories.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/speakers")
public class SpeakersController {

    @Autowired
    SpeakerRepository speakerRepository;

    @GetMapping
    public List<Speaker> list() {
        return speakerRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Speaker get(@PathVariable Long id) {
        return speakerRepository.getOne(id);
        // return sessionRepository.find(id);
    }

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public Speaker create(@RequestBody final Speaker speaker){
        return speakerRepository.saveAndFlush(speaker);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        // Vous devez également vérifier les enregistrements des enfants avant de les supprimer.
        speakerRepository.deleteById(id);
    }

    //RequestMethod.PUT permet de modifier toutes les attributs de la table, et les attributs qui ne sont pas fournis seronnt remplacés par "null"
    //RequestMethod.PATCH, lui permet de modifier certains attributs de la table que l'on veut modifier
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Speaker update(@PathVariable Long id, @RequestBody Speaker speaker) {
        //comme il s'agit d'un PUT, nous nous attendons à ce que tous les attributs soient transmis. Un PATCH n'aurait besoin que de ce qui a changé.
        //TODO: Ajoutez une validation indiquant que tous les attributs sont transmis, sinon renvoyez une charge utile incorrecte de 400
        Speaker existingSpeaker = speakerRepository.getOne(id);
        BeanUtils.copyProperties(speaker, existingSpeaker, "speaker_id");
        return speakerRepository.saveAndFlush(existingSpeaker);
    }
}
