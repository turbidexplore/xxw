if($.cookie("ACCESS_TOKEN")==undefined){
    window.location.href="/system/login";
}

function logout() {
    $.removeCookie("ACCESS_TOKEN", {path:"/",domain:"chuanqi.lingjianbang.com"});
    window.location.href="/system/login";
}

$("#root").append("<input type=\"button\" class=\"mb-2 btn btn-sm btn-outline-danger mr-1\" onclick=\"tg('"+$("#coreid").val()+"')\" value=\"下一个\">");

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

function init() {
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/productclass/getByLevelCondition",
        "method": "POST",
        "headers": {
            "Authorization": "Bearer " + $.cookie("ACCESS_TOKEN"),
            "cache-control": "no-cache",
            "Postman-Token": "0d83bfb4-ef46-46d6-ae5e-02b7a296a3e2"
        },
        "contentType": "application/json",
        "processData": false,
        "data": "{\n\t\"id\":"+$("#coreid").val()+"\n}"
    }

    $.ajax(settings).done(function (response) {

        var root=response.data[0].route_path.split("//")[1].split("/");
        root.forEach(function (value, index) {
            $("#root").append("("+(index+1)+"级)"+value+">");
        })

        $("#level").val(response.data[0].level);
        $("#root").append(response.data[0].class_name+" | " +response.data[0].level+"级分类   ");
        //("#desc").append(response.data[0].class_desc);
        if(response.data[0].level==5){
            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "/brand/name?id="+response.data[0].brand_id,
                "method": "GET",
                "processData": false,
                "headers": {
                    "Authorization": "Bearer " + $.cookie("ACCESS_TOKEN"),
                    "cache-control": "no-cache",
                    "Postman-Token": "0d83bfb4-ef46-46d6-ae5e-02b7a296a3e2"
                }
            }


            $.ajax(settings).done(function (response) {
                $("#title").append("品牌："+response.data);
            });

            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "/file/workpdf?id="+response.data[0].id,
                "method": "GET",
                "processData": false,
                "headers": {
                    "Authorization": "Bearer " + $.cookie("ACCESS_TOKEN"),
                    "cache-control": "no-cache",
                    "Postman-Token": "0d83bfb4-ef46-46d6-ae5e-02b7a296a3e2"
                }
            }


            $.ajax(settings).done(function (response) {
                $("#level5space").append(
                    "<object width=\"100%\" height=\"800px\" data=\""+response.data.basicurl+response.data.data[0].pdf_file+"\"> \n" +
                    "</object>");
            });





        }
        if(response.data[0].level==4||response.data[0].level==3){

            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "/productclass/levellogo?id="+response.data[0].id,
                "method": "GET",
                "processData": false,
                "headers": {
                    "Authorization": "Bearer " + $.cookie("ACCESS_TOKEN"),
                    "cache-control": "no-cache",
                    "Postman-Token": "0d83bfb4-ef46-46d6-ae5e-02b7a296a3e2"
                }
            }

            $.ajax(settings).done(function (response) {
                response.data.forEach(function (v,i) {
                    $("#level5space").append("<div style='width:165px;height:165px;margin: 20px; ' onclick=\"putlogo('"+v.logo+"')\">\n" +
                        "                              <a style='border-radius: 0px;' href=\"/system/tasklogo?id="+v.id+"\" class=\"card-post__category badge badge-pill badge-warning\">"+v.class_name+"</a>\n"+
                        "                      <div class=\"card card-small card-post card-post--1\">\n" +
                        "                          <div class=\"card-post__image\" style=\"border-radius: 0px;background-image: url('https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/product_class/images/"+v.logo+"');\">\n" +
                        "                          </div>\n" +
                        "                      </div>\n" +
                        "                  </div>" );
                })
            });
        }
    });
}

function putlogo(logo) {
    $("#mylogo").attr("src","https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/product_class/images/"+logo);
    $("#mylogourl").val(logo);
}

function tg(id) {

    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/basedata/tg?level="+ $("#level").val()+"&id="+id,
        "method": "GET",
        "headers": {
            "Authorization": "Bearer " + $.cookie("ACCESS_TOKEN"),
            "cache-control": "no-cache",
            "Postman-Token": "0d83bfb4-ef46-46d6-ae5e-02b7a296a3e2"
        }
    }
    $.ajax(settings).done(function (response) {
        self.location.href="/system/tasklogo?id="+response.data.data[0].taskno;

    });

}

function update() {
    if($("#mylogourl").val()==null||$("#mylogourl").val()==""){
        return;
    }
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/basedata/updatetask?url="+$("#mylogourl").val()+"&id="+$("#coreid").val(),
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
        var settings = {
            "async": true,
            "crossDomain": true,
            "url": "/basedata/task?level="+ $("#level").val(),
            "method": "GET",
            "headers": {
                "Authorization": "Bearer " + $.cookie("ACCESS_TOKEN"),
                "cache-control": "no-cache",
                "Postman-Token": "0d83bfb4-ef46-46d6-ae5e-02b7a296a3e2"
            }
        }
        $.ajax(settings).done(function (response) {
            // if (response.data.data.size()==0){
            //     alert("没有任务了");
            // }
            self.location.href="/system/tasklogo?id="+response.data.data[0].taskno;

        });
    });
}


$("#imgfile").on("change",function(){
    function getObjectURL(file) {
        var url = null;
        if (window.createObjcectURL != undefined) {
            url = window.createOjcectURL(file);
        } else if (window.URL != undefined) {
            url = window.URL.createObjectURL(file);
        } else if (window.webkitURL != undefined) {
            url = window.webkitURL.createObjectURL(file);
        }
        return url;
    }
    var blobURL = getObjectURL(this.files[0]);
    var imgUTL = '';

    var xhr = new XMLHttpRequest();
    xhr.open('GET', blobURL);
    xhr.responseType = 'blob';

    xhr.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            var imageBlob = this.response;
            var imageType = imageBlob.type;
            var imageName = null;
            if (imageType.includes('png')) {
                imageName = 'uploaded-image.png';
            } else if (imageType.includes('gif')) {
                imageName = 'uploaded-image.gif';
            } else if (imageType.includes('bmp')) {
                imageName = 'uploaded-image.bmp';
            } else {
                imageName = 'uploaded-image.jpg';
            }
            var form = new FormData();
            form.append('file', imageBlob, imageName); // 第三个参数为文件名
            $.ajax({
                type: 'POST',
                url : "/file/uploadLogo",
                data: form ,
                processData: false,
                contentType: false,
            }).done(function(result) {
                console.log(result);
                $("#mylogo").attr("src","https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/product_class/images/"+result.data);
                $("#mylogourl").val(result.data);
            });
        }
    };
    xhr.send();
})

init();

$("#myp").on('paste', function(eventObj) {
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
            "headers": {
                "Authorization": "Bearer " + $.cookie("ACCESS_TOKEN"),
                "cache-control": "no-cache",
                "Postman-Token": "0d83bfb4-ef46-46d6-ae5e-02b7a296a3e2"
            },
            success: function(result){
                // var img = document.createElement('img');
                // img.attr("src","https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/product_class/images/"+res.data);
                // editor.appendChild(img);
                $("#mylogo").attr("src","https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/product_class/images/"+result.data);
                $("#mylogourl").val(result.data);
            },
            error: function(){
                alert("上传图片错误");
            }
        });
    }
}