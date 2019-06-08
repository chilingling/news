<%--
  Created by IntelliJ IDEA.
  User: chiling
  Date: 2019/6/7
  Time: 21:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<footer>Copyright © 406小队 All Rights Reserved</footer>
<a class="back-top-menu"><span class="glyphicon glyphicon-menu-up"></span></a>
<script src="js/jquery-3.4.1.min.js"></script>
<script src="css/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
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

        $('.logout-btn').click(function () {
            $.ajax({
                url: "/news/logout",
                success: function (data) {
                    if (data.status === 200) {
                        location.reload();
                    }
                }
            })
        })
    })
</script>
