package com.shareMate.shareMate.dto;

import com.shareMate.shareMate.entity.UserEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserSimpleDto
{
    private int user_id;
    private String name;
    private String major;
    private String grade;
    private Date birth;
    private String profile_photo;
    private List<String> Hashtags;




    public UserEntity toEntity(){
        UserEntity userEntity = UserEntity.builder()
                .name(name)
                .major(major)
                .grade(grade)
                .birth(birth)
                .profile_photo(profile_photo)
                .build();
        return userEntity;
    }

    @Builder
    public UserSimpleDto(int user_id, String name, String major, String grade, Date birth, String profile_photo) {
        this.user_id = user_id;
        this.name = name;
        this.major = major;
        this.grade = grade;
        this.birth = birth;
        this.profile_photo = profile_photo;
    }
}
