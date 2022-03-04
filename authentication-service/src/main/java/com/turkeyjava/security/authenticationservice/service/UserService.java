package com.turkeyjava.security.authenticationservice.service;

import com.turkeyjava.security.authenticationservice.model.Otp;
import com.turkeyjava.security.authenticationservice.model.User;
import com.turkeyjava.security.authenticationservice.repository.OtpRepository;
import com.turkeyjava.security.authenticationservice.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final OtpRepository otpRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, OtpRepository otpRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.otpRepository = otpRepository;
    }

    public void addUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void auth (User user){
        Optional<User> optionalUser=
                userRepository.findUserByUsername(user.getUsername());

        if(optionalUser.isPresent()){
            User u=optionalUser.get();
            if(passwordEncoder.matches(user.getPassword(), u.getPassword())){
                renewOtp(u);
            }else{
                throw new BadCredentialsException("Bad credentials");
            }
        }else{
            throw new BadCredentialsException("Bad credentials");
        }
    }

    private void renewOtp(User user) {
        String code = GenerateCodeUtil.generateCode();

        Optional<Otp> optionalOtp=otpRepository.findOtpByUserId(user.getId());
        if(optionalOtp.isPresent()){
            Otp otp=optionalOtp.get();
            otp.setCode(code);
        }else{
            Otp otp=new Otp();
            otp.setUser(user);
            otp.setCode(code);
            otpRepository.save(otp);
        }
    }
    public boolean check(Otp otpToValidate){
        Optional<Otp> optionalOtp=otpRepository.findById(otpToValidate.getId());
        //Optional<Otp> optionalOtp1=otpRepository.findOtpByUser(otpToValidate.getUser());
       // System.out.println(optionalOtp1.get());
        if(optionalOtp.isPresent()){
            Otp otp=optionalOtp.get();
            if(otpToValidate.getCode().equals(otp.getCode()))
                return true;
        }
        return false;
    }
}
