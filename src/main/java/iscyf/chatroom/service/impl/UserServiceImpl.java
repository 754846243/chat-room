package iscyf.chatroom.service.impl;

import io.swagger.models.auth.In;
import iscyf.chatroom.entity.Impression;
import iscyf.chatroom.entity.User;
import iscyf.chatroom.repository.UserRepository;
import iscyf.chatroom.service.UserService;
import iscyf.chatroom.vo.UserInformationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public User findUserOneById(Integer id) {
        System.out.println(id);
        return userRepository.findUserById(id);
    }

    @Override
    public User save (User user) {
        System.out.println(user.toString());
        return userRepository.save(user);
    }

    @Override
    public User findUserByRequest (HttpServletRequest request) {
        Integer id = Integer.valueOf(request.getRemoteUser());
        return userRepository.findUserById(id);
    }
}
