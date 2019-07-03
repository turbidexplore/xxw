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

var data = [//四行五列
    ["批量上传试例","参数中文名称", "", "", "", "","","","","","","",""],
    ["-","参数英文名称", "", "", "", "","","","","","","",""],
    ["-","参数代码", "", "", "", "","","","","","","",""],
    ["-","参数单位", "", "", "", "","","","","","","",""],
    ["-","参数数据类型", "", "", "", "","","","","","","",""],
    ["型号值","", "", "", "", "","","","","","","",""]
];
var container = document.getElementById('example');
var hot=null;

var settings = {
    "async": true,
    "crossDomain": true,
    "url": "/productclass/getOne",
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

    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/basedata/getclassdata?id="+response.data.id,
        "method": "POST",
        "processData": false
    }
    $.ajax(settings).done(function (response) {
        if(response.data!=0){
            hot = new Handsontable(container,
                {
                    data: response.data,
                    minSpareRows:2,//空出多少行
                    colHeaders:true,//显示表头　
                    contextMenu:true//显示表头下拉菜单
                });
        }else {
            hot = new Handsontable(container,
                {
                    data: data,
                    minSpareRows:2,//空出多少行
                    colHeaders:true,//显示表头　
                    contextMenu:true//显示表头下拉菜单
                });
        }
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

function rmcss(obj) {
    $(obj).css("border","0px solid #fff");
}

function add() {
    $("#addtd").remove();

    $("#a").append("<td ondblclick='rm("+index+")' id='a"+index+"'><input oninput='rmcss(this)' id='avalue"+index+"' type='text' style=\"border-style:none;width: 100px;\" placeholder='中文名称'></td> <td rowspan=\"5\" id=\"addtd\"><button type=\"button\" class=\"btn btn-sm btn-success \" onclick=\"add()\">+</button><br/> <button type=\"button\" class=\"btn btn-sm btn-danger \" onclick=\"saveadd()\">=</button></td>");
    $("#b").append("<td ondblclick='rm("+index+")' id='b"+index+"'><input oninput='rmcss(this)' id='bvalue"+index+"' type='text' style=\"border-style:none;width: 100px;\" placeholder='英文名称'></td>");
    $("#c").append("<td ondblclick='rm("+index+")' id='c"+index+"'><input oninput='rmcss(this)' id='cvalue"+index+"' type='text' style=\"border-style:none;width: 100px;\" placeholder='代码'></td>");
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/basedata/parameter",
        "method": "GET",
        "processData": false
    }

    $.ajax(settings).done(function (response) {
        var unit="<select id='dvalue"+index+"'>"
        response.data.unit.forEach(function (value) {
            unit=unit+"<option value='"+value.unit_name+"("+value.unit+")"+"'>"+value.unit_name+"("+value.unit+")"+"</option>"
        });
        unit=unit+"</select>";
        $("#d").append("<td ondblclick='rm("+index+")' id='d"+index+"'>"+unit+"</td>");

        var datatype="<select  id='evalue"+index+"'>"
        response.data.datatype.forEach(function (value) {
            datatype=datatype+"<option value='"+value.datatype+"'>"+value.datatype+"</option>"
        });
        datatype=datatype+"</select>";
        $("#e").append("<td ondblclick='rm("+index+")' id='e"+index+"'>"+datatype+"</td>");
        index=index+1;
    });
}

function rmimgs(my) {
    $(my).remove();
}

function rm(obj) {
    $("#a"+obj).remove()
    $("#b"+obj).remove()
    $("#c"+obj).remove()
    $("#d"+obj).remove()
    $("#e"+obj).remove()
}

function savetable() {
    var count=hot.countRows();
    var data=[];
    for (i=0;i<=count;i++){
        if(!hot.isEmptyRow(i)) {
            data.push(hot.getDataAtRow(i));
        }
    }

    if (data.length>0){
        var settings = {
            "async": true,
            "crossDomain": true,
            "url": "/basedata/saveclassdata?id="+$("#coreid").val(),
            "method": "PUT",
            "processData": false,
            "data":JSON.stringify(data),
            "contentType": "application/json"
        }
        $.ajax(settings).done(function (response) {
            alert(response.message);

        });
    }
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

function saveadd() {
    var mydata=[];
    var a=["批量上传试例","参数中文名称"];
    var b=["-","参数英文名称"];
    var c=["-","参数英文名称"];
    var d=["-","参数英文名称"];
    var e=["-","参数英文名称"];
    for(var i=0;i<index;i++){
        if($("#avalue"+i).val()==""||$("#bvalue"+i).val()==""){
            if($("#avalue"+i).val()==""){
                $("#avalue"+i).css("border","1px solid red");
            }
            if($("#bvalue"+i).val()==""){
                $("#bvalue"+i).css("border","1px solid red");
            }
            alert("数据不能为空");
            return;
        }
        if($("#avalue"+i).val()!=null) {
            a.push($("#avalue" + i).val());
        }
        if($("#bvalue"+i).val()!=null) {
            b.push($("#bvalue" + i).val())
        }
        if($("#cvalue"+i).val()!=null) {
            c.push($("#cvalue" + i).val());
        }
        if($("#dvalue"+i).val()!=null) {
            d.push($("#dvalue" + i).val());
        }
        if($("#evalue"+i).val()!=null) {
            e.push($("#evalue" + i).val());
        }
    }
    mydata.push(a);
    mydata.push(b);
    mydata.push(c);
    mydata.push(d);
    mydata.push(e);

    if(a.length<hot.getDataAtRow(5).length){
        a.push("");
        for (var i=0;i<hot.getDataAtRow(5).length-a.length;i++){
            a.push("");
        }
    }
    var count=hot.countRows();
    for (i=5;i<=count;i++){
        if(!hot.isEmptyRow(i)) {
            mydata.push(hot.getDataAtRow(i));
        }
    }
    $("#example").html("");
    hot = new Handsontable(container,
        {
            data: mydata,
            minSpareRows:2,//空出多少行
            colHeaders:true,//显示表头　
            contextMenu:true//显示表头下拉菜单
        });
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
    $.ajax(settings).done(function (response) {});
}

function gobds(){
    window.open("/system/fiveclassview?id="+$("#coreid").val(), "_blank", "scrollbars=yes,resizable=1,modal=false,alwaysRaised=yes");
}