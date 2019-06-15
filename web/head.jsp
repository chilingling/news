<%@ page import="com.alibaba.fastjson.JSONObject" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: chiling
  Date: 2019/6/7
  Time: 21:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header>
    <div class="header-title-nav header-transition">
        <h1 class="title">新闻网<span class="glyphicon glyphicon-user login-avater"></span></h1>
        <nav>
            <ul>
                <c:choose>
                    <c:when test="${classifyId eq 0}">
                        <li><a class="active" href="/news/index">要闻</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="/news/index">要闻</a></li>
                    </c:otherwise>
                </c:choose>
                <c:forEach items="${classifyList}" var="item">
                    <c:choose>
                        <c:when test="${classifyId eq item.getId()}">
                            <li><a class="active" href="/news/classifynews?classify=${item.getId()}">${item.getName()}</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="/news/classifynews?classify=${item.getId()}">${item.getName()}</a></li>
                        </c:otherwise>
                    </c:choose>

                </c:forEach>
            </ul>
        </nav>
    </div>
    <div class="sign-login-box">
        <c:choose>
            <c:when test="${cookie['username'].value != null}">
                <div class="dropdown">
                    <span class="dropdown-toggle user-box" id="dropdownlogout" data-toggle="dropdown"><img class="user-avatar" src="img/avatar_default.jpg" alt="用户头像">你好&nbsp;${cookie['username'].value}</span>
                    <ul class="dropdown-menu pull-right" role="menu" aria-labelledby="dropdownlogout">
                        <li role="presentation"><a class="change-passwd-btn btn-default">修改密码</a></li>
                        <li role="presentation"><a class="logout-btn btn-default">退出</a></li>
                    </ul>
                </div>
            </c:when>
            <c:otherwise>
                <button class="login-btn btn btn-primary" type="button">登录</button>
                <button class="register-btn btn btn-primary" type="button">注册</button>
            </c:otherwise>
        </c:choose>
    </div>
</header>
<div class="modal" id="login-modal" tabindex="-1" role="dialog" aria-labelledby="loginModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title text-center">登录</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="" role="form">
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="accountInput">账号：</label>
                        <div class="col-sm-10">
                            <input class="form-control" type="text" id="accountInput" placeholder="请输入账号">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="passwordInput">密码：</label>
                        <div class="col-sm-10">
                            <input class="form-control" type="password" id="passwordInput" placeholder="请输入密码">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary login-sub-btn">登录</button>
            </div>
        </div>
    </div>
</div>
<div class="modal" id="register-modal" tabindex="-1" role="dialog" aria-labelledby="loginModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title text-center">注册</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" method="post" role="form">
                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="name">昵称：</label>
                        <div class="col-sm-9">
                            <input class="form-control" type="text" id="name" placeholder="请输入你的昵称">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="passwd">密码：</label>
                        <div class="col-sm-9">
                            <input class="form-control" type="password" id="passwd" placeholder="请输入密码">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="registerConfirmPasswordInput">确认密码：</label>
                        <div class="col-sm-9">
                            <input class="form-control" type="password" id="registerConfirmPasswordInput" placeholder="请输入密码">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary register-sub-btn">注册</button>
            </div>
        </div>
    </div>
</div>
<div class="modal" id="changepasswd-modal" tabindex="-1" role="dialog" aria-labelledby="changepasswdModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title text-center">修改密码</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" method="post" role="form">
                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="pripasswd">原密码：</label>
                        <div class="col-sm-9">
                            <input class="form-control" type="password" id="pripasswd" placeholder="请输入原密码">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="newpasswd">新密码：</label>
                        <div class="col-sm-9">
                            <input class="form-control" type="password" id="newpasswd" placeholder="请输入新密码">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="confirmNewPasswd">确认密码：</label>
                        <div class="col-sm-9">
                            <input class="form-control" type="password" id="confirmNewPasswd" placeholder="请确认新密码">
                            <p class="change-passwd-tips">两次密码不正确</p>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary changepasswd-sub-btn">修改</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="logoutModel">
    <div class="modal-dialog">
        <div class="modal-content message_align">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                <h4 class="modal-title">退出</h4>
            </div>
            <div class="modal-body">
                <p>您确认要退出吗？</p>
            </div>
            <div class="modal-footer">
                <input type="hidden" id="url"/>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <a class="btn btn-success logout-sub-btn" data-dismiss="modal">确定</a>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->