package co.caffeinecoders.cricketcritics.controllers;

import co.caffeinecoders.cricketcritics.entities.Community;

import co.caffeinecoders.cricketcritics.entities.Review;
import co.caffeinecoders.cricketcritics.services.CommunityService;
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
    public ResponseEntity<Community> createCommunity(@RequestBody Community community){
        return ResponseEntity.ok().body(community);
    }
    @GetMapping("/findAll")
    public ResponseEntity<List<Community>> findAll(){
       List<Community> community = communityService.findAllCommunity();
       return ResponseEntity.ok().body(community);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Community> findCommunity(@PathVariable Long id){
         Optional<Community> community = communityService.findById(id);
        if(community.isPresent()){
            return ResponseEntity.ok().body(community.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Community> updateCommunity(@PathVariable Long id, @RequestBody Community community){
        Optional<Community> communityOptional = communityService.updateCommunity(id, community);
        if(communityOptional.isPresent()){
            return ResponseEntity.ok().body(communityOptional.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/delete")
    public ResponseEntity<Community> deactivateCommunityById(@PathVariable Long id){
        Optional<Community> deactivatedReview = communityService.deactivateCommunity(id);
        if (deactivatedReview.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deactivatedReview.get());
    }
}
