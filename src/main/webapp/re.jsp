<%--
  Created by IntelliJ IDEA.
  User: Fisheep
  Date: 2020/4/24
  Time: 14:49
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

    <title>发布页面</title>
</head>
<body>

<h1>作业发布</h1>
<%--作业发布模态框--%>
<div class="modal fade" id="modal_homework_release" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">发布作业</h4>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label for="homework-name">作业名称</label>
                        <input type="text" class="form-control" id="homework-name" placeholder="作业名称">
                    </div>
                    <div class="form-group">
                        <label for="homework-group">所属组</label>
                        <select class="form-control" id="homework-group">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                            <option>5</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="homework-total-students">作业人数</label>
                        <input type="text" class="form-control" id="homework-total-students" placeholder="作业人数">
                    </div>

                    <div class="form-group">
                        <label>选择日期+时间：</label>
                        <!--指定 date标记-->
                        <div class='input-group date' id='datetimepicker2'>
                            <input type='text' class="form-control" />
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary">发布</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<%--作业列表--%>
<div class="container">
    <div class="row">
        <div class="col-md-4">
            <button type="button" class="btn btn-primary">Group</button>
            <button type="button" id="btn_homework_release" class="btn btn-success">发布作业</button>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <table class="table table-hover" id="homework_table">
                <thead>
                    <tr >
                        <th>
                            <input type="checkbox" class="check-all">
                        </th>
                        <th>id</th>
                        <th>作业名称</th>
                        <th>Group</th>
                        <th>截止时间</th>
                        <th>已提交/总人数</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                <tr>
                    <td>
                        <input type="checkbox" class="check-all">
                    </td>
                    <td>2</td>
                    <td>数据分析</td>
                    <td>三班sadfasfas啊手动阀</td>
                    <td>2020-10-23</td>
                    <td>34/103</td>
                    <td>
                        <button type="button" class="btn btn-danger">删除</button>
                        <button type="button" class="btn btn-primary">修改</button>
                        <button type="button" class="btn btn-info">获取名单</button>
                    </td>
                </tr>                <tr>
                    <td>
                        <input type="checkbox" class="check-all">
                    </td>
                    <td>2</td>
                    <td>数据分岁的法国</td>
                    <td>三班</td>
                    <td>2020-10-23</td>
                    <td>34/103</td>
                    <td>
                        <button type="button" class="btn btn-danger">删除</button>
                        <button type="button" class="btn btn-primary">修改</button>
                        <button type="button" class="btn btn-info">获取名单</button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
<script>
    $(function () {
        $('#datetimepicker1').datetimepicker({
            format: "YYYY-MM-DD",
            locale: moment.locale("zh-cn")
        });
        $('#datetimepicker2').datetimepicker({
            format: "YYYY-MM-DD hh:mm",
            locale: moment.locale("zh-cn")
        });
    })

    //用uid查询组；
    function getGroupByUid(uid) {
        $.ajax({
            url:"${pageContext.request.contextPath}/getGroupByUid/"+uid,
            type: "GET",
            success:function (result) {
                if(result.code == 100){

                }else return false;
            }
        })
    }
    function createGroupselect(){
        //1.建立之前需要到数据库查询这个user已经有了的组
        //2.拿到uid，再查询uid的组
        $.ajax({
            url:"/homework/getUidBySession",
            <%--url:"${pageContext.request.contextPath}/getUidBySession",--%>
            type:"GET",
            success:function (result) {
                console.log("result:"+result);
                if(result.code == 100){
                    var uid = result.extend.uid;
                    //2.用uid查询组

                }
            }
        })
    }

    $(document).on("click","#btn_homework_release",function () {
        $('#modal_homework_release').modal({
            backdrop:"static"
        });
        //建立所属组选择
        createGroupselect();
    })
</script>

</html>
