package co.caffeinecoders.cricketcritics.controllers;
import co.caffeinecoders.cricketcritics.entities.Actor;
import co.caffeinecoders.cricketcritics.services.ActorService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "This API creates an Actor on the DB based And returns the object created for confirmation")
    public ResponseEntity<Actor> createActor(@RequestBody Actor actorToAdd) {
        Actor actorCreated = actorService.addActor(actorToAdd);
        return ResponseEntity.ok(actorCreated);
    }

    @GetMapping("/all")
    @Operation(summary = "This API retrives a List with all the Actors objects setted as 'Active' in the DB, it returns an empty List if none of it is Active")
    public ResponseEntity<List<Actor>> findAllActiveActors() {
        List<Actor> actors = actorService.getAllActiveActors();
        return ResponseEntity.ok(actorService.getAllActiveActors());
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "This API retrives the Actor object by the given ID, it returns notFound if the resource is not present in the DB")
    public ResponseEntity<Actor> findById(Long id) {
        Optional<Actor> actorOptional = actorService.findActorById(id);
        if (actorOptional.isPresent()) {
            return ResponseEntity.ok(actorOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/edit/{id}")
    @Operation(summary = "This API updates the Actor matching by the given ID with the new Review given by RequestBody, only updatable fields are Description and Score, it returns notFound if the resource is not present or set as status 'Deactivated'")
    public ResponseEntity<Actor> updateActor(@PathVariable Long id, @RequestBody Actor actor) {
        Optional<Actor> actorOptional = actorService.updateActor(id, actor);
        if (actorOptional.isPresent()) {
            return ResponseEntity.ok().body(actorOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/delete/{id}")
    @Operation(summary = "This API deactivates the Actor by the given ID, and set the status as ìDeactivated', it returns the deactivated object or notFound if the resource is Absent or already deactivated")
    public ResponseEntity<Actor> deactivateReviewById(@PathVariable Long id){
        Optional<Actor> deactivatedActor = actorService.deactivateAcotrById(id);
        if (deactivatedActor.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deactivatedActor.get());
    }


}
