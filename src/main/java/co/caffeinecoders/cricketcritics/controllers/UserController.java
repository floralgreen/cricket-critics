package co.caffeinecoders.cricketcritics.controllers;

import co.caffeinecoders.cricketcritics.entities.User;
import co.caffeinecoders.cricketcritics.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;
    @PostMapping("/create")
    public ResponseEntity<User> addUser(@RequestBody User user){
        return ResponseEntity.ok().body(service.addUser(user));
    }
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok().body(service.getAll());
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<User> getFromId(@PathVariable Long id){
        Optional<User> userOptional = service.getUserFromId(id);
        if(userOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(userOptional.get());
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<User> editUser(@PathVariable Long id , @RequestBody User user){
        Optional<User> userOptional = service.updateUser(id,user);
        if(userOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(userOptional.get());
    }
    @PutMapping("/deactivate/{id}")
    public ResponseEntity<User> deactivateUserById(@PathVariable Long id){
        Optional<User> deactivatedUser = service.deactivateUserById(id);
        if (deactivatedUser.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deactivatedUser.get());
    }

}

