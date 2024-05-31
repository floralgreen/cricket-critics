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


    /**
     *
     * @param community given a Community Object
     * @return a PersonalizedResponse with 3 possible cases
     * 1: status 200 / Community Created / and the Community Object saved in the DB
     * 2: status 400 / Community NOT created because User doesn't Exists / an empty Optional
     * 3: status FORBIDDEN / Community Not CREATED, a BASICUSER can't create a community / an empty Optional
     */
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

    /**
     *
     * @return a list with all communities in the DB, empty list if none is active or present in the DB
     */
    public List<Community> findAllCommunity(){
        return communityRepository.findAllActiveCommunity();
    }

    /**
     *
     * @param id a communityId
     * @return the community Object
     * an empty Optional if the object is not found in the DB
     */
    public Optional<Community> findById(Long id){
        Optional<Community> community = communityRepository.findActiveCommunityById(id);
        if (community.isPresent()) {
            return community;
        }
        return Optional.empty();
    }

    /**
     *
     * @param name given the name of a Community
     * @return a List of all the communities with that name
     */
    public List<Community> findAllByName(String name){
        return communityRepository.findAllActiveCommunityByName(name);
    }

    /**
     *
     * @param userId given a userId
     * @return the list of all communities created by the specific user
     * none if the user doesn't exist or he hasn't created none
     */
    public List<Community> findUserCommunities(Long userId){
        return communityRepository.findCommunitiesByUser(userId);
    }

    /**
     *
     * @param movieId given a movieId
     * @return the list of all communities related to that specific movie
     * none if the movieId doesn't exists
     * or if there are no communities related to that movie
     */
    public List<Community> findMovieCommunities(Long movieId){
        return communityRepository.findCommunitiesByMovie(movieId);
    }

    /**
     *
     * @param id given a community iD
     * @param community and athe community object updated
     * @return the updated object saved in the DB
     * or an empty Optional if the ID is not found
     */
    public Optional<Community> updateCommunity(Long id,Community community){
        Optional<Community> communityOptional = communityRepository.findActiveCommunityById(id);
    if (communityOptional.isPresent()){
        communityOptional.get().setName(community.getName());
        communityRepository.save(communityOptional.get());
        return communityOptional;
    }
    return Optional.empty();

    }

    /**
     *
     * @param id given a Community ID
     * @return the deactivated community object
     * or an empty Optional if the Id is not found
     */
    public Optional<Community> deactivateCommunity(Long id){
        Optional<Community> communityOptional = communityRepository.findActiveCommunityById(id);
        if (communityOptional.isPresent()){
            communityOptional.get().setRecordStatusEnum(RecordStatusEnum.D);
            communityRepository.save(communityOptional.get());
        }
        return communityOptional;
    }

    /**
     * UTILYY METHOD
     *
     * @param user
     * @return true if the User is not a basic user
     */
    private boolean checkUserPermissions(User user){
        return !user.getUserEnum().equals(UserEnum.BASICUSER);
    }

}
