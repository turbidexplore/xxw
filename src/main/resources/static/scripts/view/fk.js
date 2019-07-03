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
init();
function init() {
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/feedback/all",
        "method": "GET",
        "headers": {
            "Authorization": "Bearer " + $.cookie("ACCESS_TOKEN"),
            "cache-control": "no-cache",
            "Postman-Token": "0d83bfb4-ef46-46d6-ae5e-02b7a296a3e2"
        }
    }

    $.ajax(settings).done(function (response) {
        $("#data").html("");
        response.data.forEach(function(v,i){
            var status="<button type=\"button\" style='padding: 2px 10px' class=\"mb-2 btn btn-success mr-2\" onclick='complete("+v.id+")'>完成反馈</button>";
            if(v.status==1){
                status="已处理"
            }
            $("#data").append("  <tr>\n" +
                "                          <td style='padding: 2px 10px' >"+v.title+"</td>\n" +
                "                          <td style='padding: 2px 10px' >"+v.context+"</td>\n" +
                "                          <td style='padding: 2px 10px' ><img style='width: 100px;max-height: 100px;' src='"+v.image+"'></td>\n" +
                "                          <td style='padding: 2px 10px' >"+v.phonenumer+"</td>\n" +
                "                          <td style='padding: 2px 10px' >"+v.email+"</td>\n" +
                "                          <td style='padding: 2px 10px' >"+v.create_time+"</td>\n" +
                "                          <td style='padding: 2px 10px' >"+status+"</td>\n" +
                "                        </tr>");
        });

    });
}

function complete(id) {


    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/feedback/complete?id="+id+"&text=none",
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
