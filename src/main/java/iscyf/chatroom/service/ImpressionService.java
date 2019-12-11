package iscyf.chatroom.service;

import iscyf.chatroom.entity.Impression;

import java.util.List;

/**
 * @author 陈雨菲
 * @description
 * @data 2019/12/10
 */
public interface ImpressionService {

    public List<Impression> findAllByUid (Integer uid);

    public Impression findOneById (Integer id);

    public Impression save (Impression impression);

    public boolean delete (Integer id);

}
