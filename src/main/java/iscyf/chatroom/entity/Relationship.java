package iscyf.chatroom.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author 陈佳鑫
 * @Description 好友关系实体类
 * @date 2019-12-11 15:53
 */
@Entity
@Table(name = "relationship")
public class Relationship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "uid1")
    private User user1;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "uid2")
    private User user2;

    @Column(name = "msg", nullable = true)
    private String message;

    // 表示好友请求状态，0表示尚未通过，1表示已经通过，2表示已经拒绝
    @Column(name = "ifpassed")
    private Integer ifPassed;

    @Column(name = "groupname")
    private String group  = "我的好友";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getIfPassed() {
        return ifPassed;
    }

    public void setIfPassed(Integer ifPassed) {
        this.ifPassed = ifPassed;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relationship that = (Relationship) o;
        return id == that.id &&
                ifPassed == that.ifPassed &&
                user1.equals(that.user1) &&
                user2.equals(that.user2) &&
                Objects.equals(message, that.message) &&
                group.equals(that.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user1, user2, message, ifPassed, group);
    }
}
