package co.caffeinecoders.cricketcritics.services;

import co.caffeinecoders.cricketcritics.entities.Director;
import co.caffeinecoders.cricketcritics.enums.RecordStatusEnum;
import co.caffeinecoders.cricketcritics.repositories.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DirectorService {
    @Autowired
    private DirectorRepository repository;

    public Director addDirector(Director director){
        return repository.save(director);
    }
    public Optional<Director> getDirector(Long id){
        Optional<Director> directorOptional = repository.findById(id);
        if (directorOptional.isPresent()){
            if (directorOptional.get().getRecordStatusEnum().equals(RecordStatusEnum.A)) {
                return repository.findById(id);
            }
        }
        return Optional.empty();
    }
    public List<Director> getAllDirector(){
        List<Director> directors = repository.findAll();
        List<Director> activeDirectors = new ArrayList<>();
        for (Director director : directors) {
            if (director.getRecordStatusEnum().equals(RecordStatusEnum.A)){
                activeDirectors.add(director);
            }
        }
        return activeDirectors;
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
            if (directorOptional.get().getRecordStatusEnum().equals(RecordStatusEnum.A)) {
                directorOptional.get().setRecordStatusEnum(RecordStatusEnum.D);
                repository.save(directorOptional.get());
            }
        }
        return directorOptional;
    }
}
