package ing.yisus.recipesweb.service;


import ing.yisus.recipesweb.persistence.UserEntity;
import ing.yisus.recipesweb.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean registerUser(UserEntity user) {
        if (user == null || user.getUsername() == null || user.getUsername().isEmpty()) {
            return false; // Invalid user or username
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return false; // Username already exists
        }
        userRepository.save(user);
        System.out.println("converted");
        return true; // User registered successfully
    }

    public long getUserCount() {
        return userRepository.count();
    }
}
