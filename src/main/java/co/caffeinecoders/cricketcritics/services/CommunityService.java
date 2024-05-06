package co.caffeinecoders.cricketcritics.services;

import co.caffeinecoders.cricketcritics.entities.Community;
import co.caffeinecoders.cricketcritics.entities.DTO.PersonalizedResponse;
import co.caffeinecoders.cricketcritics.entities.Director;
import co.caffeinecoders.cricketcritics.entities.User;
import co.caffeinecoders.cricketcritics.enums.RecordStatusEnum;
import co.caffeinecoders.cricketcritics.enums.UserEnum;
import co.caffeinecoders.cricketcritics.repositories.CommunityRepository;
import co.caffeinecoders.cricketcritics.repositories.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommunityService {

    @Autowired
    private CommunityRepository communityRepository;
    @Autowired
    private UserRepository userRepository;


    public PersonalizedResponse createCommunity(Community community){
        PersonalizedResponse response = new PersonalizedResponse(HttpServletResponse.SC_BAD_REQUEST, "Community NOT created, given USER doesn't Exists", Optional.empty());

        //recupero lo user con le sue reali proprietà tramite l'id arrivato dal JSON
        Optional<User> communityCreator = userRepository.findById(community.getUser().getId());

        if (communityCreator.isPresent()){

            //controllo i permessi sui dati effettivi dello user presente nel db
            if (checkUserPermissions(communityCreator.get())){
                response.setStatus(HttpServletResponse.SC_OK);
                response.setMessage("Community created successfully!");
                response.setEntity(communityRepository.save(community));
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setMessage("Community NOT created, A BASICUSER can't create a community");
            }
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
