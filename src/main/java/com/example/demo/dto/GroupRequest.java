package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class GroupRequest {

    String groupName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
