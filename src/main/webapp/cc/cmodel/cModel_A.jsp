<%@include file="/WEB-INF/jspf/power/userPower.jspf"%>
<%
        if (!pck.checkUser("Y101_1")) {
            return;
        }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!--后台UI组件Start-->
<!DOCTYPE html>
<html>
    <head>
        <title>通用模板添加</title>
        <script src="${path_home}/lib/jquery/jquery-1.11.1.js" type="text/javascript"></script>
        <%@include file="/WEB-INF/jspf/GG.jspf"%>
        <script type="text/javascript" src="${path_home}/cc/cmodel/js/cModel_A.js"></script>
        <%@include file="/WEB-INF/jspf/artDialog.jspf"%>
    </head>
    <body>
        <table class="table" id="table1">
            <tr>
                <td>模板名</td>
                <td><input type="text" name="cmodel_mc" id="cmodel_mc" /></td>
            </tr>
            <tr>
                <td>模板内容</td>
                <td><textarea id="cmodel_nr"
                              style="width: 800px; height: 500px;"></textarea></td>
            </tr>
            <tr>
                <td colspan="2">
                    <div style="text-align: center">
                        <input type="button" value="提交" id="myMybeanButton" onclick="postCModelFormData('myMybeanButton')">
                    </div>
                </td>
            </tr>
        </table>
    </body>
</html>
