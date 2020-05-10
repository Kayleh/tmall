<%--
  Created by IntelliJ IDEA.
  User: Kayleh
  Date: 2020/5/6
  Time: 19:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>

<nav class="top">
    <a href="${contextPath}">
        <span style="color: #c40000;margin: 0px" class="glyphicon glyphicon-home redColor">
        </span>
        天猫首页
    </a>
    <span>喵,欢迎来到天猫</span>
    <c:if test="${!empty user}">
        <a href="loginPage">${user.name}</a>
        <a href="forelout">退出</a>
    </c:if>

    <c:if test="${empty user}">
        <a href="loginPage">请登录</a>
        <a href="registerPage">免费注册</a>
    </c:if>

    <span class="pull-right">我的订单</span>
    <a href="forecart">
        <span style="color: #c40000;margin: 0px" class=" glyphicon glyphicon-shopping-cart redColor">
        </span>
        购物车<strong>${cartTotalItemNumber}</strong>件
    </a>
</nav>