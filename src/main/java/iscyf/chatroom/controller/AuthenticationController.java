package iscyf.chatroom.controller;

import iscyf.chatroom.utils.ResultVOUtil;
import iscyf.chatroom.vo.ResultVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 陈雨菲
 * @description 处理未登录的情况
 * @data 2019/12/9
 */
@RestController
public class AuthenticationController {

    @RequestMapping("/authentication")
    public ResultVO authentication () {
        return ResultVOUtil.error("000", "无访问权限，请先登录");
    }
}
