package co.caffeinecoders.cricketcritics.controllers;

import co.caffeinecoders.cricketcritics.entities.Community;
import co.caffeinecoders.cricketcritics.entities.Post;
import co.caffeinecoders.cricketcritics.services.PostService;
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
    public ResponseEntity<Post> createPost(@RequestBody  Post post){
        return ResponseEntity.ok(postService.createPost(post));

    }
    @GetMapping("/findAll")
    public ResponseEntity<List<Post>> findAll(){
        List<Post> postList = postService.findAllActivePost();
        return ResponseEntity.ok(postList);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Post> findPost(@PathVariable Long id){
       Optional<Post> post = postService.findPostById(id);
       if (post.isPresent()){
           return ResponseEntity.ok(post.get());
       }
       return ResponseEntity.notFound().build();
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post){
        Optional<Post> updatedPost = postService.updatePost(post,id);
        if (updatedPost.isPresent()){
            return ResponseEntity.ok(updatedPost.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/delete")
    public ResponseEntity<Post> deactivatePostById(@PathVariable Long id){
        Optional<Post> deactivateByPost = postService.deactivatePost(id);
        if (deactivateByPost.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deactivateByPost.get());
    }
}
