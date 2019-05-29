package com.news.service;

import com.alibaba.fastjson.JSONObject;
import com.news.dao.CommentDao;
import com.news.dao.LikesDao;
import com.news.domain.Comment;

import java.sql.SQLException;
import java.util.List;

public class CommentService {
    /**
     * 增加评论
     * @param comment
     * @return
     */
    public Comment addComment (Comment comment) {
        Integer newsid = comment.getNewsid();
        Integer touid = comment.getTocid();
        if (newsid == null && touid == null ) {
            return null;
        }
        CommentDao dao = new CommentDao();
        Comment comment1 = null;
        try {
            comment1 = dao.addComment(comment);
        }catch (SQLException e){
            e.printStackTrace();
        }
        boolean updateCommentNum = true;
        // 增加评论数
        if (comment1 != null && comment.getNewsid() > 0) {
            NewsService service = new NewsService();
            updateCommentNum = service.increCommentNumById(comment.getNewsid());
        }
        return comment1 != null && updateCommentNum ? comment1 : null;
    }

    /**
     * 根据新闻id 获取评论列表
     * @param id
     * @param start
     * @param end
     * @param userid
     * @return
     */
    public List<Comment> getCommnetListById(int id, int start, int end, int userid) {
        List<Comment> list = null;
        CommentDao dao = new CommentDao();
        LikesDao dao1= new LikesDao();
        try {
            list = dao.queryCommnetById(id, start, end);
        }catch (SQLException e){
            e.printStackTrace();
        }
        for (int i = 0; i < list.size(); i++){
            Comment item = list.get(i);
            int num = getCommnetLikesNumById(item.getId());
            item.setLikesnum(num);
            boolean isLiked = false;
            try {
             isLiked = dao1.getIsLiked(item.getId(), userid);
            }catch (SQLException e){
                e.printStackTrace();
            }
            item.setIsLiked(isLiked);
        }
        return list;
    }

    /**
     * 根据新闻id获取评论数量
     * @param id
     * @return
     */
    public int getCommentsNum(int id) {
        CommentDao dao = new CommentDao();
        int num = 0;
        try {
          num = dao.getCommentsNum(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return num;
    }

    /**
     * 根据评论id给评论点赞
     * @param userid
     * @param commentid
     * @return
     */
    public boolean addLikesById(int userid , int commentid){
        CommentDao dao = new CommentDao();
        int update = 0;
        try {
            update = dao.updateLikesById(userid, commentid);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return update > 0;
    }

    /**
     * 根据评论id查询点赞数量
     * @param id
     * @return
     */
    public int getCommnetLikesNumById (int id)  {
        LikesDao dao = new LikesDao();
        int num = 0;
        try {
            num = dao.getLikesCountById(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  num;
    }

    /**
     * 根据评论id获取单条评论记录
     * @param id
     * @return
     */
    public Comment getSingleCommnetById (int id) {
        Comment comment = null;
        CommentDao commentDao = new CommentDao();
        try {
            comment = commentDao.getSingleCommentById(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return comment;
    }

    /**
     * 获取该用户是否已经给该条评论点赞
     * @param commentid
     * @param userid
     * @return
     */
    public boolean getIsLiked (int commentid, int userid) {
        LikesDao dao = new LikesDao();
        boolean isLiked = false;
        try {
            isLiked = dao.getIsLiked(commentid, userid);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  isLiked;
    }

    /**
     * 取消点赞
     * @param commentid
     * @param userid
     * @return
     */
    public boolean cancelLike (int commentid, int userid) {
        LikesDao dao = new LikesDao();
        int update = 0;
        try {
            update = dao.cancelLike(commentid, userid);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return update > 0;
    }

    /**
     * 根据评论id获取用户id     未完成！！！！！
     * @param comment_id
     * @return
     */
    public int getUserIdByCommentId (int comment_id) {
        Integer userid = null;

        return userid;
    }
    public boolean deleteCommentById (int id) {
        CommentDao dao = new CommentDao();
        int update = 0;
        try {
            update = dao.deleteCommentById(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  update > 0;
    }
}
