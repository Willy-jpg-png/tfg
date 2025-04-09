package com.ggv.tfg.adapter.driven;

import com.ggv.tfg.port.in.UserDefinitionManagementPort;
import com.ggv.tfg.port.out.UserDefinitionPersistencePort;
import com.ggv.tfg.stereotype.Adapter;
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
    public void signUp(String username, String password, String email) {

    }

    @Override
    public void signOut() {

    }
}
