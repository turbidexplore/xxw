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
    "url": "/count/userfiveclass",
    "method": "GET",
    "processData": false
}

$.ajax(settings).done(function (response) {
   response.data.forEach(function (v) {
       $("#todaydata").append("<div class=\"col-lg-2 col-md-6 col-sm-12 mb-4\" onclick=\"look('"+v.uname+"',this)\">\n" +
           "                <div class=\"card card-small card-post card-post--1\">\n" +
           "                  <div class=\"card-post__image\" style=\"background-image: url("+v.headportrait+");\">\n" +
           "                    <a href=\"#\" class=\"card-post__category badge badge-pill badge-warning\" style='margin-top: 120px;'> 总数:"+v.all+"/剩余:"+v.not+" </a>\n" +
           "                  </div>\n" +
           "                    <h5 class=\"card-title\" style=\"padding-top: 15px;padding-left: 15px;\">\n" +
           "                      <a class=\"text-fiord-blue\" href=\"#\" > "+v.name+" <br> <span style='font-size: 15px;font-weight: normal;'>今日完成"+v.day+"个</span></a>\n" +
           "                    </h5>\n" +
           "                </div>\n" +
           "              </div>");
   })
});

function look(user,obj) {
    $(obj).css("border","solid 2px red;")
    $("#uname").val(user);
    init();
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
        if(response.data.ybcount!=0){
            $("#yb").html(response.data.ybcount);
        }
        if(response.data.fkcount!=0) {
            $("#fk").html(response.data.fkcount);
        }
        $("#userimage").attr("src",response.data.headportrait);
        $("#username").html(response.data.name);
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

function newinit(){
    $("#uname").val("");
    init();
}


init();
function init() {
    var tp=$("#type").val();
    var size=$("#size").val();
    if($("#uname").val()!=""){
        tp=2;
        size=99999;
    }
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/basedata/myfiveclass?page="+$("#page").val()+"&size="+size+"&text="+$("#searchtext").val()+"&type="+tp+"&uname="+$("#uname").val(),
        "method": "GET",
        "headers": {
            "Authorization": "Bearer " + $.cookie("ACCESS_TOKEN"),
            "cache-control": "no-cache",
            "Postman-Token": "0d83bfb4-ef46-46d6-ae5e-02b7a296a3e2"
        }
    }

    $.ajax(settings).done(function (response) {

        $("#data").html("");
        $("#allpagecount").val(Math.ceil(response.message/$("#size").val()));
        response.data.data.forEach(function(v,i){
            var cz="<button type=\"button\" class=\"mb-2 btn btn-success mr-2\" onclick=\"self.location.href='/system/fiveclass?id=0"+v.id+"&comid=0'\">修改</button>";
            if(v.phonenumber!=response.data.user){
                 cz="<button type=\"button\" class=\"mb-2 btn btn-success mr-2\" onclick=\"window.open('https://www.lingjianbang.com/level5/"+v.id+"','_blank','')\">预览</button>";
            }
            $("#data").append("  <tr>\n" +
                "                          <td>"+v.id+"</td>\n" +
                "                          <td><span style='color: red;font-weight: bold;'>"+v.brand_name+"</span> <br>"+v.class_name+"</td>\n" +
                "                          <td>"+v.name+"</td>\n" +
                "                          <td>"+v.level+"级</td>\n" +
                "                          <td>"+v.route_path+"</td>\n" +
                "                          <td><img style='width: 50px;max-height: 50px;' src='https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/product_class/images/"+v.logo+"'></td>\n" +
                "                          <td>"+v.skutime+"</td>\n" +
                "                          <td>"+cz+"</td>\n" +
                "                        </tr>");
        });
        if($("#uname").val()!=""){
            alert("共"+response.data.data.length+"条数据！");
        }

    });
}