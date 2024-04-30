package co.caffeinecoders.cricketcritics.controllers;

import co.caffeinecoders.cricketcritics.entities.Community;

import co.caffeinecoders.cricketcritics.entities.Review;
import co.caffeinecoders.cricketcritics.services.CommunityService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/community")
public class CommunityController {
    @Autowired
    private CommunityService communityService;

    @PostMapping("/create")
    @Operation(summary = "This API creates a Community on the DB based And returns the object created for confirmation")
    public ResponseEntity<Community> createCommunity(@RequestBody Community community){
         Community community1 = communityService.createCommunity(community);
        return ResponseEntity.ok().body(community1);
    }
    @GetMapping("/findAll")
    @Operation(summary = "This API retrives a List with all the Community objects setted as 'Active' in the DB, it returns an empty List if none of it is Active")
    public ResponseEntity<List<Community>> findAll(){
       List<Community> community = communityService.findAllCommunity();
       return ResponseEntity.ok().body(community);
    }
    @GetMapping("/find/{id}")
    @Operation(summary = "This API retrives the Community object by the given ID, it returns notFound if the resource is not present in the DB")
    public ResponseEntity<Community> findCommunity(@PathVariable Long id){
         Optional<Community> community = communityService.findById(id);
        if(community.isPresent()){
            return ResponseEntity.ok().body(community.get());
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/find/{name}")
    @Operation(summary = "This API retrieves a List with all the community setted by name")
    public ResponseEntity<Community> findCommunity(@PathVariable String name){
        Optional<Community> community = communityService.findByName(name);
        if(community.isPresent()){
            return ResponseEntity.ok().body(community.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/update/{id}")
    @Operation(summary = "This API updates the Post matching by the given ID with the new community given by RequestBody, only updatable fields are Description and Score, it returns notFound if the resource is not present or set as status 'Deactivated'")
    public ResponseEntity<Community> updateCommunity(@PathVariable Long id, @RequestBody Community community){
        Optional<Community> communityOptional = communityService.updateCommunity(id, community);
        if(communityOptional.isPresent()){
            return ResponseEntity.ok().body(communityOptional.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/delete")
    @Operation(summary = "This API deactivates the Community by the given ID, and set the status as ìDeactivated', it returns the deactivated object or notFound if the resource is Absent or already deactivated")
    public ResponseEntity<Community> deactivateCommunityById(@PathVariable Long id){
        Optional<Community> deactivatedReview = communityService.deactivateCommunity(id);
        if (deactivatedReview.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deactivatedReview.get());
    }
}
