package iscyf.chatroom.service;

import iscyf.chatroom.entity.User;

public interface UserService {

    public User findUserOne (String username);

    public User findUserOnById (Integer id);

    public User save (User user);

    public Integer getIdByUsername (String username);
}
