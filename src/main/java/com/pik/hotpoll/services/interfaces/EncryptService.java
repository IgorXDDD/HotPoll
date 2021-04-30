package com.pik.hotpoll.services.interfaces;

public interface EncryptService {

    public String encrypt(String password) ;


    public boolean check(String checkPassword, String realPassword);
}
