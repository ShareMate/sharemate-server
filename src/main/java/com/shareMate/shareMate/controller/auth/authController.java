package com.shareMate.shareMate.controller.auth;

import com.shareMate.shareMate.dto.RequestLoginDto;
import com.shareMate.shareMate.dto.RequestUserDto;
import com.shareMate.shareMate.dto.sign.LoginSuccess;
import com.shareMate.shareMate.dto.sign.ResponseSignInDto;
import com.shareMate.shareMate.service.sign.SignService;
import com.shareMate.shareMate.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
@RequiredArgsConstructor
public class authController {
    private final UserService userService;
    private final SignService signService;
    @PostMapping("/register")
    public Map doUserList(@RequestBody RequestUserDto requestUserDto){
        System.out.println("hello"+ requestUserDto);

        return userService.doInsert(requestUserDto);
    }


    @PostMapping("login")
    public ResponseEntity<ResponseSignInDto> UserLogin(@RequestBody RequestLoginDto requestLoginDto){
        ResponseSignInDto responseSignInDto = signService.doLogin(requestLoginDto);
        System.out.println("프린트");

        return ResponseEntity.ok(responseSignInDto);
    }

}