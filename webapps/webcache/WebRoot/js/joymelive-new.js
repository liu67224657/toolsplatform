var uno = getCookie('jmuc_uno');
var uid = getCookie('jmuc_u');
var token = getCookie('jmuc_token');
var sign = getCookie('jmuc_s');
var login = getCookie('jmuc_lgdomain');
var pid = getCookie('jmuc_pid');
//
var hostname = window.location.hostname;
var wwwHost = 'http://www.joyme.' + hostname.substring(hostname.lastIndexOf('.') + 1, hostname.length) + '/';
var apiHost = 'http://api.joyme.' + hostname.substring(hostname.lastIndexOf('.') + 1, hostname.length) + '/';
var passportHost = 'http://passport.joyme.' + hostname.substring(hostname.lastIndexOf('.') + 1, hostname.length) + '/';
var articleHost = 'http://article.joyme.' + hostname.substring(hostname.lastIndexOf('.') + 1, hostname.length) + '/';
var webCacheHost = 'http://webcache.joyme.' + hostname.substring(hostname.lastIndexOf('.') + 1, hostname.length) + '/';


// var pid='e64a328c73ec70e71897935a1badde6f';
// var uno='a761e8c1-ee5d-4034-a678-d070ca7c1da7';
// var uid='1000042';
// var apiHost = 'http://api.joyme.test/';
// var webCacheHost = 'http://webcache.joyme.com/';

var getLiveListBlock = false;
var getLastLiveBlock = false;
var isReply=false;
var sortBy='';
var targetEdit=false;
var domain=10
var topCommentId='';
var pageSize=10;
var uploadToken='';
var groupId = '';
var picArray=null;
var videoUrl='';
var deviceType=1;//1 pc 2 M\
var hasPermission=false;
var flag = 0;
var liveStatus=1;
var statusDec = "";
var showFlag= false;
var bs = {
    versions: function () {
        var u = navigator.userAgent, app = navigator.appVersion;
        return {//移动终端浏览器版本信息
            trident: u.indexOf('Trident') > -1, //IE内核
            presto: u.indexOf('Presto') > -1, //opera内核
            webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
            gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
            mobile: !!u.match(/AppleWebKit.*Mobile.*/) || !!u.match(/AppleWebKit/), //是否为移动终端
            ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
            android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
            iPhone: u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器
            iPad: u.indexOf('iPad') > -1, //是否iPad
            webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
        };
    }(),
    language: (navigator.browserLanguage || navigator.language).toLowerCase()
}

var uri = window.location.href;
if (uri.indexOf("?") > 0) {
    uri = uri.substring(0, uri.indexOf("?"));
}
if (uri.indexOf("#") > 0) {
    uri = uri.substring(0, uri.indexOf("#"));
}
//var newPicSuffix = '?imageView/2/w/640';
var newPicSuffix = '?imageView/2/w/1500%7CimageMogr/auto-orient%7Cwatermark/1/image/aHR0cDovL2pveW1lcGljLmpveW1lLmNvbS9hcnRpY2xlL3VwbG9hZHMvMTYwODE5LzgwLTE2MFE5MUZaMzQzOC5wbmc=/dissolve/70/gravity/SouthEast/ws/0.13';
var surportman = "";
var path = window.location.pathname;
if (uri.indexOf('http://') == 0 || uri.indexOf('https://') == 0) {
    var number = '';
    var arr = path.split('/');
    for (var i = 0; i < arr.length; i++) {
        var item = arr[i];
        if (item.indexOf(".html") > 0) {
            item = item.replace(".html", "");
            var position = item.indexOf("_");
            if (position >= 0) {
                item = item.substring(0, position);
            }
        }

        if ($.isNumeric(item)) {
            number += item;
        }
    }
    if (number.length > 8) {
        groupId = number.substring(8, number.length);
    }
}


if (groupId.length > 0) {
	getPVSum();
	addShareClick();
	
    if (bs.versions.android || bs.versions.iPhone || bs.versions.iPad) {
        $(document).ready(function () {//M
        	deviceType=2;
        	var html='<div class="zb_tab" id="title_id">'+
        	      '<p><em id="sortByDesc" class="active" onclick="sortData()">最新</em><em id="sortByAsc" onclick="sortData(\'asc\')">最早</em></p></div>'+
                  '<div class="zhibo_con" id="comment_bean_div">'+
                  '</div>';
        	$('#live_area_new').append(html);
        	getLiveList(1);


            var timer = setInterval(function () {
                getLiveStatus(liveStatus);
                getLastLiveList(flag);

            }, 10000);
        }); 
    } else {//pc
    	$('#_liveurlimg').attr('src',wwwHost+'/acitivty/qrcode/removal?height=92&width=92&url='+uri.replace('www','m'));
        $(document).ready(function () {
            var html='<h4 class="title" id="title_id">';
            html+='<cite><span id="sortByDesc" class="active" onclick="sortData()">最新</span><span id="sortByAsc" onclick="sortData(\'asc\')">最早</span></cite></h4>'+
                  '<div class="zhibo_con" id="comment_bean_div">'+
                  '</div>';
            
            $('#live_area_new').append(html);
            getLiveList(1);
            var timer = setInterval(function () {
                getLiveStatus();
                getLastLiveList(flag);
            }, 10000);
        });
    }
    getLiveStatus();
    check();
    
}

function setTimeFlag(){
	var dlLength=$('#live_area_new dl').length;
	if(dlLength > 0){
		if(topCommentId!=''&&topCommentId!=null&&topCommentId!=undefined){
			if(dlLength ==1){
				falg = parseInt($('#live_area_new dl:first').attr('data-flag'));
			}else{
				var topflag = parseInt($('#live_area_new dl:first').attr('data-flag'));
				flag = parseInt($('#live_area_new dl:first').next().attr('data-flag'));
				flag=topflag>flag?topflag:flag;	
			}

		}else{
			flag = parseInt($('#live_area_new dl:first').attr('data-flag'))
		}
	}
}

//显示编辑框
function createEditHtml(dom){
	var html='';
	if(deviceType==1){
		html+='<div class="zhibo_issue">';
	}else if(deviceType==2){
		html+='<div class="zb_issue">';
	}
    html+='<div class="article-chapter-box">'+
          '<span>选择主持人：</span>'+
          '<div class="article-chapter-tit">';
    if(deviceType==1){
        html+='<div id="author_list">'+
               getAuthorList()+
              '</div>';
    }else if(deviceType==2){
    	html+='<select id="compere" onchange="selectAuthor(0)">';
    	html+=getAuthorList();
    	html+='</select>';
    }
    html+='</div>'+
      '</div>'+
      '<div class="zhibo_edit">'+
        '<div class="zhibo_edit_con">'+
          '<div contenteditable="true" class="zhibo_edit_text" id="zhibo_edit_text"></div>'+
        '</div>'+
        '<div class="zhibo_edit_line">'+
          '<div class="zhibo_edit_linecon">'+
            '<cite class="edit_pic"></cite>'+
            '<cite class="edit_video"></cite>'+
            '<cite class="edit_link"></cite>'+
          '</div>'+
          '<span onclick="postPcCommentBean()">发布</span>'+
        '</div>'+
      '</div>'+
      '</div>';

    switch(deviceType){
        case 1:
        	if(!targetEdit){
        		$('.cont').addClass('issue');
        		$(dom).text('<返回直播页');
                showFlag = true;
                getLiveStatus();
        		$('#comment_bean_div').before(html);
        		targetEdit=true;
        	}else{
        		$('.cont').removeClass('issue');
        		$(dom).text('前往发布模式>');
        		$('.zhibo_issue').remove();
                showFlag = false;
                getLiveStatus();
        		targetEdit=false;
        	}
        	break;
        case 2:
			if(!targetEdit){
				$('.cont').addClass('issue');
				$(dom).text('<返回');
                showFlag = true;
                getLiveStatus();
				$('#comment_bean_div').before(html);
				targetEdit=true;
			}else{
				$('.cont').removeClass('issue');
				$(dom).text('发布>');
                showFlag = false;
                getLiveStatus();
				$('.zb_issue').remove();
				targetEdit=false;
			}
        	break;
        default:
        	break;
    }

    addClick();
	upload();
}
var currentComperename='';
var currentCompereicon='';

//获取支持人列表
function getAuthorList(){
	var authorHtml='';
	$('.zb_list li').each(function(index){
		var comperename=$(this).find('b').text()
		var compereicon=$(this).find('img').attr('src')
		if(index==0){
			if(deviceType==1){
                if (surportman =="" || surportman==comperename){
                    authorHtml+='<span id="selected">'+comperename+'</span>';
                }else {
                    authorHtml+='<a href="javascript:;" data-compericon="'+compereicon+'" data-compername="'+comperename+'" onclick="selectAuthor('+index+')" id="compere_'+index+'">'+comperename+'</a>';
                }

			}else if(deviceType==2){
                if (surportman =="" || surportman==comperename){
                    authorHtml+='<option data-compericon="'+compereicon+'" data-compername="'+comperename+'" selected>'+comperename+'</span>';
                }else {
                    authorHtml+='<option data-compericon="'+compereicon+'" data-compername="'+comperename+'" >'+comperename+'</span>';
                }
			}
			currentComperename=comperename;
			currentCompereicon=compereicon;
		}else{
			if(deviceType==1){
                if (surportman==comperename){
                    authorHtml+='<span id="selected">'+comperename+'</span>';
                    currentComperename = surportman;
                    currentCompereicon=compereicon;
                }else{
                    authorHtml+='<a href="javascript:;" data-compericon="'+compereicon+'" data-compername="'+comperename+'" onclick="selectAuthor('+index+')" id="compere_'+index+'">'+comperename+'</a>';
                }

			}else if(deviceType==2){
                if (surportman==comperename){
                    authorHtml+='<option data-compericon="'+compereicon+'" data-compername="'+comperename+'" selected>'+comperename+'</option>';
                    currentComperename = surportman;
                    currentCompereicon=compereicon;
                }else{
                    authorHtml+='<option data-compericon="'+compereicon+'" data-compername="'+comperename+'" >'+comperename+'</option>';
                }

			}
		}
	});
	return authorHtml;
}

