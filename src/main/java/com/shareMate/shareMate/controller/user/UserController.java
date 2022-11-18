package com.shareMate.shareMate.controller.user;
import com.shareMate.shareMate.dto.*;
import com.shareMate.shareMate.entity.UserEntity;
import com.shareMate.shareMate.repository.HashtagRepository;
import com.shareMate.shareMate.service.CustomUserDetailService;
import com.shareMate.shareMate.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api("유저 정보")
public class UserController {
    @Getter
    @Setter
    public class LoginForm {
        private String email;
        private String pwd;

    }
    private final UserService userService;
    private final CustomUserDetailService userDetailsService;
    private final HashtagRepository hashtagRepository;
    public UserController(UserService userService,CustomUserDetailService userDetailsService,HashtagRepository hashtagRepository) {
        this.userService = userService;
        this.userDetailsService=userDetailsService;
        this.hashtagRepository=hashtagRepository;
    }
    @ApiOperation(value ="1:1매칭 유저 리스트 조회",notes = "메인화면에서 나타낼 유저 리스트를 반환하는 요청")
    @ApiImplicitParam(name="pageNum",value="페이지 Number",required = true)
    @RequestMapping(value = "/users/{pageNum}",method = RequestMethod.GET)
    public ResponseEntity<ArrayList<UserSimpleDto>> getPostList(@PathVariable("pageNum") int page){
        Page<UserEntity> resultList = userService.getUserList(page, 5);

        List<UserEntity> resultDtoList = resultList.getContent();
        ArrayList<UserSimpleDto> responseList = new ArrayList<>();
        for( UserEntity u : resultDtoList){
            UserSimpleDto userDto = new UserSimpleDto(u.getUser_id(),u.getName(),u.getMajor(), u.getGrade(),u.getBirth(), u.getProfile_photo());
            List<String> hash = hashtagRepository.findAllByUser_id(u.getUser_id());
            userDto.setHashtags(hash);
            responseList.add(userDto);
        }
        return ResponseEntity.ok(responseList);
    }
    @ApiOperation(value = "유저 디테일 조회",notes ="유저 클릭시 유저의 디테일한 데이터를 반환합니다.(취향/유저정보)")
    @ApiImplicitParam(name="userID",value="디테일 정보를 볼 유저의 아이디",required = true)
    @RequestMapping(value = "/user/{userID}",method = RequestMethod.GET)
    public ResponseEntity<ResUserDetailDto> getUserDetail(@PathVariable("userID") int num){
        /*favor 가져오는 코드*/
        FavorDto favor = userService.getFavor(num);
        /* User 가져오는 코드*/
        UserDto member = userService.getUserDetail(num);
        System.out.println(favor.getMbti());
        List<String> hashtag = userService.getHashTagList(num);
        ResUserDetailDto resUserDetailDto = new ResUserDetailDto();
        resUserDetailDto.setFavor(favor);
        resUserDetailDto.setUser(member);
        resUserDetailDto.setHashtag_list(hashtag);
        return ResponseEntity.ok(resUserDetailDto);
    }
    @ApiOperation(value = "좋아요 동작",notes = "좋아요 기능을 수행합니다.")
    @ApiImplicitParam(name="userID",value="좋아요 누른 유저의 아이디",required = true)
    @RequestMapping(value = "/like/{userID}",method = RequestMethod.POST)
    public ResponseEntity LikeUser(HttpServletRequest request,@PathVariable("userID") int num){
        final int user_id =Integer.parseInt(request.getAttribute("userID").toString());
        userService.doLike(user_id,num);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @ApiOperation(value = "좋아요 취소", notes = "좋아요 기능을 취소합니다.")
    @ApiImplicitParam(name="userID",value="좋아요 취소할 유저의 아이디",required = true)
    @RequestMapping(value = "/unlike/{userID}",method = RequestMethod.POST)
    public ResponseEntity UnLikeUser(HttpServletRequest request,@PathVariable("userID") int num) {
        final int user_id = Integer.parseInt(request.getAttribute("userID").toString());
        userService.doUnLike(user_id, num);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //개인정보 수정
    @ApiOperation(value = "개인정보 수정",notes = "개인정보를 수정합니다.")
    @PutMapping("/user")
    public ResponseEntity editUser (HttpServletRequest request) {
       // userService.editUser(num,userDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
