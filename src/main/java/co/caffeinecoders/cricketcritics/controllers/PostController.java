package co.caffeinecoders.cricketcritics.controllers;

import co.caffeinecoders.cricketcritics.entities.Community;
import co.caffeinecoders.cricketcritics.entities.Post;
import co.caffeinecoders.cricketcritics.entities.Review;
import co.caffeinecoders.cricketcritics.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/create")
    @Operation(summary = "This API creates a Post on the DB based And returns the object created for confirmation")
    public ResponseEntity<Post> createPost(@RequestBody  Post post){
        return ResponseEntity.ok(postService.createPost(post));

    }
    @GetMapping("/findAll")
    @Operation(summary = "This API retrives a List with all the Post objects setted as 'Active' in the DB, it returns an empty List if none of it is Active")
    public ResponseEntity<List<Post>> findAll(){
        List<Post> postList = postService.findAllActivePost();
        return ResponseEntity.ok(postList);
    }
    @GetMapping("/find/{id}")
    @Operation(summary = "This API retrives the Post object by the given ID, it returns notFound if the resource is not present in the DB")
    public ResponseEntity<Post> findByIdPost(@PathVariable Long id){
       Optional<Post> post = postService.findPostById(id);
       if (post.isPresent()){
           return ResponseEntity.ok(post.get());
       }
       return ResponseEntity.notFound().build();
    }
    @GetMapping("/findByKeyWord/{keyword}")
    @Operation(summary = "This API retrieves a Post by written keyword")
    public ResponseEntity<List<Post>> findBYKeywordPost(@PathVariable String keyword){
        List<Post> postOptional = postService.findByKeyWord(keyword);
        return ResponseEntity.ok().body(postOptional);
    }

    @GetMapping("/userPosts/{userId}")
    @Operation(summary = "This API retrieves all the posts written by a User by giving the User ID")
    public ResponseEntity<List<Post>> findAllPostsByUserId(@PathVariable Long userId){
        List<Post> postList = postService.findAllPostsByUserId(userId);
        return ResponseEntity.ok(postList);
    }


    @GetMapping("/communityPosts/{communityId}")
    @Operation(summary = "This API retrieves all the posts written in a Community by giving the Community ID")
    public ResponseEntity<List<Post>> findAllPostsByCommunityId(@PathVariable Long communityId){
        List<Post> postList = postService.findAllPostsByCommunityId(communityId);
        return ResponseEntity.ok(postList);
    }


    @PutMapping("/update/{id}")
    @Operation(summary = "This API updates the Post matching by the given ID with the new Community given by RequestBody, only updatable fields are Description and Score, it returns notFound if the resource is not present or set as status 'Deactivated'")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post){
        Optional<Post> updatedPost = postService.updatePost(post,id);
        if (updatedPost.isPresent()){
            return ResponseEntity.ok(updatedPost.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/delete/{id}")
    @Operation(summary = "This API deactivates the Post by the given ID, and set the status as ìDeactivated', it returns the deactivated object or notFound if the resource is Absent or already deactivated")
    public ResponseEntity<Post> deactivatePostById(@PathVariable Long id){
        Optional<Post> deactivateByPost = postService.deactivatePost(id);
        if (deactivateByPost.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deactivateByPost.get());
    }


}
