package co.caffeinecoders.cricketcritics.controllers;
import co.caffeinecoders.cricketcritics.entities.Actor;
import co.caffeinecoders.cricketcritics.services.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/actor")
public class ActorController {
    @Autowired
    private ActorService actorService;

    @PostMapping("/create")
    public ResponseEntity<Actor> createActor(@RequestBody Actor actorToAdd) {
        Actor actorCreated = actorService.addActor(actorToAdd);
        return ResponseEntity.ok(actorCreated);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Actor>> findAllActiveActors() {
        List<Actor> actors = actorService.getAllActiveActors();
        return ResponseEntity.ok(actorService.getAllActiveActors());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Actor> findById(Long id) {
        Optional<Actor> actorOptional = actorService.findActorById(id);
        if (actorOptional.isPresent()) {
            return ResponseEntity.ok(actorOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Actor> updateActor(@PathVariable Long id, @RequestBody Actor actor) {
        Optional<Actor> actorOptional = actorService.updateActor(id, actor);
        if (actorOptional.isPresent()) {
            return ResponseEntity.ok().body(actorOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<Actor> deactivateReviewById(@PathVariable Long id){
        Optional<Actor> deactivatedActor = actorService.deactivateAcotrById(id);
        if (deactivatedActor.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deactivatedActor.get());
    }


}