//选择主持人
function selectAuthor(index){
	if(deviceType==1){
		$('#selected').text('')
		var oldComperename=currentComperename;
		var oldCompereicon=currentCompereicon
		currentComperename=$('#compere_'+index).attr('data-compername');
		currentCompereicon=$('#compere_'+index).attr('data-compericon');
		$('.article-chapter-tit em').html('<font>'+currentComperename+'</font><cite></cite>');
		$('#selected').text(currentComperename)
		
		$('#compere_'+index).remove();
	    $('#author_list').append('<a href="javascript:;" data-compericon="'+oldCompereicon+' "data-compername="'+oldComperename+'" onclick="selectAuthor('+index+')" id="compere_'+index+'">'+oldComperename+'</a>');
	}else if(deviceType==2){
		currentComperename=$("#compere").find("option:selected").attr('data-compername');
		currentCompereicon=$("#compere").find("option:selected").attr('data-compericon');
	}
    
}
//选择直播状态
function selectLiveStatus(optionValue){
    $.ajax({
        url: articleHost + "plus/api.php?a=setNewLiveStatus&aid="+groupId+"&type=" + optionValue,
        type: "post",
        dataType: "jsonp",
        jsonpCallback: "getlivestatuscallback",
        async:false,
        success: function (req) {
            if (req != null && req != undefined) {
                var status = req.rs;
                if(status==1){
                    //getAlert("修改成功");
                    getLiveList(1);
                    changeLiveStatus();
                }else{
                    //getAlert("修改失败");
                }
            }
        }
    });
}
function changeLiveStatus(){
    if(liveStatus==0){
        statusDec = '准备中';
    }else if(liveStatus==1){
        statusDec = '直播中';
    }else if(liveStatus==2){
        statusDec = '已结束';
    }else if(liveStatus==3){
        statusDec = '已删除';
    }
}
function addSelectLiveStatus(){
    if($('#status')!=null){
        $('#status').remove();
    }
    if($("#liveStatus").length>0){
        $('#liveStatus').remove();
    }
    var status = statusDec;
   if(showFlag){
        var innerHtml = '<select id="liveStatus" style="display: block" onchange="selectLiveStatus(this.value)">';
        if(status=='准备中'){
            innerHtml = innerHtml + '<option value="0" selected>准备中</option>'+
                '<option value="1" >直播中</option>'+
                '<option value="2" >已结束</option>'+
                '<option value="3" >已删除</option>';
            liveStatus  =0;
        }else if(status=='直播中'){
            innerHtml  = innerHtml + '<option value="0" >准备中</option>'+
                '<option value="1" selected>直播中</option>'+
                '<option value="2" >已结束</option>'+
                '<option value="3" >已删除</option>';
            liveStatus  =1;
        }else if(status=='已结束'){
            innerHtml  = innerHtml + '<option value="0" >准备中</option>'+
                '<option value="1" >直播中</option>'+
                '<option value="2" selected>已结束</option>'+
                '<option value="3" >已删除</option>';
            liveStatus  =2;
        }else if(status=='已删除'){
            innerHtml  = innerHtml + '<option value="0" >准备中</option>'+
                '<option value="1" >直播中</option>'+
                '<option value="2" >已结束</option>'+
                '<option value="3" selected>已删除</option>';
            liveStatus  =3;
        }
        innerHtml += '</select>';
        $('#title_id').prepend(innerHtml);
    }else {
       $('#title_id').prepend('<font id="status">'+status+'</font>');
   }

}
function sortData(sort){
	sortBy=sort;
	if(sortBy=='asc'){
		if($('#sortByDesc').hasClass('active')){
			$('#sortByDesc').removeClass('active');
		}
		$('#sortByAsc').addClass('active');
		$('#comment_bean_div').html('');
		getLiveList(1);
	}else{
		if($('#sortByAsc').hasClass('active')){
			$('#sortByAsc').removeClass('active');
		}
		$('#sortByDesc').addClass('active');
		$('#comment_bean_div').html('');
		getLiveList(1)
	}
}
//获取直播列表
function getLiveList(pnum) {
    if(getLiveListBlock){
        return;
    }
    getLiveListBlock = true;
    $.ajax({
        url: apiHost + "comment/bean/json/querybygroup",
        type: "post",
        async: false,
        data: {groupid: groupId, domain: domain, pnum: pnum, psize: pageSize, sort:sortBy,type:deviceType},
        dataType: "jsonp",
        jsonpCallback: "querybygroupcallback",
        success: function (req) {
            var resMsg = req[0];
            if (resMsg.rs == '1') {
                var result = resMsg.result;
                if (result == null || result == undefined) {
                    return null;
                }

                topCommentId=result.top;
                buildCommentBeanListHtml(result);

                if((sortBy==''||sortBy==null||sortBy==undefined)&& pnum==1){
                	setTimeFlag();
                }
                hideMoreReply();
                return;
            } else {
                getAlert(getMsg(resMsg.rs));
                return;
            }
        },
        error: function () {
        	getMsg();
            getLiveListBlock = false;
            return;
        },
        complete:function(){
            getLiveListBlock = false;
            return;
        }
    });
}

//获取最新的直播记录
function getLastLiveList(timeflag) {
 
    if(getLastLiveBlock){
        return;
    }
    getLastLiveBlock = true;
    
	if(isNaN(timeflag)){
		timeflag=flag;
	}
    $.ajax({
        url: apiHost + "comment/bean/json/querybyscore",
        type: "post",
        data: {groupid: groupId, domain: domain, flag: timeflag, type:deviceType, topcommentid:topCommentId},
        dataType: "jsonp",
        jsonpCallback: "querybyscorecallback",
        success: function (req) {
            var resMsg = req[0];
            if (resMsg.rs == '0') {
                getAlert(resMsg.msg);
                return;
            } else if (resMsg.rs == '-1001') {
                getAlert('缺少参数~');
                return;
            } else if (resMsg.rs == '1') {
                var result = resMsg.result;
                if (result == null || result == undefined) {
                    return;
                }
                if (result.rows == null || result.rows == undefined) {
                    return;
                }
                var newCommentTopId=result.top
                var oldCommentTopId=topCommentId;
                var topDom='';
                if((newCommentTopId!=''&&newCommentTopId!=null&&newCommentTopId!=undefined)&& topCommentId!=''&&(newCommentTopId!=topCommentId)){
                	if(oldCommentTopId!=''&& oldCommentTopId!=undefined && oldCommentTopId!=null){
                    	topDom='<dl data-flag="'+$('#commentbean_'+oldCommentTopId).attr('data-flag')+
                    	'" id="commentbean_'+oldCommentTopId+'" data-unikey="'+$('#commentbean_'+topCommentId).attr('data-unikey')+'">'+
                    	$('#commentbean_'+oldCommentTopId).html()+'</dl>';
                	}

                	$('#commentbean_'+topCommentId).remove();//删除老置顶
                	$('#commentbean_'+newCommentTopId).remove();//删除新置顶原始位置元素
            		topCommentId=newCommentTopId;
                }

                var newLiveCommentHtmlList='';
                for (var i = 0; i < result.rows.length; i++) {
                	var commentbean=result.rows[i];
                	if($('#commentbean_'+commentbean.commentId).html()==null ||$('#commentbean_'+commentbean.commentId).html()==undefined){
                		newLiveCommentHtmlList += buildCommentBeanHtml(commentbean);
                	}
                }
                if(newLiveCommentHtmlList!=''){
                    if(oldCommentTopId==''|| oldCommentTopId==undefined || oldCommentTopId==null){//无老的置顶内容
                    	$('#comment_bean_div').prepend(newLiveCommentHtmlList);
                    	if(newCommentTopId!=''&& newCommentTopId!=null && newCommentTopId!=undefined){//有新置顶
                    		if(result.rows.length>1){
                    			flag=parseInt($('#commentbean_'+newCommentTopId).next().attr('data-flag'));
                    		}
                    	}else{
                    		flag = parseInt($('#live_area_new dl:first').attr('data-flag'));
                    	}
                    }else{
                    	$('#live_area_new dl:last').after(topDom);//老置顶移到末尾
                    	$('#commentbean_'+oldCommentTopId).removeClass('dz');
                        if(newCommentTopId!=''&& newCommentTopId!=null && newCommentTopId!=undefined && newCommentTopId!=oldCommentTopId){//有新的置顶内容
                        	if($('#commentbean_'+newCommentTopId).html()!=undefined && $('#commentbean_'+newCommentTopId).html()!=''){
                        		$('#commentbean_'+newCommentTopId).remove();
                        	}
                        	$('#comment_bean_div').prepend(newLiveCommentHtmlList);
                        	if(result.rows.length>1){
                              	var newTopFlag = parseInt($('#commentbean_'+newCommentTopId).attr('data-flag'));
                            	var newFlag=parseInt($('#commentbean_'+newCommentTopId).next().attr('data-flag'));
                            	flag=newTopFlag>newFlag?newTopFlag:newFlag;	
                        	}
                        }else{
                        	$('#commentbean_'+oldCommentTopId).after(newLiveCommentHtmlList);
                        	flag = parseInt($('#commentbean_'+oldCommentTopId).next().attr('data-flag'));
                        }
                    }
                }

            } else {
                getAlert(resMsg.msg);
            }
            return;
        },
        error: function () {
            getAlert('获取失败，请刷新');
            getLastLiveBlock = false;
            return;
        },
        complete:function(){
            getLastLiveBlock = false;
            return;
        }
    });
}
//组装直播列表html
function buildCommentBeanListHtml(result){
    if (result.rows != null && result.rows != undefined) {
        var html = '';
        var commentBeanHtml='';
        for (var i = 0; i < result.rows.length; i++) {
        	if(i==0 || topCommentId != result.rows[i].commentId){
        		commentBeanHtml+=buildCommentBeanHtml(result.rows[i]);
        	}
        }
        $('#comment_bean_div').append(commentBeanHtml);

        if (result.page != null && result.page != undefined) {
        	
        	curPage = result.page.curPage;
        	maxPage = result.page.maxPage;
        	if(curPage < maxPage){
        		if($('.more_btn')!=null){
        			$('.more_btn').remove();
        		}
        		var cp=(curPage+1);
            	var moreHtml='<div class="more_btn" onclick="getLiveList('+cp+')">加载更多</div>';
                $('#comment_bean_div').append(moreHtml);

        	}
            if(curPage==maxPage){
        		if($('.more_btn')!=null){
        			$('.more_btn').remove();
        		}
            }
        }
        addModifyButtonClick();
        
    }
}

