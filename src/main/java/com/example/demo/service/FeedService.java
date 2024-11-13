package com.example.demo.service;

import com.example.demo.model.Group;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserRepository userRepository;

    public Page<Post> getUserFeed(Long userId, int page, int size) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Group> userGroups = user.getGroups();
        if (userGroups.isEmpty()) {
            throw new RuntimeException("User is not part of any groups");
        }
        Pageable pageable = PageRequest.of(page, size);
//        return groupRepository.findPostsByGroupsSortedByTime(userGroups, pageable);
        return null;
    }
}
