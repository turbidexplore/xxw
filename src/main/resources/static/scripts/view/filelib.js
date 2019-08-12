if($.cookie("ACCESS_TOKEN")==undefined){
    window.location.href="/system/login";
}

function logout() {
    $.removeCookie("ACCESS_TOKEN", {path:"/",domain:"chuanqi.lingjianbang.com"});
    window.location.href="/system/login";
}

var settings = {
    "async": true,
    "crossDomain": true,
    "url": "/user/get",
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
        window.location.href="/system/login";
    }else {
        $("#userimage").attr("src",response.data.headportrait);
        $("#username").html(response.data.name);
        if(response.data.ybcount!=0){
            $("#yb").html(response.data.ybcount);
        }
        if(response.data.fkcount!=0) {
            $("#fk").html(response.data.fkcount);
        }
    }
});

function index() {
    $("#page").val(0);
    init();
}

function next() {
    if($("#allpagecount").val()-1>$("#page").val()) {

        $("#page").val( ($("#page").val()*1) +1);
        init();
    }else {
        alert("最后一页了！")
    }
}

function back() {
    if($("#page").val()!=0) {
        $("#page").val($("#page").val() - 1);
        init();
    }else {
        alert("没有了！")
    }
}

function last() {
    $("#page").val( $("#allpagecount").val()-1);
    init();
}

init();
function init() {
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/basedata/filelibs?page="+$("#page").val(),
        "method": "GET",
        "headers": {
            "Authorization": "Bearer " + $.cookie("ACCESS_TOKEN"),
            "cache-control": "no-cache",
            "Postman-Token": "0d83bfb4-ef46-46d6-ae5e-02b7a296a3e2"
        }
    }

    $.ajax(settings).done(function (response) {
        $("#data").html("");
        $("#allpagecount").val(Math.ceil(response.message/20));
        response.data.forEach(function(v,i){
            var urltype=v.url.split(".")[5];
            if(1< v.url.split(",").length){
                urltype="多文件";
            }
            $("#data").append("  <tr>\n" +
                "                          <td style='padding: 2px 10px' >"+v.company_name+"</td>\n" +
                "                          <td style='padding: 2px 10px' >"+v.brand_name+"</td>\n" +
                "                          <td style='padding: 2px 10px' >"+v.title+"</td>\n" +
                "                          <td style='padding: 2px 10px' >"+urltype+"</td>\n" +
                "                          <td style='padding: 2px 10px' >"+v.phone+"</td>\n" +
                "                          <td style='padding: 2px 10px' >"+v.email+"</td>\n" +
                "                          <td style='padding: 2px 10px' >"+v.name+"</td>\n" +
                "                          <td style='padding: 2px 10px' >"+v.create_time+"</td>\n" +
                "                          <td style='padding: 2px 10px' ><button style='padding: 2px 10px'  class=\"mb-2 btn btn-success mr-2\" onclick=\"download('"+v.url+"')\">下载</button></td>\n" +
                "                        </tr>");
        });

    });
}

function download(url){
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/basedata/downloadFilelibs",
        "method": "POST",
        "headers": {
            "Authorization": "Bearer " + $.cookie("ACCESS_TOKEN"),
            "cache-control": "no-cache",
            "Postman-Token": "0d83bfb4-ef46-46d6-ae5e-02b7a296a3e2"
        }
    }
    $.ajax(settings).done(function (response) {
       if(1< url.split(",").length){
           url.split(",").forEach(function (value) {
               window.open(value);
           })
       }else {
           window.open(url);
       }
    });
}