//组装单条直播内容html
function buildCommentBeanHtml(data){
    var commentId=data.commentId;
    var commentCount=data.scoreCommentSum;
	var commentBeanHtml='<dl data-flag="' + parseInt(data.createTime / 1000)+'" ';
	if(topCommentId != null && topCommentId!=undefined && commentId==topCommentId){
		commentBeanHtml+='class="zd"';
	}
	commentBeanHtml+='id="commentbean_'+commentId+'" data-unikey="'+data.uniqueKey+'">';
	var expand = data.expandstr;
    var videoHtml = '';
    var picHtml='';

    if(expand.picList!=null && expand.picList!=undefined){
        if (expand.picList.length > 0) {
        	var picData=expand.picList
        	for(var j=0;j<picData.length;j++){
        		if(picData[j]!=null && picData[j]!=''){
        			picHtml+='<span id="pic_position_'+commentId+'_'+j+'" name="pic_'+commentId+'">'+
        			         '<img src="' + picData[j] + newPicSuffix+ '"   alt="' + picData[j] + '" data-image="' + picData[j] + '" class="preview"/>'+
        			         '<cite onclick="removeCommentPic(\''+commentId+'\',\''+j+'\')"></cite></span>';
        		}
        	}
        }	
    }

    if(expand.videoUrl != '' && expand.videoUrl!=null && expand.videoUrl!=undefined){
        	if(expand.videoUrl instanceof Array){
                    videoHtml = '<span id="video_position_'+commentId+'">'+
                    '<video controls="controls" poster="'+expand.videoUrl[0]+'?vframe/jpg/offset/0">'+
                    '<source src="' + expand.videoUrl[0] + '" type="video/mp4">'+
                   // '<source src="' + expand.videoUrl[0] + '" type="video/ogg" />'+
//                    '<object data="' + expand.videoUrl[0] + '" >'+
//                      '<embed src="' + expand.videoUrl[0] + '"  />'+
//                    '</object>'+
                    '你的浏览器不支持html5视频播放'+
                    '</video>'+
                    '<cite onclick="removeCommentVideo(\''+commentId+'\')"></cite></span>';
        		}else{
                	if(expand.videoUrl.indexOf('iframe')>0){
                		videoHtml='<span id="video_position_'+commentId+'">'+
                		           expand.videoUrl+
                		          '<cite onclick="removeCommentVideo(\''+commentId+'\')"></cite></span>';
                	}else{
                        videoHtml = '<span id="video_position_'+commentId+'">'+
                        '<video controls="controls" poster="'+expand.videoUrl+'?vframe/jpg/offset/0">'+
                        '<source src="' + expand.videoUrl + '" type="video/mp4">'+
                        //'<source src="' + expand.videoUrl + '" type="video/ogg" />'+
                        //'<source src="' + expand.videoUrl + '" type="video/webm" />'+  
//                          '<object data="' + expand.videoUrl + '" >'+
//                            '<embed src="' + expand.videoUrl + '"  />'+
//                          '</object>'+
                        '你的浏览器不支持html5视频播放'+
                        '</video>'+
                        '<cite onclick="removeCommentVideo(\''+commentId+'\')"></cite></span>';	
                	}

         } 
    }
	
    var replyData=data.replyRows;
    var totalRows=replyData==null?0:replyData.page==null?0:replyData.page.totalRows;
    var replyText='';
	if(deviceType==1){
	    var replyText='回复'+(totalRows<=0?'':('('+totalRows)+')');
	    
		if((replyData!=null&&totalRows>0)){
			replyText='收起回复';
		}
		commentBeanHtml+='<dt>'+
        '<div class="name_box">'+
          '<cite class="name_pic"><img src="'+expand.compereicon+'" alt=""></cite>'+
          '<b class="name_text">[ 主持人 ]  '+expand.comperename+'</b>'+

          '<em>'+data.dateStr+'</em>'+
          '<code>置顶</code>'+
        '</div>'+
        '<div class="name_con" id="comment_name_con_'+commentId+'">'+
	         '<div contenteditable="false" class="bj_text" id="comment_edit_box_'+commentId+'">'+data.description+'</div>'+
		     picHtml+
		     videoHtml+
		   '</div>'+
	     '</dt>'+ 
	     '<dd id="commentbean_button_'+commentId+'">'+
		   '<div class="control_line">'+
			 '<p class="fb_btn">'+
			   '<cite class="zd_btn" onclick="setCommentBeanTop(\''+commentId+'\',1)"></cite>'+
			   '<cite class="sc_btn" onclick="removeCommentBean(\''+commentId+'\',1)"></cite>'+
			   '<cite class="bj_btn" id="modify_comment_bean_'+commentId+'" onclick="modifyCommentBeanHtml(\''+commentId+'\',1,\''+ expand.comperename+'\')"></cite>'+
			 '</p>'+
            '<a href="javascript:void(0);" id="contAgreelink_' + commentId + '" data-commentid="' + commentId +
            '" class="dianzan" onclick="contAgreeClick(this)"></a>'+
            '<span id="contAgreenum_' + commentId + '">'+
            '<a href="javascript:void(0);" name="cont_agree_num" data-commentid="' + commentId + '" onclick="contAgreeClick(this)">' +
            ((commentCount == null || commentCount == 0) ? '' : ('(' + commentCount + ')')) +
            '</a></span>'+
			 '<span class="hf_btn" id="reply_num_'+commentId+'" data-commentid="'+commentId+
			 ' " data-replynum="'+totalRows+'" onclick="createreply(this)">'+replyText+'</span>'+
		   '</div>';
		commentBeanHtml+=buildReplyBoxHtml(replyData,commentId);
	}else if(deviceType==2){
		replyText='回复';//+(totalRows<=0?'':('('+totalRows)+')');
		commentBeanHtml+='<dt>'+
        '<div class="name_box">'+
          '<cite class="name_pic"><img src="'+expand.compereicon+'" alt=""></cite>'+
          '<b class="name_text">[ 主持人 ]  '+expand.comperename+'</b>'+

          '<em>'+data.dateStr+'</em>'+
          '<code>置顶</code>'+
        '</div>'+
        '<div class="name_con" id="comment_name_con_'+commentId+'">'+
	         '<div contenteditable="false" class="bj_text" id="comment_edit_box_'+commentId+'">'+data.description+'</div>'+
		     picHtml+
		     videoHtml+
		   '</div>'+
	     '</dt>'+ 
	     '<dd id="commentbean_button_'+commentId+'">'+
		   '<div class="control_line">'+
			 '<p class="fb_btn">'+
			   '<cite class="zd_btn" onclick="setCommentBeanTop(\''+commentId+'\',1)"></cite>'+
			   '<cite class="sc_btn" onclick="removeCommentBean(\''+commentId+'\',1)"></cite>'+
			   '<cite class="bj_btn" id="modify_comment_bean_'+commentId+'" onclick="modifyCommentBeanHtml(\''+commentId+'\',1,\''+ expand.comperename+'\')"></cite>'+
			 '</p>'+
            '<a href="javascript:void(0);" id="contAgreelink_' + commentId + '" data-commentid="' + commentId +
            '" class="dianzan" onclick="contAgreeClick(this)"></a>'+
            '<span id="contAgreenum_' + commentId + '">'+
            '<cite href="javascript:void(0);"  data-commentid="' + commentId + '" onclick="contAgreeClick(this)">' +
            ((commentCount == null || commentCount == 0) ? '' : ('(' + commentCount + ')')) +
            '</cite></span>'+
            /*'<span id="contAgreenum_' + commentId + '">'+
            '<a href="javascript:void(0);" name="cont_agree_num" data-commentid="' + commentId + '" onclick="contAgreeClick(this)">' +
            ((commentCount == null || commentCount == 0) ? '' : ('(' + commentCount + ')')) +
            '</a></span>'+*/
			 '<span class="hf_btn" id="reply_num_'+commentId+'" data-commentid="'+commentId+
			 ' " data-replynum="'+totalRows+'" onclick="openReplyBox(this,\''+commentId+'\',0)">'+replyText+'</span>'+
		   '</div>';
		commentBeanHtml+=buildMReplyBoxHtml(replyData,commentId);
	}
		
	commentBeanHtml+='</dd></dl>';
	return commentBeanHtml
}
function getTime() {
    var myDate = new Date();
    return myDate.getFullYear() + "_" + myDate.getMonth() + "_" + myDate.getDate();
}


//点赞
function contAgreeClick(dom) {
    var cid = $(dom).attr('data-commentid');
    var agreeTime = window.localStorage.getItem(cid +'-'+ getTime());
    if (agreeTime != null && parseInt(agreeTime) ==1) {
        alert('您已经赞过了~');
    }else {
        contAgreeComment(domain, cid);
        window.localStorage.setItem(cid +'-'+ getTime(), 1);
    }

}
//PC 点赞
function contAgreeComment(domain, cid) {
    $.ajax({
        url: apiHost + "jsoncomment/reply/commentAgree",
        type: "post",
        data: {domain: domain, cid: cid},
        dataType: "jsonp",
        jsonpCallback: "agreecallback",
        success: function (req) {
            var resMsg = req[0];
            if (resMsg.rs == '0') {
                alert(resMsg.msg);
                return;
            } else if (resMsg.rs == '-1001') {
                alert('请先保存您的内容，登录之后再回来~');
                return;
            } else if (resMsg.rs == '-10102') {
                alert('请先保存您的内容，登录之后再回来~');
                return;
            } else if (resMsg.rs == '-100') {
                alert('app不存在~');
                return;
            } else if (resMsg.rs == '-10104') {
                alert('用户不存在~');
                return;
            } else if (resMsg.rs == '-40011') {
                alert('评论不存在~~');
                return;
            } else if (resMsg.rs == '-40012') {
                alert('评论不存在~~');
                return;
            } else if (resMsg.rs == '-40000') {
                alert('您的请求缺少unikey参数~');
                return;
            } else if (resMsg.rs == '-40001') {
                alert('您的请求缺少domain参数~');
                return;
            } else if (resMsg.rs == '-40002') {
                alert('您的请求缺少jsonparam参数~');
                return;
            } else if (resMsg.rs == '-40003') {
                alert('您的请求中jsonparam参数格式错误~')
                return;
            } else if (resMsg.rs == '-40013') {
                alert('您的请求中domain参数错误~')
                return;
            } else if (resMsg.rs == '-40005') {
                alert('评论内容未填写~')
                return;
            } else if (resMsg.rs == '-40008') {
                alert('评论对象不存在~');
                return;
            } else if (resMsg.rs == '-40006') {
                alert('oid参数错误~');
                return;
            } else if (resMsg.rs == '-40007') {
                alert('parentId参数错误~');
                return;
            } else if (resMsg.rs == '-40010') {
                alert('上级回复已删除~');
                return;
            } else if (resMsg.rs == '-40017') {
                alert('内容含有敏感词：' + resMsg.result[0]);
                return;
            } else if (resMsg.rs == '-40018') {
                alert('评论不存在~');
                return;
            } else if (resMsg.rs == '-40019') {
                alert('您已被禁言~');
                return;
            } else if (resMsg.rs == '1') {
                var numObj = $('a[name=cont_agree_num][data-commentid=' + cid + ']');
                if (bs.versions.android || bs.versions.iPhone || bs.versions.iPad){
                    numObj =$('cite[data-commentid=' + cid + ']')
                }
                var objStr = numObj.html();
                var num;
                if (numObj.length == 0 || objStr == null || objStr.length == 0) {
                    num = parseInt(1);
                } else {
                    var num = numObj.html().replace("(", '').replace(')', '');
                    num = parseInt(num);
                    num = num + 1;
                }
                numObj.html('(' + num + ')');
            } else {
                alert(resMsg.msg);
                return;
            }
        },
        error: function () {
            alert('获取失败，请刷新');
        }
    });
}
function removeCommentPic(commentId,i){
	$('#pic_position_'+commentId+'_'+i).remove();
}

