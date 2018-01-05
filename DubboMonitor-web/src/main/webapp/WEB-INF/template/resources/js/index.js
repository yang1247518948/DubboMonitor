/**
 * Created by zyy on 2017/11/16.
 */
window.onload = function() {

    (function() {
        if (!0) return;
        var e = ' figure, footer, header,section'.split(',');
        var i= e.length;
        while (i--){
            document.createElement(e[i])
        }
    })();
    var URL = " ";
    var startTime=null;  // 请求的开始时间
    var endtime=null;   // 请求的结束时间
    
    
    
    
 // 执行一个开始时间
 laydate.render({
 elem: '#test1' // 指定元素
 ,type:"datetime"
 ,format:"yyyy-MM-dd HH:mm:ss"
 ,trigger: 'click'
 ,theme: '#3e4551'
 ,done: function(value){
 startTime = value;; 
 }
 });

 // 执行一个结束时间
 laydate.render({
 elem: '#test2' // 指定元素
 ,type:"datetime"
 ,format:"yyyy-MM-dd HH:mm:ss"
 ,trigger: 'click'
 ,theme: '#3e4551'
 ,done: function(value){
 endtime = value; 
 }
 });

 

    var meth = document.getElementById("me");  // 获取方法分页的id
    var requ = document.getElementById("re");  // 获取请求分页的id
    var vm = new Vue({
        el: 'body',
        data:{
            code:0  ,  // 出错代码
            /* 请求数据 */
            re:{
                beginTime:null,
                endTime:null,
                runTime:null,
                url:null,
                userId:null
            },   // 筛选条件
            num:0,    // 返回数据总条数
            page:1  , // 第几页
            items:[], // 列表细节
            err:" "  , // 错误说明
            count:0, 
            
            /* 方法数据 */
            me:{
                methodName:null,
                runTime:null
            },     // 筛选条件
            num2:0 ,    // 总条数
            page2:1 ,   // 第几页
            items2:[] ,    // 方法列表细节array
            errmsg:" ", // 错误说明
            count2:0
        },
        
        methods: {
            // 请求筛选表单提交
            requestFrom:function(e){
                e.preventDefault();  // 阻止默认事件
                requ.style.display = "block";
                meth.style.display = "none" ;
                
                if( ( (Date.parse(new Date (startTime) )/1000) !=null )&&( (Date.parse(new Date (endtime) )/1000)!=null )&&( (Date.parse(new Date (startTime) )/1000) > (Date.parse(new Date (endtime) )/1000) )){
                	   this.items = [];
                   // alert("开始时间应该小于结束时间,否则没办法筛选！");
                   this.err = "开始时间应该小于结束时间,否则没办法筛选！";
                }else{
                   	this.re.beginTime = startTime;  // 开始时间
                    this.re.endTime = endtime;   // 结束时间
                    var formData = this.re;  // 提交的表单数据
                    this.$http.get("/dlmonitor-web/request/getRequestInfoList?page=1", formData)
                    .then(function(res){
                        var arr=res.data;  // 返回obj
                        this.page = arr.page;// 返回跳转到第几页
                        this.count = arr.count;
                        this.num = Math.ceil(arr.count/15);// 返回请求总页数
                        this.items=[];
                        if(arr.code==0 ){
                            arr = arr.data;    // 返回请求列表
                            for(var i=0; i<arr.length; i++){
                            	  this.items.push({
                                      url:arr[i].url,
                                      runTime:arr[i].runTime,
                                      appName:arr[i].appName,
                                      startTime:new Date(parseInt(arr[i].startTime)).toLocaleString(),
                                      requestMethod:arr[i].requestMethod,
                                      userId:arr[i].userId,
                                      requestId:arr[i].requestId
                                  });
                            } 
                            for(var i=0; i<this.items.length; i++){
                           	    if(this.items[i].runTime==0){
                        	    	 this.items[i].runTime = "<1";
                        	   }
                            }    
                        }else if(arr.code==-1){
                        	this.items = [];
                           // alert("参数错误")
                            this.err = " 参数错误!"
                        }else if(arr.code==-2){
                           // alert("服务器错误")
                        	this.items = [];
                            this.err = " 服务器错误!"
                        }else if(arr.code==-3){
                           // alert("未获取数据")
                        	this.items = [];
                            this.err = " 未获取错误!"
                        }
                    },function(res){
                        window.location.href='../../notFound.ftl';
                    })
                }
            },

            // 方法筛选
            methodFrom:function(){
                requ.style.display = "none";
                meth.style.display = "block" ;
                var formData = this.me; // 提交的表单数据
                this.$http.get("/dlmonitor-web/method/getMethodInfoList?page=1",formData )
                    .then(function(res){
                        var arr=res.data;  // 返回obj
                        this.count2 = arr.count;
                        this.page2 = arr.page;// 返回跳转到第几页
                        this.num2 = Math.ceil((arr.count)/15);// 请求总页数
                        this.items2=[];
                        if(arr.code==0){
                        	arr = arr.data ;   // 返回方法列表
                            for(var i=0; i<arr.length; i++){
                            	 this.items2.push({
                                   methodName:arr[i].methodName,
                                   runTime:arr[i].runTime,
                                   appName:arr[i].appName,
                                   startTime:new Date(parseInt(arr[i].startTime)  ).toLocaleString(),
                                   threadNum:arr[i].threadNum,
                                   className:arr[i].className,
                                   rpcUse:arr[i].rpcUse,
                                   requestId:arr[i].requestId
                               });                
                            }
                            for(var i=0; i<this.items2.length; i++){
                            	    if(this.items2[i].runTime==0){
                            	    	this.items2[i].runTime = "<1";
                            	    }
                            	    if(this.items2[i].rpcUse==0){
                            	    	this.items2[i].rpcUse="否";
                            	    }else if(this.items2[i].rpcUSe==1){
                            	    	this.items2[i].rpcUse="是";
                            	    }
                            }
                        }else if(arr.code =="-1"){
                        	this.items2 = [];
                            this.errmsg = " 参数错误!"
                        }else if(arr.code =="-2"){
                        	this.items2 = [];
                            this.errmsg = " 服务器错误!"
                        }else if(arr.code =="-3"){
                        	this.items2 = [];
                            this.errmsg = " 未获取错误!"
                        }
                },function(res){
                        window.location.href='../../notFound.ftl';
                });
            },

            /* 请求分页 */
            firstPage:function(){
                return this.getPageData(1);
            },
            getPageData:function(n){
            	console.log(n);
                if( n== undefined||n==null){
                    alert("没有填写页数，不能跳转！");
                    this.errmsg ="没有填写页数，不能跳转！";
                }else{
                	var FormData = this.re;
                    this.$http.get("/dlmonitor-web/request/getRequestInfoList?page="+n,FormData)
                    .then(function(res){
                        var arr = res.data;  // 返回obj
                        if(arr.code==0) {
                            this.count = arr.count;
                            this.num = Math.ceil((arr.count) / 15);// 请求总页数
                            this.page = n;// 跳转到第几页
                            arr = arr.data;    // 返回请求列表
                            this.items = [];
                            for (var i = 0; i < arr.length; i++) {
                                this.items.push({
                                    url          : arr[i].url,
                                    runTime      : arr[i].runTime,
                                    appName      : arr[i].appName,
                                    startTime    : new Date(parseInt(arr[i].startTime)).toLocaleString(),
                                    requestMethod: arr[i].requestMethod,
                                    userId       : arr[i].userId,
                                    requestId    : arr[i].requestId
                                }); // 请求列表展示
                            }
                            for (var i = 0; i < this.items.length; i++){
                            	if(this.items[i].runTime==0){
                            		 this.items[i].runTime="<1";
                            	}
                            }
                        }else if(arr.code ==-1){
                            this.errmsg = " 参数错误!";
                            this.items=[];
                            this.page = "";// 返回跳转到第几页
                            this.count = 0;
                            this.num = 0;// 返回请求总页数
                        }else if(arr.code ==-2){
                            this.errmsg = " 服务器错误!";
                            this.items=[];
                            this.page = "";// 返回跳转到第几页
                            this.count = 0;
                            this.num = 0;// 返回请求总页数
                        }else if(arr.code ==-3){
                            this.errmsg = " 未获取错误!";
                            this.items=[];
                            this.page = "";// 返回跳转到第几页
                            this.count = 0;
                            this.num = 0;// 返回请求总页数
                        }
                        },function(){
                        window.location.href='../../notFound.ftl';
                        })

                }
            	// console.log(n);
            },
            prePage:function(){
            	if(this.page>1){
                    return this.getPageData(parseInt(this.page)-1);
                }
            },
            nextPage:function(){
            	if(this.page<this.num){
                    return this.getPageData(parseInt(this.page)+1);
            	}
            },

            /* 方法分页 */
            firstPage2:function(){
                return this.getPageData2(1);
            },
            getPageData2:function(n){
            	     
                if(n==undefined||n==null){
                    alert("没有填写页数，不能跳转！");
                    this.errmsg ="没有填写页数，不能跳转！";
                }else{
                	    var FormData = this.me;
                    this.$http.get("/dlmonitor-web/method/getMethodInfoList?page="+n,FormData).then(function(res){
                        var arr=res.data;  // 返回obj
                        if(arr.code==0){
                            this.count2 = arr.count;
                            this.page2 = n;// 跳转到第几页
                            this.num2 = Math.ceil((arr.count)/15);// 请求总页数

                            arr = arr.data ;   // 返回方法列表
                            this.items2=[];
                            for(var i=0; i<arr.length; i++){
                                this.items2.push({
                                    methodName:arr[i].methodName,
                                    runTime:arr[i].runTime,
                                    appName:arr[i].appName,
                                    startTime:new Date(parseInt(arr[i].startTime)  ).toLocaleString(),
                                    threadNum:arr[i].threadNum,
                                    className:arr[i].className,
                                    rpcUse:arr[i].rpcUse,
                                    requestId:arr[i].requestId
                                });
                            }
                            for (var i = 0; i < this.items2.length; i++) {
                            	if(this.items2[i].runTime==0){
                            		this.items2[i].runTime ="<1";
                            	}
                            	if(this.items2[i].rpcUse==0){
                            		this.items2[i].rpcUse = "否";
                            	}else if(this.items2[i].rpcUse==1){
                            		this.items2[i].rpcUse = "是";
                            	}
                            }
                        }else if(arr.code ==-1){
                            this.errmsg = " 参数错误!";
                            this.items2=[];
                            this.page2 = "";// 返回跳转到第几页
                            this.count2 = 0;
                            this.num2 = 0;// 返回请求总页数
                        }else if(arr.code ==-2){
                            this.errmsg = " 服务器错误!";
                            this.items2=[];
                            this.page2 = "";// 返回跳转到第几页
                            this.count2 = 0;
                            this.num2 = 0;// 返回请求总页数
                        }else if(arr.code ==-3){
                            this.errmsg = " 未获取错误!";
                            this.items2=[];
                            this.page2 = "";// 返回跳转到第几页
                            this.count2 = 0;
                            this.num2 = 0;// 返回请求总页数
                        }
                    },function(){
                        window.location.href='../../notFound.ftl';
                    });
                }

            },
            prePage2:function(){
            	if(this.page2>1){
                return this.getPageData2(parseInt(this.page2)-1);
            	}
            },
            nextPage2:function(){
            	if(this.page2<this.num2){
                return this.getPageData2(parseInt(this.page2)+1);
            	}
            }
        },
        created:function(){
            this.getPageData(1);
        },
    });

};

