<%--
  Created by IntelliJ IDEA.
  User: chiling
  Date: 2019/6/7
  Time: 21:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<footer>Copyright © 406小队 All Rights Reserved</footer>
<a class="back-top-menu"><span class="glyphicon glyphicon-menu-up back-top-icon"></span></a>
<div class="read-mode-box" >
    <input type="checkbox" class="read-mode-btn" checked data-toggle="toggle" data-on="白天" data-off="夜晚" >
</div>
<script src="js/jquery-3.4.1.min.js"></script>
<script src="css/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<script src="css/bootstrap-toggle.min.js"></script>
<script>
    $(document).ready(function() {
        $(window).scroll(function () {

            if ($(window).scrollTop() > '666') {
                $('.back-top-menu').css({'display': 'block'})
            } else if ($(window).scrollTop() <= '666') {
                $('.back-top-menu').css({'display': 'none'})
            }
        })
        $('.back-top-menu').click(function () {
            $('html,body').animate({ scrollTop: 0 }, 500)
        })
        // 弹出登录对话框
        $('.login-btn').click(function () {
            $('#login-modal').modal('toggle')
        })
        // 弹出注册对话框
        $('.register-btn').click(function () {
            $('#register-modal').modal('toggle')
        })
        $('.change-passwd-btn').click(function () {
            $('#changepasswd-modal').modal('toggle')
        })
        $('.logout-btn').click(function () {
            $('#logoutModel').modal("toggle")
        })
        $('.register-sub-btn').click(function () {
            // var name   = '123';
            // var passwd = '123';
            // var phone = '123';
            var data = {
                name: $("#name")[0].value,
                passwd: $("#passwd")[0].value
                // phone: phone
            }
            $.ajax({
                type: "POST",
                url: '/news/register',
                data: JSON.stringify(data),
                dataType: "json",
                contentType: 'application/json',
                success: function (data) {
                    if (data.status === 200) {
                        // console.log(111)
                        location.reload();
                    }
                }
            })

        })
        $('.login-sub-btn').click(function () {
            var data = {
                name: $("#accountInput")[0].value,
                passwd: $("#passwordInput")[0].value
            }
            $.ajax({
                type: "POST",
                url: '/news/login',
                data: JSON.stringify(data),
                dataType: "json",
                contentType: 'application/json',
                success: function (data) {
                    if (data.status === 200) {
                        location.reload();
                    }
                }
            })

        })

        $('.logout-sub-btn').click(function () {
            $.ajax({
                url: "/news/logout",
                success: function (data) {
                    if (data.status === 200) {
                        location.reload();
                    }
                }
            })
        })

        $('.changepasswd-sub-btn').click(function () {
            var pripasswd = $('#pripasswd')[0].value
            var newpasswd = $('#newpasswd')[0].value
            var confirmPasswd = $('#confirmNewPasswd')[0].value
            if (pripasswd === "") {
                $('.change-passwd-tips').text("愿密码不可以为空")
                $('.change-passwd-tips').css({"visibility": "visible"})
                return
            } else if (newpasswd !== confirmPasswd) {
                $('.change-passwd-tips').css({"visibility": "visible"})
                $('.change-passwd-tips').text("两次输入的密码不正确")
                return
            } else {
                $('.change-passwd-tips').css({"visibility": "hidden"})
            }
            var data = {
                "newpasswd": newpasswd,
                "pripasswd": pripasswd
            }
            $.ajax({
                url: "/news/updatepasswd",
                type: "PUT",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(data),
                success: function (data) {
                    $('#changepasswd-modal').modal('toggle')
                    if(data.status === 200) {
                        alert("修改成功")
                    }else {
                        alert("修改失败")
                    }
                }
            })
        })
    })
    $('.read-mode-btn').change(function() {
        var isDay = $(this).prop("checked")
        $('body').toggleClass('night-body')
        $('header').toggleClass('night-header')
        $('.header-title-nav>nav>ul>li>a').each(function () {
            $(this).toggleClass('night-header-nav-a')
        })
        $('.container .info').toggleClass('night-info')
        $('footer').toggleClass('night-footer')
        $('.news-item-content-title').toggleClass('night-news-item-content-title')
        $('.news-list li').toggleClass('night-li')
    })

</script>
