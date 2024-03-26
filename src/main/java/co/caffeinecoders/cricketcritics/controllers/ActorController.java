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
        Actor actorCreated = actorService.createActor(actorToAdd);
        return ResponseEntity.ok(actorCreated);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Actor>> findAllActor() {
        return ResponseEntity.ok(actorService.getAllActor());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Actor> findById(Long id) {
        Optional<Actor> actorOptional = actorService.getActor(id);
        if (actorOptional.isPresent()) {
            return ResponseEntity.ok(actorOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Actor> updateActor(@PathVariable Long id, @RequestBody Actor actor) {
        Optional<Actor> actorOptional = actorService.updateActor(id, actor);
        if (actorOptional.isPresent()) {
            return ResponseEntity.ok().body(actorOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Actor> removeActor(Long id) {
        Optional<Actor> actorOptional = actorService.deleteActor(id);
        if (actorOptional.isPresent()) {
            return ResponseEntity.ok().body(actorOptional.get());
        }
        return ResponseEntity.notFound().build();
    }
}