function removeCommentVideo(commentId){
	$('#video_position_'+commentId).remove();
}

//显示更多回复
function showMoreReply(commentId){
	$('#reply_show_'+commentId).remove();
	$('#commentbean_reply_list_'+commentId+' li').each(function(){
		$(this).css('display','block');
	})
	if($('#reply_page_' + commentId).length>0){
		$('#reply_page_' + commentId).css('display', 'block');
	}
}
//隐藏回复
function hideMoreReply() {
	$('.hf_dl_con').each(function(index) {
		var id = $(this).attr('id');
		$('#' + id + ' li').each(function(index) {
			if (index > 2) {
				$(this).css('display', 'none');
			}
		})
		var array=id.split('_');
		if($('#' + id + ' li').length>3){
			if($('#reply_page_' + array[3]).length>0){
				$('#reply_page_' + array[3]).css('display', 'none');
			}
			
			$('#' + id).after('<a id="reply_show_'+array[3]+'" href="javascript:;" onclick="showMoreReply(\''+array[3]+'\')">查看更多...</a>')
		}
	})
}

//组装整个回复域(PC)
function buildReplyBoxHtml(replyData,commentId){
	var isShow='display: none;';
	var replyText='回复';
	if((replyData!=null&&replyData.rows.length>0)){
		isShow='display: block;';
		replyText='收起回复';
	    
	}else{
		replyText='回复';
		replyText+=replyData==null?'':'('+replyData.rows.length+')';
	}	

	$('#reply_num_'+commentId).text(replyText);
	var replyHtml='<div class="hf_con" style="'+isShow+'" id="commentbean_reply_'+commentId+'">';
	
	if(deviceType==1){
		replyHtml+='<cite></cite>';
	}
                   
	replyHtml+='<div class="hf_con_list">';
    if(deviceType==1){
    	replyHtml+='<div class="hf_dl">';
    }
	replyHtml+='<ul class="hf_dl_con" id="commentbean_reply_list_'+commentId+'">';
	
	replyHtml+=buildReplyListHtml(replyData,commentId,0)
	replyHtml+='</ul>';
	if(replyData != null && replyData != undefined){
		if(replyData.page!=null && replyData.page.maxPage>1){
			replyHtml+=getLiveRePageHtml(replyData.page,commentId);
		}
	}
	replyHtml+='<div class="hf_input">'+
    '<div contenteditable="true" id="commentbean_reply_text_'+commentId+'"></div>'+
    '<span data-commentid="'+commentId+'" onclick="replyComment(\''+commentId+'\',0)">评论</span>'+
    '</div>'+
    '</div>';
    
    replyHtml+='</div>';        
	return replyHtml;
}

//组装整个回复域(M)
function buildMReplyBoxHtml(replyData,commentId){
	var replyText='';
	var isShow='display: block;';
	if((replyData!=null&&replyData.rows.length>0)){
		replyText='回复';
		replyText+=replyData==null?'':'('+replyData.rows.length+')';
	    
	}else{
		isShow='display: none;';
		replyText='回复';
	}
	$('#reply_num_'+commentId).text(replyText);
	var replyHtml='<div class="hf_con" style="'+isShow+'" id="commentbean_reply_'+commentId+'">'+
                   '<cite></cite>'+
                   '<div class="hf_con_list">';
    if(deviceType==1){
    	replyHtml+='<div class="hf_dl">';
    }
	replyHtml+='<ul class="hf_dl_con" id="commentbean_reply_list_'+commentId+'">';
	
	replyHtml+=buildReplyListHtml(replyData,commentId,0)
	replyHtml+='</ul>';
	if(replyData != null && replyData != undefined){
		if(replyData.page!=null && replyData.page.maxPage>1){
			replyHtml+=getLiveRePageHtml(replyData.page,commentId);
		}
	}
    replyHtml+='</div>';        
	return replyHtml;
}

//打开评论框
function openReplyBox(dom,commentId,parentId){
	$('#video_position_'+commentId).css('display','none');
    if (uno == null || uno==''|| uid == null || uid=='') {
        $('.new-area').blur();
        $('.pl-box').hide();
        $('.mask').hide();
        loginRedirect();
        return false;
    }
	var replyBox='<div id="reply_box_div_'+commentId+'" class="pl-box" style="display: block;">'+ 
                   '<div class="mask" style="display: block;"></div>'+ 
                   '<div class="cmnt-wrap">'+ 
                     '<div class="cmnt-area">'+ 
                       '<form>'+
                         '<textarea class="new-area" id="new-area"></textarea>'+ 
                       '</form>'+
                     '</div>'+ 
                     '<div class="cmnt-bar">'+ 
                       '<span class="cmnt-btn fabu fn-right" onclick="replyFromBox(\''+commentId+'\','+parentId+')">回复</span>'+ 
                       '<span class="cmnt-btn cancel fn-right" onclick="destroyReplyBox(\''+commentId+'\')">取消</span>'+ 
                       '<span class="cmnt-num" id="cmnt-num">还可以输入<b class="inp-num" id="inp-num">140</b>个字</span>'+ 
                     '</div>'+ 
                     '<div class="cmnt-beyond">'+
                       '<span>请输入至少2个字</span>'+
                     '</div>'+ 
                     '<div class="cmnt-success"></div>'+ 
                   '</div>'+ 
                 '</div>';
	$('#wrapper').append(replyBox);
	$('#reply_box_div_'+commentId).css('display','block');
	if(parentId > 0){
		var nick=$('#reply_'+parentId).find('a').attr('data-nick');
		$('#new-area').attr('placeholder','@'+nick+':');
	}
	$('#new-area').focus();
	
    //监听输入的字符数
    $('#new-area').on('input propertychange' || 'keyup', function () {
        var text = $('#new-area').val();
        //超出文字提示
        if (getStrlen(text) > 140) {
            $('#cmnt-num').show();
            $('#cmnt-num').html('已超出<b class="inp-num" id="inp-num">' + (getStrlen(text) - 140) + '</b>个字符');
        } else if (getStrlen(text) > 130 && getStrlen(text) <= 140) {
            $('#cmnt-num').show();
            $('#cmnt-num').html('还可以输入<b class="inp-num" id="inp-num">' + (140 - getStrlen(text)) + '</b>个字');
        } else {
            if ($('#inp-num') != null) {
                $('#cmnt-num').show();
                $('#inp-num').html(140 - getStrlen(text));
            }
        }
    });
}

//回复
function replyFromBox(commentId,parentId){
	replyId=parentId;
	replyComment(commentId);
	
}

//关闭回复弹窗
function destroyReplyBox(commentId){
	replyId=0;
	$('#video_position_'+commentId).css('display','block');
    $('#reply_box_div_'+commentId).remove();
}

//组装回复列表
function buildReplyListHtml(replyData,commentId,index){
	var replyListHtml='';
	if(null != replyData && undefined != replyData){
		for(var i=index;i<replyData.rows.length;i++){
			var reply=replyData.rows[i];
			replyListHtml +=buildReplyHtml(reply,commentId);
		}
	}
	return replyListHtml;
}


//组装单个回复
function buildReplyHtml(reply,commentId){
	var replyHtml='';
	switch(deviceType){
	    case 1:
	    	replyHtml='<li id="reply_'+reply.reply.rid+'">'+
            '<cite><img src="'+reply.user.icon +'" alt=""></cite>'+
            '<p><a href="javascript:;" id="reply_nick_'+reply.reply.rid+'">'+reply.user.name+'</a>';
              if (reply.puser != null && reply.puser.name != null) {
              	replyHtml+='@'+reply.puser.name + ":";
              }else{
              	replyHtml+=':'
              }
              replyHtml+=reply.reply.body.text+'</p>'+
              '<div class="hf_titline">'+
                '<em class="hf_sj">'+reply.reply.post_date+'</em>'+
                '<span>'+
                  '<code class="hf_dz" id="agree_live_comment_'+reply.reply.rid+'" onclick="agreeLiveComment(\''+commentId+'\','+reply.reply.rid+',1)">('+reply.reply.agree_sum+')</code>'+
                  '<code class="hf_hf" onclick="addTextToArea(\''+commentId+'\','+reply.reply.rid+')">回复</code>';
                  if(uno != null && uid != null && uid == reply.user.uid && uno==reply.user.uno){
                  	replyHtml+='<code class="hf_sc" onclick="removeLiveComment(\''+commentId+'\','+reply.reply.rid+')">删除</code>';
                  }
                  replyHtml +='</span>'+
              '</div>'+
            '</li>';
	    	break;
	    case 2:
	    	replyHtml='<li id="reply_'+reply.reply.rid+'" onclick="openReplyBox(this,\''+commentId+'\','+reply.reply.rid+')">'+
            '<p><a href="javascript:;"  data-nick="' + reply.user.name + '" data-oid="0" data-pid="' + reply.reply.rid +'"id="reply_nick_'+reply.reply.rid+'">'+reply.user.name+'</a>';
              if (reply.puser != null && reply.puser.name != null) {
              	replyHtml+='<i>回复</i><b>'+reply.puser.name + ":</b>";
              }else{
              	replyHtml+=':'
              }
              replyHtml+=reply.reply.body.text+'</p>'+
            '</li>';
	    	break;
	    default:
	    	break;
	}

     return replyHtml;
}

