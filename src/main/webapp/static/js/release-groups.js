///////////////////////// 这里是发布作业模态框控制js /////////////////////////////////////
function openHomeworkModal(release) {
    console.log("dadadadl啊啦啦啦啦啦");
    var clickOpenModalBtn = "#btn_homework_release";
    var homeworkName = "#homework-name";
    var homeworkGroup = "#homework-group";
    var homeworkTotalStudents = "#homework-total-students";
    var homeworkDead = "#homeworkDead";
    var modal = "#modal_homework_release";
    if (release == "modify") {
        clickOpenModalBtn = ".btn.glyphicon.glyphicon-pencil";
        homeworkName = "#homework-modify-name";
        homeworkGroup = "#homework-modify-group";
        homeworkTotalStudents = "#homework-modify-total-students";
        homeworkDead = "#homeworkDead-modify";
        modal = "#modal_homework_modify";
        $("#homework-modify-name").attr("homework-modify-id","")
    }
    $(document).on("click", clickOpenModalBtn, function () {
        //1.首先清空模态框中的表单中的值
        $(homeworkName).val("");
        // $(homeworkGroup).empty();
        $(homeworkTotalStudents).val("");
        $(homeworkDead).val("");
        //建立所属组选择
        // createGroupselect(homeworkGroup);
        //2.再打开模态款
        $(modal).modal({
            backdrop: "static"
        });
        console.log("clickOpenModalBtn：" + clickOpenModalBtn);
        console.log("111点击homeworkGroup：" + homeworkGroup);
        if (release == "release") {
            createGroupselect("#homework-group");
            console.log("发布作业模态框获取option：" + $("#modal_homework_release").find("option:eq(0)").text());
            //如果是修改框，还需要给修改框赋值
        }
        if (release == "modify") {
            createGroupselect("#homework-modify-group");
            var homeworkId = $(this).prev("span").attr("homeworkid");
            console.log("这里这里这里：" + $("#homework-modify-group").find("option:eq(0)").text());
            console.log("homeworkId: " + homeworkId);
            //这个函数里面创建选择
            getHomeworkByHomeId(homeworkId);
            // $(document).ready(function() {
            //
            // });
                // getHomeworkByHomeId(homeworkId);
                // var result = getHomeworkByHomeId(homeworkId);
                // console.log("单个homework："+result);
                // homework = result.extend.homework;
                // console.log("后单个homework："+homework);
            }

    });
}
function createGroupselect(homeworkGroup){
    console.log("createGroupselect__homeworkGroup："+homeworkGroup)
    //1.建立之前需要到数据库查询这个user已经有了的组
    //2.拿到uid，再查询uid的组
    $.ajax({
        url:"/homework/getUidBySession",
        // <%--url:"${pageContext.request.contextPath}/getUidBySession",--%>
        type:"GET",
        async: false,
        success:function (result) {
            // console.log("result:"+result);
            if(result.code == 100){
                getGroupByUid(result.extend.uid, homeworkGroup);
                // getGroupByUid(result.extend.uid, homeworkGroup);
                // var groupsResult = getGroupByUid(4);
                // var groups = groupsResult.extend.groupsMap;
                // //2.用uid查询组
                // console.log(groups);
            }
        }
    })
}
// function createGroupselect(){
//     console.log("createGroupselect__homeworkGroup：")
//     //1.建立之前需要到数据库查询这个user已经有了的组
//     //2.拿到uid，再查询uid的组
//     $.ajax({
//         url:"/homework/getUidBySession",
//         // <%--url:"${pageContext.request.contextPath}/getUidBySession",--%>
//         type:"GET",
//         success:function (result) {
//             // console.log("result:"+result);
//             if(result.code == 100){
//                 getGroupByUid(result.extend.uid, "#homework-modify-group");
//                 getGroupByUid(result.extend.uid, "#homework-group");
//                 // var groupsResult = getGroupByUid(4);
//                 // var groups = groupsResult.extend.groupsMap;
//                 // //2.用uid查询组
//                 // console.log(groups);
//             }
//         }
//     })
// }

//用uid查询组；
//这里的ajax需要异步加载，不然后面的定义不
function getGroupByUid(uid, homeworkGroup) {
    //清楚样式
    // $("#homework-group").removeAttr("disabled");
    $(homeworkGroup).empty();
    console.log("getGroupByUid__homeworkGroup："+homeworkGroup)

    $.ajax({
        url:"/homework/getGroupByUid/"+uid,
        type: "GET",
        async: false,
        success:function (result) {
            if(result.code == 100){
                var groupsList = result.extend.groupsList;
                // var options;
                $.each(groupsList, function (index, item) {
                    console.log(item.groupId);
                    console.log(item.groupName);
                    //这里options选择框，需要把value放上去，不然提交表单的时候没有值
                    $(homeworkGroup).append($("<option></option>").append(item.groupName).attr("value", item.groupId));
                })
                // options.appendTo($("#homework-group"));
            }
            // else{
            //     $("#homework-group").attr("disabled");
            // }
        }
    })
}

