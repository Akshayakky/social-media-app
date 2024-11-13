package com.example.demo.service;

import com.example.demo.dto.UserRequest;
import com.example.demo.model.Group;
import com.example.demo.model.User;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    /**
     * Get user by ID
     * @param userId The ID of the user to fetch
     * @return The user object
     */
    public User getUser(Long userId) {
        logger.info("Fetching user with ID: {}", userId);
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            logger.error("User not found with ID: {}", userId);
            throw new RuntimeException("User not found!");
        }
        logger.info("User found: {}", user.get().getFirstName());
        return user.get();
    }

    /**
     * Create a new user and assign groups
     * @param request UserRequest containing user details and group IDs
     * @return The created user
     */
    public User createUser(UserRequest request) {
        logger.info("Creating user: {}", request.getFirstName() + " " + request.getLastName());
        User user = new User();
        user.setType(request.getType());
        user.setProfileImageUrl(request.getProfileImageUrl());
        user.setLastName(request.getLastName());
        user.setFirstName(request.getFirstName());

        // Fetch groups by ID
        List<Group> groups = groupRepository.findAllById(request.getGroupIds());

        if (groups.isEmpty()) {
            logger.error("No valid groups found for IDs: {}", request.getGroupIds());
            throw new RuntimeException("Groups do not exist!");
        }

        user.setGroups(groups);
        userRepository.save(user);

        // Update each group with the new user
        for (Group group : groups) {
            group.getUsers().add(user);
            groupRepository.save(group);
            logger.info("Added user to group: {}", group.getGroupName());
        }

        logger.info("User created successfully: {} {}", user.getFirstName(), user.getLastName());
        return user;
    }

    /**
     * Add an existing user to the repository
     * @param user User object to save
     * @return The saved user
     */
    public User addUser(User user) {
        logger.info("Saving user: {} {}", user.getFirstName(), user.getLastName());
        User savedUser = userRepository.save(user);
        logger.info("User saved with ID: {}", savedUser.getUserId());
        return savedUser;
    }
}
