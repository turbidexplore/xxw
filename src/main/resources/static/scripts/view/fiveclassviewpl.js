if($.cookie("ACCESS_TOKEN")==undefined){
    window.location.href="/system/login";
}

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

    });
}

var data = [//四行五列
    ["中文名称", "产地", "样品单价", "批量价格", "最小包装量", "最小起订量", "质保时间", "样品", "纸质样本", "PDF样本", "3D模型", "2D图纸", "产品视频", "LOGO"],
    ["英文名称", "", "", "", "", "", "", "", "", "", "", "", "", ""],
    ["代码", "", "", "", "", "", "", "", "", "", "", "", "", ""],
    ["单位", "", "", "", "", "", "", "", "", "", "", "", "", ""],
    ["数据类型", "", "", "", "", "", "", "", "", "", "", "", "", ""],
    ["", "", "", "", "","","","","","","",""]
];
var container = document.getElementById('example');
var hot=null;


    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/basedata/getclassdata?id="+$("#coreid").val(),
        "method": "POST",
        "processData": false
    }
    $.ajax(settings).done(function (response) {
        if(response.data!=0){
            hot = new Handsontable(container,
                {
                    data: response.data,
                    autoColumnSize:true,
                    minSpareRows:5,//空出多少行
                    minSpareCols:5,
                    colHeaders:true,//显示表头　
                    contextMenu:true//显示表头下拉菜单
                });
        }else {
            hot = new Handsontable(container,
                {
                    data: data,
                    autoColumnSize:true,
                    minSpareRows:5,//空出多少行
                    minSpareCols:5,
                    colHeaders:true,//显示表头　
                    contextMenu:true//显示表头下拉菜单
                });
        }
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

function goback() {
    location.href = "/system/fiveclass?id="+$("#coreid").val()+"&comid=0";
}

function next() {
    location.href = "/system/skuinfopl?id="+$("#coreid").val();
}

function add() {
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/basedata/parameter",
        "method": "GET",
        "processData": false
    }
    $.ajax(settings).done(function (response) {
        $("#a").append("<td id='a"+index+"'><input onchange='rmcss(this)' oninput=\"enname(this,'bvalue"+index+"')\" id='avalue"+index+"' type='text' style=\"border-style:none;width: 100px;\" placeholder='中文名称'></td></td>");
        $("#b").append("<td id='b"+index+"'><input onchange='rmcss(this)' id='bvalue"+index+"' type='text' style=\"border-style:none;width: 100px;\" placeholder='英文名称'></td>");
        $("#c").append("<td id='c"+index+"'><input onchange='rmcss(this)' id='cvalue"+index+"' type='text' style=\"border-style:none;width: 100px;\" placeholder='代码'></td>");
        var unit="<select id='dvalue"+index+"'><option value=\"无\">默认</option>" ;
        response.data.unit.forEach(function (value) {
            unit=unit+"<option value='"+value.unit_name+"("+value.unit+")"+"'>"+value.unit_name+"("+value.unit+")"+"</option>"
        });
        unit=unit+"</select>";
        $("#d").append("<td ondblclick='rm("+index+")' id='d"+index+"'>"+unit+"</td>");
            var datatype="<select id='evalue"+index+"'>"
        response.data.datatype.forEach(function (value) {
            datatype=datatype+"<option value='"+value.datatype+"'>"+value.datatype+"</option>"
        });
        datatype=datatype+"</select>";
        $("#e").append("<td id='e"+index+"'>"+datatype+"<button type=\"button\"  style='height: 20px;line-height: 0px;margin-left: 15px;' class=\"btn btn-sm btn-danger \" onclick='rm("+index+")' >删除</button></td>");
        index=index+1;
        });
}

function enname(obj,id) {
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/basedata/getenname?text="+$(obj).val(),
        "method": "GET",
        "processData": false
    }
    $.ajax(settings).done(function (response) {
        $("#"+id).val(response.data);
    });
}

function rm(obj) {
    $("#a"+obj).remove()
    $("#b"+obj).remove()
    $("#c"+obj).remove()
    $("#d"+obj).remove()
    $("#e"+obj).remove()
}

function savetable(obj) {
    var index = layer.load();
    $(obj).attr("disabled",true);
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
            "url": "/basedata/bdsclassdatapl?id="+$("#coreid").val(),
            "method": "PUT",
            "processData": false,
            "data":JSON.stringify(data),
            "contentType": "application/json"
        }
        $.ajax(settings).done(function (response) {
            layer.close(index);
            alert(response.message);
            window.open("https://www.lingjianbang.com/level5/"+$("#coreid").val(),'_blank','');
            location.href = "/system/fiveclass?id=0&comid=0";
        });
    }
}

function saveadd() {
    var mydata=[];
    var a=["型号/中文名称", "产地", "样品单价", "批量价格", "最小包装量", "最小起订量", "质保时间", "样品", "纸质样本", "PDF样本", "3D模型", "2D图纸", "产品视频", "LOGO"];
    var b=["型号/英文名称", "", "", "", "", "", "", "", "", "", "", "", "", ""];
    var c=["型号/代码", "", "", "", "", "", "", "", "", "", "", "", "", ""];
    var d=["型号/单位", "", "", "", "", "", "", "", "", "", "", "", "", ""];
    var e=["型号/数据类型", "", "", "", "", "", "", "", "", "", "", "", "", ""];
    for(var i=0;i<index;i++){
            if($("#avalue"+i).val()==""){
                $("#avalue"+i).css("border","1px solid red");
                alert("数据不能为空");
                return;
            }
        if($("#avalue"+i).val()!=null) {
            a.push($("#avalue" + i).val());
        }
        if($("#bvalue"+i).val()!=null) {
            b.push($("#bvalue" + i).val());
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
            autoColumnSize:true,
            minSpareRows:5,//空出多少行
            minSpareCols:5,
            colHeaders:true,//显示表头　
            contextMenu:true//显示表头下拉菜单
        });
}

$("#copy").click(function() {
    var copyText = $("#filepath");//获取对象
    copyText .select();//选择
    document.execCommand("Copy");//执行复制
})

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
            url:"/file/upload",
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
                $("#filepath").val(result.data);
            },
            error: function(){
                alert("上传图片错误");
            }
        });
    }
}



function sc(i) {
    var form = new FormData();
    form.append('file', i.files[0]); // 第三个参数为文件名
    $.ajax({
        type: 'POST',
        url : "/file/upload",
        data: form ,
        processData: false,
        contentType: false,
    }).done(function(result) {
        alert("上传成功！");
        $("#filepath").val(result.data);
    });
}
