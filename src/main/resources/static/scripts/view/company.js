if($.cookie("ACCESS_TOKEN")==undefined){
    window.location.href="/system/login";
}

function logout() {
    $.removeCookie("ACCESS_TOKEN", {path:"/",domain:"chuanqi.lingjianbang.com"});
    window.location.href="/system/login";
}
function areas(pid,id,value) {
    if(id=="country"||id=="province"||id=="city"){
        $("#district").html("");
        $("#district").append("<option value='0'>请选择...</option>")
    }
    if(id=="province"||id=="country"){
        $("#city").html("");
        $("#city").append("<option value='0'>请选择...</option>")
    }
    if(id=="country"){
        $("#province").html("");
        $("#province").append("<option value='0'>请选择...</option>")
    }
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/basedata/areas?pid="+pid,
        "method": "GET",
        "processData": false,
        "headers": {
            "Authorization": "Bearer " + $.cookie("ACCESS_TOKEN"),
            "cache-control": "no-cache",
            "Postman-Token": "0d83bfb4-ef46-46d6-ae5e-02b7a296a3e2"
        }
    }

    $.ajax(settings).done(function (response) {
        $("#"+id).html("");
        $("#"+id).append("<option value='0'>请选择...</option>")
        response.data.forEach(function (v,i) {
            if(v.id==value){
                $("#"+id).append("<option value='"+v.id+"' selected>"+v.area_name+"</option>")
            }else {
                $("#"+id).append("<option value='"+v.id+"'>"+v.area_name+"</option>")
            }
        })
    });
}
function areas1(pid,id,value) {
    if(id=="country"||id=="province"||id=="city"){
        $("#district").html("");
        $("#district").append("<option value='0'>请选择...</option>")
    }
    if(id=="province"||id=="country"){
        $("#city").html("");
        $("#city").append("<option value='0'>请选择...</option>")
    }
    if(id=="country"){
        $("#province").html("");
        $("#province").append("<option value='0'>请选择...</option>")
    }
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/basedata/areas?pid="+pid,
        "method": "GET",
        "processData": false,
        "headers": {
            "Authorization": "Bearer " + $.cookie("ACCESS_TOKEN"),
            "cache-control": "no-cache",
            "Postman-Token": "0d83bfb4-ef46-46d6-ae5e-02b7a296a3e2"
        }
    }

    $.ajax(settings).done(function (response) {
        $("#"+id).html("");
        $("#"+id).append("<option value='0'>请选择...</option>")
        response.data.forEach(function (v,i) {
            if(v.id==value){
                $("#"+id).append("<option value='"+v.id+"' selected>"+v.area_name+"</option>")
            }else {
                $("#"+id).append("<option value='"+v.id+"'>"+v.area_name+"</option>")
            }

        })
    });
}

init();
function init() {
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

    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/company/incomplete",
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
        $("#company_logo").attr("src", "https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/temp/" + response.data.company.logo);
        $("#company_logo_url").val(response.data.company.logo);
        $("#company_name_title").html(response.data.company.company_name);
        $("#company_name").val(response.data.company.company_name);
        $("#company_en").val(response.data.company.company_en);
        $("#company_law").val(response.data.company.company_law);
        $("#company_addr").val(response.data.company.company_addr);
        $("#company_capital").val(response.data.company.company_capital);
        $("#company_url").val(response.data.company.company_url);
        $("#company_url1").val(response.data.company.company_url1);
        $("#contact_man").val(response.data.company.contact_man);
        $("#contact_phone").val(response.data.company.contact_phone);
        $("#contact_phone1").val(response.data.company.contact_phone1);
        $("#contact_email").val(response.data.company.contact_email);
        $("#company_desc").html(response.data.company.company_desc);
        $("#unified_code").val(response.data.company.unified_code);
        $("#paper_code").val(response.data.company.paper_code);
        $("#electron_code").val(response.data.company.electron_code);
        $("#id").val(response.data.company.id);
        areas1(0, "country", response.data.company.country);
        areas1(response.data.company.country, "province", response.data.company.province);
        areas1(response.data.company.province, "city", response.data.company.city);
        areas1(response.data.company.city, "district", response.data.company.district);
        response.data.brands.forEach(function (v,i) {
            $("#data").append("  <tr>\n" +
                "                          <td><input style='width: 80px;' id=\"name"+v.id+"\" type='text' value='"+v.brand_name+"'></td>\n" +
                "                          <td><img style='width: 100%;' id='mylogo"+v.id+"' src='https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/brand/logo/"+v.brand_logo+"'>" +
                " <br><input onchange='sc("+v.id+",this)' type=\"file\" id=\"imgfile"+v.id+"\" style=\"width: 100%\"  /><input type='hidden' id='mylogourl"+v.id+"' value='"+v.brand_logo +"'></td>\n" +
                "                          <td id=\""+v.id+"country\"></td>\n" +
                "                          <td id=\""+v.id+"province\"></td>\n" +
                "                          <td id=\""+v.id+"city\"></td>\n" +
                "<td><button type=\"button\" class=\"mb-2 btn btn-outline-success mr-2\" onclick=\"gx("+v.id+")\">更新</button></td>"+
                "                        </tr>");
            $("#"+v.id+"country").append(" <select  onchange=\"areas2(this.value,'province"+v.id+"',0,"+v.id+")\"  id=\"country"+v.id+"\" class=\"form-control\">\n" +
                "                                  </select>")
            $("#"+v.id+"province").append(" <select  onchange=\"areas2(this.value,'city"+v.id+"',0,"+v.id+")\"  id=\"province"+v.id+"\" class=\"form-control\">\n" +
                "                                  </select>")
            $("#"+v.id+"city").append(" <select    id=\"city"+v.id+"\" class=\"form-control\">\n" +
                "                                  </select>")

            areas2(0, "country"+v.id,v.country_id,v.id);
            areas2(v.country_id, "province"+v.id, v.province_id,v.id);
            areas2(v.province_id, "city"+v.id, v.city_id,v.id);
        });
    });

}

