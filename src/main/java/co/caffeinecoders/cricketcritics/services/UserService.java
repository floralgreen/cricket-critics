package co.caffeinecoders.cricketcritics.services;

import co.caffeinecoders.cricketcritics.entities.User;
import co.caffeinecoders.cricketcritics.enums.RecordStatusEnum;
import co.caffeinecoders.cricketcritics.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public User addUser(User user) {
        //salviamo l'oggetto e poi lo ritorniamo
        return repository.save(user);
    }
    public List<User> getAll() {
        //ritorniamo tutta la lista degli oggetti
        return repository.findAll();
    }
    public Optional<User> getUserFromId(Long id){
        //cerchiamo e poi ritorniamo l'oggetto tramite id
        Optional<User> userOptional = repository.findById(id);
        //controlliamo che l'oggetto Optional sia presente
        if(userOptional.isPresent()){
            //se presente ritorniamo l'oggetto Optional
            return userOptional;
        }else {
            //se non presente ritorniamo un oggetto Optional vuoto
            return Optional.empty();
        }
    }
    public Optional<User> updateUser(Long id,User user){
        //recuperiamo l'oggetto da modificare grazie all'id
        Optional<User> userOptional = getUserFromId(id);
        //controlliamo se l'oggetto è presente
        if(userOptional.isPresent()){
            //modifichiamo tutti i parametri dell'oggetto
            userOptional.get().setUserName(user.getUserName());
            userOptional.get().setName(user.getName());
            userOptional.get().setLastName(user.getLastName());
            userOptional.get().setEmail(user.getEmail());
            userOptional.get().setPassword(user.getPassword());
            userOptional.get().setRecordStatusEnum(user.getRecordStatusEnum());
            userOptional.get().setUserName(user.getUserName());
            userOptional.get().setReviews(user.getReviews());
            //salviamo l'oggetto aggiornato
            User userUpdated = repository.save(userOptional.get());
            //ritorniamo l'oggetto che è stato aggiornato
            return Optional.of(userUpdated);
        }else {
            //se non presente ritorniamo un oggetto vuoto
            return Optional.empty();
        }
    }
    public Optional<User> deactivateUserById(Long id){
        Optional<User> userToDeactivate = repository.findById(id);
        if (userToDeactivate.isPresent()){
            userToDeactivate.get().setRecordStatusEnum(RecordStatusEnum.D);
            repository.save(userToDeactivate.get());
        }
        return userToDeactivate;
    }
}