//直播评论分页 html
function getLiveRePageHtml(page, commentId) {
	var rePageHtml='';
	switch(deviceType){
	    case 1:
	    	rePageHtml = '<div class="hf_dl_page" id="reply_page_'+commentId+'">';
	    	if(page.curPage > 1){
	    		rePageHtml += '<span class="active" onclick="getReplyList(\'' + commentId + '\','+ (page.curPage - 1) + ',0)">上一页</span>&nbsp;';
	    	}
	    	if (page.maxPage <= 4) {
	    		for (var cp = 1; cp <= page.maxPage; cp++) {
	    			if (cp == page.curPage) {
	    				rePageHtml += '<a href="javascript:void(0);" class="active" onclick="getReplyList(\''+ commentId + '\',' + cp + ',0)">' + cp + '</a>&nbsp;';
	    			} else {
	    				rePageHtml += '<a href="javascript:void(0);" onclick="getReplyList(\''+ commentId + '\',' + cp + ',0)">' + cp + '</a>&nbsp;'
	    			}
	    		}
	    	} else {
	    		if (page.curPage < 3) {
	    			for (var cp = 1; cp <= 4; cp++) {
	    				if (cp == page.curPage) {
	    					rePageHtml += '<a href="javascript:void(0);" class="active" onclick="getReplyList(\''+ commentId + '\',' + cp + ',0)">' + cp + '</a>&nbsp;';
	    				} else {
	    					rePageHtml += '<a href="javascript:void(0);" onclick="getReplyList(\''+ commentId+ '\','+ cp+ ',0)">'+ cp+ '</a>&nbsp;'
	    				}
	    			}
	    		} else {
	    			if ((page.curPage + 2) <= page.maxPage) {
	    				for (var cp = (page.curPage - 1); cp <= (page.curPage + 2); cp++) {
	    					if (cp == page.curPage) {
	    						rePageHtml += '<a href="javascript:void(0);" class="active" onclick="getReplyList(\''+ commentId + '\',' + cp + ',0)">' + cp + '</a>&nbsp;';
	    					} else {
	    						rePageHtml += '<a href="javascript:void(0);" onclick="getReplyList(\''+ commentId+ '\','+ cp+ ',0)">'+ cp+ '</a>&nbsp;'
	    					}
	    				}
	    			} else {
	    				for (var cp = (page.maxPage - 3); cp <= page.maxPage; cp++) {
	    					if (cp == page.curPage) {
	    						rePageHtml += '<a href="javascript:void(0);" class="active" onclick="getReplyList(\''+ commentId + '\',' + cp + ',0)">' + cp + '</a>&nbsp;';
	    					} else {
	    						rePageHtml += '<a href="javascript:void(0);" onclick="getReplyList(\''+ commentId+ '\','+ cp+ ',0)">'+ cp+ '</a>&nbsp;'
	    					}
	    				}
	    			}
	    		}
	    	}
	    	
	    	if(page.curPage < page.maxPage){
	    		rePageHtml += '<span class="active" onclick="getReplyList(\'' + commentId + '\','+ (page.curPage + 1) + ',0)">下一页</span>';
	    	}
	    	
	    	rePageHtml += '</div>';
	    	break;
	    case 2:
	    	if(page.curPage ==1 && page.maxPage>1 ){
	    		rePageHtml='<a id="m_reply_more_button_'+commentId+'" href="javascript:;" onclick="getReplyList(\''+ commentId + '\',1,'+page.totalRows+')">查看更多&gt;</a>';
	    	}
	    	
	    	break;
	    default:
	    	break;
	}
	
	return rePageHtml;
}


// 发布新的直播内容
function postPcCommentBean(){
    surportman = currentComperename;
	var expstr = {
			comperename:currentComperename,
			compereicon:currentCompereicon,
			picList:picArray,
			videoUrl:videoUrl
	}
	var description='';
	var commentbeanDiv='';
	description=$('.zhibo_edit_text').html();
	if(description==''||description==null||description==undefined){
		if(videoUrl==''&&picArray==null){
			switch(deviceType){
			case 1:
				getAlert('内容不能为空')
				break;
			case 2:
	    		$('#wp_comment_alert_no_button').attr('class','wp_comment_alert active');
	    		$('#wp_comment_tips_no_button').text('内容不能为空');
	            setTimeout(function(){$('#wp_comment_alert_no_button').attr('class','wp_comment_alert');}, 1500);
				break;
			default:
				break;
		    }	
			return;	
		}
	}
	postCommentBean(description,expstr);
}

//发布直播内容
function postCommentBean(description,expstr) {
	if(!hasPermission){
		if(deviceType==1){
			getAlert('无操作权限')
		}else if(deviceType==2){
    		$('#wp_comment_alert_no_button').attr('class','wp_comment_alert active');
    		$('#wp_comment_tips_no_button').text('无操作权限');
            setTimeout(function(){$('#wp_comment_alert_no_button').attr('class','wp_comment_alert');}, 1500);
		}
	}
    $.ajax({
        url: apiHost + "comment/bean/json/post",
        type: "post",
        data: {domain:domain,groupid:groupId,description:description,profileid:pid,expstr:JSON.stringify(expstr)},
        dataType: "jsonp",
        jsonpCallback: "postcallback",
        success: function (req) {
            var resMsg = req[0];
            if (resMsg.rs == '1') {
            	resMsg.result.expandstr=JSON.parse(resMsg.result.expandstr)
            	insertNewCommentbeanHtml(resMsg.result);
            	if(deviceType==1){
            		getAlert('发布成功');
            	}else if(deviceType==2){
            		$('#wp_comment_alert_no_button').attr('class','wp_comment_alert active');
            		$('#wp_comment_tips_no_button').text('发布成功');
                    setTimeout(function(){$('#wp_comment_alert_no_button').attr('class','wp_comment_alert');}, 1500);
            	}
                return;
            } else {
            	getAlert(getMsg(resMsg.rs));
                return;
            }
        }
    });
}

//将新添加的直播内容写入页面
function insertNewCommentbeanHtml(commentbean){
	if(null != commentbean && undefined != commentbean){
		var newLiveCommentHtml=buildCommentBeanHtml(commentbean);
		if(topCommentId==''||topCommentId == null || topCommentId==undefined){//无置顶
			$("#comment_bean_div").prepend(newLiveCommentHtml);
			if($("#comment_bean_div").html()==null){
				$("#comment_bean_div").append(newLiveCommentHtml)
			}
		}else{
			$('#commentbean_'+topCommentId).after(newLiveCommentHtml)
		}	
		$('.zhibo_edit_text').html('');
		
		addModifyButtonClick();
    	picNum=0;
    	videoNum=0;
    	picArray=null;
    	videoUrl='';
    	$('.edit_pic').html('');
    	$('.edit_video').html('');
    	$('.iframe_text').val('');
    	$('.up_block.uping').remove();
    	$('.up_block.upend').remove();
	}
}
//添加编辑按钮点击事件
function addModifyButtonClick(){
	$('.bj_btn').on('click',function(){
		var con_box=$(this).parents('dl').find('.name_con');
		var text_box=$(this).parents('dl').find('.bj_text');
		text_box.attr('contenteditable','true');
		con_box.addClass('bj');
		text_box.addClass('bj');
		$(this).addClass('active');
	})
}
// 修改直播内容
function modifyCommentBeanHtml(commentId,type,publishuser){

	if($('#modify_comment_bean_'+commentId).hasClass('active')==false){
		$('#modify_comment_bean_'+commentId).addClass('active')
		return;
	}
	var pics=new Array($('span[name="pic_'+commentId+'"]') .length);
	$('span[name="pic_'+commentId+'"]').each(function(index){
		var pic=$(this).children('img').first().attr('alt');
		pics[index]=pic;
	});
	var vurl='';
	var vp=$('#video_position_'+commentId);
	if(vp!=null && vp!=undefined){
		vurl=vp.children().first().children().attr('src');
	}
	var expstr = {
			comperename:publishuser,
			compereicon:currentCompereicon,
			picList:pics,
			videoUrl:vurl
	}
	
	var description='';
	var commentbeanDiv='';
	description=$('#comment_edit_box_'+commentId).text();
	if((description==null||description=='')&&vurl==''&&pics==null){
		getAlert('修改后内容不能为空');
		return;
	}
	modifyCommentBean(commentId,description,expstr);
}

//修改直播内容
function modifyCommentBean(commentId,description,expstr) {
	if(!hasPermission){
		if(deviceType==1){
			getAlert('无操作权限')
		}else if(deviceType==2){
    		$('#wp_comment_alert_no_button').attr('class','wp_comment_alert active');
    		$('#wp_comment_tips_no_button').text('无操作权限');
            setTimeout(function(){$('#wp_comment_alert_no_button').attr('class','wp_comment_alert');}, 1500);
		}
	}
    $.ajax({
        url: apiHost + "comment/bean/json/modify",
        type: "post",
        data: {cid:commentId,domain:domain,groupid:groupId,description:description,profileid:pid,expstr:JSON.stringify(expstr)},
        dataType: "jsonp",
        jsonpCallback: "postcallback",
        success: function (req) {
            var resMsg = req[0];
            if (resMsg.rs == '1') {
            	$('#modify_comment_bean_'+commentId).removeClass('active');
            	$('#comment_edit_box_'+commentId).attr('contenteditable',"false"); 
            	$('#comment_edit_box_'+commentId).removeClass('bj');
            	$('#comment_name_con_'+commentId).removeClass('bj');
            	picNum=0;
            	videoNum=0;
            	$('.edit_pic').html('');
            	$('.edit_video').html('');
            	if(deviceType==1){
            		getAlert('修改成功');
            	}else if(deviceType==2){
            		$('#wp_comment_alert_no_button').attr('class','wp_comment_alert active');
            		$('#wp_comment_tips_no_button').text('修改成功');
                    setTimeout(function(){$('#wp_comment_alert_no_button').attr('class','wp_comment_alert');}, 1500);
            	}
                return;
            } else {
            	getAlert(getMsg(resMsg.rs));
                return;
            }
        }
    });
}

//删除直播内容
function removeCommentBean(commentId) {
	if(deviceType==1){
		var r=confirm("确定删除?");
		if (r==false){
		  return;
		}else{
			doRemove(commentId);
		}
	}else if(deviceType==2){
	    $('#wp_comment_alert').attr('class','wp_comment_alert active');
	    $('#wp_comment_tips').text('确定删除?');
	    $('#wp_confirm').one('click', function(){
	    	$('#wp_comment_alert').removeClass('active');
	    	doRemove(commentId);
	    	return true;
	    });
		$('#wp_cancel').one('click', function(){
			$('#wp_comment_alert').removeClass('active');
			$('#wp_confirm').unbind('click');
			$('#wp_cancel').unbind('click');
			
			return false;
	    });
	}
}

