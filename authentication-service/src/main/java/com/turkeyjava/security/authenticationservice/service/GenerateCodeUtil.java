package com.turkeyjava.security.authenticationservice.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.NoSuchElementException;

public final class GenerateCodeUtil {
    public GenerateCodeUtil() {
    }

    public static String generateCode(){
        String code;

        try{
            SecureRandom random=SecureRandom.getInstanceStrong();
            int c= random.nextInt(9000)+1000;
            code=String.valueOf(c);
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException("Problem when generating the random code.");
        }
        return code;
    }
}
