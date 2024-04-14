package co.caffeinecoders.cricketcritics.controllers;

import co.caffeinecoders.cricketcritics.entities.Director;
import co.caffeinecoders.cricketcritics.services.DirectorService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/director")
public class DirectorController {

    @Autowired
    private DirectorService service;



    @PostMapping("/create")
    @Operation(summary = "This API creates a Director on the DB based And returns the object created for confirmation")
    public ResponseEntity<Director> addDirector(@RequestBody Director director){
        return ResponseEntity.ok().body(service.addDirector(director));
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "This API retrives the Director object by the given ID, it returns notFound if the resource is not present in the DB")
    public ResponseEntity<Director> findDirector(@PathVariable Long id){
        Optional<Director> directorOptional = service.getActiveDirector(id);
        if (directorOptional.isPresent()){
                return ResponseEntity.ok().body(directorOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findall")
    @Operation(summary = "This API retrives a List with all the Directors objects setted as 'Active' in the DB, it returns an empty List if none of it is Active")
    public ResponseEntity<List<Director>> findAllDirector(){
        return ResponseEntity.ok().body(service.getAllActiveDirector());
    }

    @PutMapping("/edit/{id}")
    @Operation(summary = "This API updates the Director matching by the given ID with the new Review given by RequestBody, only updatable fields are Description and Score, it returns notFound if the resource is not present or set as status 'Deactivated'")
    public ResponseEntity<Director> updateDirector(@PathVariable Long id, @RequestBody Director director){
        Optional<Director> directorOptional = service.updateDirector(director, id);
        if (directorOptional.isPresent()){
            return ResponseEntity.ok().body(directorOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/delete/{id}")
    @Operation(summary = "This API deactivates the Director by the given ID, and set the status as ìDeactivated', it returns the deactivated object or notFound if the resource is Absent or already deactivated")
    public ResponseEntity<Director> deactivateDirector(@PathVariable Long id){
        Optional<Director> directorOptional = service.deactivateDirector(id);
        if (directorOptional.isPresent()){
            return ResponseEntity.ok(directorOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

}
