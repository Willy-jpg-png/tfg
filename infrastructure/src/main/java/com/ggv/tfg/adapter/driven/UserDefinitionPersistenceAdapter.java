package com.ggv.tfg.adapter.driven;

import com.ggv.tfg.port.out.UserDefinitionPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Adapter
@Slf4j
public class UserDefinitionPersistenceAdapter implements UserDefinitionPersistencePort {

    @Override
    public void signIn(String username, String password) {

    }

    @Override
    public void signUp(String username) {

    }

    @Override
    public void signOut() {

    }
}
