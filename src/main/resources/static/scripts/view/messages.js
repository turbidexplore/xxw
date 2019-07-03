function logout() {
    $.removeCookie("ACCESS_TOKEN", {path:"/",domain:"chuanqi.lingjianbang.com"});
    window.location.href="/system/login";
}

var data = [//四行五列
    ["", "", "", "", "","",""],

];
var container = document.getElementById('example');
var hot = new Handsontable(container,
    {
        data: data,
        minSpareRows:5,//空出多少行
        colHeaders:true,//显示表头　
        contextMenu:true//显示表头下拉菜单
    });



init();
function init() {
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/user/get",
        "method": "GET",
        "processData": false,
        "headers": {
            "Authorization": "Bearer "+ $.cookie("ACCESS_TOKEN"),
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

    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/basedata/getemail",
        "method": "GET",
        "processData": false
    }

    $.ajax(settings).done(function (response) {
        $("#context").html(response.data);
    });

}

function savetable() {
    var count=hot.countRows();

    for (i=0;i<=count;i++){
        if(!hot.isEmptyRow(i)) {
            var scount=hot.getDataAtRow(i).length;
            for(a=0;a<=scount;a++){
                if(""!=hot.getDataAtRow(i)[a]&&null!=hot.getDataAtRow(i)[a]&&"发送成功"!=hot.getDataAtRow(i)[a]) {
                    var data=[];
                    data.push($("#context").html());
                    data.push(hot.getDataAtRow(i)[a]);
                    var settings = {
                        "async": true,
                        "crossDomain": true,
                        "url": "/basedata/sendmail",
                        "method": "POST",
                        "processData": false,
                        "data":JSON.stringify(data),
                        "contentType": "application/json"
                    }

                    $.ajax(settings).done(function (response) {

                    });
                    hot.setDataAtCell(i,(a+1),"发送成功","");

                }
            }
        }
    }

}

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
            url:"/file/ocr",
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
                $("#images").attr("src","https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/ocr/"+result.data);
                $("#images_url").val("https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/ocr/"+result.data);
            },
            error: function(){
                alert("上传图片错误");
            }
        });
    }
}


function changeimg(obj) {
    if($("#images_url").val()!="") {
        obj.src = $("#images_url").val();
        obj.style.border="2px solid green ";

    }
}

function changeword(obj) {
    if($("#word").val()!="") {
        obj.innerHTML = $("#word").val();
        obj.style.border="2px solid green ";
    }
}

function changeurl(obj) {
    if($("#url").val()!="") {
        obj.href = $("#url").val();
        obj.style.border="2px solid green ";
    }
}

function addchat(obj) {
    $("#"+obj).append(" <ul style=\"list-style: none;display: flex;margin:0;padding:0\"  >\n" +
        "                        <li style=\"flex: 1;margin-bottom:10px;list-style: none;\">\n" +
        "                          <div style=\"width:90%;margin:0 auto;\">\n" +
        "                            <img style=\"width: 100%;\" onclick=\"changeimg(this)\" src=\"https://iph.href.lu/600x300?text=New Info\">\n" +
        "                          </div>\n" +
        "                          <div style=\"width:90%;margin:0 auto;\">\n" +
        "                            <p style=\"position: relative;font-size: 13px;line-height: 18px;max-height: 108px;overflow: hidden;\" onclick=\"changeword(this)\">\n" +
        "                              文字" +
        "                            </p>\n" +
        "                          </div>\n" +
        "                          <div style=\"text-align: center;\">\n" +
        "                            <a ondrag=\"changeurl(this)\" style=\"display:block;width:90%;margin: 10px auto;padding: 10px 0;color:#fff;background-color: #f70;font-size:12px;text-decoration:none;\" href=\"#\">\n" +
        "                              跳转对应内容>>\n" +
        "                            </a>\n" +
        "                          </div>\n" +
        "                        </li>\n" +
        "                        <li style=\"flex: 1;margin-bottom:10px;list-style: none;\">\n" +
        "                          <div style=\"width:90%;margin:0 auto;\">\n" +
        "                            <img onclick=\"changeimg(this)\" style=\"width: 100%;\" src=\"https://iph.href.lu/600x300?text=New Info\">\n" +
        "                          </div>\n" +
        "                          <div style=\"width:90%;margin:0 auto;\">\n" +
        "                            <p style=\"position: relative;font-size: 13px;line-height: 18px;max-height: 108px;overflow: hidden;\" onclick=\"changeword(this)\">\n" +
        "                              文字" +
        "                            </p>\n" +
        "                          </div>\n" +
        "                          <div style=\"text-align: center;\">\n" +
        "                            <a ondrag=\"changeurl(this)\"  style=\"display:block;width:90%;margin: 10px auto;padding: 10px 0;color:#fff;background-color: #f70;font-size:12px;text-decoration:none;\" href=\"#\">\n" +
        "                              跳转对应内容>>\n" +
        "                            </a>\n" +
        "                          </div>\n" +
        "                        </li>\n" +
        "                        <li style=\"flex: 1;margin-bottom:10px;list-style: none;\">\n" +
        "                          <div style=\"width:90%;margin:0 auto;\">\n" +
        "                            <img onclick=\"changeimg(this)\" style=\"width: 100%;\" src=\"https://iph.href.lu/600x300?text=New Info\">\n" +
        "                          </div>\n" +
        "                          <div style=\"width:90%;margin:0 auto;\">\n" +
        "                            <p style=\"position: relative;font-size: 13px;line-height: 18px;max-height: 108px;overflow: hidden;\" onclick=\"changeword(this)\">\n" +
        "                              文字" +
        "                            </p>\n" +
        "                          </div>\n" +
        "                          <div style=\"text-align: center;\">\n" +
        "                            <a ondrag=\"changeurl(this)\" style=\"display:block;width:90%;margin: 10px auto;padding: 10px 0;color:#fff;background-color: #f70;font-size:12px;text-decoration:none;\" href=\"#\">\n" +
        "                              跳转对应内容>>\n" +
        "                            </a>\n" +
        "                          </div>\n" +
        "                        </li>\n" +
        "                      </ul>\n");
}