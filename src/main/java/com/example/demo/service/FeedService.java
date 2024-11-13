package com.example.demo.service;

import com.example.demo.model.Group;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedService {

    private static final Logger logger = LoggerFactory.getLogger(FeedService.class);

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * Retrieves the user's feed by getting posts from all the groups they belong to.
     *
     * @param userId the user's ID
     * @param page the page number for pagination
     * @param size the number of items per page
     * @return a paginated list of posts
     */
    public Page<Post> getUserFeed(Long userId, int page, int size) {
        logger.info("Fetching feed for user with ID: {}, page: {}, size: {}", userId, page, size);

        // Fetch user from repository
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("User not found with ID: {}", userId);
                    return new EntityNotFoundException("User not found");
                });

        logger.info("User found: {}", userId);

        // Get groups the user belongs to
        List<Group> userGroups = user.getGroups();

        // If no groups, log and throw an exception
        if (userGroups.isEmpty()) {
            logger.warn("User with ID: {} is not part of any groups", userId);
            throw new RuntimeException("User is not part of any groups");
        }

        logger.info("User is part of {} groups", userGroups.size());

        // Prepare Pageable object for pagination
        Pageable pageable = PageRequest.of(page, size);
        List<Long> groupIds = userGroups.stream().map(Group::getGroupId).collect(Collectors.toList());

        // Fetch posts from the groups
        logger.info("Fetching posts for groups with IDs: {}", groupIds);

        Page<Post> posts = groupRepository.findPostsByGroupsSortedByTime(groupIds, pageable);

        // Log the number of posts retrieved
        logger.info("Retrieved {} posts for user with ID: {}", posts.getContent().size(), userId);

        return posts;
    }
}
