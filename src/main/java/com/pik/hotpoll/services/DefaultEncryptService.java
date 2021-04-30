package com.pik.hotpoll.services;

import com.pik.hotpoll.services.interfaces.EncryptService;

public class DefaultEncryptService implements EncryptService {
    @Override
    public String encrypt(String password) {
        return password;
    }

    @Override
    public boolean check(String checkPassword, String realPassword) {
        return checkPassword.equals(realPassword);
    }
}
