<%--
  Created by IntelliJ IDEA.
  User: Fisheep
  Date: 2020/4/23
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <%@ page language="java" isELIgnored="false" contentType="text/html;charset=UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <script type="text/javascript" src="/homework/static/js/jquery-1.12.4.min.js"></script>
    <link href="/homework/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="/homework/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

    <title>主页</title>
</head>
<body>
    <div>
        <form id="code">
            <input type="text" placeholder="作业暗号或者群组暗号">
        </form>
    </div>
    <div>
        <button type="button" id="btn-code-submit">提交暗号</button>
    </div>
    <br>
    <br>
    <br>
    <div>
        <a href="/homework/toLogin" type="button" class="btn btn-primary">我要发布</a>
<%--        <button type="button" id="btn-work-release" href="/toLogin">我要发布</button>--%>
    </div>
</body>

<script>

    <%--onclick="window.open('${pageContext.request.contextPath}/login.jsp','_blank');"--%>
    <%--$("#btn-work-release").click(function () {--%>
    <%--    console.log("${pageContext.request.contextPath}");--%>
    <%--    $.ajax({--%>
    <%--        url:"/homework/toLogin",--%>
    <%--        type:"GET",--%>
    <%--        // success:function (result) {--%>
    <%--        //     alert(result);--%>
    <%--        // }--%>
    <%--    })--%>
    <%--})--%>
</script>
</html>
