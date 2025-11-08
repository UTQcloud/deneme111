package com.yzmglstm.services.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yzmglstm.dto.DtoUsers;
import com.yzmglstm.entities.Users;
import com.yzmglstm.repository.UsersRepository;
import com.yzmglstm.services.IUsersServices;
import com.yzmglstm.dto.DtoUsersIU;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.yzmglstm.dto.DtoLoginRequest; 
import java.util.Optional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersServicesImpl implements IUsersServices {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder; 

    
    @Autowired
    public UsersServicesImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

   @Override
    public DtoUsers saveUsers(DtoUsersIU dtoUsersIU) {
        
        Users user = new Users();   
        BeanUtils.copyProperties(dtoUsersIU, user);
        
        user.setPassword(passwordEncoder.encode(dtoUsersIU.getPassword()));
        
        
        Users savedUser = usersRepository.save(user);

        DtoUsers response = new DtoUsers();
        BeanUtils.copyProperties(savedUser, response);
        return response;
    }

    
    @Override
    public List <DtoUsers> GetAllUsers(){

        List <DtoUsers> dtoResponseList = new ArrayList<>();
        List <Users> users = usersRepository.findAll();

        for(Users user : users){
            DtoUsers dtoUsers = new DtoUsers();
            BeanUtils.copyProperties(user, dtoUsers);
            dtoResponseList.add(dtoUsers);
        }

        return dtoResponseList;

    }

   

    @Override
    public DtoUsers loginUser(DtoLoginRequest dtoLoginRequest) {
        
        Optional<Users> userOptional = usersRepository.findByMail(dtoLoginRequest.getMail());

        if (userOptional.isEmpty()) {
            throw new RuntimeException("E-posta veya şifre hatalı.");
        }

        Users user = userOptional.get();
        String plainPassword = dtoLoginRequest.getPassword(); 
        String hashedPassword = user.getPassword(); 

        if (passwordEncoder.matches(plainPassword, hashedPassword)) {
            DtoUsers response = new DtoUsers();
            BeanUtils.copyProperties(user, response);
            return response;
        } else {
            throw new RuntimeException("E-posta veya şifre hatalı.");
        }
    }

}
