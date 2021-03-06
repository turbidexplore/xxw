if($.cookie("ACCESS_TOKEN")==undefined){
    window.location.href="/system/login";
}

$(document).ready(function(){
    $('.my-editor').notebook();
});

$("#go").hide();
$("#hgo").hide();
hidemenu();
function hidemenu() {
    if ($("#hidden").val()==1){
        $("#menu").show();
        $("#context").addClass("p-0");
        $("#context").addClass("offset-lg-2");
        $("#context").addClass("offset-md-3");
        $("#context").removeClass("col-lg-12");
        $("#context").addClass("col-lg-10");
        $("#hidden").val(0);
    } else {
        $("#menu").hide();
        $("#context").removeClass("p-0");
        $("#context").removeClass("offset-lg-2");
        $("#context").removeClass("offset-md-3");
        $("#context").removeClass("col-lg-10");
        $("#context").addClass("col-lg-12");
        $("#hidden").val(1);
    }
}
function save(value) {
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/productclass/saveClassdata?id="+$("#coreid").val()+"&value="+value,
        "method": "PUT",
        "processData": false,
        "headers": {
            "Authorization": "Bearer " + $.cookie("ACCESS_TOKEN"),
            "cache-control": "no-cache",
            "Postman-Token": "0d83bfb4-ef46-46d6-ae5e-02b7a296a3e2"
        }
    }
    $.ajax(settings).done(function (response) {
        window.location.reload();
    });
}

