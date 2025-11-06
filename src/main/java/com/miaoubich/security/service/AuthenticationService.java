package com.miaoubich.security.service;

import com.miaoubich.security.dto.AuthenticationRequest;
import com.miaoubich.security.dto.AuthenticationResponse;
import com.miaoubich.security.dto.RegisterRequest;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception;
}
