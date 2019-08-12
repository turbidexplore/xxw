function logout() {
    $.removeCookie("ACCESS_TOKEN", {path:"/",domain:"chuanqi.lingjianbang.com"});
    $.removeCookie("ACCESS_TOKEN",{path:"/"});
    window.location.href="/system/login";
}

if($.cookie("ACCESS_TOKEN")==undefined){
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
    $("#data").html("");
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/basedata/task?level=3",
        "method": "GET",
        "headers": {
            "Authorization": "Bearer " + $.cookie("ACCESS_TOKEN"),
            "cache-control": "no-cache",
            "Postman-Token": "0d83bfb4-ef46-46d6-ae5e-02b7a296a3e2"
        }
    }

    $.ajax(settings).done(function (response) {
        $("#todaycount").html(response.data.todaycount);
        $("#okcount").html(response.data.okcount);
        $("#ingcount").html(response.data.ingcount);
        $("#allcount").html(response.data.allcount);
        $("#count").html(response.data.count);
        $("#level3").html(response.data.level3);
        $("#level4").html(response.data.level4);
        $("#level5").html(response.data.level5);
        $("#countb").html(response.data.countb);
        // $("#level3b").html(response.data.level3b);
        // $("#level4b").html(response.data.level4b);
        // $("#level5b").html(response.data.level5b);
        response.data.todaydata.forEach(function (v,i) {
            if(i==0){
                $("#todaydata").append("<div class=\"col-lg-1 col-md-6 col-sm-12 mb-4\" >\n" +
                    "                <div class=\"card card-small card-post card-post--1\">\n" +
                    "                  <div class=\"card-post__image\" style=\"background-image: url("+v.headportrait+");\">\n" +
                    "                    <a href=\"#\" class=\"card-post__category badge badge-pill badge-warning\" style='margin-top: 120px;'> 总数:"+v.all+" </a>\n" +
                    "                  </div>\n" +
                    "                    <h5 class=\"card-title\" style=\"padding-top: 15px;padding-left: 15px;\">\n" +
                    "                      <a class=\"text-fiord-blue\" href=\"#\"> "+v.name+" <br> <span style='font-size: 15px;font-weight: normal;'>完成"+v.today+"个</span></a>\n" +
                    "                    </h5>\n" +
                    "<a class=\"btn btn-sm btn-white\" style='background-color: #cc9000;color: white;' href=\"#\">\n" +
                    "                        ♛ 今日之星 </a>"+
                    "                </div>\n" +
                    "              </div>");
            }else if(i==1){
                $("#todaydata").append("<div class=\"col-lg-1 col-md-6 col-sm-12 mb-4\" >\n" +
                    "                <div class=\"card card-small card-post card-post--1\">\n" +
                    "                  <div class=\"card-post__image\" style=\"background-image: url("+v.headportrait+");\">\n" +
                    "                    <a href=\"#\" class=\"card-post__category badge badge-pill badge-info\" style='margin-top: 120px;'> 总数:"+v.all+" </a>\n" +
                    "                  </div>\n" +
                    "                    <h5 class=\"card-title\" style=\"padding-top: 15px;padding-left: 15px;\">\n" +
                    "                      <a class=\"text-fiord-blue\" href=\"#\">"+v.name+" <br> <span style='font-size: 15px;font-weight: normal;'>完成"+v.today+"个</span></a>\n" +
                    "                    </h5>\n" +
                    "<a class=\"btn btn-sm btn-white\" href=\"#\">\n" +
                    "                        <i class=\"far fa-bookmark mr-1\"></i> No."+(i+1)+" </a>"+
                    "                </div>\n" +
                    "              </div>");
            }else if(i==2){
                $("#todaydata").append("<div class=\"col-lg-1 col-md-6 col-sm-12 mb-4\" >\n" +
                    "                <div class=\"card card-small card-post card-post--1\">\n" +
                    "                  <div class=\"card-post__image\" style=\"background-image: url("+v.headportrait+");\">\n" +
                    "                    <a href=\"#\" class=\"card-post__category badge badge-pill badge-info\" style='margin-top: 120px;'> 总数:"+v.all+" </a>\n" +
                    "                  </div>\n" +
                    "                    <h5 class=\"card-title\" style=\"padding-top: 15px;padding-left: 15px;\">\n" +
                    "                      <a class=\"text-fiord-blue\" href=\"#\">"+v.name+" <br>  <span style='font-size: 15px;font-weight: normal;'>完成"+v.today+"个</span></a>\n" +
                    "                    </h5>\n" +
                    "<a class=\"btn btn-sm btn-white\" href=\"#\">\n" +
                    "                        <i class=\"far fa-bookmark mr-1\"></i> No."+(i+1)+" </a>"+
                    "                </div>\n" +
                    "              </div>");
            }else {
                $("#todaydata").append("<div class=\"col-lg-1 col-md-6 col-sm-12 mb-4\" >\n" +
                    "                <div class=\"card card-small card-post card-post--1\">\n" +
                    "                  <div class=\"card-post__image\" style=\"background-image: url("+v.headportrait+");\">\n" +
                    "                    <a href=\"#\" class=\"card-post__category badge badge-pill badge-dark\" style='margin-top: 120px;'> 总数:"+v.all+" </a>\n" +
                    "                  </div>\n" +
                    "                    <h5 class=\"card-title\" style=\"padding-top: 15px;padding-left: 15px;\">\n" +
                    "                      <a class=\"text-fiord-blue\" href=\"#\">"+v.name+" <br>  <span style='font-size: 15px;font-weight: normal;'>完成"+v.today+"个</span></a>\n" +
                    "                    </h5>\n" +
                    "<a class=\"btn btn-sm btn-white\" href=\"#\">\n" +
                    "                        <i class=\"far fa-bookmark mr-1\"></i> No."+(i+1)+" </a>"+
                    "                </div>\n" +
                    "              </div>");
            }
        })
        response.data.data.forEach(function(v,i) {
            $("#data").append(" <div class=\"blog-comments__item d-flex p-3\"  onclick=\"self.location.href='\/system\/tasklogo?id="+v.taskno+"'\">\n" +
                "                      <div class=\"blog-comments__content\">\n" +
                "                        <div class=\"blog-comments__meta text-muted\">\n" +
                "                         <button class=\"mb-2 btn btn-primary mr-2\" type=\"button\" onclick=\"self.location.href='\/system\/tasklogo?id="+v.taskno+"'\">三级"+
                "</button> <a class=\"text-secondary\" href=\"#\">补全产品类型的LOGO信息|3级</a> \n" +
                "                          <span class=\"text-muted\">"+v.create_time+"</span>\n" +
                "                        </div>\n" +
                // "                        <p class=\"m-0 my-1 mb-2 text-muted\">Well, the way they make shows is, they make one show ...</p>\n" +
                "                        <div class=\"blog-comments__actions\">\n" +
                "                          <div class=\"btn-group btn-group-sm\">\n" +
                "\n" +
                "                        </div>\n" +
                "                      </div>\n" +
                "                    </div>\n" +
                "                  </div>"
            );
        });

    });

    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/basedata/task?level=4",
        "method": "GET",
        "headers": {
            "Authorization": "Bearer " + $.cookie("ACCESS_TOKEN"),
            "cache-control": "no-cache",
            "Postman-Token": "0d83bfb4-ef46-46d6-ae5e-02b7a296a3e2"
        }
    }

    $.ajax(settings).done(function (response) {

        response.data.data.forEach(function(v,i) {
            $("#data").append(" <div class=\"blog-comments__item d-flex p-3\"  onclick=\"self.location.href='\/system\/tasklogo?id="+v.taskno+"'\">\n" +
                "                      <div class=\"blog-comments__content\">\n" +
                "                        <div class=\"blog-comments__meta text-muted\">\n" +
                "                         <button class=\"mb-2 btn btn-primary mr-2\" type=\"button\" onclick=\"self.location.href='\/system\/tasklogo?id="+v.taskno+"'\">四级"+
                "</button> <a class=\"text-secondary\" href=\"#\">补全产品类型的LOGO信息|4级</a> \n" +
                "                          <span class=\"text-muted\">"+v.create_time+"</span>\n" +
                "                        </div>\n" +
                // "                        <p class=\"m-0 my-1 mb-2 text-muted\">Well, the way they make shows is, they make one show ...</p>\n" +
                "                        <div class=\"blog-comments__actions\">\n" +
                "                          <div class=\"btn-group btn-group-sm\">\n" +

                "                        </div>\n" +
                "                      </div>\n" +
                "                    </div>\n" +
                "                  </div>"
            );
        });

    });

    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/basedata/task?level=5",
        "method": "GET",
        "headers": {
            "Authorization": "Bearer " + $.cookie("ACCESS_TOKEN"),
            "cache-control": "no-cache",
            "Postman-Token": "0d83bfb4-ef46-46d6-ae5e-02b7a296a3e2"
        }
    }

    $.ajax(settings).done(function (response) {

        response.data.data.forEach(function(v,i) {

            $("#data").append(" <div class=\"blog-comments__item d-flex p-3\"  onclick=\"self.location.href='\/system\/tasklogo?id="+v.taskno+"'\">\n" +
                "                      <div class=\"blog-comments__content\">\n" +
                "                        <div class=\"blog-comments__meta text-muted\">\n" +
                "                         <button class=\"mb-2 btn btn-primary mr-2\" type=\"button\" onclick=\"self.location.href='\/system\/tasklogo?id="+v.taskno+"'\">五级"+
                "</button> <a class=\"text-secondary\" href=\"#\">补全产品类型的LOGO信息|5级</a> \n" +
                "                          <span class=\"text-muted\">"+v.create_time+"</span>\n" +
                "                        </div>\n" +
                // "                        <p class=\"m-0 my-1 mb-2 text-muted\">Well, the way they make shows is, they make one show ...</p>\n" +
                "                        <div class=\"blog-comments__actions\">\n" +
                "                          <div class=\"btn-group btn-group-sm\">\n" +
                "                        </div>\n" +
                "                      </div>\n" +
                "                    </div>\n" +
                "                  </div>"
            );
        });

    });


}