package com.shareMate.shareMate.controller.group;

import com.shareMate.shareMate.dto.*;

import com.shareMate.shareMate.entity.GroupEntity;
import com.shareMate.shareMate.service.GroupService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class groupController {

    private final GroupService groupService;


    @ApiOperation(value = "그룹매칭 전체 리스트 조회",notes = "그룹매칭 메인화면에 나타낼 그룹매칭 게시글 리스트를 반환합니다.")

    //그룹 전체 리스트 조회
    @GetMapping("/groups")
    public ResponseEntity<ArrayList<GroupListDto>> getAll(HttpServletRequest request) {


        System.out.println("filter로부터 전달받은 userid: "+request.getAttribute("userid"));
        List<GroupEntity> list = groupService.getAllGroup();
        ArrayList<GroupListDto> responseList = new ArrayList<>();
        for (GroupEntity e : list) {
            GroupListDto groupListDto = new GroupListDto(e.getGroupID(), e.getUserID(), e.getTitle(), e.getMaxNum(), e.getCurNum(),e.getCreated_at());
            responseList.add(groupListDto);
        }
        return ResponseEntity.ok(responseList);
    }
    @ApiOperation(value = "그룹매칭 디테일 조회",notes = "그룹매칭 게시글을 클릭하여 조회할 때 사용합니다.")
    @ApiImplicitParam(
            name = "num"
            , value = "게시글 id"
            , required = true
            , dataType = "int"
            , paramType = "query"
            , defaultValue = "None")
    @GetMapping("/group")
    public ResponseEntity<Optional<GroupDetailDto>>getDetail(@RequestParam int num){

        Optional<GroupDetailDto> groupDetailDto = groupService.getDetailGroup(num);
        return ResponseEntity.ok(groupDetailDto);
    }

    @ApiOperation(value = "그룹매칭 게시글 작성",notes = "작성자가 그룹매칭 게시글을 작성합니다.")
    @ApiImplicitParam(
            name = "group"
            , value = "수정된 게시글 정보"
            , required = true
            , dataType = "GroupDetailDto"
            , paramType = "query"
            , defaultValue = "None")
    @PostMapping("/group")
    public ResponseEntity addGroup(@RequestBody GroupDetailDto group)
    {
        groupService.addGroup(group);
        return  ResponseEntity.ok(HttpStatus.OK);

    }
    @ApiOperation(value = "그룹매칭 게시글 수정",notes = "작성자가 그룹매칭 게시글을 수정합니다.")
    @PutMapping("/group")
    public ResponseEntity editPost(@RequestParam int groupID,@RequestBody GroupDetailDto group ,HttpServletRequest request){

        System.out.println(Integer.parseInt(request.getAttribute("userid").toString()));
        Optional<GroupDetailDto> origin_group = groupService.getDetailGroup(groupID);
        //System.out.println("zz "+ origin_post.get().getUser_id() + "/"+origin_post.get().getPost_id());
        if(Integer.parseInt(request.getAttribute("userid").toString() )==origin_group.get().getUserID()){
            System.out.println("같아");
            this.groupService.editGroup(groupID,group);
            return new ResponseEntity(group ,HttpStatus.OK);
        }
        System.out.println("다름");
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    //그룹 삭제
    @ApiOperation(value = "그룹매칭 게시글 삭제",notes = "작성자가 그룹매칭 게시글을 삭제합니다.")
    @DeleteMapping("/group")
    public ResponseEntity delGroup(@RequestParam int postId){

        groupService.delGroup(postId);
        return ResponseEntity.ok(HttpStatus.OK);

    }

    //참가하기 (유저)
    @ApiOperation(value = "그룹매칭 참여하기",notes = "유저가 그룹매칭을 참여할 때 사용합니다.")
    @GetMapping("/join")
    public ResponseEntity joinGroup(HttpServletRequest request ,@RequestParam int groupId){
        final int userid = (int) request.getAttribute("userid");
        groupService.joinGroup(groupId,userid);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    //참여 취소
    @GetMapping("/leave")
    @ApiOperation(value = "그룹매칭 참여 취소",notes = "유저가 그룹매칭을 탈퇴할 때 사용합니다.")
    public ResponseEntity leaveGroup(HttpServletRequest request ,@RequestParam int groupId){
        final int userid = (int) request.getAttribute("userid");
        groupService.leaveGroup(groupId,userid);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
