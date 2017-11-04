<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/jstllibs.jsp" %>
<html>
<head><title>上传背景图片</title>
    <script>
        function check(s) {
            var platform = document.getElementById("sel_paltform_"+s).value;

            if (platform == null || platform.length == 0) {
                alert('请选择平台')
                return false;
            }

            return true;
        }
    </script>
</head>
<body>
上传背景图片
<form action="${ctx}/batch/image/uploadbackground.do" method="post" enctype="multipart/form-data"onsubmit="check('background');">
    <table>
        <tr>
            <td>上传文件：</td>
            <td><input type="file" name="img"/></td>
            <td></td>
        </tr>
        <tr>
            <td>选择平台：</td>
            <td>
                <select name="platform" id="sel_paltform_backgournd">
                    <option value="">请选择</option>
                    <option value="0">IOS</option>
                    <option value="1">安卓</option>
                </select>
            </td>
            <td></td>
        </tr>
        <tr>
            <td colspane="3"><input type="submit" value="提交"/></td>
        </tr>
    </table>
</form>
<br/><br/>
<hr/>
上传ICON
<form action="${ctx}/batch/image/uploadicon.do" method="post" enctype="multipart/form-data"onsubmit="check('icon');">
    <table>
        <tr>
            <td>上传文件：</td>
            <td><input type="file" name="img"/></td>
            <td></td>
        </tr>
        <tr>
            <td>选择平台：</td>
            <td>
                <select name="platform" id="sel_paltform_icon">
                    <option value="">请选择</option>
                    <option value="0">IOS</option>
                    <option value="1">安卓</option>
                </select>
            </td>
            <td></td>
        </tr>
        <tr>
            <td colspane="3"><input type="submit" value="提交"/></td>
        </tr>
    </table>
</form>
<br/><br/>
<hr/>
上传loading页面
<form action="${ctx}/batch/image/uploadloading.do" method="post" enctype="multipart/form-data" id="myfrom" onsubmit="check('loading');">
    <table>
        <tr>
            <td>上传文件：</td>
            <td><input type="file" name="img"/></td>
            <td></td>
        </tr>
        <tr>
            <td>选择平台：</td>
            <td>
                <select name="platform" id="sel_paltform_loading">
                    <option value="">请选择</option>
                    <option value="0">IOS</option>
                    <option value="1">安卓</option>
                </select>
            </td>
            <td></td>
        </tr>
        <tr>
            <td colspane="3"><input type="submit" value="提交"/></td>
        </tr>
    </table>
</form>
</body>
</html>