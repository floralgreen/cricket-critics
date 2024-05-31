package co.caffeinecoders.cricketcritics.services;

import co.caffeinecoders.cricketcritics.entities.Actor;

import co.caffeinecoders.cricketcritics.enums.RecordStatusEnum;
import co.caffeinecoders.cricketcritics.repositories.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorService {
    @Autowired
    private ActorRepository actorRepository;


    /**
     *
     * @param actor given an Actor object as Param
     * @return the saved Object in the Database
     */
    public Actor addActor(Actor actor) {
        Actor savedActor = actorRepository.saveAndFlush(actor);
        return actorRepository.save(savedActor);
    }


    /**
     *
     * @return the full list of active Actors in the Db
     * or an empty list if none is active
     */
    public List<Actor> getAllActiveActors() {
        return actorRepository.findAllActiveActor();


    }

    /**
     *
     * @param id given an actorId
     * @return Optional with the Actor Object found
     * or an empty Optional if the actor is not found or not active
     */
    public Optional<Actor> findActorById(Long id) {
        Optional<Actor> actorOptional = actorRepository.findActiveActorById(id);
        return actorOptional;
    }

    /**
     *
     * @param id given the actorId that has to be updated
     * @param actor the updated Actor Object
     * @return the updated Actor saved on the DB
     *  or empty Optional if the Id is not found
     */
    public Optional<Actor> updateActor(Long id, Actor actor) {
        Optional<Actor> actorOptional = actorRepository.findActiveActorById(id);
        if (actorOptional.isPresent()) {
            actorOptional.get().setName(actor.getName());
            actorOptional.get().setLastName(actor.getLastName());
            actorOptional.get().setAge(actor.getAge());
            actorOptional.get().setMovies(actor.getMovies());
            actorOptional.get().setNationality(actor.getNationality());
            actorOptional.get().setDateOfBirth(actor.getDateOfBirth());
            actorOptional.get().setPlaceOFBirth(actor.getPlaceOFBirth());
            Actor actorUpdated = actorRepository.save(actorOptional.get());
            return Optional.of(actorUpdated);
        } else {
            return Optional.empty();
        }
    }


    /**
     *
     * @param id given an actorId to deactivate
     * @return the deactivated actor or
     * empty Optional if the Id is not found
     */
    public Optional<Actor> deactivateActorById(Long id) {
        Optional<Actor> actorToDeactivate = actorRepository.findById(id);
        if (actorToDeactivate.isPresent()) {
            actorToDeactivate.get().setRecordStatusEnum(RecordStatusEnum.D);
            actorRepository.save(actorToDeactivate.get());
        }
        return actorToDeactivate;
    }
}



