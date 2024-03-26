package co.caffeinecoders.cricketcritics.Controller;

import co.caffeinecoders.cricketcritics.entities.Director;
import co.caffeinecoders.cricketcritics.services.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/director")
public class DirectorController {
    @Autowired
    private DirectorService service;

    @PostMapping("/create")
    public ResponseEntity<Director> addDirector(@RequestBody Director director){
        return ResponseEntity.ok().body(service.addDirector(director));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Director> findDirector(@PathVariable Long id){
        Optional<Director> directorOptional = service.getDirector(id);
        if (directorOptional.isPresent()){
            return ResponseEntity.ok().body(directorOptional.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Director> updateDirector(@PathVariable Long id, @RequestBody Director director){
        Optional<Director> directorOptional = service.updateDirector(director, id);
        if (directorOptional.isPresent()){
            return ResponseEntity.ok().body(directorOptional.get());
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Director> removeDirector(@PathVariable Long id){
        Optional<Director> directorOptional = service.deleteDirector(id);
        if (directorOptional.isPresent()){
            return ResponseEntity.ok().body(directorOptional.get());
        }
        return ResponseEntity.notFound().build();
    }
}
