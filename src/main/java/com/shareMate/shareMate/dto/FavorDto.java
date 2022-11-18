package com.shareMate.shareMate.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@ApiModel(value = "취향 정보", description = "유저의 취향 정보를 나타내는 domain class 입니다.")
public class FavorDto {

    @ApiModelProperty(value = "유저 id", required = true, example = "35")
    private int user_id;
    @ApiModelProperty(value = "취침시간", required = true, example = "0")
    private int sleep_time;
    @ApiModelProperty(value = "흡연 여부", required = true, example = "1")
    private int smoking;
    @ApiModelProperty(value = "기상시간", required = true, example = "3")
    private int wakeup_time;
    @ApiModelProperty(value = "음주", required = true, example = "1")
    private int drinking;
    @ApiModelProperty(value = "공부시간대", required = true, example = "1")
    private int study_time;
    @ApiModelProperty(value = "청결도", required = true, example = "1")
    private int cleanness;
    @ApiModelProperty(value = "선호 나이대", required = true, example = "1")
    private int prefered_age;
    @ApiModelProperty(value = "선호 학과", required = true, example = "0")
    private int prefered_major;
    @ApiModelProperty(value = "코골이 여부", required = true, example = "1")
    private int snoring;
    @ApiModelProperty(value = "mbti", required = true, example = "string")
    private String mbti;
    @ApiModelProperty(value = "자기소개", required = true, example = "string")
    private String self_intro;

//    @Builder
//    public FavorDto(int user_id, int sleep_time, int smoking, int wakeup_time, int drinking, int study_time, int cleanness, int prefered_age, int prefered_major, int snoring, String mbti, String self_intro) {
//        this.user_id = user_id;
//        this.sleep_time = sleep_time;
//        this.smoking = smoking;
//        this.wakeup_time = wakeup_time;
//        this.drinking = drinking;
//        this.study_time = study_time;
//        this.cleanness = cleanness;
//        this.prefered_age = prefered_age;
//        this.prefered_major = prefered_major;
//        this.snoring = snoring;
//        this.mbti = mbti;
//        this.self_intro = self_intro;
//    }
}


