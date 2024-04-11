package co.caffeinecoders.cricketcritics.controllers;

import co.caffeinecoders.cricketcritics.entities.Director;
import co.caffeinecoders.cricketcritics.services.DirectorService;
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
    public ResponseEntity<Director> addDirector(@RequestBody Director director){
        return ResponseEntity.ok().body(service.addDirector(director));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Director> findDirector(@PathVariable Long id){
        Optional<Director> directorOptional = service.getActiveDirector(id);
        if (directorOptional.isPresent()){
                return ResponseEntity.ok().body(directorOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Director>> findAllDirector(){
        return ResponseEntity.ok().body(service.getAllActiveDirector());
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Director> updateDirector(@PathVariable Long id, @RequestBody Director director){
        Optional<Director> directorOptional = service.updateDirector(director, id);
        if (directorOptional.isPresent()){
            return ResponseEntity.ok().body(directorOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<Director> deactivateDirector(@PathVariable Long id){
        Optional<Director> directorOptional = service.deactivateDirector(id);
        if (directorOptional.isPresent()){
            return ResponseEntity.ok(directorOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

}
