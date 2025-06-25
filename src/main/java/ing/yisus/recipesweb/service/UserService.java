package ing.yisus.recipesweb.service;


import ing.yisus.recipesweb.model.User;
import ing.yisus.recipesweb.persistence.UserEntity;
import ing.yisus.recipesweb.repository.UserRepository;
import ing.yisus.recipesweb.util.DtoUserMapper;
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
        return true; // User registered successfully
    }

    public long getUserCount() {
        return userRepository.count();
    }

    public User loginUser(String username, String password) {
        if (username == null || password == null) {
            return null; // Invalid input
        }
        return userRepository.findByUsername(username)
                .filter(user -> user.getPassword().equals(password))
                .map(DtoUserMapper::entityToModel) // Convert UserEntity to User model)
                .orElse(null); // Return null if no matching user found
    }
}
