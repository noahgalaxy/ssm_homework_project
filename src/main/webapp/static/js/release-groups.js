
function openHomeworkRelease(){
    $(document).on("click","#btn_homework_release",function () {
        //1.首先清空模态框中的表单中的值
        $("#homework-name").val("");
        $("#homework-group").empty();
        $("#homework-total-students").val("");
        $("#homeworkDead").val("");
        //2.再打开模态款
        $('#modal_homework_release').modal({
            backdrop:"static"
        });
        //建立所属组选择
        createGroupselect();
    });
}
function createGroupselect(){
    //1.建立之前需要到数据库查询这个user已经有了的组
    //2.拿到uid，再查询uid的组
    $.ajax({
        url:"/homework/getUidBySession",
        // <%--url:"${pageContext.request.contextPath}/getUidBySession",--%>
        type:"GET",
        success:function (result) {
            // console.log("result:"+result);
            if(result.code == 100){
                getGroupByUid(result.extend.uid);
                // var groupsResult = getGroupByUid(4);
                // var groups = groupsResult.extend.groupsMap;
                // //2.用uid查询组
                // console.log(groups);
            }
        }
    })
}

//用uid查询组；
function getGroupByUid(uid) {
    //清楚样式
    // $("#homework-group").removeAttr("disabled");
    $("#homework-group").empty();
    $.ajax({
        url:"/homework/getGroupByUid/"+uid,
        type: "GET",
        success:function (result) {
            if(result.code == 100){
                var groupsList = result.extend.groupsList;
                // var options;
                $.each(groupsList, function (index, item) {
                    console.log(item.groupId);
                    console.log(item.groupName);
                    //这里options选择框，需要把value放上去，不然提交表单的时候没有值
                    $("#homework-group").append($("<option></option>").append(item.groupName).attr("value", item.groupId));
                })
                // options.appendTo($("#homework-group"));
            }
            // else{
            //     $("#homework-group").attr("disabled");
            // }
        }
    })
}

// 发布作业模态框提交
function homeworkSubmit(){
    $("#modal-homework-relese").click(function () {
        var groupsIdString = getSelectedGroup();
        console.log("序列化表单：\n"+$("#modal-homework-form").serialize());
        $.ajax({
            url:"/homework/homeworkRelease",
            type:"POST",
            // data:"homeworkName="+15135+"&homeworktotalnums="+45,
            // data:"homeworkName=测试uoy&groupsId=6&homeworktotalnums=66",
            // data:"homeworkName=测试uoy&groupsId=6&homeworktotalnums=66",
            data:$("#modal-homework-form").serialize()+"&groupsIdString="+groupsIdString,
            success:function (result) {
                if(result.code == 100){
                    //1.显示发布成功；
                    alert("发布成功")
                    //2.刷新作业页面；
                }else{
                    //1.显示发布异常
                    alert("发布失败，请重试")
                }
            },
            error:function () {
                alert("前端提交异常！")
            }
        })
    });
}
/**
 *提交之前，获取发布作业框的所选组,
 */
function getSelectedGroup(){
    //对于multiple的select元素，返回数组;
    var gr1 = $("#homework-group ").val();
    // var gr2 = $("#homework-group option:selected").val();
    console.log("gr1:"+gr1);
    console.log("a比对"+gr1.length);
    //返回拼接的字符串
    return gr1.length > 0?gr1.join("-"):"-";
    // console.log("gr2:"+gr2);
    //遍历
    // for(gr in gr1){
    //     console.log(gr+"啦啦啦啦");
    // }
}