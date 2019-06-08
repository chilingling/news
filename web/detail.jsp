<%@ page import="com.news.utils.RelativeDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: chiling
  Date: 2019/6/8
  Time: 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="relativeTimeFormat" class="com.news.utils.RelativeDateFormat" />
<html>
<head>
    <title>detail</title>
    <%@include file="headlink.jsp"%>
    <link rel="stylesheet" href="css/detail.css">
</head>
<body>
<%@include file="head.jsp"%>
<div class="container">
    <h1 class="title">${news.getTitle()}</h1>
    <p class="info"><span>${news.getTime()}</span>　<span>来源: ${news.getSource()}</span> &nbsp;<span>评论数：${news.getCommentnum()}</span></p>
    <div>
        <article class="content-article">
            ${news.content}
        </article>
    </div>
    <div class="comment-input-box">
        <div class="comment-box-title">
            <span><span class="comment-title">网友评论</span><span class="comment-tips">文明上网理性发言</span></span><span>${news.getCommentnum()}条评论</span>
        </div>
        <div class="comment-box-connent">
            <div class="comment-box-avatar">
                <img src="img/avatar_default.jpg" alt="默认头像">
            </div>
            <div class="comment-box-input">
                <textarea name="" id="comment-area" cols="30" rows="10" placeholder="说两句吧"></textarea>
                <c:choose>
                    <c:when test="${cookie['username'].value != null}">
                        <button class="comment-box-login add-comment-btn">评论</button>
                    </c:when>
                    <c:otherwise>
                        <button class="comment-box-login">请先登录</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    <!-- 评论列表 -->
    <div>
        <div class="comment-list-head">
            <span>
                <span class="all-comment">全部评论</span>
                <%--&nbsp;/&nbsp;<span class="my-comment">我的评论</span>--%>
            </span>
            <%--<span>--%>
                <%--<span class="top-fresh">最新</span>/ <span class="top-hits">最热</span>--%>
            <%--</span>--%>
        </div>
        <div class="comment-block">
            <ul class="comment-list">
                <c:if test="${commentList.size() <= 0}">
                    <li class="comment-item"><p>暂无评论</p></li>
                </c:if>
                <c:forEach items="${commentList}" var="item">
                    <li class="comment-item">
                        <div>
                            <!-- 评论 -->
                            <div class="comment-main">
                                <div class="comment-user-avatar"><img src="img/avatar_default.jpg" alt="默认头像"></div>
                                <div class="comment-content">
                                    <%--<p><a href="" class="comment-user">${item.getUsername()}</a><span class="comment-time">${RelativeDateFormat.format(item.getTime())}</span></p>--%>
                                    <%--<% String formatTime = RelativeDateFormat.format(${item.getTime}) %>--%>
                                    <p><a href="" class="comment-user">${item.getUsername()}</a><span class="comment-time">${item.getTime()}</span></p>
                                    <p>${item.getContent()}</p>
                                </div>
                            </div>
                            <!-- 回复 -->
                            <%--<div class="comment-reply-box">--%>
                                <%--<ul class="comment-reply-list">--%>
                                    <%--<li class="comment-reply-item">--%>
                                        <%--<div class="comment-reply-block">--%>
                                            <%--<p><strong class="reply-user-name">万水千山:</strong>先将所有作弊入学的学生全部除名，毕业了的收回学位证书及毕业证书，还社会及其它考生一个公正公平的学习及社会环境，对于家长要查实是否行贿，是否做假，一经查实，坚决处理。</p>--%>
                                            <%--<p><span>赞 10</span>&nbsp;&nbsp;<span>回复</span>&nbsp;&nbsp;<span>1小时前</span></p>--%>
                                        <%--</div>--%>
                                    <%--</li>--%>
                                    <%--<li class="comment-reply-item">--%>
                                        <%--<div class="comment-reply-block">--%>
                                            <%--<p><strong class="reply-user-name">A👑A一笑就天晴</strong> :应该严查的是学校吧，收钱时没关系，一出事摘得干干净净的，重点应该是学校，我看那个敢去搞美国的学校，人家拿枪毙了你们这群没事瞎操心的，难道要国家去查，现实吗？俗话说，女人何苦为难女人啊</p>--%>
                                            <%--<p><span>赞 10</span>&nbsp;&nbsp;<span>回复</span>&nbsp;&nbsp;<span>1小时前</span></p>--%>
                                        <%--</div>--%>
                                    <%--</li>--%>
                                <%--</ul>--%>
                            <%--</div>--%>
                            <!-- 赞与回复按钮 -->
                            <div class="comment-likes-reply-box">
                                <a class="comment-likes-btn" data-id = "${item.getId()}">赞 ${item.getLikesnum()}</a>
                                <%--<span class="comment-reply-btn">回复</span>--%>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
<%@include file="footer.jsp"%>
    <script>
        $(document).ready(function () {
            $('.add-comment-btn').click(function () {
                var comment = $('#comment-area')[0].value;
                var id = location.search.slice(1).split("=")[1]
                var data = {
                    "newsid": id,
                    "content": comment
                }
                $.ajax({
                    type: "POST",
                    url: "/news/addcomment",
                    data: JSON.stringify(data),
                    dataType: "json",
                    contentType: 'application/json',
                    success: function (data) {
                        if (data.status === 200) {
                            location.reload()
                        }
                    }
                })
            })

            $('.comment-likes-btn').click(function () {
                var id = $(this).attr("data-id")
                console.log(id)
                $.ajax({
                    url: "/news/likescomment?id=" + id,
                    dataType: 'json',
                    contentType: "application/json",
                    success: function (data) {
                        if (data.status === 200) {
                            location.reload()
                        }
                    }
                })
            })
        })
    </script>
</body>
</html>
