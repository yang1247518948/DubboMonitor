<!doctype html>
<html>
<head>
    <title>方法链--详情</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="resources/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="resources/details.css" type="text/css">
    <script type="text/javascript" src="resources/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="resources/js/jquery.ztree.core.js"></script>
</head>

<body>
<div id="main">

<table border=0 align=left  width="900">
    <tr>
        <td align=left valign=top width="100%" >
            <p style="margin: 0;color: #320357">app调用链</p>
            <ul id="apptree" class="ztree" style="width: 100%; overflow: auto;"></ul>
        </td>
    </tr>
</table><br/><br/>
<table border=0 align=left id="table">
    <tr>
        <td align=left valign=top width="100%">
            <p style="display: inline-block;margin:0 0 20px 0;color: #320357">请求调用链</p>
            <button class="hvr-float-shadow" onclick="openData()">请求链全部展开</button>
            <button class="hvr-float-shadow" onclick="closeData()">请求链全部收缩</button>
            <p><b>注：移动光标至数据上查看详情</b></p>
            <ul id="tree" class="ztree" style="width: 100%; overflow: auto;"></ul>
        </td>
    </tr>
</table>
</div>
<div id="footer">
    <p>Copyright ©  All Rights Reserved.</p>
    <p>丹露成都技术有限公司 版权所有</p>
    <p>四川省 成都市 高新区环球中心E1-1804</p>
</footer>
</body>
<script type="text/javascript">
    var zNodes = [],appzNodes=[];
    var zTree;
    var demoIframe;
    window.onload = function() {
        function GetRequest() { //获得url里面的参数
            var url = location.search; //获取url中"?"符后的字串
            var theRequest = {};
            if (url.indexOf("?") != -1) {
                var str = url.substr(1);
                strs = str.split("&");
                for (var i = 0; i < strs.length; i++) {
                    theRequest[strs[i].split("=")[0]] = unescape(strs[i]
                        .split("=")[1]);
                }
            }
            return theRequest;
        }
        var requestId = GetRequest().requestId;  //获得requestId
        var t=null;
        if (requestId != undefined) {   //发请求拿到属性链的参数
            $.ajax({
                url : "methodRequest/getMethodChain",
                type : 'get',
                dataType : 'json',
                async : false,//同步请求
                data : {
                    requestId : requestId
                },
                success : function(data) {
                    zNodes = data.data; //第二层数据
                    t = $("#tree");  //请求树的初始化
                    t = $.fn.zTree.init(t, setting, zNodes);

                    appzNodes = data.app;
                    appt = $("#apptree");  //appname树的初始化
                    appt = $.fn.zTree.init(appt, setting, appzNodes);
                },
                error : function(msg) {
                    alert(msg);
                }
            });
        }
    };

    var setting = {
        view : {
            dblClickExpand : false,
            showLine : true,
            selectedMulti : false
        },
        data : {
            simpleData : {
                enable : true,
                idKey : "id",
                pIdKey : "pId",
                rootPId : ""
            }
        },
        callback : {
            beforeClick : function(treeId, treeNode) {
                var zTree = $.fn.zTree.getZTreeObj("tree");
                if (treeNode.isParent) {
                    zTree.expandNode(treeNode);
                    return false;
                } else {
                    return true;
                }
            }
        }
    };

    /*  zNodes =[
     { id:1, pId:0, name:"展开、折叠 自定义图标不同", open:false},
     { id:11, pId:1, name:"叶子节点1", iconSkin:"icon01"},
     { id:12, pId:1, name:"叶子节点2", iconSkin:"icon02"},
     { id:13, pId:1, name:"叶子节点3", iconSkin:"icon03"},
     { id:2, pId:0, name:"展开、折叠 自定义图标相同", open:false},
     { id:21, pId:2, name:"叶子节点1", iconSkin:"icon04"},
     { id:22, pId:2, name:"叶子节点2", iconSkin:"icon05"},
     { id:23, pId:2, name:"叶子节点3", iconSkin:"icon06"},
     { id:3, pId:0, name:"不使用自定义图标", open:true },
     { id:31, pId:3, name:"叶子节点1"},
     { id:32, pId:3, name:"叶子节点2"},
     { id:33, pId:3, name:"叶子节点3"}
     ];
    $(document).ready(function(){
        var t = $("#tree");
        t = $.fn.zTree.init(t, appsetting, appzNodes);
    });*/



    //一键展开
    function openData() {
        for (var i = 0; i < zNodes.length; i++) {
            zNodes[i].open = true;
        }
        $(document).ready(function() {
            var t = $("#tree");
            t = $.fn.zTree.init(t, setting, zNodes);
            demoIframe = $("#testIframe");
            var zTree = $.fn.zTree.getZTreeObj("tree");
            zTree.selectNode(zTree.getNodeByParam("id", 1));

        });
    }
    //一键收缩
    function closeData() {
        for (var i = 0; i < zNodes.length; i++) {
            zNodes[i].open = false;
        }
        $(document).ready(function() {
            var t = $("#tree");
            t = $.fn.zTree.init(t, setting, zNodes);
            demoIframe = $("#testIframe");
            var zTree = $.fn.zTree.getZTreeObj("tree");
            zTree.selectNode(zTree.getNodeByParam("id", 1));

        });
    }
    


/*
     $(document).ready(function(){
     var appt = $("#apptree");
     appt = $.fn.zTree.init(appt, setting, appzNodes);
     });
    appzNodes =[
        { id:1, pId:0, name:"aaaaaaaaaaaaaa", open:false},
        { id:11, pId:1, name:"aaaaaaaaaaaaaaa", iconSkin:"icon01"},
        { id:12, pId:1, name:"aaaaaaaaaaaaaaaaaaaaaaa", iconSkin:"icon02"},
        { id:13, pId:1, name:"aaaaaaaaaaaaaaaaa叶子节点3", iconSkin:"icon03"},
        { id:2, pId:0, name:"展aaaaaaaaaaaaaa开、折叠 自定义图标相同", open:false},
        { id:21, pId:2, name:"叶aaaaaaaaaaaaaaaaaaaaa子节点1", iconSkin:"icon04"},
        { id:22, pId:2, name:"叶aaaaaaaaaaaaaaa子节点2", iconSkin:"icon05"},
        { id:23, pId:2, name:"叶子节点3", iconSkin:"icon06"},
        { id:3, pId:0, name:"不使用自定义图标", open:true },
        { id:31, pId:3, name:"叶子节点1"},
        { id:32, pId:3, name:"叶子节点2"},
        { id:33, pId:3, name:"叶子节点3"}
        ];*/



    //-->
</script>
</html>