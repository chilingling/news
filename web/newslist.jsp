<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: chiling
  Date: 2019/6/7
  Time: 21:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container main">
    <ul class="news-list">
        <c:forEach items="${data.content}" var="item">
            <li>
                <div class="news-item-block">
                    <a href="/news/detailnews?id=${item.getId()}" class="news-item-block-img"><img src="${item.getImg()}" alt="${item.getTitle()}"></a>
                    <div class="news-item-content-block">
                        <a href="/news/detailnews?id=${item.getId()}" class="news-item-content-title"><h4>${item.getTitle()}</h4></a>
                        <p class="news-item-content-info"><span>${item.getSource()}</span><span class="comment-count glyphicon glyphicon-comment glyphicon glyphicon-comment">${item.getCommentnum()}</span></p>
                    </div>
                </div>
            </li>
        </c:forEach>
    </ul>
    <div class="container text-center">
        <%--For displaying Page numbers.
        The when condition does not display a link for the current page--%>
        <ul class="pagination text-center">
            <%-- 上一页 --%>
            <c:if test="${data.currentPage != 1}">
                <li class="page-item"><a class="page-link" href="${preLink}page=${data.currentPage - 1}">上一页</a></li>
            </c:if>
            <c:forEach begin="1" end="${data.totalpages}" var="i">
                <c:choose>
                    <c:when test="${data.currentPage eq i}">
                        <li class="page-item active"><a class="page-link">${i}</a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item"><a class="page-link" href="${preLink}page=${i}">${i}</a></li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <%--下一页 --%>
            <c:if test="${data.currentPage lt data.totalpages}">
                <li class="page-item"><a class="page-link" href="${preLink}page=${data.currentPage + 1}">下一页</a></li>
            </c:if>
        </ul>
    </div>
</div>
