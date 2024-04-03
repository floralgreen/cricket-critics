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
    private UserRepository userRepository;

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllActiveUsers() {
        return userRepository.findAllActiveUsers();
    }

    public Optional<User> getUserFromId(Long id) {
        Optional<User> userOptional = userRepository.findActiveUserById(id);
        return userOptional;
    }

    public Optional<User> updateUser(Long id, User user) {
        Optional<User> userOptional = userRepository.findActiveUserById(id);
        if (userOptional.isPresent()) {
            userOptional.get().setUserName(user.getUserName());
            userOptional.get().setName(user.getName());
            userOptional.get().setLastName(user.getLastName());
            userOptional.get().setEmail(user.getEmail());
            userOptional.get().setPassword(user.getPassword());
            User userUpdated = userRepository.save(userOptional.get());

            return Optional.of(userUpdated);
        } else {

            return Optional.empty();
        }
    }

    public Optional<User> deactivateUserById(Long id) {
        Optional<User> userToDeactivate = userRepository.findActiveUserById(id);
        if (userToDeactivate.isPresent()) {
            userToDeactivate.get().setRecordStatusEnum(RecordStatusEnum.D);
            userRepository.save(userToDeactivate.get());
        }
        return userToDeactivate;
    }
}


