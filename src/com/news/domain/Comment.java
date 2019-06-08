package com.news.domain;

import java.util.Date;

public class Comment {
    public int getNewsid() {
        return newsid;
    }

    public void setNewsid(int newsid) {
        this.newsid = newsid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFromuid() {
        return fromuid;
    }

    public void setFromuid(int fromuid) {
        this.fromuid = fromuid;
    }

    public int getTocid() {
        return tocid;
    }

    public void setTocid(int touid) {
        this.tocid = touid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getLikesnum() {
        return likesnum;
    }

    public void setLikesnum(int likesnum) {
        this.likesnum = likesnum;
    }

    /**
     * newsid   新闻id
     * content  评论内容
     * fromid   添加评论的用户
     * touid    评论的用户
     * time     评论时间
     * likesnum     点赞数
     */
    private int newsid;
    private String content;
    private int fromuid;
    private int tocid;
    private String time;
    private int likesnum;
    private int id;
    private String username;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    private boolean isLiked;

    public boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }
}
