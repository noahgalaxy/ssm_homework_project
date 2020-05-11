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
<script src="/homework/static/js/release-groups.js"></script>
<%--<h1>作业发布</h1>--%>
<%--<ul class="nav nav-pills navbar-inverse">--%>

<%--</ul>--%>
<%--navbar-inverse--%>
<nav class="navbar navbar-default navbar-fixed-top navbar-inverse">
    <div class="container">
        <ul class="nav nav-pills">
            <li role="presentation" class="active"><a href="/homework/toRelease" role="button">Home</a></li>
            <%--            <li role="presentation" class="active"><a href="#">Profile</a></li>--%>
            <li role="presentation" ><a href="/homework/toGroup" role="button">Groups</a></li>
            <li role="presentation" ><a class="btn" role="button" id="btn_homework_release">发布作业</a></li>
        </ul>
    </div>
</nav>
<br>
<br>
<br>
<br>
<%--<nav class="navbar navbar-default navbar-fixed-bottom ">--%>
<%--    <div class="container">--%>

<%--    </div>--%>
<%--</nav>--%>
<%--作业发布模态框--%>
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

<%--作业列表--%>
<div class="container">
    <div class="row">
        <%--        <div class="col-md-4">--%>
        <%--            <button type="button" class="btn btn-primary">Group</button>--%>
        <%--            <button type="button" id="btn_homework_release" class="btn btn-success">发布作业</button>--%>
        <%--        </div>--%>
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
            format: "YYYY-MM-DD HH:mm",
            locale: moment.locale("zh-cn")
        });
    });

    <%--//用uid查询组；--%>
    <%--function getGroupByUid(uid) {--%>
    <%--    //清楚样式--%>
    <%--    // $("#homework-group").removeAttr("disabled");--%>
    <%--    $("#homework-group").empty();--%>
    <%--    $.ajax({--%>
    <%--        url:"${pageContext.request.contextPath}/getGroupByUid/"+uid,--%>
    <%--        type: "GET",--%>
    <%--        success:function (result) {--%>
    <%--            if(result.code == 100){--%>
    <%--                var groupsList = result.extend.groupsList;--%>
    <%--                // var options;--%>
    <%--                $.each(groupsList, function (index, item) {--%>
    <%--                    console.log(item.groupId);--%>
    <%--                    console.log(item.groupName);--%>
    <%--                    //这里options选择框，需要把value放上去，不然提交表单的时候没有值--%>
    <%--                    $("#homework-group").append($("<option></option>").append(item.groupName).attr("value", item.groupId));--%>
    <%--                })--%>
    <%--                // options.appendTo($("#homework-group"));--%>
    <%--            }--%>
    <%--            // else{--%>
    <%--            //     $("#homework-group").attr("disabled");--%>
    <%--            // }--%>
    <%--        }--%>
    <%--    })--%>
    <%--}--%>
    <%--function createGroupselect(){--%>
    <%--    //1.建立之前需要到数据库查询这个user已经有了的组--%>
    <%--    //2.拿到uid，再查询uid的组--%>
    <%--    $.ajax({--%>
    <%--        url:"/homework/getUidBySession",--%>
    <%--        &lt;%&ndash;url:"${pageContext.request.contextPath}/getUidBySession",&ndash;%&gt;--%>
    <%--        type:"GET",--%>
    <%--        success:function (result) {--%>
    <%--            // console.log("result:"+result);--%>
    <%--            if(result.code == 100){--%>
    <%--                getGroupByUid(4);--%>
    <%--                // var groupsResult = getGroupByUid(4);--%>
    <%--                // var groups = groupsResult.extend.groupsMap;--%>
    <%--                // //2.用uid查询组--%>
    <%--                // console.log(groups);--%>
    <%--            }--%>
    <%--        }--%>
    <%--    })--%>
    <%--}--%>
    openHomeworkRelease();
    homeworkSubmit();

    // $(document).on("click","#btn_homework_release",function () {
    //     //1.首先清空模态框中的表单中的值
    //     $("#homework-name").val("");
    //     $("#homework-group").empty();
    //     $("#homework-total-students").val("");
    //     $("#homeworkDead").val("");
    //     //2.再打开模态款
    //     $('#modal_homework_release').modal({
    //         backdrop:"static"
    //     });
    //     //建立所属组选择
    //     createGroupselect();
    // });
    //
    // /**
    //  *获取发布作业框的所选组,并发一个ajax
    //  */
    // function getSelectedGroup(){
    //     //对于multiple的select元素，返回数组;
    //     var gr1 = $("#homework-group ").val();
    //     // var gr2 = $("#homework-group option:selected").val();
    //     console.log("gr1:"+gr1);
    //     console.log("a比对"+gr1.length);
    //     //返回拼接的字符串
    //     return gr1.length > 0?gr1.join("-"):"-";
    //     // console.log("gr2:"+gr2);
    //     //遍历
    //     // for(gr in gr1){
    //     //     console.log(gr+"啦啦啦啦");
    //     // }
    // }
    //
    // // 发布作业模态框提交
    // $("#modal-homework-relese").click(function () {
    //     var groupsIdString = getSelectedGroup();
    //     console.log("序列化表单：\n"+$("#modal-homework-form").serialize());
    //     $.ajax({
    //         url:"/homework/homeworkRelease",
    //         type:"POST",
    //         // data:"homeworkName="+15135+"&homeworktotalnums="+45,
    //         // data:"homeworkName=测试uoy&groupsId=6&homeworktotalnums=66",
    //         // data:"homeworkName=测试uoy&groupsId=6&homeworktotalnums=66",
    //         data:$("#modal-homework-form").serialize()+"&groupsIdString="+groupsIdString,
    //         success:function (result) {
    //             if(result.code == 100){
    //                 //1.显示发布成功；
    //                 alert("发布成功")
    //                 //2.刷新作业页面；
    //             }else{
    //                 //1.显示发布异常
    //                 alert("发布失败，请重试")
    //             }
    //         },
    //         error:function () {
    //             alert("前端提交异常！")
    //         }
    //     })
    // })
</script>

</html>
