<!DOCTYPE html>
<html lang="cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="author" content="dy,1848370794@qq.com">
    <meta name="renderer" content="webkit">
    <meta name="format-detection" content="telephone=no">
    <title>首页-日志调用</title>
    <link rel="stylesheet" type="text/css" href="resources/index.css">
    <script type="text/javascript" src="resources/js/laydate.js"></script>
    <script type="text/javascript" src="resources/js/vue.js"></script>
    <script type="text/javascript" src="resources/js/vue-resource.js"></script>
    <script type="text/javascript" src="resources/js/index.js"></script>
</head>

<body>
<header>
    <div id="header">
       <!-- <p>列表</p>-->

        <!--请求筛选-->
        <form  id="request" v-cloak @submit.prevent="requestFrom" >
            <p>请求筛选：<small>(注：下面的所有筛选条件为非必选)</small></p>
            <input v-model="re.beginTime" id="test1" class="input-style "  type="text" placeholder="选择开始时间" style="width: 262px;" readonly />
            <input v-model="re.endTime"   id="test2" class="input-style" type="text" placeholder="选择结束时间" style="width: 262px;" readonly/>
            <input v-model="re.runTime"    class="input-style" type="number" min="1" placeholder="输入高于此值的消耗时间" style="width: 155px;"/>
            <input v-model="re.url"        class="input-style" type="text" placeholder="输入访问地址" style="width:220px;"/>
            <input v-model="re.userId"     class="input-style" type="text"  placeholder="输入指定用户" style="width:100px;"/>
            <input type="submit" class="btn" value="高级筛选" />
        </form>

        <!--方法筛选-->
        <form  id="way" v-cloak @submit.prevent="methodFrom">
            <p>方法筛选：</p>
            <input v-model="me.methodName" class="input-style" type="text" placeholder="输入访问方法名" />
            <input v-model="me.runTime"    class="input-style" type="number" min="1" placeholder="输入高于此值的消耗时间" />
            <input type="submit" class="btn" value="高级筛选"/>
        </form>

    </div>
</header>

<section id="main">
    <!--请求列表-->
    <div id="re">
        <div class="none" v-cloak v-show="items.length==0">请求筛选   暂无数据...  {{err}}</div>

        <ul class="list" v-for="item in items" v-show="items.length!=0" v-cloak >
            <li>{{item.url}}</li>
            <li>请求应用的名字：{{item.appName}}</li>
            <li>请求方式：{{item.requestMethod}}</li>
            <li>请求发生时间：{{item.startTime}}</li>
            <li>请求耗费时间：{{item.runTime}} &nbsp;ms</li>
            <li><img src="resources/img/user.png" alt="发起请求的用户" width="" height="">{{item.userId}}</li>
            <li><a href="requestDetailsPage?requestId={{item.requestId}}" @click="show(item.requestId)" target="_blank"
                   style="position: absolute;left:0;top:1em;min-height:5em;max-height: 8em;display:block;width: 1200px;"></a></li>
        </ul>
        <!-- 请求分页处理-->
        <div id="pagination">
            <div class="page-form"  v-cloak >
                <span>共{{num}}页</span>
                <span>&nbsp;&nbsp;&nbsp;共{{count}}条</span>
                <button class="page-btn" @click="firstPage">首页</button>
                <button class="page-btn" @click="prePage">上一页</button>
                第<input type="text" v-model="page"/>页
                <input type="button" @click="getPageData(this.page)" value="跳转" class="page-jump">
                <!--<input type="button" value="跳转" class="page-btn" @click="getPageData(page)" v-model="page" />-->
                <button class="page-btn" @click="nextPage">下一页</button>
            </div>
        </div>
    </div>

    <!--方法链-->
    <div id="me" class="hide">
        <div class="none"  v-show="items2.length==0">方法筛选   暂无数据...{{errmsg}}</div>
        <ul class="list" v-for="item in items2" v-show="items2.length!=0" v-cloak>
            <li>方法名： {{item.methodName}}</li>
            <li>请求发生时间：{{item.startTime}}</li>
            <li>请求耗费时间：{{item.runTime}} &nbsp;ms</li>
            <li>是否RPC调用：{{item.rpcUse}}</li>
            <li>带包名的类名：{{item.className}}</li>
            <li>线程号：{{item.threadNum}}</li>
            <li>应用名：{{item.appName}} &nbsp;</li>
            <li><a href="methodDetailsPage?requestId={{item.requestId}}" @click="show(item.requestId)" target="_blank"
                   style="position: absolute;left:0;top:1em;min-height:5em;max-height: 8em;display:block;width: 1200px;"></a></li>

        </ul>
        <!-- 方法分页处理-->
        <div id="pagination2">
            <div class="page-form"  action="" v-cloak >
                <span>共{{num2}}页</span>
                <span>&nbsp;&nbsp;&nbsp;共{{count2}}条</span>
                <button class="page-btn" @click="firstPage2">首页</button>
                <button class="page-btn" @click="prePage2">上一页</button>
                第<input type="text"  v-model="page2" />页
                <input type="button" @click="getPageData2(this.page2)" value="跳转" class="page-jump">
                <button class="page-btn" @click="nextPage2">下一页</button>
            </div>
        </div>

    </div>

</section>
</body>
</html>

