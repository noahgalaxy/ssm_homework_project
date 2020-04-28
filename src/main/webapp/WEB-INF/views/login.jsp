<%--
  Created by IntelliJ IDEA.
  User: Fisheep
  Date: 2020/4/23
  Time: 15:24
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

    <title>登陆注册</title>
</head>
<body>
<%--    登陆模态框--%>
    <div class="modal fade" id="modal-sign-up" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title"></h4>
                </div>
                <div class="modal-body">
                    <form id="form-login">
                        <div class="form-group" id="div-email">
                            <label for="exampleInputEmail1">邮箱</label>
                            <input type="email" name="email" class="form-control" id="exampleInputEmail1" placeholder="Email">
                            <span id="email-span" class="help-block"></span>
                        </div>
                        <div class="form-group" id="div-passWord">
                            <label for="exampleInputPassword1">密码</label>
                            <input type="password" name="passWord" class="form-control" id="exampleInputPassword1" placeholder="Password">
                            <span id="passWord-span" class="help-block"></span>
                        </div>
                    </form>
                </div>
                <div class="modal-footer" id="div-close-sure">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="btn-modal-sure" sign="sign-in"></button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <div>
        <button type="button" class="btn btn-default" id="btn-sign-up">登陆</button>
    </div>
    <div>
        <button type="button" class="btn btn-default" id="btn-sign-in">注册</button>
    </div>

</body>
<script>

    function openModal(signUp){
        text = "登陆";
        signAttr = "sign-up";
        if(!signUp){
            text = "注册";
            signAttr = "sign-in";
        }
        // 清空样式
        $("#div-passWord").removeClass("has-error");
        $("#passWord-span").text("");
        $("#email-span").text("");
        $("#div-email").removeClass("has-error");
        $("#div-email").removeClass("has-success");
        //清空表单内容
        $("#modal-sign-up form")[0].reset();
        $("#btn-modal-sure").text(text);
        $(".modal-title").text(text);
        $(".modal-title").empty();
        // $("#btn-modal-sure").append(text);
        $("#btn-modal-sure").attr("sign", signAttr);
        // $(".modal-title").append(text);
        $("#modal-sign-up").modal({
            backdrop:'static'
        });
    }

    $("#div-passWord").change(function () {
        //是登陆框才处理
        if($("#btn-modal-sure").attr("sign") == "sign-up"){
            $("#div-passWord").removeClass("has-error");
            $("#passWord-span").text("");
        }
    })

    $("#div-email").change(function () {
        //如果是登陆框的话就返回
        if($("#btn-modal-sure").attr("sign") == "sign-up"){
            return;
        }
        $("#email-span").text("");
        $("#div-email").removeClass("has-error");
        $("#div-email").removeClass("has-success");
        var email = $("#exampleInputEmail1").val();
        console.log("准备接受校验的email："+email);
        var flag = true;
        var emailReg = /^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/;
        if(!emailReg.test(email)){
           $("#email-span").text("邮箱格式不正确");
           $("#div-email").addClass("has-error");
           flag = false;
        }
        if(flag){
            console.log("前端提交的email："+email);
            $.ajax({
                url:"/homework/checkEmail",
                type:"POST",
                data:"email="+email,
                success:function (result) {
                    if (result.code == 100){
                        // alert("有效email");
                        $("#div-email").addClass("has-success");

                    }else{
                        // alert("无效email");
                        $("#email-span").text("邮箱重复");
                        $("#div-email").addClass("has-error");
                    }
                },
                    error:function () {
                        alert("系统错误")
                    }
                })
            }

        });

    $("#btn-sign-up").click(function () {
        openModal(true);

    })

    $("#btn-sign-in").click(function () {
        openModal(false);
    })

    $("#btn-modal-sure").click(function () {
        //如果是登陆
        if($("#btn-modal-sure").attr("sign") == "sign-up"){
            $.ajax({
                url:"/homework/userSignUp",
                type: "POST",
                data:$("#form-login").serialize(),
                success:function (result) {
                    if(result.code == 100) {
                        alert("登陆成功！");
                        //转到处理toRelease这个controller，然后这个controller返回对应jsp;
                        window.location.href = "/homework/toRelease";
                        //如果直接打开的话，可以用window.open(*.jsp);
                    }else{
                        $("#div-passWord").addClass("has-error");
                        $("#passWord-span").text("账号或者密码错误！");
                        // alert("账号或者密码错误！");
                    }
                },
                error:function () {
                    alert("系统错误")
                }
            })
        }else if($("#btn-modal-sure").attr("sign") == "sign-in"){
            $.ajax({
                url:"/homework/userSignIn",
                type: "POST",
                data:$("#form-login").serialize(),
                success:function (result) {
                    if(result.code == 100) {
                        alert("注册成功！");
                    }else{
                        alert("注册失败，账号重复");
                    }
                },
                error:function () {
                    alert("系统错误，请重试")
                }
            })
        }


    });
</script>
</html>
