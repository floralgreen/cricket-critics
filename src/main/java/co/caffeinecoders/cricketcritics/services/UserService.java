package co.caffeinecoders.cricketcritics.services;

import co.caffeinecoders.cricketcritics.entities.DTO.PersonalizedResponse;
import co.caffeinecoders.cricketcritics.entities.Review;
import co.caffeinecoders.cricketcritics.entities.User;
import co.caffeinecoders.cricketcritics.enums.RecordStatusEnum;
import co.caffeinecoders.cricketcritics.enums.UserEnum;
import co.caffeinecoders.cricketcritics.repositories.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private final Integer REVIEW_REQUIRMENT = 25;
    private final Integer LIKES_REQUIRMENT = 250;


    public User addUser(User user) {
        user.setPassword(cryptPassword(user.getPassword()));
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
            userOptional.get().setPassword(cryptPassword(user.getPassword()));
            User userUpdated = userRepository.save(userOptional.get());

            return Optional.of(userUpdated);
        } else {

            return Optional.empty();
        }
    }

    /**
     *
     * @param id given the User ID to upgrade
     * @return PersonalizedResponse with 3 status
     * OK(user satisfy requisites and is upgraded + user object upgraded)
     * NOT_ALLOWRD(user doesn't satisfy requisites + user object not modified
     * NOT FOUND(User with given ID is not Found or deleted)
     */
    public PersonalizedResponse upgradeUser(Long id) {
        Optional<User> userOptional = userRepository.findActiveUserById(id);
        PersonalizedResponse personalizedResponse = new PersonalizedResponse(HttpServletResponse.SC_NOT_FOUND, "User Not Found", Optional.empty());

        if (userOptional.isPresent()) {
            boolean isUpgradable = checkRequirements(userOptional.get());
            if (isUpgradable) {
                userOptional.get().setUserEnum(UserEnum.REVIEWER);
                userRepository.save(userOptional.get());

                personalizedResponse.setStatus(HttpServletResponse.SC_OK);
                personalizedResponse.setMessage("User successfully upgraded to REVIEWER");
                personalizedResponse.setEntity(userOptional.get());
            } else {
                personalizedResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                personalizedResponse.setMessage("User cannot be upgraded to REVIEWER");
                personalizedResponse.setEntity(userOptional.get());
            }
        }

        return personalizedResponse;
    }



    public Optional<User> deactivateUserById(Long id) {
        Optional<User> userToDeactivate = userRepository.findActiveUserById(id);
        if (userToDeactivate.isPresent()) {
            userToDeactivate.get().setRecordStatusEnum(RecordStatusEnum.D);
            userRepository.save(userToDeactivate.get());
        }
        return userToDeactivate;
    }

    /**
     * Utility method
     * @param userToCheck given a User to check
     * @return true if the user satisfies the requirements false if not
     */
    private boolean checkRequirements(User userToCheck){
        boolean isUpgradable = false;
        List<Review> reviews = userToCheck.getReviews();
        Integer likesSum = 0;

        if (reviews.size() >= REVIEW_REQUIRMENT) {
            for (Review review : reviews) {
                likesSum += review.getLikesCounter();
            }
            if (likesSum >= LIKES_REQUIRMENT) {
                isUpgradable = true;
            }
        }
        return isUpgradable;
    }

    /**
     *
     * @param password given a String containing a user password
     * @return the hashed password by the BCryptPasswordEncoder
     */
    private String cryptPassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}


