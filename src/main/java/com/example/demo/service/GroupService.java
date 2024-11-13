package com.example.demo.service;

import com.example.demo.model.Group;
import com.example.demo.model.Post;
import com.example.demo.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    GroupRepository groupRepository;

    public List<Post> posts(Long groupId) {
        Optional<Group> group = groupRepository.findById(groupId);
        if (group.isEmpty()) {
            throw new EntityNotFoundException("Group with ID " + groupId + " not found!");
        }
        List<Post> posts = group.get().getPosts();
        if (posts == null) {
            return Collections.emptyList();
        }
        return posts;
    }
}
