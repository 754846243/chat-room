package iscyf.chatroom.controller;

import iscyf.chatroom.configure.QiniuConfigure;
import iscyf.chatroom.entity.Impression;
import iscyf.chatroom.entity.User;
import iscyf.chatroom.service.ImpressionService;
import iscyf.chatroom.service.UserService;
import iscyf.chatroom.utils.QiniuUpload;
import iscyf.chatroom.vo.UserInformationVO;
import iscyf.chatroom.vo.UserVO;
import iscyf.chatroom.utils.ResultVOUtil;
import iscyf.chatroom.vo.ResultVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author 陈雨菲
 * @description 用户
 * @data 2019/12/9
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    public ImpressionService impressionService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 注册一个用户
     * @param userVO 用户
     * @param result
     */
    @PostMapping("/register")
    public ResultVO save (@Valid @RequestBody UserVO userVO, BindingResult result) {
        if(result.hasErrors()){
            return ResultVOUtil.error("500", result.getFieldError().getDefaultMessage());
        }

        String username = userVO.getUsername();
        String password = userVO.getPassword();

        User user = userService.findUserOneById(username);
        if (user != null) {
            return ResultVOUtil.error("500", "该用户名已经被使用");
        }

        user = new User(username, passwordEncoder.encode(password));

        return ResultVOUtil.success(userService.save(user));
    }

    /**
     * 用户上传头像
     * @param file
     * @return
     */
    @PostMapping("/avatar")
    public ResultVO uploadAvatar (@RequestParam MultipartFile file,
                                  HttpServletRequest request) {
        String uid = request.getRemoteUser();

        String originalFilename = file.getOriginalFilename();
        String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
        if (StringUtils.equalsIgnoreCase(suffixName, "jpg") && StringUtils.equalsIgnoreCase(suffixName, "png")) {
            return ResultVOUtil.error("500", "图片格式不对");
        }

        String fileName = "avatar" + uid + suffixName;
        String avatarUrl;
        try {
            avatarUrl = QiniuUpload.upload(file, fileName);
        } catch (Exception e) {
            return ResultVOUtil.error("500", "图片上传失败");
        }
        return ResultVOUtil.success(avatarUrl);
    }

    /**
     * 更新用户本人个人信息
     * @param gender 用户性别
     * @param age 用户年龄
     * @param avatar 用户头像地址
     * @return
     */
    @PostMapping("/update")
    public ResultVO update (@RequestParam String gender,
                            @RequestParam Integer age,
                            @RequestParam String avatar,
                            HttpServletRequest request) {
        User user = userService.findUserByRequest(request);
        user.setGender(gender);
        user.setAge(age);
        user.setAvatar(avatar);
        userService.save(user);
        return ResultVOUtil.success(user);
    }

    /**
     * 获得用户本人个人信息
     * @param request
     * @return
     */
    @GetMapping
    public ResultVO getUserInfo (HttpServletRequest request) {
        User user = userService.findUserByRequest(request);
        if (user.getAvatar() == null) user.setAvatar(QiniuConfigure.path + "avatar0");
        return ResultVOUtil.success(user);
    }

    /**
     * 根据用户id获得用户信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResultVO getUserInfoById (@PathVariable Integer id) {
        User user = userService.findUserOneById(id);
        List<Impression> impressions = impressionService.findAllByUid(id);
        UserInformationVO userInformation = new UserInformationVO(user, impressions);
        return ResultVOUtil.success(userInformation);
    }
}