// 发布和修改作业模态框提交
function homeworkSubmit(release){
    var modalSubmitButtonClick = "#modal-homework-relese-submit";
    var modalSubmitForm = "#modal-homework-form";
    var whichSelect = "#homework-group";
    var url = "/homework/homeworkRelease";
    var method = "GET";
    var successMessage = "发布成功";
    var failMessage = "发布失败，请重试";
    var modal = "#modal_homework_release";

    if(release == "modify"){
        modalSubmitButtonClick = "#btn-homework-modify-submit";
        modalSubmitForm = "#modal-homework-modify-form";
        whichSelect = "#homework-modify-group";
        url = "/homework/homework";
        method = "PUT";
        successMessage = "修改成功";
        failMessage = "修改失败，请重试";
        modal = "#modal_homework_modify";
    }
    $(document).on("click",modalSubmitButtonClick, function () {
        var groupsIdString = getSelectedGroup(whichSelect);
        var data = $(modalSubmitForm).serialize()+"&groupsIdString="+groupsIdString;
        if(release == "modify"){
            //首先将拿到作业id这个属性，拼接到表单内数据里面
            data+="&homeworkId="+$("#homework-modify-name").attr("homework-modify-id");
            //然后将这个属性置空
            $("#homework-modify-name").attr("homework-modify-id","")
        }
        console.log("序列化表单：\n"+$("#modal-homework-form").serialize());
        $.ajax({
            url:url,
            type:method,
            // data:"homeworkName="+15135+"&homeworktotalnums="+45,
            // data:"homeworkName=测试uoy&groupsId=6&homeworktotalnums=66",
            // data:"homeworkName=测试uoy&groupsId=6&homeworktotalnums=66",
            data:data,
            success:function (result) {
                if(result.code == 100){
                    //1.显示成功消息；
                    alert(successMessage);
                    //2.关闭模态框
                    $(modal).modal('hide');
                    //3.刷新作业页面；
                    getHomeworks(1);
                }else{
                    //1.显示失败消息
                    alert(failMessage)
                }
            },
            error:function () {
                alert("前端提交异常！")
            }
        })
    });

}
/**
 * 输入selectd的元素选择器
 *提交之前，获取发布作业框的所选组,
 */
