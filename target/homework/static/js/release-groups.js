///////////////////////// 这里是发布作业模态框控制js /////////////////////////////////////
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
///////////////////////// 这里是发布作业模态框控制js /////////////////////////////////////
///////////////////////// 这里是加载groups内容的js  /////////////////////////////////////

///////////////////////// 这里是加载groups内容的js  /////////////////////////////////////


///////////////////////// 这里是加载release内容的js  /////////////////////////////////////
// 1.首先拿到数据，跟据登陆这uid来取下面这些数据：作业id，作业名称，所属组，截止时间，已提交/总人数，操作（删除，修改，名单）
// 2.构造td

/**
 * status 代表要加载的项目：
 *      1：全部加载；
 *      2：加载已截止；
 *      3：加载未截止；
 *      4：加载已交满；
 * @param status
 */
function getHomeworks(status) {
    $.ajax({
        url:"/homework/getHomeworksByUid",
        type:"POST",
        success:function (result) {
            if (result.code == 100){
                // alert("获取成功")
                //1.首先要清空所有元素,将全选那个checkbox置为未选中
                $("#homework_table tbody").empty();
                $(".check-all").prop("checked", false);

                console.log(result);
                var homeworks = result.extend.homeworks;
                $.each(homeworks, function (index, homework) {
                    if((status == 4 && homework.homeworksubmittednums != homework.homeworktotalnums)
                        || (status== 3 && homework.expired == true)
                        || (status == 2 && homework.expired == false)){
                        return true; // 跳出本轮循环，类似于continue，而return false类似break，终止循环。
                    }
                    var singleCheckBox = $("<td><input type='checkbox' class='check-single'></td>");
                    var homeId = $("<td></td>").text(homework.homeworkId);
                    var homeName = $("<td></td>").text(homework.homeworkName.length <= 10?
                        homework.homeworkName:homework.homeworkName.substr(0, 10) + "...");
                    var homeCode = $("<td></td>").text(homework.homeworkCode);
                    var homeDead = $("<td></td>").text(homework.homeworkDead);
                    var submitAndTotal = $("<td></td>").text(homework.homeworksubmittednums+"/"+homework.homeworktotalnums);
                    var homeGroupString = "-";
                    if(homework.groups.length != 0){
                        homeGroupString = "";
                        for(var group of homework.groups){
                            homeGroupString += group.groupName;
                        }
                    }
                    var homeGroup = $("<td></td>").text(homeGroupString);

                    var btnDelete =$("<span></span>").addClass("btn glyphicon glyphicon-remove")
                                .attr("aria-hidden", true).attr("homeworkid", homework.homeworkId);

                        // $("<button type='button'></button>").addClass("btn btn-default btn-group-sm")
                        // .append($("<span></span>").addClass("glyphicon glyphicon-remove").attr("aria-hidden", true))
                        // .attr("type", "button");
                    var btnModify = $("<span></span>").addClass("btn glyphicon glyphicon-pencil").attr("aria-hidden", true);
                    var btnGetList = $("<span></span>").addClass("btn glyphicon glyphicon-list").attr("aria-hidden", true);
                    var btnOperation = $("<td></td>").append(btnDelete).append(btnModify).append(btnGetList);

                    $("<tr></tr>").append(singleCheckBox).append(homeId).append(homeName).append(homeCode)
                        .append(homeGroup)
                        .append(homeDead)
                        .append(submitAndTotal)
                        .append(btnOperation)
                        .appendTo("#homework_table tbody");
                });
            }
            else{
                alert("后端获取失败")
            }
        }
    })
}

//给操作按钮绑定事件；
$(document).on("click",".btn.glyphicon",function () {
    var deleteHomeworkName = $(this).parents("tr").find("td:eq(2)").text();
    var deleteHomeworkId = $(this).attr("homeworkid");
    console.log()
    if(confirm("确认删除作业【"+deleteHomeworkName+"】吗？")){
        $.ajax({
            url:"/homework/homework/"+deleteHomeworkId,
            type:"DELETE",
            success:function (result) {
                if(result.code == 100){
                    alert("删除成功！")
                    getHomeworks(status);
                }else{
                    alert("后端删除失败")
                }
            },
            error:function () {
                alert("前端异常");
            }

        })
    }
});
///////////////////////// 这里是加载release内容的js  /////////////////////////////////////