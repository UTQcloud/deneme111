package com.yzmglstm.controller;

import com.yzmglstm.dto.DtoLoginRequest;
import com.yzmglstm.dto.DtoUsers;
import com.yzmglstm.dto.DtoUsersIU;
import java.util.List;

public interface IUsersController {


    public DtoUsers saveUsers(DtoUsersIU dtoUsers);

    public List <DtoUsers> GetAllUsers(); 

    public DtoUsers loginUser(DtoLoginRequest dtoLoginRequest);

    

}