var url="";
var settings = {
    "async": true,
    "crossDomain": true,
    "url": "/productclass/getOne?id="+$("#coreid").val()+"&comid="+$("#comid").val(),
    "method": "GET",
    "processData": false,
    "headers": {
        "Authorization": "Bearer " + $.cookie("ACCESS_TOKEN"),
        "cache-control": "no-cache",
        "Postman-Token": "0d83bfb4-ef46-46d6-ae5e-02b7a296a3e2"
    }
}
$.ajax(settings).done(function (response) {

    if(response.data==null){
        window.location.href="/system/nodatafiveclass";
    }
    if(response.data.skutype==1){
        url = "/system/fiveclassview_vue?id="+response.data.id;
        $("#hgo").show();
    }else if(response.data.skutype==2){
        url = "/system/fiveclassviewpl?id="+response.data.id;
        $("#hgo").show();
    }else {
        $("#go").show();
    }

    var routh_paths=response.data.route_path.split("//")[1];
    var routh_path=routh_paths.split("/");
    $("#classname").html( routh_path[0]+">"+routh_path[1]+">"+routh_path[2]+">"+routh_path[3]+"  "+response.data.brand_name+" | "+response.data.class_name);
    $("#coreid").val(response.data.id);
    if(null!=response.data.logo) {
        $("#logo").attr("src", "https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/product_class/images/" + response.data.logo);
    }else {
        $("#logo").attr("src", "https://iph.href.lu/500x500?text=%E9%9B%B6%E4%BB%B6%E9%82%A6&fg=666666&bg=CCCCCC");
    }
    if(null!=response.data.companylogo) {
        $("#companylogo").attr("src", "https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/temp/" + response.data.companylogo);
    }else {
        $("#companylogo").attr("src", "https://iph.href.lu/500x500?text=%E9%9B%B6%E4%BB%B6%E9%82%A6&fg=666666&bg=CCCCCC");
    }

    $("#company").html("ID:<span style='font-weight: bold;'> "+response.data.id+" </span><br>"+"公司:<span style='font-weight: bold;'> "+response.data.company_name +" </span>  " +
        "品牌:<span style='font-weight: bold;'> "+response.data.brand_name+" </span>");
    $("#add_user").html("创建者:["+response.data.add_user+"] <button type=\"button\" onclick=\"save(4)\"  class=\"mb-2 btn btn-sm btn-outline-warning mr-1\" id='atg'>  跳 过  </button>&nbsp;&nbsp;" +
        "              &nbsp;&nbsp;");
    $("#desc").html(response.data.class_desc);
    $("#level5space").append(
        "<object width=\"100%\" height=\"1000px\" data=\""+"https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/product_class/pdf/"+response.data.pdf_file+"\"> \n" +
        "</object>");

    if(null!=response.data.industry) {
        var settings = {
            "async": true,
            "crossDomain": true,
            "url": "/trade/getByCode?code=" + response.data.industry,
            "method": "GET",
            "processData": false
        }
        $.ajax(settings).done(function (res) {
            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "/trade/getByPCode?pcode=0",
                "method": "GET",
                "processData": false
            }
            $.ajax(settings).done(function (response) {
                response.data.forEach(function (v) {
                    if (res.data.c == v.id) {
                        $("#lv1").append("<option value='" + v.id + "' selected>" + v.name + "</option>")
                    } else {
                        $("#lv1").append("<option value='" + v.id + "'>" + v.name + "</option>")
                    }
                })

            });
            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "/trade/getByPCode?pcode=" + res.data.c,
                "method": "GET",
                "processData": false
            }
            $.ajax(settings).done(function (response) {
                response.data.forEach(function (v) {
                    if (res.data.b == v.id) {
                        $("#lv2").append("<option value='" + v.id + "' selected>" + v.name + "</option>")
                    } else {
                        $("#lv2").append("<option value='" + v.id + "'>" + v.name + "</option>")
                    }
                })

            });

            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "/trade/getByPCode?pcode=" + res.data.b,
                "method": "GET",
                "processData": false
            }
            $.ajax(settings).done(function (response) {
                response.data.forEach(function (v) {
                    if (res.data.a == v.id) {
                        $("#lv3").append("<option value='" + v.id + "' selected>" + v.name + "</option>")
                    } else {
                        $("#lv3").append("<option value='" + v.id + "'>" + v.name + "</option>")
                    }
                })

            });

        });
    }else {

        var settings = {
            "async": true,
            "crossDomain": true,
            "url": "/trade/getByPCode?pcode=0",
            "method": "GET",
            "processData": false
        }
        $.ajax(settings).done(function (response) {
            var i=0;
            response.data.forEach(function (v) {
                if (i==0) {
                    var settings = {
                        "async": true,
                        "crossDomain": true,
                        "url": "/trade/getByPCode?pcode=" + v.id,
                        "method": "GET",
                        "processData": false
                    }
                    $.ajax(settings).done(function (response) {
                        var ii=0;
                        response.data.forEach(function (b) {
                            if (ii==0) {
                                $("#lv2").append("<option value='" + b.id + "' selected>" + b.name + "</option>")
                                var settings = {
                                    "async": true,
                                    "crossDomain": true,
                                    "url": "/trade/getByPCode?pcode=" + b.id,
                                    "method": "GET",
                                    "processData": false
                                }
                                $.ajax(settings).done(function (response) {
                                    var iii=0;
                                    response.data.forEach(function (c) {
                                        if (iii==0) {
                                            $("#lv3").append("<option value='" + c.id + "' selected>" + c.name + "</option>")
                                            iii++;
                                        } else {
                                            $("#lv3").append("<option value='" + c.id + "'>" + c.name + "</option>")
                                        }
                                    })

                                });
                                ii++;
                            } else {
                                $("#lv2").append("<option value='" + b.id + "'>" + b.name + "</option>")
                            }
                        })

                    });
                    $("#lv1").append("<option value='" + v.id + "' selected>" + v.name + "</option>")
                    i++;
                } else {
                    $("#lv1").append("<option value='" + v.id + "'>" + v.name + "</option>")
                }
            })

        });
    }

    response.data.images.forEach(function (value){
        var index =$(".move_div").length;
        $("#imgs").append("<div class='move_div' ondblclick='rmimgs(this)'><div class='drag'   style='float: left;margin: 10px;'><input type='hidden' value='" + value.url + "'><img src=\"https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/product_class/images/" +value.url+ "\" alt=\"logo\"  width=\"80px;\">" +
            "</div><div class='move_div_span'><span class='move_span' onclick='moveLeft(this)'><</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class='move_span' onclick='moveRight(this)'>></span></div></div>");

        reIndex();
    });


});

