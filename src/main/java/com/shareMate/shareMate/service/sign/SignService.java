package com.shareMate.shareMate.service.sign;


import com.shareMate.shareMate.dto.RequestLoginDto;
import com.shareMate.shareMate.dto.sign.ResponseSignInDto;
import com.shareMate.shareMate.entity.UserEntity;
import com.shareMate.shareMate.jwt.TokenHelper;
import com.shareMate.shareMate.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.Access;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignService {
    private final UserRepository userRepository;

    private final TokenHelper accTokenHelper;
    private final TokenHelper refTokenHelper;
    @AllArgsConstructor
    @Data
    static class LoginSuccessResponse {
        private String token;
    }
    @ResponseBody
    public ResponseSignInDto doLogin(RequestLoginDto requestLoginDto){
        Map json = new HashMap<String,Object>();
        System.out.println("lgser");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        UserEntity user = userRepository.findByEmail(requestLoginDto.toEntity().getEmail()).orElseThrow(()-> {throw new UsernameNotFoundException("유저 없음");});
        if(encoder.matches(requestLoginDto.toEntity().getPwd(),user.getPwd()))
        {
            String jwtAccToken = accTokenHelper.createToken( String.valueOf(user.getUser_id()));
            String jwtRefToken =refTokenHelper.createToken( String.valueOf(user.getUser_id()));
            //System.out.println("사인 서비스 토큰 "+ jwtRefToken);
            //System.out.println("사인 서비스 토큰 "+ jwtAccToken);
            return new ResponseSignInDto(jwtAccToken,jwtRefToken);
        }
        else{
            return new ResponseSignInDto(null,null);

        }



//        if (user==null){
//            json.put("status","fail");
//            json.put("text","존재하지 않는 유저입니다.");
//            return json;
//
//        }
//        if (encoder.matches(requestLoginDto.toEntity().getPwd(),user.get().getPwd())) {
//            json.put("status","success");
//            json.put("text","로그인 성공");
//            return json;
//        }
//        else {
//            json.put("status","fail");
//            json.put("text","비밀번호가 다릅니다.");
//            System.out.println(user.get().getPwd().getClass());
//            System.out.println(requestLoginDto.toEntity().getPwd().getClass());
//            return json;
//        }
    }
}
