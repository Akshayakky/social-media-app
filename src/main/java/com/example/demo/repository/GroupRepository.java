package com.example.demo.repository;

import com.example.demo.model.Group;
import com.example.demo.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query("SELECT p FROM Post p WHERE p.group.groupId IN :groupIds ORDER BY p.createdAt DESC")
    Page<Post> findPostsByGroupsSortedByTime(@Param("groupIds") List<Long> groupIds, Pageable pageable);
}