package com.shareMate.shareMate.service.user;

import com.shareMate.shareMate.dto.*;
import com.shareMate.shareMate.dto.response.BaseResponse;
import com.shareMate.shareMate.dto.response.MessageUtils;

import com.shareMate.shareMate.dto.sign.RequestSignUpDto;
import com.shareMate.shareMate.dto.sign.ResponseSignInDto;
import com.shareMate.shareMate.dto.sign.ResponseSignUpDto;
import com.shareMate.shareMate.dto.sign.TempUserDto;
import com.shareMate.shareMate.entity.*;
import com.shareMate.shareMate.exception.SignUpFailureException;
import com.shareMate.shareMate.exception.UserNotFoundException;
import com.shareMate.shareMate.jwt.TokenHelper;
import com.shareMate.shareMate.repository.*;
import com.shareMate.shareMate.service.sign.SignService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLOutput;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FavorRepository favorRepository;
    private final HashtagRepository hashtagRepository;
    private final SignService signService;
    private final LikeRepository likeRepository;
    private final BlockRepository blockRepository;

    private final TokenHelper accessTokenHelper;
    private final TokenHelper refreshTokenHelper;
    private final BCryptPasswordEncoder encoder;
    public List<UserEntity> doSelectAll() {
        return userRepository.findAll();
    }
    public UserSimpleDto doSelectOne (int num){
        Optional<UserEntity> userEntity= userRepository.findById(num);
        List<HashTagEntity> hash_list = hashtagRepository.findAllByUserID(num);
        UserSimpleDto userSimpleDto = new UserSimpleDto();
        if(userEntity.isPresent()){
            List<String> hashtags= new ArrayList<>();
            for (HashTagEntity h : hash_list){
                hashtags.add(h.getHashTag());
            }

            userSimpleDto.setUserID(userEntity.get().getUserID());
            userSimpleDto.setName(userEntity.get().getName());
            userSimpleDto.setAge(userEntity.get().getAge());
            userSimpleDto.setGender(userEntity.get().getGender());
            userSimpleDto.setName(userEntity.get().getName());
            userSimpleDto.setMajor(userEntity.get().getMajor());
            userSimpleDto.setHashtags(hashtags);

        }
        return userSimpleDto;


    }



    public Map doInsert(RequestSignUpDto requestSignUpDto) {
        String originalPwd=requestSignUpDto.getPwd();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String securePwd = encoder.encode(requestSignUpDto.getPwd());
        Map<String, Object> map = new HashMap<>();

        if (userRepository.findByEmail(requestSignUpDto.getEmail()).isPresent()) {
            throw new SignUpFailureException(MessageUtils.INVALID_SIGNUP);
        } else {
            requestSignUpDto.setPwd(securePwd);
            UserEntity newUser = requestSignUpDto.toEntity();
            newUser.setPwd(securePwd);
            userRepository.save(newUser);
            String accessToken = accessTokenHelper.createToken(String.valueOf(newUser.getUserID()));
            String refreshToken = refreshTokenHelper.createToken( String.valueOf(newUser.getUserID()));

            Optional<UserEntity> user = userRepository.findUserEntityByEmail(newUser.getEmail());

            map.put("token", new ResponseSignInDto(accessToken,refreshToken));
            map.put("user",new TempUserDto(
                    user.get().getUserID(),
                    user.get().getEmail(),
                    user.get().getName(),
                    user.get().getMajor(),
                    user.get().getGrade(),
                    user.get().getGender(),
                    user.get().getAge(),
                    user.get().getProfile_photo()
            ));

            return map;
        }
    }



    public void doUpdate(int  num , ReqUpdateUserDto reqUpdateUserDto) {
        Optional<UserEntity> user =userRepository.findById(num);
        System.out.println(reqUpdateUserDto.getKakao_link());
        user.get().setKakao_link(reqUpdateUserDto.getKakao_link());

        user.get().setAge(reqUpdateUserDto.getAge());
        user.get().setGrade(reqUpdateUserDto.getGrade());
        user.get().setName(reqUpdateUserDto.getName());
        user.get().setMajor(reqUpdateUserDto.getMajor());

        userRepository.save(user.get());
    }

    //  delete
    public void doDelete(Integer id) {
        userRepository.deleteById(id);
    }

    public Page<UserEntity> getUserList(Integer page, Integer size,Boolean gender) {
        Pageable pageable = PageRequest.of(page, size);

        Page<UserEntity> postList = userRepository.findAllByGender(pageable,gender);
        //Page<UserEntity> postList = userRepository.findAll(pageable);
        System.out.println("====");
       for(UserEntity e : postList){
           System.out.println(e.getEmail());
       }

        return postList;
    }
    public Optional<List<UserEntity>> getUserBlockList(List list){
        if(list.size()==0) return null;
        return userRepository.findAllByUserID(list);
    }

    public List<UserSimpleDto> getUserLikeList(Integer id){
        List<LikeEntity> likesID = (likeRepository.findByUserFromID(id));
        List<UserSimpleDto> userSimpleList = new ArrayList<>();

        for (LikeEntity like : likesID) {
            Optional<UserEntity> user = userRepository.findById(like.getUserToID());
            List<HashTagEntity> hash = hashtagRepository.findAllByUserID(like.getUserToID());
            List <String> hashes = new ArrayList<>() ;
            for (HashTagEntity h : hash){
                hashes.add(h.getHashTag());
            }
            UserSimpleDto userSimpleDto = new UserSimpleDto(
                    user.get().getUserID(),
                    user.get().getName(),
                    user.get().getMajor(),
                    user.get().getAge(),
                    user.get().getGender(),
                    user.get().getProfile_photo());
            userSimpleDto.setHashtags(hashes);
            userSimpleList.add(userSimpleDto);
        }

        return userSimpleList;
    }
    public UserDto getUserDetail(Integer num) {
        Optional<UserEntity> member = userRepository.findById(num);
        return new UserDto(member.get().getUserID(), member.get().getEmail(),member.get().getName(),member.get().getMajor(),member.get().getGrade(), member.get().getGender(), member.get().getAge(),member.get().getProfile_photo(),member.get().getKakao_link(),member.get().getCreated_at(),member.get().getUpdated_at());

    }

    public List<HashTagEntity> getHashTagList(int num) {
        System.out.println("Zz");
        List<HashTagEntity> hashtagList = hashtagRepository.findAllByUserID(num);
        System.out.println(hashtagList);
        return hashtagList;

    }

    public void doInsertHashTags (int userID, List<String> hashtags){

        for (String s : hashtags){
            HashTagEntity hashTagEntity = new HashTagEntity();
            hashTagEntity.setHashTag(s);
            hashTagEntity.setUserID(userID);
            hashtagRepository.save(hashTagEntity);

        }
        return ;

    }


    public FavorDto getFavor(Integer num) {
        Optional<FavorEntity> member = favorRepository.findById(num);
        FavorDto res = new FavorDto();
        res.setCleanness(member.get().getCleanness());
        res.setMbti(member.get().getMbti());
        res.setUserID(member.get().getUserID());
        res.setDrinking(member.get().getDrinking());
        res.setSnoring(member.get().getSnoring());


        res.setSmoking(member.get().getSmoking());
        res.setSelfIntro(member.get().getSelfIntro());
        res.setStudyTime(member.get().getStudyTime());
        res.setSleepTime(member.get().getSleepTime());
        res.setWakeupTime(member.get().getWakeupTime());

        return res;
    }



    public void doLike(Integer user_id, Integer target_id){
        LikeEntity likeEntity = new LikeEntity(user_id,target_id);
        System.out.println("dolike/" + likeEntity.getUserFromID()+ " " +likeEntity.getUserToID());
        System.out.println();
        likeRepository.save(likeEntity);

        return ;
    }

    public void doUnLike(Integer user_id, Integer target_id){
        Optional<LikeEntity> like =likeRepository.findLikeEntityByUserFromIDAndUserToID(user_id,target_id);
        likeRepository.delete(like.get());
        return;
    }

    public boolean dupCheck(String email ){

        Optional<UserEntity> user = userRepository.findByEmail(email);
        if(user.isPresent()) return true;
        else return false;
    }


    public boolean pwdCheck(int userID, String pwd) {
        /* DB pwd와 요청 pwd가 일치하는지 반환*/
        UserEntity userEntity = userRepository.findById(userID).orElseThrow( ()-> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        String realPwd = userEntity.getPwd();
        boolean checked = encoder.matches(pwd,realPwd);

        return checked;
    }

    public void updatePwd (int userID,String pwd){
        UserEntity userEntity = userRepository.findById(userID).orElseThrow( ()-> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        userEntity.setPwd(encoder.encode(pwd));
        userRepository.save(userEntity);
        return ;

    }

    public UserEntity doSelectOneByEmail(String email){
       Optional<UserEntity> user = userRepository.findByEmail(email);
       return user.get();
    }

    public List<Integer> getBlockUser(Integer userFromID){
        Optional<List<BlockEntity>> block = blockRepository.findAllByUserFromID(userFromID);
        List <Integer> blockList = new ArrayList<>() ;
        for (BlockEntity b : block.get()){
            blockList.add(b.getUserToID());
        }
        return blockList;
    }
    public void saveBlockUser(Integer userFromID, Integer userToID){
        BlockEntity block = new BlockEntity();
        block.setUserFromID(userFromID);
        block.setUserToID(userToID);
        blockRepository.save(block);
    }

    public Boolean doUpdateProfile(int userID , String url) {
        Optional<UserEntity> user = userRepository.findById(userID);
        user.get().setProfile_photo(url);
        userRepository.save(user.get());

        return Boolean.TRUE;
    }
}
