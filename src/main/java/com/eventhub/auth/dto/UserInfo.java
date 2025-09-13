package com.eventhub.auth.dto;

import com.eventhub.auth.entity.Role;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserInfo {
    private Long id;
    private String firstName;
    private String email;
    private Role role;
}