function doRemove(commentId){
    $.ajax({
        url: apiHost + "comment/bean/json/del",
        type: "post",
        data: {cid:commentId,profileid:pid,groupid:groupId,domain:domain},
        dataType: "jsonp",
        //jsonpCallback: "postcallback",
        success: function (req) {
            var resMsg = req[0];
            if (resMsg.rs == '1') {
            	if(topCommentId==commentId){
            		topCommentId='';
            	}
            	$('#commentbean_'+commentId).remove();
                return true;
            } else {
            	getAlert(getMsg(resMsg.rs));
                return false;
            }
        }
    });
}

var replyId=0;
function addTextToArea(commentId,rid){
	replyId=rid;
	var nick=$('#reply_nick_'+rid).text();
	$('#commentbean_reply_text_'+commentId).html('@'+nick+':');
}

//评论
function replyComment(commentId){
	var text='';
	switch(deviceType){
	    case 1:
	    	if(pid==null ||pid==''|| uid==null ||uid==''|| uno==null||uno==''){
	    		var r=confirm("您还未登录，请保存内容，登录回来后回复。点击确定将跳转至登录页！");
	    		if (r==false){
	    		  return;
	    		}else{
	    			window.location.href=passportHost+'auth/loginpage';
	    		} 
	    	}
	    	text=$('#commentbean_reply_text_'+commentId).text();
	    	if(replyId>0){
	    		var nick=$('#reply_nick_'+replyId).text();
	    		text = text.replace('@' + nick + ":", '');
	    	}
	    	break;
	    case 2:
	    	if(pid==null ||pid==''|| uid==null ||uid==''|| uno==null||uno==''){
		        $('#wp_comment_alert').attr('class','wp_comment_alert active');
		        $('#wp_comment_tips').text('您还未登录，点确定跳转至登录页');
		        $('#wp_confirm').on('click', function(){
		        	window.location.href=passportHost+'auth/loginpage';
		        });
		    	$('#wp_cancel').on('click', function(){
		    		$('#wp_comment_alert').removeClass('active');
		    		return;
		        });	
	    	}else{
	    		text = $('#new-area').val();
		    	if(replyId>0){
		    		var nick=$('#reply_nick_'+replyId).text();
		    		text = text.replace('@' + nick + ":", '');
		    	}
		    	if(text==''||text==null||text==undefined){
		    		getAlert('评论不能为空');
		    		return;
		    	}
	    	}
	    	break;
	}
	replyCommentBean(commentId,text);
}

//评论
function replyCommentBean(commentId,text) {//1:pc 2:m
    if(isReply){
        return;
    }
    isReply = true;
    var body = {
    		text: text,
            pic: ""
    }

    $.ajax({
        url: apiHost + "comment/bean/json/reply",
        type: "post",
        data: {cid:commentId,pid:replyId,body:JSON.stringify(body),uno:uno,domain:domain},
        dataType: "jsonp",
        jsonpCallback: "replycallback",
        success: function (req) {
            var resMsg = req[0];
            if (resMsg.rs == '1') {
            	if(null != resMsg.result && resMsg.result != undefined){
            		insertNewReplyHtml(resMsg.result,commentId)
            		replyId=0;
            		return;
            	}
            } else {
            	getAlert(getMsg(resMsg.rs));
            	if(deviceType==2){
            		destroyReplyBox(commentId);
            	}
                return;
            }
        },
        error: function () {
        	getMsg('操作频繁，稍后再试');
        	isReply = false;
            return;
        },
        complete:function(){
        	destroyReplyBox(commentId);
        	isReply = false;
            return;
        }
    });
}

//刷新回复列表,用于分页
function reBuildReplyListHtml(data,commentId){
	switch (deviceType){
		case 1:
			var replyHtml=buildReplyBoxHtml(data,commentId);
			$('#commentbean_reply_'+commentId).remove();
		    $('#commentbean_button_'+commentId).after(replyHtml);
		    $('#commentbean_reply_'+commentId).css('display','block');;
			$('#reply_num_'+commentId).text('收起回复');
			break;
		case 2:
			var replyHtml=buildReplyListHtml(data,commentId,3);
			$('#m_reply_more_button_'+commentId).remove();
			$('#commentbean_reply_list_'+commentId).append(replyHtml);
			break;
		default:
			break;
	}	
}

//点赞
function agreeLiveComment(commentId,rid) {
	var unikey=$('#commentbean_'+commentId).attr('data-unikey');
    $.ajax({
        url: apiHost + "comment/bean/json/agree",
        type: "post",
        data: {uno: uno, domain:domain, unikey:unikey,rid: rid},
        dataType: "jsonp",
        jsonpCallback: "agreecallback",
        success: function (req) {
            var resMsg = req[0];
            if (resMsg.rs == '1') {
                var numObj = $('#agree_live_comment_'+rid);
                var objStr = numObj.html();
                var num;
                if (numObj.length == 0 || objStr == null || objStr.length == 0) {
                    num = parseInt(1);
                } else {
                    var num = numObj.html().replace("(", '').replace(')', '');
                    num = parseInt(num);
                    num = num + 1;
                }
                numObj.html('(' + num + ')');
            } else {
                getAlert(getMsg(resMsg.rs));
                return;
            }
        },
        error: function () {
            getAlert();
        }
    });
}

//置顶
function setCommentBeanTop(commentId) {//1:pc 2:m
    $.ajax({
        url: apiHost + "comment/bean/json/settop",
        type: "post",
        data: {cid:commentId,profileid:pid},
        dataType: "jsonp",
        jsonpCallback: "settopcallback",
        success: function (req) {
            var resMsg = req[0];
            if (resMsg.rs == '1') {
            	$('#comment_bean_div').html('');
            	getLiveList(1);
            	return;
            } else {
            	getAlert(getMsg(resMsg.rs));
                return;
            }
        }
    });
}

//新增回复写入页面
function insertNewReplyHtml(reply,commentId){
	$('#video_position_'+commentId).css('display','block');
	var replyHtml=buildReplyHtml(reply,commentId);
	$('#commentbean_reply_list_'+commentId).prepend(replyHtml);
	var tag=$('#reply_num_'+commentId).attr('data-replynum');
	var num=parseInt(tag)+1
	$('#reply_num_'+commentId).attr('data-replynum',num);
	
	switch(deviceType){
	  case 1:
		  $('#commentbean_reply_text_'+commentId).text('');
		  break;
	  case 2:
		  $('#reply_box_div_'+commentId).remove();
		  $('#commentbean_reply_'+commentId).css('display','block');
		  //$('#reply_num_'+commentId).text('回复('+num+')');
		  break;
	  default:
		  break;
	}
	
}

//评论列表
function getReplyList(commentId,pnum,totalRows) {
    var pSize=pageSize;
    if(deviceType==2){
    	pSize=totalRows;  	
    }
    $.ajax({
        url: apiHost + "comment/bean/json/livesublist",
        type: "post",
        data: {cid:commentId,pnum:pnum,psize:pSize},
        dataType: "jsonp",
        jsonpCallback: "sublistcallback",
        success: function (req) {
            var resMsg = req[0];
            if (resMsg.rs == '1') {
            	reBuildReplyListHtml(resMsg.result,commentId);
                return;
            } else {
				getAlert(getMsg(resMsg.rs));
				return;
            }
        }
    });
}

//删除直播评论
function removeLiveComment(commentId,rid){
	if(deviceType==1){
		var r=confirm("确定删除?");
		if (r==false){
		  return;
		}else{
			doRemoveLiveComment(commentId,rid);
		}
	}else if(deviceType==2){
	    $('#wp_comment_alert').attr('class','wp_comment_alert active');
	    $('#wp_comment_tips').text('确定删除?');
	    $('#wp_confirm').on('click', function(){
	    	$('#wp_comment_alert').removeClass('active');
	    	doRemoveLiveComment(commentId,rid);
	    });
		$('#wp_cancel').on('click', function(){
			$('#wp_comment_alert').removeClass('active');
			return;
	    });
	}

}

function doRemoveLiveComment(commentId,rid){
	var unikey=$('#commentbean_'+commentId).attr('data-unikey');
	$.ajax({
        url: apiHost + 'comment/bean/json/remove',
        type: "post",
        data: {uno: uno, domain:domain, unikey:unikey, rid: rid},
        dataType: "jsonp",
        jsonpCallback: "removeCommentcallback",
        success: function (req) {
            var resMsg = req[0];
            if (resMsg.rs == '1') {
            	$('#reply_'+rid).remove();
            	var tag=$('#reply_num_'+commentId).attr('data-replynum');
            	var num=parseInt(tag)-1
            	$('#reply_num_'+commentId).attr('data-replynum',num);
                return;
            } else {
            	getAlert(getMsg(resMsg.rs));
                return;
            }
        }
    });
}


//统计直播参与者总数（pc+m）
function getPVSum(){
	 $.ajax({
	    	url: webCacheHost + "json/pagestat/pvlist.do",
	        type: "post",
	        data: {pageids : groupId, pagetype : 1,pageshow:'live'},
	        dataType: "jsonp",
	        jsonpCallback: "pvlistcallback",
	        success: function (req) {
	            var resMsg = req[0];
	            if (resMsg.rs == '1') {
	            	var data=resMsg.result;
	            	for(var i in data){
	            		if(deviceType==1){
	            			$('#_viewcount').text(data[i].pvSum);
	            		}else if(deviceType==2){
	            			$('#_viewcount').text(data[i].pvSum+'人参与');
	            		}
	            		
	            	}
	            	
	                return;
	            } else {
	                return;
	            }
	        }
	    });
}

function check() {
    $.ajax({
    	url: apiHost + "comment/bean/json/check",
        type: "post",
        data: {profileid: pid},
        dataType: "jsonp",
        jsonpCallback: "checkcallback",
        success: function (req) {
            var resMsg = req[0];
            if (resMsg.rs == '1') {
            	uploadToken=resMsg.uploadtoken;

            	if(deviceType==1){
            		$('#title_id').append('<a href="javascript:;" class="go" onclick="createEditHtml(this)">前往发布模式 ></a>');
            	}else if(deviceType==2){
            		$('#title_id').append('<a href="javascript:;" class="go" onclick="createEditHtml(this)">发布 ></a>');
            	}
            	hasPermission=true;
            	return;
            } else {
            	hasPermission=false;
                return;
            }
        }
    });
}