function getSelectedGroup(whichSelect){
    //对于multiple的select元素，返回数组;
    var gr1 = $(whichSelect).val();
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
///////////////////////// 这里是修改作业模态框控制js /////////////////////////////////////

function getHomeworkByHomeId(homeworkId) {
    //首先需要将所有选中状态置为未选中
    $("#homework-modify-group").find("option").prop("selected", false);
    $.ajax({
        url:"/homework/homework/"+homeworkId,
        type:"GET",
        success:function (result) {
            if(result.code == 100){
                $("#homework-modify-name").attr("homework-modify-id",result.extend.homework.homeworkId);
                $("#homework-modify-name").val(result.extend.homework.homeworkName);
                $("#homework-modify-total-students").val(result.extend.homework.homeworktotalnums);
                $("#homeworkDead-modify").val(result.extend.homework.homeworkDead);
                if(result.extend.homework.groups.length != 0){
                    console.log("进入被选中")
                    // console.log("被选中的："+$("#homework-modify-group").find("option[value='6']").text());
                    // $("#homework-modify-group").find("option[value=6]").prop("selected","selected");
                    for(group of result.extend.homework.groups){
                        var groupId = group.groupId;
                        console.log("被选中的："+groupId+"\n文本："+$("#homework-modify-group").find("option[value='"+groupId+"']").text());
                        $("#homework-modify-group").find("option[value='"+groupId+"']").prop("selected", "selected");
                    }
                    //     var groupId = group.groupId;
                    //     console.log("啊Jake四的groupId: "+groupId);
                    //    // console.log( "里面: "+$("#homework-modify-group").find("option[value='"+groupId+"']").text());
                    //    console.log( "里面: "+$("#homework-modify-group option[value=6]").text());
                    // }
                    // console.log("被选中的："+$("#homework-modify-group").find('option:selected').text());
                }
                // return result;
            }else {
                console.log("获取失败");
                // return null;
            }
        }
    })
}

/**
 * 给更改作业按钮绑定打开事件
 * 这个func目前没有用
 */
function openHomeworkModify(){
    $(document).on("click",".btn.glyphicon.glyphicon-pencil",function () {
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
///////////////////////// 这里是修改作业模态框控制js /////////////////////////////////////
///////////////////////// 这里是加载groups内容的js  /////////////////////////////////////

///////////////////////// 这里是加载groups内容的js  /////////////////////////////////////


///////////////////////// 这里是加载release内容的js  /////////////////////////////////////
// 1.首先拿到数据，跟据登陆这uid来取下面这些数据：作业id，作业名称，所属组，截止时间，已提交/总人数，操作（删除，修改，名单）
// 2.构造td

/**
 * 创建作业目录
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
                    var homeName = $("<td></td>").addClass("clickkk").text(homework.homeworkName.length <= 10?
                        homework.homeworkName:homework.homeworkName.substr(0, 10) + "...");
                    var homeCode = $("<td></td>").text(homework.homeworkCode);
                    var homeDead = $("<td></td>").text(homework.homeworkDead);
                    var submitAndTotal = $("<td></td>").text(homework.homeworksubmittednums+"/"+homework.homeworktotalnums);
                    var homeGroupString = "-";
                    var homeGroupIdString = "-";
                    if(homework.groups.length != 0){
                        homeGroupIdString = "";
                        homeGroupString = "";
                        for(var group of homework.groups){
                            homeGroupString += group.groupName+" ";
                            homeGroupIdString += group.groupId;
                        }
                    }
                    var homeGroup = $("<td></td>").text(homeGroupString.length>10?
                                                        homeGroupString.substr(0, 10) + "...":homeGroupString);

                    var btnDelete =$("<span></span>").addClass("btn glyphicon glyphicon-remove")
                                .attr("aria-hidden", true).attr("homeworkid", homework.homeworkId);
                                // .attr("hah", "glyphicon-remove11");

                        // $("<button type='button'></button>").addClass("btn btn-default btn-group-sm")
                        // .append($("<span></span>").addClass("glyphicon glyphicon-remove").attr("aria-hidden", true))
                        // .attr("type", "button");
                    var btnModify = $("<span></span>").addClass("btn glyphicon glyphicon-pencil").attr("aria-hidden", true);
                    var btnGetList = $("<span></span>").addClass("btn glyphicon glyphicon-th-large").attr("aria-hidden", true);
                    var btnOperation = $("<td></td>").append(btnDelete).append(btnModify).append(btnGetList);

                    $("<tr></tr>").append(singleCheckBox).append(homeId).append(homeName).append(homeCode)
                        .append(homeGroup)
                        .append(homeDead)
                        .append(submitAndTotal)
                        .append(btnOperation)
                        .appendTo("#homework_table tbody");
                });
                //此外还需要构建模态框的组，select里面的组option
                // createGroupselect("#homework-modify-group");
                // createGroupselect("#homework-group");
                // createGroupselect()
            }
            else{
                alert("后端获取失败")
            }
        }
    })
}


//给操作按钮绑定事件；
$(document).on("click",".glyphicon-remove",function () {
    var deleteHomeworkName = $(this).parents("tr").find("td:eq(2)").text();
    var deleteHomeworkId = $(this).attr("homeworkid");
    console.log();
    if(confirm("确认删除作业【"+deleteHomeworkName+"】吗？")){
        deleteHomeworkByIds(deleteHomeworkId)
    }
});

/**
 * 删除作业
 * @param deleteHomeworkId：单个id（4）或者由-连接的id字符串（4-6-100）
 */
function deleteHomeworkByIds(deleteHomeworkId) {
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
///////////////////////// 这里是加载release内容的js  /////////////////////////////////////

/**
 * 给作业名称td表格绑定点击事件
 */
$(document).on("click","td[class='clickkk']", function () {
    var homeworkId = $(this).parent().find("td:eq(1)").text();
    console.log("homeworkId: "+homeworkId);
    // $.ajax({
    //     url:"/homework/singlehomework/"+homeworkId,
    //     type:"GET"
    // })
    window.location.href = "/homework/singlehomework/"+homeworkId;
});
    // let homeworkId = $(this).parent().find("td:eq(1)").text();

    // window.location.href = "single-homework.html?name="+homeworkId;
    // window.open("/WEB-INF/views/single-homework.html")
    // $.ajax({
    //     url:"/homework/toGroup",
    //     type:"GET"
    // })


function tdclick() {
    let homeworkId = $(this).parent().find("td:eq(1)").text();
    console.log("点击td："+homeworkId);
}