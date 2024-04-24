package co.caffeinecoders.cricketcritics.services;

import co.caffeinecoders.cricketcritics.entities.Community;
import co.caffeinecoders.cricketcritics.entities.Director;
import co.caffeinecoders.cricketcritics.enums.RecordStatusEnum;
import co.caffeinecoders.cricketcritics.repositories.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommunityService {
    @Autowired
    private CommunityRepository communityRepository;
    public Community createCommunity(Community community){
        return communityRepository.save(community);
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

}
