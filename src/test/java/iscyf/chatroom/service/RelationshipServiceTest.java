package iscyf.chatroom.service;

import iscyf.chatroom.entity.Relationship;
import iscyf.chatroom.entity.User;
import iscyf.chatroom.repository.RelationshipRepository;
import iscyf.chatroom.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author 陈佳鑫
 * @ClassName RelationshipServiceTest
 * @Description
 * @date 2019-12-13 15:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RelationshipServiceTest {
    @Autowired
    RelationshipRepository relationshipRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void addOneRelationship() {
        User user1 = userRepository.findByUsername("guest1");
        User usre2 = userRepository.findByUsername("guest2");
        Relationship relationship = new Relationship();
        relationship.setUser1(user1);
        relationship.setUser2(usre2);
        relationshipRepository.save(relationship);
    }
}