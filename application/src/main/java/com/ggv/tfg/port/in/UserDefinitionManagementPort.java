package com.ggv.tfg.port.in;

public interface UserDefinitionManagementPort {

    void signIn(String username, String password);

    void signUp(String username, String password, String email);

    void signOut();
}
