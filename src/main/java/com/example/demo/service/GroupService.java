package com.example.demo.service;

import com.example.demo.model.Group;
import com.example.demo.model.Post;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    private static final Logger logger = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * Retrieves the posts for a given group ID.
     *
     * @param groupId the ID of the group
     * @return a list of posts in the group
     */
    public List<Post> posts(Long groupId) {
        logger.info("Fetching posts for group with ID: {}", groupId);

        Optional<Group> groupOptional = groupRepository.findById(groupId);

        if (groupOptional.isEmpty()) {
            logger.error("Group with ID: {} not found", groupId);
            throw new EntityNotFoundException("Group with ID " + groupId + " not found!");
        }

        Group group = groupOptional.get();
        List<Post> posts = group.getPosts();

        if (posts == null || posts.isEmpty()) {
            logger.warn("No posts found for group with ID: {}", groupId);
            return Collections.emptyList();
        }

        logger.info("Retrieved {} posts for group with ID: {}", posts.size(), groupId);
        return posts;
    }

    /**
     * Creates a new group with the specified name.
     *
     * @param groupName the name of the group
     * @return the created group
     */
    public Group createGroup(String groupName) {
        logger.info("Creating group with name: {}", groupName);

        // Ensure that group name is valid (if needed)
        if (groupName == null || groupName.trim().isEmpty()) {
            logger.error("Group name cannot be null or empty");
            throw new IllegalArgumentException("Group name cannot be null or empty");
        }

        Group group = new Group();
        group.setGroupName(groupName);

        // Save the new group
        Group savedGroup = groupRepository.save(group);

        logger.info("Successfully created group with ID: {}", savedGroup.getGroupId());
        return savedGroup;
    }
}
