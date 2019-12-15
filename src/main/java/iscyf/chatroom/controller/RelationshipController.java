package iscyf.chatroom.controller;

import iscyf.chatroom.entity.Relationship;
import iscyf.chatroom.entity.User;
import iscyf.chatroom.service.RelationshipService;
import iscyf.chatroom.service.UserService;
import iscyf.chatroom.utils.ResultVOUtil;
import iscyf.chatroom.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description 对好友关系的控制，包括查找所有好友，
 * @date 2019-12-11 17:50
 */
@RestController
@RequestMapping(value = "friends")
public class RelationshipController {
    @Autowired
    private UserService userService;

    @Autowired
    private RelationshipService relationshipService;

    /**
     * @description
     * @param  username 要添加的好友的用户名
     * @param  request 用于获取当前用户信息
     * @Return iscyf.chatroom.entity.Relationship
     */
    @PostMapping(value = "/")
    public ResultVO addOneRelationship(@RequestParam String username,
                                       @RequestParam(required = false) String groupname,
                                       HttpServletRequest request) {
        User user1 = userService.findUserOne(request.getRemoteUser());
        User user2 = userService.findUserOne(username);
        if (user1.equals(user2)) {
            return ResultVOUtil.error("400", "不能添加自己为好友");
        }
        Relationship relationship = relationshipService.findRelationshipByUsers(user1, user2);
        if (relationship != null){
            if (relationship.getIfPassed() == 1) {
                return ResultVOUtil.error("400", "对方已是您的好友");
            } else {
                relationship.setIfPassed(0);
            }
        } else {
            relationship = relationshipService.addOneRelationship(user1, user2);
        }
        if (groupname != null) relationship.setGroup(groupname);
//        else relationship.setGroup("我的好友");
        relationshipService.addOneRelationship(relationship.getUser2(), relationship.getUser1());
        return ResultVOUtil.success("好友请求已发送，等待通过。", relationship);
    }

    /**
     * @description 接受,或拒绝好友请求
     * @param  rid 好友关系的id
     * @param  ifPassed 要进行的操作
     * @Param  groupname 分组名称
     * @Return iscyf.chatroom.vo.ResultVO
     */
    @PatchMapping(value = "/{rid}/{ifPassed}")
    public ResultVO acceptFriendRequest(@PathVariable Integer rid,
                                        @PathVariable Integer ifPassed,
                                        @RequestParam String groupname) {
        Relationship relationship = relationshipService.findRelationshipById(rid);
        relationshipService.changeStatus(relationship, ifPassed);
        Relationship relationship_1;
        if (ifPassed == 1) {
            relationship_1 = relationshipService.findRelationshipByUsers(relationship.getUser2(), relationship.getUser1());
            relationship_1.setGroup(groupname);
            relationshipService.changeStatus(relationship_1, 1);
            return ResultVOUtil.success(relationship_1);
        }
        return ResultVOUtil.success(relationship);
    }

    /**
     * @description
     * @param  username 关系id
     * @Return iscyf.chatroom.vo.ResultVO
     */
    @DeleteMapping("/{username}")
    public ResultVO deleteOneRelationship(@PathVariable String username, HttpServletRequest request) {
        User user1 = userService.findUserOne(request.getRemoteUser());
        User user2 = userService.findUserOne(username);
        Relationship relationship = relationshipService.findRelationshipByUsers(user1, user2);
        relationshipService.deleteOneRelationship(relationship.getUser1(), relationship.getUser2());
        return ResultVOUtil.success("删除成功", relationship);
    }


    /**
     * @description 查找当前用户的所有分组信息
     * @param  request 用户获取当前用户信息
     * @Return iscyf.chatroom.vo.ResultVO 包含所有分组的名称和每个分组的成员数
     */
    @GetMapping(value = "/groups")
    public ResultVO findGroupNames(HttpServletRequest request) {
        User user = userService.findUserOne(request.getRemoteUser());
        Map<String, Integer> groupInfo = relationshipService.findGroupNames(user.getId());
        return ResultVOUtil.success(groupInfo);
    }

    /**
     * @description 根据分组名称查询好友
     * @param  request 用于获取当前用户信息
     * @Return iscyf.chatroom.vo.ResultVO 返回当前分组下所有用户的列表
     */
    @GetMapping(value = "/groups/{group}")
    public ResultVO findAllRelationships(@PathVariable("group") String group,
                                         HttpServletRequest request) {
        User user = userService.findUserOne(request.getRemoteUser());
        List<Relationship> relationships;
        if (group == null) {
            return ResultVOUtil.error("400","请传入分组名称");
        }else {
            relationships = relationshipService.findFriendByGroup(user, group);
        }
        List<User> friends = new ArrayList<>();
        for (Relationship relationship: relationships) {
            User friend = relationship.getUser2();
            friends.add(friend);
        }
        return ResultVOUtil.success(friends);
    }

    /**
     * @description 根据好友关系状态获取好友列表
     * @param  ifPassed 0：尚未通过，1：已经通过，2：已经拒绝
     * @param  request 用于获取当前用户信息
     * @Return iscyf.chatroom.vo.ResultVO 返回不同好友关系状态下所有用户的列表
     */
    @GetMapping(value = "/{ifPassed}")
    public ResultVO findAllRelationships(@PathVariable Integer ifPassed,
                                         HttpServletRequest request) {
        User user = userService.findUserOne(request.getRemoteUser());
        List<Relationship> relationships;
        if (ifPassed != 0 && ifPassed != 1 && ifPassed != 2) {
            return ResultVOUtil.error("400","请传入正确的好友关系状态参数");
        }else {
            relationships = relationshipService.findFriendByIfPassed(user, ifPassed);
        }
        List<User> friends = new ArrayList<>();
        for (Relationship relationship: relationships) {
            User friend = relationship.getUser2();
            friends.add(friend);
        }
        return ResultVOUtil.success(friends);
    }
}
