if($.cookie("ACCESS_TOKEN")==undefined){
    window.location.href="/system/login";
}

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


var settings = {
    "async": true,
    "crossDomain": true,
    "url": "/productclass/getOne?id="+$("#coreid").val(),
    "method": "GET",
    "processData": false,
    "headers": {
        "Authorization": "Bearer " + $.cookie("ACCESS_TOKEN"),
        "cache-control": "no-cache",
        "Postman-Token": "0d83bfb4-ef46-46d6-ae5e-02b7a296a3e2"
    }
}
$.ajax(settings).done(function (response) {
    if(response.status!=200){
        window.location.href="/system/nodata";
    }
    if(response.data.skutype==1){
        location.href = "/system/fiveclassview?id="+response.data.id;
    }else if(response.data.skutype==2){
        location.href = "/system/fiveclassviewpl?id="+response.data.id;
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

    $("#company").html("公司:<span style='font-weight: bold;'> "+response.data.company_name +" </span>  " +
        "品牌:<span style='font-weight: bold;'> "+response.data.brand_name+" </span>");
    $("#add_user").html("创建者:["+response.data.add_user+"] <button type=\"button\" onclick=\"save(1)\"  class=\"mb-2 btn btn-sm btn-outline-warning mr-1\">  跳 过  </button>&nbsp;&nbsp;" +
        "              <button type=\"button\" onclick=\"save(2)\"  class=\"mb-2 btn btn-outline-success mr-2\">  完  成 </button>&nbsp;&nbsp;");
    $("#desc").val(response.data.class_desc);
    $("#level5space").append(
        "<object width=\"100%\" height=\"900px\" data=\""+"https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/product_class/pdf/"+response.data.pdf_file+"\"> \n" +
        "</object>");


    response.data.images.forEach(function (value){
        $("#imgs").append("<div onclick='rmimgs(this)'  style='float: left;margin: 10px;'><input type='hidden' value='" + value.url + "'><img src=\"https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/product_class/images/" +value.url+ "\" alt=\"logo\"  width=\"80px;\"></div>");

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
                $("#imgs").append("<div onclick='rmimgs(this)'  style='float: left;margin: 10px;'><input type='hidden' value='" + result.data + "'><img src=\"https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/product_class/images/" +result.data+ "\" alt=\"logo\"  width=\"80px;\"></div>");
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
    var data ={"id":$("#coreid").val(),"desc":$("#desc").val(),"imgs":imgs};
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
        go();
    });
}

function gobds(){
    window.open("/system/fiveclassview?id="+$("#coreid").val(), "_blank", "scrollbars=yes,resizable=1,modal=false,alwaysRaised=yes");
}


function go() {
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
                    location.href = "/system/fiveclassview?id="+$("#coreid").val();
                }
            },
            pl: {
                text: '批量',
                btnClass: 'btn-primary',
                action: function() {
                    location.href = "/system/fiveclassviewpl?id="+$("#coreid").val();
                }
            },
            cancel: {
                text: '取消',
                btnClass: 'btn-error'
            }
        }
    });
}
