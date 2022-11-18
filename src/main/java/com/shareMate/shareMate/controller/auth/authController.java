package com.shareMate.shareMate.controller.auth;

import com.shareMate.shareMate.dto.RequestLoginDto;
import com.shareMate.shareMate.dto.UserDto;
import com.shareMate.shareMate.dto.sign.ResponseSignInDto;
import com.shareMate.shareMate.service.sign.SignService;
import com.shareMate.shareMate.service.user.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class authController {
    private final UserService userService;
    private final SignService signService;

    @ApiOperation(value = "회원가입",notes = "회원가입 버튼 클릭시 회원가입을 진행하는 요청입니다.")
    @PostMapping("/register")
    public Map doUserList(@RequestBody UserDto userDto){

        System.out.println("hello"+ userDto);

        return userService.doInsert(userDto);
    }

    @ApiOperation(value = "로그인(JWT 토큰 발급)",notes = "id,pw를 이용하여 로그인을 진행하고, jwt토큰을 발급합니다. ")
    @ApiParam()
    @PostMapping("/login")
    public ResponseEntity<ResponseSignInDto> UserLogin(@RequestBody RequestLoginDto requestLoginDto){
        ResponseSignInDto responseSignInDto = signService.doLogin(requestLoginDto);
        System.out.println("프린트");
        if(responseSignInDto.getAccessToken()==null){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseSignInDto);
        }
        else return ResponseEntity.status(HttpStatus.OK).body(responseSignInDto);
    }

}
