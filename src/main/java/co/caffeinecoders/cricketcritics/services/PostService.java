package co.caffeinecoders.cricketcritics.services;

import co.caffeinecoders.cricketcritics.entities.Director;
import co.caffeinecoders.cricketcritics.entities.Post;
import co.caffeinecoders.cricketcritics.enums.RecordStatusEnum;
import co.caffeinecoders.cricketcritics.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Post createPost(Post post){
        return postRepository.save(post);
    }
    public List<Post> findAllActivePost(){
        return postRepository.findAllActivePost();
    }
    public Optional<Post> findPostById(Long id){
        Optional<Post> postOptional = postRepository.findActivePostById(id);
        return postOptional;
    }
    public Optional<Post> updatePost(Post post,Long id){
        Optional<Post> postOptional = postRepository.findActivePostById(id);
        if(postOptional.isPresent()){
            postOptional.get().setText(post.getText());
            postOptional.get().setSentDate(post.getSentDate());
            postOptional.get().setCommunity(post.getCommunity());
            postOptional.get().setUser(post.getUser());
            postRepository.save(postOptional.get());
        }
        return postOptional;
    }
    public Optional<Post> deactivatePost(Long id){
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isPresent()){
            postOptional.get().setRecordStatusEnum(RecordStatusEnum.D);
            postRepository.save(postOptional.get());
        }
        return postOptional;
    }
}
