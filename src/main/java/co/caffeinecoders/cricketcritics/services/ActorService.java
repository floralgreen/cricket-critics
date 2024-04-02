package co.caffeinecoders.cricketcritics.services;

import co.caffeinecoders.cricketcritics.entities.Actor;
import co.caffeinecoders.cricketcritics.entities.Movie;
import co.caffeinecoders.cricketcritics.enums.RecordStatusEnum;
import co.caffeinecoders.cricketcritics.repositories.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ActorService {
    @Autowired
    private ActorRepository actorRepository;

    public Actor createActor(Actor actorToAdd) {
        return (actorRepository.saveAndFlush(actorToAdd));
    }


    public List<Actor> getAllActiveActors() {
        List<Actor> actors = actorRepository.findAll();
        List<Actor> actorActive = new ArrayList<Actor>();
        for (Actor actor: actors) {
            if (actor.getRecordStatusEnum().equals(RecordStatusEnum.A)) {
                actorActive.add(actor);
            }
        }
        return actorActive;
    }

    public Optional<Actor> findActorById(Long id) {
        Optional<Actor> actorOptional = actorRepository.findById(id);
        if (actorOptional.isPresent()) {
            if (actorOptional.get().getRecordStatusEnum().equals(RecordStatusEnum.A)) {
                return actorOptional;
            }

        }
        return Optional.empty();
    }

    public Optional<Actor> updateActor(Long id, Actor actorUpdate) {
        Optional<Actor> actorOptional = actorRepository.findById(id);
        if (actorOptional.isPresent()) {
            if (actorOptional.get().getRecordStatusEnum().equals(RecordStatusEnum.A)) {
                actorOptional.get().setName(actorUpdate.getName());
                actorOptional.get().setLastName(actorUpdate.getLastName());
                actorOptional.get().setAge(actorUpdate.getAge());
                actorOptional.get().setMovies(actorUpdate.getMovies());
                actorOptional.get().setNationality(actorUpdate.getNationality());
                actorOptional.get().setDataOfBirth(actorUpdate.getDataOfBirth());
                actorOptional.get().setPlaceOFBirth(actorUpdate.getPlaceOFBirth());
                actorRepository.save(actorOptional.get());
                return actorOptional;
            }
        }
        return Optional.empty();
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



