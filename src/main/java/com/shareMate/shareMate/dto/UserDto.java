package com.shareMate.shareMate.dto;

import com.shareMate.shareMate.entity.UserEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;

@Getter
@Setter
@ApiModel(value = "회원가입" , description = "회원가입시 사용할 유저의 domain class")

public class UserDto {

    @ApiModelProperty(value = "이메일",example = "qwer@ajou.ac.kr")
    private String email;
    @ApiModelProperty(value = "닉네임",example = "키키")
    private String name;
    @ApiModelProperty(value = "전공",example = "소프트웨어학과")
    private String major;
    @ApiModelProperty(value = "학년",example = "3")
    private String grade;
    @ApiModelProperty(value = "생일")
    private Date birth;
    @ApiModelProperty(value = "카카오 오픈채팅링크",example = "https://open.kakao.com/o/s2qDCFOe")
    private String kakao_id;
    @ApiModelProperty(value = "프로필 사진",example = "https://kikimong.com/wp-content/uploads/2022/01/%EC%B9%B4%EC%B9%B4%E.png")
    private String profile_photo;



    public UserEntity toEntity(){
        UserEntity userEntity = UserEntity.builder()
                .email(email)
                .name(name)
                .major(major)
                .grade(grade)
                .birth(birth)
                .profile_photo(profile_photo)
                .build();
        return userEntity;
    }

    @Builder
    public UserDto( String email, String pwd, String name, String major, String grade, Date birth, String profile_photo) {
        this.email = email;
        this.name = name;
        this.major = major;
        this.grade = grade;
        this.birth = birth;
        this.profile_photo = profile_photo;
    }
}
