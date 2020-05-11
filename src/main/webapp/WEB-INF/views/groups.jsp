<%--
  Created by IntelliJ IDEA.
  User: Fisheep
  Date: 2020/5/7
  Time: 17:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ page language="java" isELIgnored="false" contentType="text/html;charset=UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <script type="text/javascript" src="/homework/static/js/jquery-1.12.4.min.js"></script>
    <link href="/homework/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="/homework/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css" rel="stylesheet">

    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdn.bootcss.com/moment.js/2.24.0/moment.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>

    <script src="/homework/static/js/release-groups.js"></script>

    <title>Gruops</title>
</head>
<body>
    <nav class="navbar navbar-default navbar-fixed-top navbar-inverse">
        <div class="container">
            <ul class="nav nav-pills">
                <li role="presentation" ><a href="/homework/toRelease" role="button">Home</a></li>
                <%--            <li role="presentation" class="active"><a href="#">Profile</a></li>--%>
                <li role="presentation" class="active"><a href="/homework/toGroup" role="button">Groups</a></li>
                <li role="presentation" ><a class="btn" role="button" id="btn_homework_release">发布作业</a></li>
            </ul>
        </div>
    </nav>
<%--    发布作业模态框--%>
    <div class="modal fade" id="modal_homework_release" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">发布作业</h4>
                </div>
                <div class="modal-body">
                    <form id="modal-homework-form">
                        <div class="form-group">
                            <label for="homework-name">作业名称</label>
                            <input type="text" name="homeworkName" class="form-control" id="homework-name" placeholder="作业名称">
                        </div>
                        <div class="form-group">
                            <label for="homework-group">所属组</label>
                            <select multiple class="form-control" name="groupsId"  id="homework-group">
                                <%--                            此处动态生成--%>
                                <%--                            <option>1</option>--%>
                                <%--                            <option>2</option>--%>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="homework-total-students">作业人数</label>
                            <input type="text" name="homeworktotalnums" class="form-control" id="homework-total-students" placeholder="作业人数">
                        </div>

                        <div class="form-group">
                            <label>选择结束时间：</label>
                            <!--指定 date标记-->
                            <div class='input-group date' id='datetimepicker2'>
                                <input type='text' name="homeworkDead" class="form-control" id="homeworkDead" />
                                <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                            </div>
                            <%--                        --%>
                            <%--                        <label>选择日期+时间：</label>--%>
                            <%--                        <!--指定 date标记-->--%>
                            <%--                        <div class='input-group date' id='datetimepicker2'>--%>
                            <%--                            <input type='text' name="homeworkDead" class="form-control" />--%>
                            <%--                            <span class="input-group-addon">--%>
                            <%--                                <span class="glyphicon glyphicon-calendar"></span>--%>
                            <%--                            </span>--%>
                            <%--                        </div>--%>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="modal-homework-relese">发布</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
</body>
<script>
    $(function () {
        $('#datetimepicker1').datetimepicker({
            format: "YYYY-MM-DD",
            locale: moment.locale("zh-cn")
        });
        $('#datetimepicker2').datetimepicker({
            format: "YYYY-MM-DD HH:mm",
            locale: moment.locale("zh-cn")
        });
    });

    openHomeworkRelease();
    homeworkSubmit();

</script>
</html>
