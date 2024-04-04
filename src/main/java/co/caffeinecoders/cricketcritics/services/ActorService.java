package co.caffeinecoders.cricketcritics.services;

import co.caffeinecoders.cricketcritics.entities.Actor;

import co.caffeinecoders.cricketcritics.enums.RecordStatusEnum;
import co.caffeinecoders.cricketcritics.repositories.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ActorService {
    @Autowired
    private ActorRepository actorRepository;

    public Actor addActor(Actor actor) {
        Actor savedActor = actorRepository.saveAndFlush(actor);
        return actorRepository.save(savedActor);
    }


    public List<Actor> getAllActiveActors() {
        return actorRepository.findAllActiveActor();


    }

    public Optional<Actor> findActorById(Long id) {
        Optional<Actor> actorOptional = actorRepository.findActiveActorById(id);
        return actorOptional;


    }

    public Optional<Actor> updateActor(Long id, Actor actor) {
        Optional<Actor> actorOptional = actorRepository.findActiveActorById(id);
        if (actorOptional.isPresent()) {
            actorOptional.get().setName(actor.getName());
            actorOptional.get().setLastName(actor.getLastName());
            actorOptional.get().setAge(actor.getAge());
            actorOptional.get().setMovies(actor.getMovies());
            actorOptional.get().setNationality(actor.getNationality());
            actorOptional.get().setDataOfBirth(actor.getDataOfBirth());
            actorOptional.get().setPlaceOFBirth(actor.getPlaceOFBirth());
            Actor actorUpdated = actorRepository.save(actorOptional.get());
            return Optional.of(actorUpdated);
        } else {
            return Optional.empty();
        }
    }

    public Optional<Actor> deactivateAcotrById(Long id) {
        Optional<Actor> actorToDeactivate = actorRepository.findById(id);
        if (actorToDeactivate.isPresent()) {
            actorToDeactivate.get().setRecordStatusEnum(RecordStatusEnum.D);
            actorRepository.save(actorToDeactivate.get());
        }
        return actorToDeactivate;
    }
}



