package com.evertix.tutofastapi.service;

import com.evertix.tutofastapi.model.dto.UserDetail;

public interface UserService {
    UserDetail findUserById(Long id);
}
