package com.adventureit.userservice.Service;

import com.adventureit.userservice.Exceptions.InvalidUserEmailException;
import com.adventureit.userservice.Exceptions.InvalidUserPasswordException;
import com.adventureit.userservice.Exceptions.InvalidUserPhoneNumberException;
import com.adventureit.userservice.Requests.RegisterUserRequest;
import com.adventureit.userservice.Responses.RegisterUserResponse;

public interface UserService {
    public RegisterUserResponse RegisterUser(RegisterUserRequest req) throws InvalidUserEmailException, InvalidUserPasswordException, InvalidUserPhoneNumberException;
}