//获取直播状态
function getLiveStatus() {
    $.ajax({
        url: articleHost + "plus/api.php?a=getNewLiveStatus&aid=" + groupId,
        type: "get",
        dataType: "jsonp",
        jsonpCallback: "getlivestatuscallback",
        async:false,
        success: function (req) {
            if (req != null && req != undefined) {
                var status = req.status;
                if(status!=null){

                    //$('#title_id').prepend('<font id="status">'+status+'</font>');
                    statusDec = status;
                    addSelectLiveStatus();
                }else{
                    if($('#status')!=null){
                        $('#status').remove();
                    }
                }
            }
        }
    });
}

//删除图片
function removePic(dom){
	var index=$(dom).attr('data-index');
	$('#upload_pic_'+index).remove();
	picNum--;
	if(picNum==0){
		$('.edit_pic').html('');
	}else{
		$('.edit_pic').html('<em>'+picNum+'<em>');
	}
	
}

//删除视频
function removeVideo(dom){
	$('#upload_video').remove();
	videoNum=0
	videoUrl='';
	$('.edit_video').html('');
	$('.iframe_text').val('');
}

//图片传进度条
function uploadPicProcessLine(uploadRate){
    $('#up_block_line_'+picNum).html('<span style="width:'+uploadRate+'%">'+uploadRate+'%</span>');
}

//视频上传进度条
function uploadVideoProcessLine(uploadRate){
	$('.up_block').children('div.up_block_line').html('<span style="width:'+uploadRate+'%">'+uploadRate+'%</span>');
}

//保存图片
function savePic(){
	$('.dialog.edit_pic_d').css('display','none');
	if(picNum<=0){
		getAlert('未上传图片')
		return;
	}
	picArray=new Array(picNum);
	for(var i=0;i<picNum; i++){
		picArray[i]=$('#pic_'+i).attr('src');
	}
	getAlert('保存成功');
	
}

//保存视频
function saveVideo(){
	$('.dialog.edit_video_d').css('display','none');
	
	if(videoNum<=0){
		getAlert('未上传视频')
		return;
	}
	getAlert('保存成功');
	
}

//添加链接
function addLink(){
	var link=$('.link_text').val();
	var af='<a href="'+link+'" target="_blank">'+link+'</a>';
	var text=$('.zhibo_edit_text').html()+af;
	$('.zhibo_edit_text').html(text);
	$('.dialog.edit_link_d').css('display','none');
	$('.link_text').val('');
}

var picNum=0;
var videoNum=0;

function createPicBox(){
	//$('.dialog.edit_pic_d').css('display','none');
	if(picNum>=8){
		getAlert('最多只能上传8张图片');
		return;
	}
    var picHtml='<div class="up_block uping" id="upload_pic_'+picNum+'">'+
    '<div class="up_block_con"><img id="pic_'+picNum+'" src="" alt=""></div>'+
    '<cite class="up_block_remove" data-index="'+picNum+'" onclick="removePic(this)"></cite>'+
    '<div class="up_block_line" id="up_block_line_'+picNum+'">'+
    '</div>';
   $('#container').before(picHtml);
}

function createVideoBox(){
	//$('.dialog.edit_pic_d').css('display','none');
	if(videoNum>0){
		getAlert('已上传过视频');
		return;
	}
   videoNum++;
   var videoHtml='<div class="up_block uping" id="upload_video">'+
   '<div class="up_block_con"></div>'+
   '<cite class="up_block_remove"  onclick="removeVideo(this)"></cite>'+
   '<div class="up_block_line" id="up_block_line_video">'+
   '</div>';
   $('#videopost').after(videoHtml);

}

function previewVideo(){
	if(videoNum>0){
		getAlert('已上传过视频');
		return;
	}
	videoNum++;
 	var sourceLink=$('.iframe_text').val();
	videoUrl='<iframe height=498 width=510 src="'+sourceLink+'" frameborder=0 allowfullscreen></iframe>';
	var video
	if(videoUrl.indexOf('iframe')>0){
		video=videoUrl;
	}else{
		video='<video controls poster="'+sourceLink+'?vframe/jpg/offset/0">'+
		 '<source src="'+sourceLink+'" type="video/ogg" />'+
	     '<source src="'+sourceLink+'" type="video/webm" />'+
	     '<source src="'+sourceLink+'" type="video/mp4">'+
	     '<object data="'+sourceLink+'" >'+
	       '<embed src="'+sourceLink+'"  />'+
	     '</object>';
	     
	     '您的浏览器不能使用最新的视频播放方式呢'+
	     '</video>';
	}

	
	var videoHtml='<div class="up_block upend" id="upload_video">'+
	'<div class="up_block_con">'+video+'</div>'+
	'<cite class="up_block_remove"  onclick="removeVideo(this)"></cite>'+
	'<div class="up_block_line" id="up_block_line_video">'+
	'</div>';
	$('#videopost').after(videoHtml);
	$('.edit_video').html('<em>'+videoNum+'<em>');
}

function showUploadPic(sourceLink){
	$('#pic_'+picNum).attr('src',sourceLink);
    $('#upload_pic_'+picNum).removeClass('uping').addClass('upend');
    picNum++;
    $('.edit_pic').html('<em>'+picNum+'<em>');
}

function showUploadVideo(sourceLink){
    $('.edit_video').html('<em>'+videoNum+'<em>');
    videoUrl=sourceLink;
    var video='<video controls="controls" poster="'+sourceLink+'?vframe/jpg/offset/0">'+
               '<source src="'+sourceLink+'" type="video/ogg" />'+
               '<source src="'+sourceLink+'" type="video/webm" />'+
               '<source src="'+sourceLink+'" type="video/mp4">'+
//              '<object data="'+sourceLink+'" >'+
//                '<embed src="'+sourceLink+'"  />'+
//              '</object>';
              '</video>';
    
    $('#upload_video').removeClass('uping').addClass('upend');
    $('#upload_video').children('div.up_block_con').html(video);
}

function upload(){

	 var QiniuPic = new QiniuJsSDK();
     var picUploader = QiniuPic.uploader({
         runtimes: 'html5,flash,html4',    //上传模式,依次退化
         browse_button: 'pickfiles',       //上传选择的点选按钮，**必需**
         uptoken: uploadToken,
         unique_names: false , 
         save_key: false,
         domain: 'http://joymepic.joyme.com/',
         container: 'container',           //上传区域DOM ID，默认是browser_button的父元素，
         max_file_size: '8mb',           //最大文件体积限制
         flash_swf_url: 'http://lib.joyme.com/static/third/qiniuupload/plupload/Moxie.swf',  //引入flash,相对路径
         max_retries: 3,                   //上传失败最大重试次数
         dragdrop: true,                   //开启可拖曳上传
         chunk_size: '4mb',                //分块上传时，每片的体积
         auto_start: true,                 //选择文件后自动上传，若关闭需要自己绑定事件触发上传
         init: {
             'FilesAdded': function (up, files) {
                 var i = 1;
                 plupload.each(files, function (file) {
                     files.splice(i,1);
                     // 文件添加进队列后,处理相关的事情
                 });
             },
             'BeforeUpload': function (up, file) {
            	 if(picNum>=8){
                	 this.trigger('Error', {
                         code : -1,
                         message : "最多只能上传8张图片",
                         file:file
                     });
                     return false; 
            	 }
                 var picType = file.name.substring(file.name.lastIndexOf('.'),file.name.length).toUpperCase();

                 if(picType != '.JPG' && picType != '.PNG' && picType != '.GIF' && picType != '.JPEG'){
                	 this.trigger('Error', {
                         code : -1,
                         message : "只能上传*.jpg,*.png,*.gif,*.jpeg的图片",
                         file:file
                     });
                     return false;
                 }
                 if (file.size >= 1024 * 1024 * 8) {
                	 this.trigger('Error', {
                         code : -1,
                         message : "上传的图片大小不超过18mb",
                         file:file
                     });
                     return false;
                 }
                 createPicBox();
             },
             'UploadProgress': function (up, file) {
                 var chunk_size = plupload.parseSize(this.getOption('chunk_size'));
                 var uploadRate=Math.ceil(file.loaded*100/file.size);
                 uploadPicProcessLine(uploadRate);
                
             },
             'FileUploaded': function (up, file, info) {
                 try {
                      var domain = up.getOption('domain');
                      var res = JSON.parse(info);
                       var sourceLink = domain +res.key; //获取上传成功后的文件的Url
                       showUploadPic(sourceLink);
                 } catch (ex) {
                     alert(ex);
                 }
             },
             'Error': function (up, err, errTip) {
                 alert(errTip);
                 //上传出错时,处理相关的事情
             },
             'UploadComplete': function () {

             },
             'Key': function (up, file) {
                 // 若想在前端对每个文件的key进行个性化处理，可以配置该函数
            	 var picType = file.name.substring(file.name.lastIndexOf('.'),file.name.length).toLowerCase();
            	 var day = new Date(); 
            	 var Year= day.getFullYear();
            	 var Month= day.getMonth()+1;
            	 if(Month<10){
            		 Month='0'+Month;
            	 }
            	 var Day = day.getDate();
            	 var timestamp=Math.ceil(day.getTime());
            	 var key = "live/pic/"+Year+Month+'/'+Day+timestamp+picType;
                 
                 // do something with key here
                 return key
             }
         }

     });
     picUploader.stop();

     var QiniuVideo = new QiniuJsSDK();
     var videoUploader = QiniuVideo.uploader({
         runtimes: 'html5,flash,html4',    //上传模式,依次退化
         browse_button: 'videofiles',       //上传选择的点选按钮，**必需**
         uptoken: uploadToken,
         unique_names: false , 
         save_key: false,
         domain: 'http://joymepic.joyme.com/',
         container: 'videopost',           //上传区域DOM ID，默认是browser_button的父元素，
         max_file_size: '500mb',           //最大文件体积限制
         flash_swf_url: 'http://lib.joyme.com/static/third/qiniuupload/plupload/Moxie.swf',  //引入flash,相对路径
         max_retries: 3,                   //上传失败最大重试次数
         dragdrop: true,                   //开启可拖曳上传
         chunk_size: '4mb',                //分块上传时，每片的体积
         auto_start: true,                 //选择文件后自动上传，若关闭需要自己绑定事件触发上传
         init: {
             'FilesAdded': function (up, files) {
                 var i = 1;
                 plupload.each(files, function (file) {
                     files.splice(i,1);
                     // 文件添加进队列后,处理相关的事情
                 });
             },
             'BeforeUpload': function (up, file) {
            	 if(videoNum>0){
                     this.trigger('Error', {
                         code : -1,
                         message : "已上传过视频",
                         file:file
                     });
            		 return false;
            	 }
                 var videoType = file.name.substring(file.name.lastIndexOf('.'),file.name.length).toUpperCase();

                 if(videoType != '.MOV' && videoType != '.MP4'){
                	 this.trigger('Error', {
                         code : -1,
                         message : "只能上传*.MOV,*.MP4格式视频",
                         file:file
                     });
                     return false;
                 }
                 
                 if (file.size >= 1024 * 1024 * 100) {
                     this.trigger('Error', {
                         code : -1,
                         message : "上传的视频大小不超过100mb",
                         file:file
                     });
                     return false;
                 }
                 createVideoBox(); 
                 
             },
             'UploadProgress': function (up, file) {
                 var chunk_size = plupload.parseSize(this.getOption('chunk_size'));
                 var uploadRate=Math.ceil(file.loaded*100/file.size);
                 uploadVideoProcessLine(uploadRate);
             },
             'FileUploaded': function (up, file, info) {
                 try {
                	  var videoType = file.name.substring(file.name.lastIndexOf('.')+1,file.name.length).toLowerCase();
                      var domain = up.getOption('domain');
                      var res = JSON.parse(info);
                      var sourceLink = domain + res.key; //获取上传成功后的文件的Url
                      //ideoAvthumb(res.key)
                      showUploadVideo(sourceLink);
                      
                 } catch (ex) {
                     alert(ex);
                 }
             },
             'Error': function (up, err, errTip) {
                 alert(errTip);
                 //上传出错时,处理相关的事情
             },
             'UploadComplete': function () {

             },
             'Key': function (up, file) {
            	 var videoType = file.name.substring(file.name.lastIndexOf('.'),file.name.length).toLowerCase();
            	 var day = new Date(); 
            	 var Year= day.getFullYear();
            	 var Month= day.getMonth()+1;
            	 if(Month<10){
            		 Month='0'+Month;
            	 }
            	 var Day = day.getDate();
            	 var timestamp=Math.ceil(day.getTime()/1000);
            	 var key = "live/video/"+Year+Month+'/'+Day+timestamp+videoType;
                 return key
             }
         }

     });
     videoUploader.stop();

}
//视频转码
function videoAvthumb(videoName){
	 $.ajax({
	    	url: apiHost+'',
	        type: "post",
	        dataType: "jsonp",
	        jsonpCallback: "videoAvthumbcallback",
	        async:false,
	        success: function (req) {
	        	alert(req)
	        	return true;
	        }
	    });
}
//提示弹窗
function getAlert(msg){
	switch (deviceType){
		case 1:
			alert(msg);
		    break;
		case 2:
		    $('#wp_comment_alert').attr('class','wp_comment_alert active');
		    $('#wp_comment_tips').text(msg);
		    $('#wp_confirm').on('click', function(){
		    	$('#wp_comment_alert').removeClass('active');
		    });
			$('#wp_cancel').on('click', function(){
				$('#wp_comment_alert').removeClass('active');
		    });
			break;
		default:
			alert(msg);
			break;
	}
}

