
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

init();
function init() {
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/basedata/mytasks?page="+$("#page").val()+"&size="+$("#size").val()+"&text="+$("#searchtext").val(),
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
        response.data.forEach(function(v,i){
            $("#data").append("  <tr>\n" +
                "                          <td>"+v.class_name+"</td>\n" +
                "                          <td>"+v.level+"级</td>\n" +
                "                          <td>"+v.route_path+"</td>\n" +
                "                          <td><img style='width: 100px;max-height: 100px;' src='https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/product_class/images/"+v.logo+"'></td>\n" +
                "                          <td>"+v.create_time+"</td>\n" +
                "                          <td><button type=\"button\" class=\"mb-2 btn btn-success mr-2\" onclick=\"self.location.href='/system/tasklogo?id="+v.taskno+"'\">修改</button></td>\n" +
                "                        </tr>");
        });

    });
}

function complete(id) {

    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "http://localhost:10001/feedback/complete?id="+id,
        "method": "PUT",
        "headers": {
            "Authorization": "Bearer " + $.cookie("ACCESS_TOKEN"),
            "cache-control": "no-cache",
            "Postman-Token": "0d83bfb4-ef46-46d6-ae5e-02b7a296a3e2"
        }
    }

    $.ajax(settings).done(function (response) {
        alert("已完成该条反馈信息！")
        init();
    });
}