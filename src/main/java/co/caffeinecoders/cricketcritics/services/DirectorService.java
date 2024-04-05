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

    public Director addDirector(Director director){
        return repository.save(director);
    }
    public Optional<Director> getActiveDirector(Long id){
        Optional<Director> directorOptional = repository.findActiveDirectorById(id);
        return directorOptional;
    }
    public List<Director> getAllActiveDirector(){
        return repository.findAllActiveDirector();
    }
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
    public Optional<Director> deactivateDirector(Long id){
        Optional<Director> directorOptional = repository.findById(id);
        if (directorOptional.isPresent()){
                directorOptional.get().setRecordStatusEnum(RecordStatusEnum.D);
                repository.save(directorOptional.get());
        }
        return directorOptional;
    }
}
