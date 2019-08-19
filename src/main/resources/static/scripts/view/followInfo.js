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
        "url": "/count/followInfo?page="+$("#page").val(),
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
            var type=v.type;
            if(v.type=="SeniorAdministrator"){
                type="高级管理员";
            }else if(v.type=="GeneralAdministrator"){
                type="普通管理员";
            }else if(v.type=="GeneralPersonal"){
                type="个人用户";
            }else if(v.type=="AdvancedPersonal"){
                type="高级个人用户";
            }else if(v.type=="GeneralCompany"){
                type="企业用户";
            }else if(v.type=="AdvancedCompany"){
                type="高级企业用户";
            }
            var name=v.name;
            if(name==undefined){
                name="无";
            }
            $("#data").append("  <tr>\n" +
                "                          <td style='padding: 2px 10px' >"+v.company_name+"</td>\n" +
                "                          <td style='padding: 2px 10px' >"+v.class_name+"</td>\n" +
                "                          <td style='padding: 2px 10px' >"+name+"</td>\n" +
                "                          <td style='padding: 2px 10px' >"+v.nikename+"</td>\n" +
                "                          <td style='padding: 2px 10px' >"+type+"</td>\n" +
                "                          <td style='padding: 2px 10px' >"+v.userinfo+"</td>\n" +
                "                          <td style='padding: 2px 10px' >"+v.ip+"</td>\n" +
                "                          <td style='padding: 2px 10px' >"+v.create_time+"</td>\n" +
                "                        </tr>");
        });

    });
}