$("#gridtable").hide();
function edittable(obj){
    if(obj.checked==true){
        $("#gridtable").show();
    }else {
        $("#gridtable").hide();
    }
}

var index=0;

function rmimgs(my) {
    $(my).remove();
    reIndex();
}




$("#imgs").on('paste', function(eventObj) {
    // 处理粘贴事件
    var event = eventObj.originalEvent;
    var imageRe = new RegExp(/image\/.*/);
    var fileList = $.map(event.clipboardData.items, function (o) {
        if(!imageRe.test(o.type)){ return }
        var blob = o.getAsFile();
        return blob;
    });
    if(fileList.length <= 0){ return }
    upload(fileList);
    //阻止默认行为即不让剪贴板内容在div中显示出来
    event.preventDefault();
});
function upload(fileList) {
    for(var i = 0, l = fileList.length; i < l; i++){
        var fd = new FormData();
        var f = fileList[i];
        fd.append('file', f);
        $.ajax({
            url:"/file/uploadLogo",
            type: 'POST',
            dataType: 'json',
            data: fd,
            processData: false,
            contentType: false,
            xhrFields: { withCredentials: true },
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Access-Control-Allow-Credentials': 'true'
            },
            success: function(result){
                $("#imgs").append("<div class='move_div' ondblclick='rmimgs(this)'><div style='float: left;margin: 10px;'><input type='hidden' value='" + result.data + "'><img src=\"https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/product_class/images/" +result.data+ "\" alt=\"logo\"  width=\"80px;\"></div>" +
                    "<div class='move_div_span'><span class='move_span' onclick='moveLeft(this)'><</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class='move_span' onclick='moveRight(this)'>></span></div></div>");
                reIndex();
            },
            error: function(){
                alert("上传图片错误");
            }
        });
    }
}

$("#classlogo").on('paste', function(eventObj) {
    // 处理粘贴事件
    var event = eventObj.originalEvent;
    var imageRe = new RegExp(/image\/.*/);
    var fileList = $.map(event.clipboardData.items, function (o) {
        if(!imageRe.test(o.type)){ return }
        var blob = o.getAsFile();
        return blob;
    });
    if(fileList.length <= 0){ return }
    uploada(fileList);
    //阻止默认行为即不让剪贴板内容在div中显示出来
    event.preventDefault();
});
function uploada(fileList) {
    for(var i = 0, l = fileList.length; i < l; i++){
        var fd = new FormData();
        var f = fileList[i];
        fd.append('file', f);
        $.ajax({
            url:"/file/uploadLogo",
            type: 'POST',
            dataType: 'json',
            data: fd,
            processData: false,
            contentType: false,
            xhrFields: { withCredentials: true },
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Access-Control-Allow-Credentials': 'true'
            },
            success: function(result){
                var settings = {
                    "async": true,
                    "crossDomain": true,
                    "url": "/basedata/updatetask?url=" + result.data + "&id=" + $("#coreid").val(),
                    "method": "PUT",
                    "headers": {
                        "Authorization": "Bearer " + $.cookie("ACCESS_TOKEN"),
                        "cache-control": "no-cache",
                        "Postman-Token": "0d83bfb4-ef46-46d6-ae5e-02b7a296a3e2"
                    },
                    "processData": false
                }
                $.ajax(settings).done(function (response) {
                    console.log(response);
                });
                $("#logo").attr("src","https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/product_class/images/"+result.data);
            },
            error: function(){
                alert("上传图片错误");
            }
        });
    }
}


function update() {
    var imgs=[];
    $("#imgs input[type=hidden]").each(function () {
        imgs.push(this.value);
    });
    var data ={"id":$("#coreid").val(),"desc":$("#desc").html(),"imgs":imgs,"industry":$("#lv3").val()};
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/productclass/updateDesc",
        "method": "PUT",
        "processData": false,
        "data":JSON.stringify(data),
        "contentType": "application/json",
        "headers": {
            "Authorization": "Bearer " + $.cookie("ACCESS_TOKEN"),
            "cache-control": "no-cache",
            "Postman-Token": "0d83bfb4-ef46-46d6-ae5e-02b7a296a3e2"
        }
    }
    $.ajax(settings).done(function (response) {
        $("#atg").hide();
        alert("保存成功！");
    });
}


