package com.yzmglstm.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoUsersIU {

    private String firstName;
    private String lastName;
    private Date birthDate;
    private String mail;
    private String phone;
    private String password;
    


}
