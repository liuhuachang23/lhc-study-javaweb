package com.atguigu.qqzone.service.imp;

import com.atguigu.qqzone.dao.ReplyDAO;
import com.atguigu.qqzone.pojo.HostReply;
import com.atguigu.qqzone.pojo.Reply;
import com.atguigu.qqzone.pojo.Topic;
import com.atguigu.qqzone.pojo.UserBasic;
import com.atguigu.qqzone.service.HostReplyService;
import com.atguigu.qqzone.service.ReplyService;
import com.atguigu.qqzone.service.UserBasicService;

import java.util.List;

/**
 * @Author: 刘华昌
 * @DATE: 2022/7/4 16:43 星期一
 * @Operating:
 * @Description:
 */
public class ReplyServiceImp implements ReplyService {

    private ReplyDAO replyDAO = null;
    //此处引入的是其他POJO对应的Service接口，而不是DAO接口
    //其他POJO对应的业务逻辑是封装在service层的，我需要调用别人的业务逻辑方法，而不要去深入考虑人家内部的细节
    private HostReplyService hostReplyService = null;
    private UserBasicService userBasicService = null;

    @Override
    public List<Reply> getReplyListByTopicId(Integer id) {

        List<Reply> replyList = replyDAO.getReplyList(new Topic(id));
        //遍历所有的回复，获取回复中的主人回复，则将主人回复设置到回复中进去
        for (int i = 0; i < replyList.size(); i++) {
            Reply reply = replyList.get(i);

            //将关联的主人回复 添加到reply中
            HostReply hostReply = hostReplyService.getHostReplyByReplyId(reply.getId());
            reply.setHostReply(hostReply);

            //将回复的作者 加入到reply中
            Integer authorId = reply.getAuthor().getId();
            UserBasic author = userBasicService.getUserBasicById(authorId);
            reply.setAuthor(author);

        }
        return replyList;
    }

    @Override
    public void addReply(Reply reply) {
        replyDAO.addReply(reply);
    }

    @Override
    public void delReply(Integer id) {
        //1. 根据id获取到reply
        Reply reply = replyDAO.getReply(id);
        if (reply != null) {
            //查询是否有关联的hostReply
            HostReply hostReply = hostReplyService.getHostReplyByReplyId(reply.getId());
            //2. 如果reply有关联的hostReply，要先删除hostReply
            // （外键约束：hostReply的外键replyId为 reply的主键，所以要先删除hostReply）
            if (hostReply != null){
                hostReplyService.delHostReply(hostReply.getId());
            }
            //3. 删除reply
            replyDAO.delReply(id);
        }
    }

    @Override
    public void delReplyList(Topic topic) {
        List<Reply> replyList = replyDAO.getReplyList(topic);
        if (replyList != null){
            for (Reply reply: replyList) {
                delReply(reply.getId());
            }
        }
    }
}
