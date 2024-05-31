package co.caffeinecoders.cricketcritics.services;

import co.caffeinecoders.cricketcritics.entities.Community;
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

    /**
     *
     * @param post given an Post object as Param
     * @return the saved Object in the Database
     */
    public Post createPost(Post post){
        return postRepository.save(post);
    }

    /**
     *
     * @return the full list of active Posts in the Db
     * or an empty list if none is active
     */
    public List<Post> findAllActivePost(){
        return postRepository.findAllActivePost();
    }

    /**
     *
     * @param id given a postId
     * @return Optional with the Post Object found
     * or an empty Optional if the post is not found or not active
     */
    public Optional<Post> findPostById(Long id){
        Optional<Post> postOptional = postRepository.findActivePostById(id);
        return postOptional;
    }

    /**
     *
     * @param keyWord given a keyword to search
     * @return a List of posts that has that specific keyword in their text field
     */
    public List<Post> findByKeyWord(String keyWord) {
        List<Post> postOptional = postRepository.findPostByKeyword(keyWord);
            return postOptional;
    }

    /**
     *
     * @param id given the postId that has to be updated
     * @param post updated Post Object
     * @return the updated Post saved on the DB
     *  or empty Optional if the Id is not found
     */
    public Optional<Post> updatePost(Post post,Long id){
        Optional<Post> postOptional = postRepository.findActivePostById(id);
        if(postOptional.isPresent()){
            postOptional.get().setText(post.getText());
            postRepository.save(postOptional.get());
        }
        return postOptional;
    }

    /**
     *
     * @param id given an postId to deactivate
     * @return the deactivated post or
     * empty Optional if the Id is not found
     */
    public Optional<Post> deactivatePost(Long id){
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isPresent()){
            postOptional.get().setRecordStatusEnum(RecordStatusEnum.D);
            postRepository.save(postOptional.get());
        }
        return postOptional;
    }

    /**
     *
     * @param userId given the id of a user
     * @return a List with all the posts made by that user
     */
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
