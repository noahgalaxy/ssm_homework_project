





function createPage(homeworkId) {
    //1.首先创建作业信息
    ajaxGetHomeworks(homeworkId);
    //2.然后动态生成提交列表：姓名和文件名
    ajaxGetSubmits(homeworkId);
}

function ajaxGetHomeworks(homeworkId) {
    $.ajax({
        url:"homework/"+homeworkId,
        type:"GET",
        success:function (result) {
            if (result.code == 100){
                console.log("get");
                createHomework(result);
            }else{
                console.log("后端获取homework失败！")
            }
        },
        error:function () {
            alert("前端提交homeworks异常！")
        }
    })

    function createHomework(result) {
        var singleHomeworkDiv = $("#single-homework-info");
        var h1 = singleHomeworkDiv.find("h1").eq(0);
        var deadLineFooter = singleHomeworkDiv.children("blockquote").find("footer").eq(0);
        var submitNumsFooter = singleHomeworkDiv.find("footer").eq(1);
        var homeworkCodeFooter = singleHomeworkDiv.find("footer").eq(2);
        var groupsFooter = singleHomeworkDiv.find("footer").eq(3);
        h1.text(result.extend.homework.homeworkName);
        deadLineFooter.append(result.extend.homework.homeworkDead);
        submitNumsFooter.append(result.extend.homework.homeworksubmittednums+" / "+result.extend.homework.homeworktotalnums);
        homeworkCodeFooter.append(result.extend.homework.homeworkCode);
        var groupText = "";
        var groupNameList = new Array();
        $.each(result.extend.homework.groups, function (index, group) {
            console.log(group.groupName);
            // groupText += group.groupName+ " | ";
            groupNameList.push(group.groupName);
        });
        groupText = groupNameList.join(" | ");
        groupsFooter.append(groupText);
    }
}
function ajaxGetSubmits(homeworkId) {
    $.ajax({
        url:"submit/"+homeworkId,
        type:"GET",
        success:function (result) {
            if (result.code == 100){
                console.log("ajaxGetSubmits");
                createList(result);
            }else{
                console.log("后端获取submits失败！")
            }
        },
        error:function () {
            alert("前端提交submits异常！")
        }
    });
    function createList(result) {
        // var tbody = $("#div-table-list").children().first().find("tbody").eq(0);
        var table = $("#div-table-list").children().first();
        // 遍历查到的提交的作业，构造表格
        var tbody = $("<tbody></tbody>");
        var length = -1;
        $.each(result.extend.submits, function (index, submit) {
            length += 1;
            console.log(submit);
            var tr = $("<tr></tr>");
            var noTd = $("<td></td>").append(index+1);
            var nameTd = $("<td></td>").append(submit.uploaderName);
            var fileNameTd = $("<td></td>").append(submit.submitFileName);
            tr.append(noTd).append(nameTd).append(fileNameTd);
            tr.appendTo(tbody);
        });
        tbody.appendTo(table);
    }
}
