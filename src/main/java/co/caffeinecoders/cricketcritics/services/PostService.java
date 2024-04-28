package co.caffeinecoders.cricketcritics.services;

import co.caffeinecoders.cricketcritics.entities.Director;
import co.caffeinecoders.cricketcritics.entities.Post;
import co.caffeinecoders.cricketcritics.entities.Review;
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

    public List<Post> findAllPostsByUserId(Long userId) {
        List<Post> postList = postRepository.findAllPostsByUserId(userId);
        return postList;
    }

    /**
     *
     * @param communityId given a community Id
     * @return a list with all the posts of a specific community
     */
    public List<Post> findAllPostsByCommunityId(Long communityId) {
        List<Post> postsList = postRepository.findAllPostsByCommunityId(communityId);
        return postsList;
    }


}
