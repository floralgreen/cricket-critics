package co.caffeinecoders.cricketcritics.services;

import co.caffeinecoders.cricketcritics.entities.Community;
import co.caffeinecoders.cricketcritics.entities.DTO.PersonalizedResponse;
import co.caffeinecoders.cricketcritics.entities.Director;
import co.caffeinecoders.cricketcritics.entities.User;
import co.caffeinecoders.cricketcritics.enums.RecordStatusEnum;
import co.caffeinecoders.cricketcritics.enums.UserEnum;
import co.caffeinecoders.cricketcritics.repositories.CommunityRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommunityService {

    @Autowired
    private CommunityRepository communityRepository;
    public PersonalizedResponse createCommunity(Community community){
        PersonalizedResponse response = new PersonalizedResponse(HttpServletResponse.SC_FORBIDDEN, "Community NOT created, A BASICUSER can't create a community", Optional.empty());
        if (checkUserPermissions(community.getUser())){
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage("Community created successfully!");
            response.setEntity(communityRepository.save(community));
        }

        return response;
    }
    public List<Community> findAllCommunity(){
        return communityRepository.findAllActiveCommunity();
    }
    public Optional<Community> findById(Long id){
        Optional<Community> community = communityRepository.findActiveCommunityById(id);
        if (community.isPresent()) {
            return community;
        }
        return Optional.empty();
    }
    public List<Community> findAllByName(String name){
        return communityRepository.findAllActiveCommunityByName(name);
    }

    public List<Community> findUserCommunities(Long userId){
        return communityRepository.findCommunitiesByUser(userId);
    }

    public List<Community> findMovieCommunities(Long movieId){
        return communityRepository.findCommunitiesByMovie(movieId);
    }

    public Optional<Community> updateCommunity(Long id,Community community){
        Optional<Community> communityOptional = communityRepository.findActiveCommunityById(id);
    if (communityOptional.isPresent()){
        communityOptional.get().setName(community.getName());
        communityRepository.save(communityOptional.get());
        return communityOptional;
    }
    return Optional.empty();

    }
    public Optional<Community> deactivateCommunity(Long id){
        Optional<Community> communityOptional = communityRepository.findActiveCommunityById(id);
        if (communityOptional.isPresent()){
            communityOptional.get().setRecordStatusEnum(RecordStatusEnum.D);
            communityRepository.save(communityOptional.get());
        }
        return communityOptional;
    }

    /**
     *
     *
     * @param user
     * @return true if the User is not a basic user
     */
    private boolean checkUserPermissions(User user){
        return !user.getUserEnum().equals(UserEnum.BASICUSER);
    }

}