function next() {
    $.confirm({
        title: '上传sku',
        content: '请选择sku的上传类型！',
        type: 'green',
        icon: 'glyphicon glyphicon-question-sign',
        buttons: {
            bds: {
                text: '表达式',
                btnClass: 'btn-primary',
                action: function() {
                    window.open("/system/fiveclassview_vue?id="+$("#coreid").val(),'_blank','')
                }
            },
            pl: {
                text: '批量',
                btnClass: 'btn-primary',
                action: function() {
                    window.open("/system/fiveclassviewpl?id="+$("#coreid").val(),'_blank','')
                }
            },
            cancel: {
                text: '取消',
                btnClass: 'btn-error'
            }
        }
    });
}


function skugo() {
    window.open(url,'_blank','');
}

function changevalue(type,ntype,obj) {
    $("#"+type).html(" <option value=\"\">无</option>");
    if(type=="lv2") {
        $("#" + ntype).html(" <option value=\"\">无</option>");
    }
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/trade/getByPCode?pcode=" + $(obj).val(),
        "method": "GET",
        "processData": false
    }
    $.ajax(settings).done(function (response) {
        var iii=0;
        response.data.forEach(function (c) {
            if (iii==0) {
                var settings = {
                    "async": true,
                    "crossDomain": true,
                    "url": "/trade/getByPCode?pcode=" + c.id,
                    "method": "GET",
                    "processData": false
                }
                $.ajax(settings).done(function (response) {
                    var ii=0;

                    response.data.forEach(function (b) {
                        if (ii==0) {
                            $("#"+ntype).append("<option value='" + b.id + "' selected>" + b.name + "</option>")
                            ii++;
                        } else {
                            $("#"+ntype).append("<option value='" + b.id + "'>" + b.name + "</option>")
                        }
                    })

                });
                $("#"+type).append("<option value='" + c.id + "' selected>" + c.name + "</option>")
                iii++;
            } else {
                $("#"+type).append("<option value='" + c.id + "'>" + c.name + "</option>")
            }
        })

    });
}

// 行业信息，三级变化时，带出产品简介
function changeLast(obj){
    console.log(obj.value)
}

$('#imgs .drag').each(function(index){
    $(this).myDrag({
        direction:'x'
    });
});

function moveLeft(obj) {
    var index = $(obj).parent().parent().attr("index")
    var htmlCurrent = $(".move_div:eq("+index+")").html();
    var htmlleft = $(".move_div:eq("+(parseInt(index)-1)+")").html();
    $(".move_div:eq("+index+")").html(htmlleft);
    $(".move_div:eq("+(parseInt(index)-1)+")").html(htmlCurrent);
}

function moveRight(obj) {
    var index = $(obj).parent().parent().attr("index")
    console.log("index="+index)
    var htmlCurrent = $(".move_div:eq("+index+")").html();
    var htmlright = $(".move_div:eq("+(parseInt(index)+1)+")").html();
    $(".move_div:eq("+index+")").html(htmlright);
    $(".move_div:eq("+(parseInt(index)+1)+")").html(htmlCurrent);
}
//重新计算序号
function reIndex(){
    $.each($(".move_div"), function(i,val){
        $(this).attr("index",i);
    });
}

// $(".form-row").myDrag({
//     parent:'parent', //定义拖动不能超出的外框,拖动范围
//     randomPosition:true, //初始化随机位置
//     direction:'all', //方向
//     handler:false, //把手
//     dragStart:function(x,y){}, //拖动开始 x,y为当前坐标
//     dragEnd:function(x,y){}, //拖动停止 x,y为当前坐标
//     dragMove:function(x,y){} //拖动进行中 x,y为当前坐标
// });