//package com.shareMate.shareMate.service;
//
//import com.shareMate.shareMate.dto.UserDto;
//import com.shareMate.shareMate.service.user.UserService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Date;
//
//<<<<<<< HEAD
//@SpringBootTest
//class UserServiceTest {
//    @Autowired
//    UserService userService;
//    @Test
//    void doInsert() {
//        UserDto userDto = UserDto.builder()
//                .email("2222@naver.com")
//                .name("테스트임")
//                .pwd("1234")
//                .major("12")
//                .grade("23")
//                .birth(new Date())
//                .profile_photo("ssd")
//                .build();
//
//        userService.doInsert(userDto);
//    }
//=======
////@SpringBootTest
////class UserServiceTest {
////    @Autowired
////    UserService userService;
////
////    }
//>>>>>>> 67337a99d8d770742a8a082b6ef1cb859364a29d
////    @Test
////    void doUpdate(){
//////        UserEntity userEntity = new UserEntity();
//////        userEntity.setId(3);
//////        userEntity.setName("문민수바뀜");
//////        userEntity.setEmail("email바뀜");
//////        userEntity.setPassword("1234");
//////        userEntity.setCreate_at(LocalDateTime.now());
////        UserEntity userEntity  = userService.doSelectOne(1);
////        userEntity.setName("문민수바뀜");
////        userService.doUpdate(userEntity);
////    }
////
////
////    @Test
////    void doDelete() {
////        userService.doDelete(2);
////    }
