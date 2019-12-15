package iscyf.chatroom.service.impl;

import iscyf.chatroom.entity.Relationship;
import iscyf.chatroom.entity.User;
import iscyf.chatroom.repository.RelationshipRepository;
import iscyf.chatroom.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 陈佳鑫
 * @Description
 * @date 2019-12-11 16:22
 */
@Service
public class RelationshipImpl implements RelationshipService {
    @Autowired
    RelationshipRepository relationshipRepository;

    @Override
    public Relationship findRelationshipById(Integer id) {
        return relationshipRepository.findRelationshipById(id);
    }

    @Override
    public Relationship findRelationshipByUsers(User user1, User user2) {
        return relationshipRepository.findRelationshipByUser1AndUser2(user1, user2);
    }

    @Override
    public Relationship addOneRelationship(User user1, User user2) {
        /**
         * @description 发送好友申请
         * @param user1
         * @param user2
         * @Return iscyf.chatroom.entity.Relationship
         */
        Relationship relationship = new Relationship();
        relationship.setUser1(user1);
        relationship.setUser2(user2);
        relationship.setIfPassed(0);
        relationshipRepository.saveAndFlush(relationship);
        return relationship;
    }

    /**
     * @description 接受好友请求
     * @param  relationship 1
     * @param  Id 2
     * @Return java.lang.Boolean
     */
    @Override
    public Relationship changeStatus(Relationship relationship, Integer Id) {
        relationship.setIfPassed(Id);
        relationshipRepository.flush();
        return relationship;
    }

    @Override
    public List<Relationship> findFriendByGroup(User user, String group) {
        return relationshipRepository.findAllByUser1AndGroup(user.getId(), group);
    }

    @Override
    public List<Relationship> findFriendByIfPassed(User user, Integer ifPassed) {
        return relationshipRepository.findRelationshipsByIfPassed(user.getId(), ifPassed);
    }

    /**
     * @description 删除好友关系
     * @param user1
     * @param user2
     * @Return iscyf.chatroom.entity.Relationship
     */
    @Override
    public void deleteOneRelationship(User user1, User user2) {
        relationshipRepository.deleteRelationshipByUser1AndUser2(user1.getId(), user2.getId());
    }

    @Override
    public Map<String, Integer> findGroupNames(Integer id) {
        return relationshipRepository.findAllGroupNames(id);
    }
}
