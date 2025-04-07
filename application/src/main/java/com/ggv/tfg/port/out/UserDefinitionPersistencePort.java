package com.ggv.tfg.port.out;

public interface UserDefinitionPersistencePort {

    void signIn(String username, String password);

    void signUp(String username, String password, String email);

    void signOut();
}
