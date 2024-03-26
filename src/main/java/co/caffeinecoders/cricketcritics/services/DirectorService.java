package co.caffeinecoders.cricketcritics.services;

import co.caffeinecoders.cricketcritics.entities.Director;
import co.caffeinecoders.cricketcritics.repositories.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DirectorService {
    @Autowired
    private DirectorRepository repository;

    public Director addDirector(Director director){
        return repository.save(director);
    }
    public Optional<Director> getDirector(Long id){
        return repository.findById(id);
    }
    public Optional<Director> updateDirector(Director director, Long id){
        Optional<Director> directorOptional = repository.findById(id);
        if (directorOptional.isPresent()){
            directorOptional.get().setName(director.getName());
            directorOptional.get().setLastName(director.getLastName());
            directorOptional.get().setDateOfBirth(director.getDateOfBirth());
            directorOptional.get().setNationality(director.getNationality());
            directorOptional.get().setAge(director.getAge());
            directorOptional.get().setPlaceOFBirth(director.getPlaceOFBirth());
            directorOptional.get().setMovies(director.getMovies());
            repository.save(directorOptional.get());
        }
        return directorOptional;
    }
    public Optional<Director> deleteDirector(Long id){
        Optional<Director> directorOptional = repository.findById(id);
        if (directorOptional.isPresent()){
            repository.delete(directorOptional.get());
        }
        return directorOptional;
    }
}
