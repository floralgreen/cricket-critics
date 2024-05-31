package co.caffeinecoders.cricketcritics.services;

import co.caffeinecoders.cricketcritics.entities.Director;
import co.caffeinecoders.cricketcritics.enums.RecordStatusEnum;
import co.caffeinecoders.cricketcritics.repositories.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DirectorService {
    @Autowired
    private DirectorRepository repository;

    /**
     *
     * @param director given a Director Object
     * @return the saved Object in the DB
     */
    public Director addDirector(Director director){
        return repository.save(director);
    }

    /**
     *
     * @param id given a directorId
     * @return Optional with the Director Object found
     * or an empty Optional if the Director is not found or not active
     */
    public Optional<Director> getActiveDirector(Long id){
        Optional<Director> directorOptional = repository.findActiveDirectorById(id);
        return directorOptional;
    }

    /**
     *
     * @return the full list of active Director in the Db
     * or an empty list if none is active
     */
    public List<Director> getAllActiveDirector(){
        return repository.findAllActiveDirector();
    }

    /**
     *
     * @param director the updated Director Object
     * @param id given the directorId that has to be updated
     * @return the updated Director saved on the DB
     * or empty Optional if the Id is not found
     */
    public Optional<Director> updateDirector(Director director, Long id){
        Optional<Director> directorOptional = repository.findById(id);
        if (directorOptional.isPresent()){
            if (directorOptional.get().getRecordStatusEnum().equals(RecordStatusEnum.A)) {
                directorOptional.get().setName(director.getName());
                directorOptional.get().setLastName(director.getLastName());
                directorOptional.get().setDateOfBirth(director.getDateOfBirth());
                directorOptional.get().setNationality(director.getNationality());
                directorOptional.get().setAge(director.getAge());
                directorOptional.get().setPlaceOFBirth(director.getPlaceOFBirth());
                directorOptional.get().setMovies(director.getMovies());
                repository.save(directorOptional.get());
            }
        }
        return directorOptional;
    }

    /**
     *
     * @param id given an directorId to deactivate
     * @return the deactivated director or
     * empty Optional if the Id is not found
     */
    public Optional<Director> deactivateDirector(Long id){
        Optional<Director> directorOptional = repository.findById(id);
        if (directorOptional.isPresent()){
                directorOptional.get().setRecordStatusEnum(RecordStatusEnum.D);
                repository.save(directorOptional.get());
        }
        return directorOptional;
    }
}
