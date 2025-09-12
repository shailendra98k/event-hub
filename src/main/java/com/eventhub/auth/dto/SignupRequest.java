package com.eventhub.auth.dto;

import com.eventhub.auth.entity.Role;

public class SignupRequest {
    public String email;
    public String password;
    public Role role; // buyer, venueOwner, admin
}
