package iscyf.chatroom.service;

import iscyf.chatroom.entity.Relationship;
import iscyf.chatroom.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author 陈佳鑫
 * @Description
 * @date 2019-12-11 16:14
 */
public interface RelationshipService {
    public Relationship findRelationshipById(Integer id);

    public Relationship findRelationshipByUsers(User user1, User user2);

    public Relationship addOneRelationship(User user1, User user2);

    public Relationship changeStatus(Relationship relationship, Integer Id);

    public void deleteOneRelationship(User user1, User user2);

    public Map<String, Integer> findGroupNames(Integer id);

    public List<Relationship> findFriendByGroup(User user, String group);

    public List<Relationship> findFriendByIfPassed(User user, Integer ifPassed);
}