//错误信息
function getMsg(code){
	var msg = '';
	if(code==''||code==undefined||code==null){
		msg='获取失败，请刷新';
	}
	switch(parseInt(code)){
		case -1:
			msg='请先保存您的内容，登录之后再回来~';
		    break;
		case 0:
			msg='系统错误';
		    break;
		case -100:
			msg='app不存在~';
			break;
		case -1001:
			msg='您的请求缺少参数~';
		    break;
		case -10102:
			msg='请先保存您的内容，登录之后再回来~';
		    break;
		case -10104:
			msg='用户不存在~';
		    break;
		case -2012:
			msg='已点过赞~';
			break;
		case -40000:
			msg='您的请求缺少unikey参数~';
		    break;
		case -40001:
			msg='您的请求缺少domain参数~';
		    break;
		case -40002:
			msg='您的请求缺少jsonparam参数~';
		    break;
		case -40003:
			msg='您的请求中jsonparam参数格式错误~';
		    break;
		case -40005:
			msg='评论内容未填写~';
		    break;
		case -40007:
			msg='parentId参数错误~';
		    break;
		case -40008:
			msg='评论对象不存在~';
		    break;
		case -40009:
			msg='主楼评论已删除~';
		    break;
		case-40010:
			msg='上级回复已删除~';
		case -40012:
			msg="评论不存在~";
		    break;
		case -40013:
			msg='您的请求中domain参数错误~';
		    break;
		case -40016:
			msg='您已赞过';
			break;
		case -40017:
			msg='内容含有敏感词';
		    break;
		case -40019:
			msg='您已被禁言~';
		    break;
		case -40020:
			msg='一分钟内不能重复发送相同的内容~';
		    break;
        case -40022:
            msg='两次评论间隔不能少于15秒，请稍后再试~';
            break;
		default:
			msg='未知错误';
		    break;
	}
	return msg;
}

function addShareClick(){
	var pageLoad={
			init:function(){
				pageLoad.backUpPC();
				pageLoad.backUpM();
			},
			
			backUpPC:function(){
				var backbox=$('.back_box');
				var backbtn=backbox.find('.back_up');
				var w=$(window).width();
				if(w>1200){
					var right=((w-1080)/2)+1095;
					backbox.css('right',right);
				}else{
					backbox.css('left',0);
				}
				$(window).scroll(function(){
					var sh=$(this).scrollTop();
					if(sh>400){
						backbox.show();
					}else{
						backbox.hide();
					}
				});
				backbtn.on('click',function(){
					$(window).scrollTop(0);
					backbox.hide();
				})				
	
				

			},
			backUpM:function(){
			      var backbox=$('.up_box');
			      $('#wrapper').scroll(function(){
			        var sh=$(this).scrollTop();
			        if(sh>400){
			          backbox.show();
			        }else{
			          backbox.hide();
			        }
			      });
			      backbox.on('click',function(){
			        $('#wrapper').scrollTop(0);
			        backbox.hide();
			      })  
			    },
		};
		pageLoad.init();
		
}
function addClick(){
	$('.edit_pic_btn').on('click',function(){
		savePic();
	});
	$('.add_video').on('click',function(){
		saveVideo();
	});
    $('.iframe_text').next().on('click',function(){
//    	if(videoNum>0){
//    		$('.iframe_text').val('');
//    		getAlert('已上传过视频');
//    		return;
//    	}
    	if(videoNum<=0 && ($('.iframe_text').val()==''||$('.iframe_text').val()==null)){
    		getAlert('iframe不能为空');
    		return;
    	}
   	    previewVideo();
    })
	$('.edit_link_con').find('cite').on('click',function(){
    	if(videoNum>0){
    		getAlert('已上传过视频')
    	}
		addLink();
	})
	var pageLoad={
		init:function(){
//			pageLoad.goissue();
			pageLoad.selectMenu();
			pageLoad.showdialog();
			pageLoad.editbtn('.bj_btn');
			//pageLoad.unfold();
		},
		unfold:function(){
			var btn=$('.hf_btn');
			var target=false;
			var num=3;
			btn.on('click',function(){
				var cont=$(this).parent().next();
				console.log(cont.attr('class'))
				if(!target){
					cont.show();
					btn.text('收起回复');
					target=true;
				}else{
					cont.hide();
					btn.text('回复('+num+')');
					target=false;
				}
			})
		},
		
		selectMenu:function(){
			var select_box=$('.article-chapter-tit');
			var select_box_cont=select_box.children('div');
			var texts=select_box_cont.children('span').text();
			var tits='<em><font>'+texts+'</font><cite></cite></em>';
			select_box_cont.before(tits);
			var tits_box=select_box.find('font');
			var select_h=(select_box_cont.children('a').length+1)*31;
			if(select_h>310){
				select_h=310;
				select_box_cont.css('overflow-y','scroll');
			}
			var flag=false;
			select_box.children('em').on('click',function(e){
				if(e && e.stopPropagation){e.stopPropagation();}else{window.event.cancelBubble = true;}
				if(!flag){
					select_box_cont.height(select_h);
					flag=true;
				}else{
					select_box_cont.height(0);
					flag=false;
				}
			})
			$(document).on('click',function(e){
				if(e && e.stopPropagation){e.stopPropagation();}else{window.event.cancelBubble = true;}
				select_box_cont.height(0);
				flag=false;
			})
			select_box.find('a').on('click',function(){
				var text=$(this).text();
				tits_box.text(text);
			})
		},
		showdialog:function(){
			var btns=$('.zhibo_edit_linecon').children('cite');
			btns.on('click',function(){
				var tits=$(this).attr('class');
				$('.'+tits+'_d').show();
			})
			$('.close_btn').on('click',function(){
				$('.dialog').hide();
				var ancestordom=$(this).parent().parent().parent();
				if(ancestordom.hasClass('edit_pic_d')){
					picNum=0;
					picArray=null;
					$('.edit_pic').html('');
			    	
				}else if(ancestordom.hasClass('edit_video_d')){
					videoNum=0;
					videoUrl='';
					$('.edit_video').html('');
					$('.iframe_text').val('');
				}
				$('.up_block.uping').remove();
		    	$('.up_block.upend').remove();
			})
		},
		editbtn:function(btn){
			var btn=$(btn);
			btn.on('click',function(){
				var con_box=$(this).parents('dl').find('.name_con');
				var text_box=$(this).parents('dl').find('.bj_text');
				text_box.attr('contenteditable','true');
				con_box.addClass('bj');
				text_box.addClass('bj');
				$(this).addClass('active');
			})
		}
	};
	pageLoad.init();
	
}

function createreply(dom){ 
	var dom=$(dom);
	var commentId=dom.attr('data-commentid');
	var num=dom.attr('data-replynum');
	var tag=$('#commentbean_reply_'+commentId);
	if(tag.css('display')=='none'){
		dom.text('收起回复');
		tag.css('display','block');
		
	}else{
		var repHtml='';
		if(parseInt(num)>0){
			repHtml='('+num+')';
		}
		dom.text('回复'+repHtml);
		tag.css('display','none');
	}
}
 