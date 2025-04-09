package com.ggv.tfg.usecase;

import com.ggv.tfg.port.in.UserDefinitionManagementPort;
import com.ggv.tfg.port.out.UserDefinitionPersistencePort;
import com.ggv.tfg.stereotype.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@UseCase
@RequiredArgsConstructor
@Slf4j
public class UserDefinitionUseCase implements UserDefinitionManagementPort {

    private UserDefinitionPersistencePort userDefinitionPersistencePort;

    @Override
    public void signIn(String username, String password) {

        userDefinitionPersistencePort.signIn(username, password);
    }

    @Override
    public void signUp(String username, String password, String email) {

        userDefinitionPersistencePort.signUp(username, password, email);
    }

    @Override
    public void signOut() {

        userDefinitionPersistencePort.signOut();
    }

}
