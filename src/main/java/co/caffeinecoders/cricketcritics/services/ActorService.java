package co.caffeinecoders.cricketcritics.services;

import co.caffeinecoders.cricketcritics.entities.Actor;
import co.caffeinecoders.cricketcritics.repositories.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorService {
    @Autowired
    private ActorRepository actorRepository;

    public Actor postActor(Actor actorToAdd){
        return  actorRepository.saveAndFlush(actorToAdd);
    }
    public List<Actor> getAllActor(){
         return actorRepository.findAll();
    }
    public Optional<Actor> getActor(Long actorId){
        return actorRepository.findById(actorId);
    }
    public Optional<Actor> updateActor(Long id,Actor actorUpdate){
        Optional<Actor> actorOptional = actorRepository.findById(id);
        if(actorOptional.isPresent()) {
            actorOptional.get().setName(actorUpdate.getName());
            actorOptional.get().setLastName(actorUpdate.getLastName());
            actorOptional.get().setAge(actorUpdate.getAge());
            actorOptional.get().setMovies(actorUpdate.getMovies());
            actorOptional.get().setNationality(actorUpdate.getNationality());
            actorOptional.get().setDataOfBirth(actorUpdate.getDataOfBirth());
            actorOptional.get().setPlaceOFBirth(actorUpdate.getPlaceOFBirth());
            actorRepository.save(actorOptional.get());
        }
        return actorOptional;
    }
    public Optional<Actor>deleteActor(Long id){
        Optional<Actor> actorOptional = actorRepository.findById(id);
        if(actorOptional.isPresent()){
            actorRepository.delete(actorOptional.get());
        }
        return actorOptional;
    }


}
