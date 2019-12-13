package iscyf.chatroom.service;

import iscyf.chatroom.entity.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    public User findUserOne (String username);

    public User findUserOneById (Integer id);

    public User save (User user);

    public User findUserByRequest (HttpServletRequest request);
}
