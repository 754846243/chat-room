package iscyf.chatroom.repository;

import iscyf.chatroom.entity.Relationship;
import iscyf.chatroom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 陈佳鑫
 * @Description
 * @date 2019-12-11 16:06
 */
@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, Integer> {
    Relationship findRelationshipById(Integer id);

    @Query(value = "select * from relationship where uid1 = ? and groupname = ? and ifpassed = 1", nativeQuery = true)
    List<Relationship> findAllByUser1AndGroup(Integer id, String group);

    @Query(value = "select * from relationship where uid1 = ? and ifPassed = ?", nativeQuery = true)
    List<Relationship> findRelationshipsByIfPassed(Integer id, Integer ifPassed);

    @Query(value = "select groupname, COUNT(*) as number from relationship where uid1 = ? GROUP BY groupname", nativeQuery = true)
    Map<String, Integer> findAllGroupNames(Integer id);

    Relationship findRelationshipByUser1AndUser2(User user1, User user2);

    @Modifying
    @Transactional
    @Query(value = "delete from relationship where uid1 = ? and uid2 = ?", nativeQuery = true)
    void deleteRelationshipByUser1AndUser2(Integer uid1, Integer uid2);
}
