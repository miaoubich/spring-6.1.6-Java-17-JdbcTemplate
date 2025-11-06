package com.miaoubich.security.service;

import com.miaoubich.security.dto.AuthenticationRequest;
import com.miaoubich.security.dto.AuthenticationResponse;
import com.miaoubich.security.dto.RegisterRequest;
import com.miaoubich.security.mapper.UserMapper;
import com.miaoubich.security.model.User;
import com.miaoubich.security.service.dao.UserDAO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    private final UserDAO userDAO;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(UserDAO userDAO, UserMapper userMapper, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userDAO = userDAO;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword);
        User user = userMapper.toEntity(request);;
        userDAO.saveUser(user);

        var token = jwtService.generateToken(user);

        return userMapper.toResponse(token);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception {
//        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
//        Authentication auth = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
//        );
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        User user = userDAO.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        var token = jwtService.generateToken(user);

        return userMapper.toResponse(token);
    }
}
