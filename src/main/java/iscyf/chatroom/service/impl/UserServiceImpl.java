package iscyf.chatroom.service.impl;

import iscyf.chatroom.entity.User;
import iscyf.chatroom.repository.UserRepository;
import iscyf.chatroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 陈雨菲
 * @description
 * @data 2019/12/10
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserOne(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findUserOnById(Integer id) {
        System.out.println(id);
        return userRepository.findUserById(id);
    }

    @Override
    public User save (User user) {
        System.out.println(user.toString());
        return userRepository.save(user);
    }

    @Override
    public Integer getIdByUsername(String username) {
        return findUserOne(username).getId();
    }
}
