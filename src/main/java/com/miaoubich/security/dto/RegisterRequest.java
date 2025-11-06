package com.miaoubich.security.dto;

import com.miaoubich.security.model.Role;

public class RegisterRequest {

    private String username;
    private String password;
    private Role role;

    public RegisterRequest() {
    }
    public RegisterRequest(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return """
                RegisterRequest{
                username='%s',
                password='[PROTECTED]',
                role=%s}
                }
                """.formatted(username, role);
    }
}
