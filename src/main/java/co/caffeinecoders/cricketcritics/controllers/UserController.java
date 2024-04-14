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
    @Operation(summary = "This API creates a User on the DB based And returns the object created for confirmation")
    public ResponseEntity<User> createUser(@RequestBody User user){
        return ResponseEntity.ok().body(service.addUser(user));
    }


    //GET
    @GetMapping("/all")
    @Operation(summary = "This API retrives a List with all the Users objects setted as 'Active' in the DB, it returns an empty List if none of it is Active")
    public ResponseEntity<List<User>> findAllUsers(){
        return ResponseEntity.ok().body(service.getAllActiveUsers());
    }


    @GetMapping("/find/{id}")
    @Operation(summary = "This API retrives the User object by the given ID, it returns notFound if the resource is not present in the DB")
    public ResponseEntity<User> findById(@PathVariable Long id){
        Optional<User> userOptional = service.getUserFromId(id);
        if(userOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(userOptional.get());
    }

    @GetMapping("/find")
    @Operation(summary = "This API retrives the User object by the given Username, it returns notFound if the resource is not present in the DB")
    public ResponseEntity<User> findByUsername(@RequestParam String username){
        Optional<User> userOptional = service.getUserFromUsername(username);
        if (userOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userOptional.get());
    }
    @GetMapping("/findByReviewsNumber")
    @Operation(summary = "This API retrives the User object by the given ReviewsNumber, it returns notFound if the resource is not present in the DB")
    public ResponseEntity<List<User>> findUserByReviews(@RequestParam Integer reviewNumber) {
        List<User> userList = service.findUserByReview(reviewNumber);
        return ResponseEntity.ok(userList);
    }

    //PUT
    @PutMapping("/edit/{id}")
    @Operation(summary = "This API updates the User matching by the given ID with the new Review given by RequestBody, only updatable fields are Description and Score, it returns notFound if the resource is not present or set as status 'Deactivated'")
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
    @Operation(summary = "This API deactivates the User by the given ID, and set the status as ìDeactivated', it returns the deactivated object or notFound if the resource is Absent or already deactivated")
    public ResponseEntity<User> deactivateUserById(@PathVariable Long id){
        Optional<User> deactivatedUser = service.deactivateUserById(id);
        if (deactivatedUser.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deactivatedUser.get());
    }





}

