package iscyf.chatroom.controller;

import iscyf.chatroom.entity.Impression;
import iscyf.chatroom.service.ImpressionService;
import iscyf.chatroom.service.UserService;
import iscyf.chatroom.utils.ResultVOUtil;
import iscyf.chatroom.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 陈雨菲
 * @description 好友印象
 * @data 2019/12/9
 */
@RestController
@RequestMapping("impressions")
public class ImpressionController {

    @Autowired
    private UserService userService;

    @Autowired
    public ImpressionService impressionService;

    /**
     * 获得好友印象列表
     */
    @GetMapping
    public ResultVO getFriendImpressions (HttpServletRequest request) {
        Integer uid = Integer.valueOf(request.getRemoteUser());
        List<Impression> impressions = impressionService.findAllByUid(uid);
        return ResultVOUtil.success(impressions);
    }

    /**
     * 添加一条好友印象
     * @param uid 用户id
     * @param impression 新好友印象
     * @return
     */
    @PostMapping
    public ResultVO postOneFriendImpression (@RequestParam Integer uid,
                                             @RequestParam String impression) {
        System.out.println("添加一条印象");
        Impression friendImpression = new Impression(uid, impression);
        System.out.println(friendImpression.toString());
        friendImpression = impressionService.save(friendImpression);
        System.out.println(friendImpression.toString());
        return ResultVOUtil.success(friendImpression);
    }

    /**
     * 删除一条好友印象
     * @param id 需要删除的印象id
     */
    @DeleteMapping
    public ResultVO deleteOneFriendImpression (@RequestParam Integer id,
                                               HttpServletRequest request) {
        Impression impression = impressionService.findOneById(id);
        if (impression == null ) {
            return ResultVOUtil.error("500", "该印象不存在");
        }
        if (impression.getUid() != Integer.valueOf(request.getRemoteUser())) {
            return ResultVOUtil.error("500", "抱歉您只能删除自己的好友印象");
        }

        if (impressionService.delete(id)) {
            return getFriendImpressions(request);
        } else {
            return ResultVOUtil.error("500", "删除失败");
        }

    }
}
