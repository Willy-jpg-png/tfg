package com.ggv.tfg.usecase;

import com.ggv.tfg.port.in.UserDefinitionManagementPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@UseCase
@RequiredArgsConstructor
@Slf4j
public class UserDefinitionUseCase implements UserDefinitionManagementPort {

    @Override
    public void signIn(String username, String password) {
        log.info("SignIn user {}", username);
    }

    @Override
    public void signUp(String username, String password, String email) {
        log.info("SignUp user {}", username);
    }

    @Override
    public void signOut() {
        log.info("SignOut");
    }

}
