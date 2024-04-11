package co.caffeinecoders.cricketcritics.controllers;

import co.caffeinecoders.cricketcritics.entities.DTO.PersonalizedResponse;
import co.caffeinecoders.cricketcritics.entities.User;
import co.caffeinecoders.cricketcritics.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
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

    //POST
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user){
        return ResponseEntity.ok().body(service.addUser(user));
    }


    //GET
    @GetMapping("/all")
    public ResponseEntity<List<User>> findAllUsers(){
        return ResponseEntity.ok().body(service.getAllActiveUsers());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        Optional<User> userOptional = service.getUserFromId(id);
        if(userOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(userOptional.get());
    }

    @GetMapping("/find")
    public ResponseEntity<User> findByUsername(@RequestParam String username){
        Optional<User> userOptional = service.getUserFromUsername(username);
        if (userOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userOptional.get());
    }

    //PUT
    @PutMapping("/edit/{id}")
    public ResponseEntity<User> editUser(@PathVariable Long id , @RequestBody User user){
        Optional<User> userOptional = service.updateUser(id,user);
        if(userOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(userOptional.get());
    }

    @PutMapping("/upgrade/{id}")
    @Operation(summary = "This API upgrades the User corresponding to the given ID, and returns a Response Message if the Upgraded has been successful, not successful because of the requierments not satisfied or not found if the User is not Found or deleted")
    public ResponseEntity<PersonalizedResponse> upgradeUser(@PathVariable Long id){
        PersonalizedResponse personalizedResponse = service.upgradeUser(id);
        return ResponseEntity.status(personalizedResponse.getStatus()).body(personalizedResponse);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<User> deactivateUserById(@PathVariable Long id){
        Optional<User> deactivatedUser = service.deactivateUserById(id);
        if (deactivatedUser.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deactivatedUser.get());
    }

}

