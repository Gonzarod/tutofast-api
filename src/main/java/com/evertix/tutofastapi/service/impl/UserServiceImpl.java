package com.evertix.tutofastapi.service.impl;

import com.evertix.tutofastapi.model.Tutorship;
import com.evertix.tutofastapi.model.User;
import com.evertix.tutofastapi.model.dto.SaveTutorshipRequest;
import com.evertix.tutofastapi.model.dto.TutorshipRequest;
import com.evertix.tutofastapi.model.dto.UserDetail;
import com.evertix.tutofastapi.repository.UserRepository;
import com.evertix.tutofastapi.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    ModelMapper mapper;

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetail findUserById(Long id) {
        return this.userRepository.findById(id).map(this::convertToResource).orElse(null);
    }

    private UserDetail convertToResource(User entity){return mapper.map(entity, UserDetail.class);}
}