function areas2(pid,id,value,x) {

    if(id=="province"+x||id=="country"+x){
        $("#city"+x).html("");
        $("#city"+x).append("<option value='0'>请选择...</option>")
    }
    if(id=="country"+x){
        $("#province"+x).html("");
        $("#province"+x).append("<option value='0'>请选择...</option>")
    }
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/basedata/areas?pid="+pid,
        "method": "GET",
        "processData": false,
        "headers": {
            "Authorization": "Bearer " + $.cookie("ACCESS_TOKEN"),
            "cache-control": "no-cache",
            "Postman-Token": "0d83bfb4-ef46-46d6-ae5e-02b7a296a3e2"
        }
    }

    $.ajax(settings).done(function (response) {
        $("#"+id).html("");
        $("#"+id).append("<option value='0'>请选择...</option>")
        response.data.forEach(function (v,i) {
            if(v.id==value){
                $("#"+id).append("<option value='"+v.id+"' selected>"+v.area_name+"</option>")
            }else {
                $("#"+id).append("<option value='"+v.id+"'>"+v.area_name+"</option>")
            }

        })
    });
}
function update() {
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/company/update",
        "method": "PUT",
        "processData": false,
        "contentType":"application/json; charset=UTF-8",
        "headers": {
            "Authorization": "Bearer " + $.cookie("ACCESS_TOKEN"),
            "cache-control": "no-cache",
            "Postman-Token": "0d83bfb4-ef46-46d6-ae5e-02b7a296a3e2"
        },
        "data":JSON.stringify( {
            "id":$("#id").val(),
            "company_name":$("#company_name").val(),
            "company_en":$("#company_en").val(),
            "company_law":$("#company_law").val(),
            "company_addr":$("#company_addr").val(),
            "company_capital":$("#company_capital").val(),
            "company_url":$("#company_url").val(),
            "company_url1":$("#company_url1").val(),
            "contact_man":$("#contact_man").val(),
            "contact_phone":$("#contact_phone").val(),
            "contact_phone1":$("#contact_phone1").val(),
            "contact_email":$("#contact_email").val(),
            "company_desc":$("#company_desc").val(),
            "country":$("#country").val(),
            "city":$("#city").val(),
            "district":$("#district").val(),
            "province":$("#province").val(),
            "logo":$("#company_logo_url").val(),
            "unified_code":$("#unified_code").val(),
            "paper_code":$("#paper_code").val(),
            "electron_code":$("#electron_code").val(),
            "status":"2"
        })
    }
    $.ajax(settings).done(function (response) {
        window.location.reload();
    });
}

function hg(status) {
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/company/update",
        "method": "PUT",
        "processData": false,
        "headers": {
            "Authorization": "Bearer " + $.cookie("ACCESS_TOKEN"),
            "cache-control": "no-cache",
            "Postman-Token": "0d83bfb4-ef46-46d6-ae5e-02b7a296a3e2"
        },
        "data":JSON.stringify( {
            "id":$("#id").val(),
            "status":status
        })
    }
    $.ajax(settings).done(function (response) {
        window.location.reload();
    });
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
            url:"/file/uploadCompanyLogo",
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
                $("#company_logo").attr("src","https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/temp/"+result.data);
                $("#company_logo_url").val(result.data);
            },
            error: function(){
                alert("上传图片错误");
            }
        });
    }
}

function gx(id) {
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/brand/update",
        "method": "PUT",
        "processData": false,
        "contentType": "application/json",
        "headers": {
            "Authorization": "Bearer " + $.cookie("ACCESS_TOKEN"),
            "cache-control": "no-cache",
            "Postman-Token": "0d83bfb4-ef46-46d6-ae5e-02b7a296a3e2"
        },
        "data":JSON.stringify( {
            "id":id,
            "brand_name":$("#name"+id).val(),
            "country_id":$("#country"+id).val(),
            "province_id":$("#province"+id).val(),
            "city_id":$("#city"+id).val(),
            "brand_logo":$("#mylogourl"+id).val()
        })
    }
    $.ajax(settings).done(function (response) {
        alert("更新成功！")
    });
}

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

function sc(id,i) {
    var blobURL = getObjectURL(i.files[0]);//这里的objURL就是input file的blob:路径
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
                url : "/file/uploadBrandLogo",
                data: form ,
                processData: false,
                contentType: false,
            }).done(function(result) {
                console.log(result);
                $("#mylogo"+id).attr("src","https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/brand/logo/"+result.data);
                $("#mylogourl"+id).val(result.data);
            });
        }
    };
    xhr.send();
}