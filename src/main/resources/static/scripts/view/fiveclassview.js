var yss=[];
var ys=[];
var a=0;
var b=0;
var ao=null;
var bo=null;
var color=["#FFFF00","#FFA500","#FF4040"," #FF1493","#FA8072","#D1EEEE",
    "#CAFF70","#8B8B00","#8B1A1A","#32CD32","#0D0D0D","#0000EE"," #8B1A1A"," #8B8378","#9AC0CD","#B03060","#EED8AE"];
function yueshu(obj,value,rowsize) {
    if (a == value) {
        $(obj).css("border","0px solid #fff");
        ys[0]=null;
        a=0;
        ao=null;
        return;
    }
    if (b==value) {
        $(obj).css("border","0px solid #fff");
        ys[1]=null;
        b=0;
        bo=null;
        return;
    }
    if (a == 0) {
        a = value;
        ao=obj;
        ys.push("csvalue"+value+""+rowsize)
        $(obj).css("border", "2px solid red");
    } else {
        b = value;
        bo=obj;
        ys.push("csvalue"+value + "" + rowsize)
        $(obj).css("border", "2px solid red");
    }

    if(ao!=null&&bo!=null){
        $(ao).css("border","0px solid #fff");
        $(bo).css("border","0px solid #fff");
        a=0;
        b=0;
        var aoo=ao;
        var boo=bo;
        ao=null;
        bo=null;
        var isok=0;
        yss.forEach(function (value1) {
            if(value1[0]==ys[0]&&value1[1]==ys[1]||value1[0]==ys[1]&&value1[1]==ys[0]){
                isok++;
            }
        });
        if(isok>0){
            alert("约束已存在");
            return;
        }
        $(aoo).css("border","2px solid "+color[yss.length]);
        $(boo).css("border","2px solid "+color[yss.length]);
        yss.push(ys);
        alert("已添加约束");
        ys=[];
    }


}


var data=[];
function changecodename(obj,value) {
    $("#codename"+value).html(obj.value);
    bdsword();
}
addtable();
function addtable() {
    data.push({"index":1,"col":[],"colsize":2,"rowsize":0});
    $("#bds").append("<div id=\"bds"+data.length+"\" style=\"margin: 10px;width: 82px;border: 1px solid grey;float: left;\">" +
        "<select style=\"width: 80px;border-style:none;\" id='bdstype"+data.length+"' onchange='bdsword()'>\n" +
        "<option value=\"\">默认</option>\n" +
        "<option value=\".\">.</option>\n" +
        "<option value=\"-\">-</option>\n" +
        "<option value=\"*\">*</option>\n" +
        "</select><br>\n" +
        "<input id='bdsname"+data.length+"' oninput='changecodename(this,"+data.length+")' style=\"width: 80px;border-style:none;\" value='A"+data.length+"' type='text'  placeholder='中文名称'>\n" +
        "<button type=\"button\" style=\"width: 80px;border-style:none;border-radius: 0\" class=\"btn btn-sm btn-danger \" onclick=\"rmbds("+data.length+")\">删除</button>"+
        "</div>");
    $("#tables").append(" <table  class=\"gridtable\" style='float: left;margin: 10px;' id=\"gridtable"+data.length+"\" >\n" +
        "<tr id=\"a"+data.length+"\">\n" +
        "<td><span style='font-weight: bold;color: red;' id='codename"+data.length+"'>[A"+data.length+"]</span>/中文名称</td>\n" +
        "<td rowspan=\"5\" id=\"addtd"+data.length+"\">\n" +
        "<button type=\"button\" class=\"btn btn-sm btn-success \" onclick=\"addcs('"+data.length+"')\">添加列</button>\n" +
        "</td>\n" +
        "</tr>\n" +
        "<tr id=\"b"+data.length+"\"><td>英文名称</td></tr>\n" +
        "<tr id=\"c"+data.length+"\"><td>代码</td></tr>\n" +
        "<tr id=\"d"+data.length+"\"><td>单位</td></tr>\n" +
        "<tr id=\"e"+data.length+"\"><td>数据类型</td></tr>\n" +
        "<tr id=\"addth"+data.length+"\">\n" +
        "<td colspan=\"2\"><center> <button type=\"button\" class=\"btn btn-sm btn-success \" onclick=\"addrow('"+data.length+"')\" >添加行</button></center></td>\n" +
        "</tr>\n" +
        "</table>")
    bdsword();
}

function addcs(value) {
    var colsize=data[value-1].colsize= data[value-1].colsize+1;
    var index=data[value-1].index;
    data[value-1].col.push(index);
    $("#addtd"+value).remove();
    $("#addth"+value).remove();
    $("#gridtable"+value).append(" <tr id=\"addth"+value+"\">\n" +
        "<td colspan=\""+colsize+"\"><center> <button type=\"button\" class=\"btn btn-sm btn-success \" onclick=\"addrow("+value+")\">添加行</button></center></td>\n" +
        "</tr>");
    $("#a"+value).append("<td  id='a"+value+index+"'><input  id='avalue"+value+index+"' type='text' style=\"border-style:none;width: 100px;\" placeholder='中文名称'></td> <td rowspan=\"5\" id=\"addtd"+value+"\"><button type=\"button\" class=\"btn btn-sm btn-success \" onclick=\"addcs("+value+")\">添加列</button></td>");
    $("#b"+value).append("<td  id='b"+value+index+"'><input  id='bvalue"+value+index+"' type='text' style=\"border-style:none;width: 100px;\" placeholder='英文名称'></td>");
    $("#c"+value).append("<td id='c"+value+index+"'><input  id='cvalue"+value+index+"' type='text' style=\"border-style:none;width: 100px;\" placeholder='代码'></td>");
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/basedata/parameter",
        "method": "GET",
        "processData": false
    }
    $.ajax(settings).done(function (response) {
        var unit="<select id='dvalue"+value+index+"'>"
        response.data.unit.forEach(function (val) {
            unit=unit+"<option value='"+val.unit_name+"("+val.unit+")"+"'>"+val.unit_name+"("+val.unit+")"+"</option>"
        });
        unit=unit+"</select>";
        $("#d"+value).append("<td  id='d"+value+index+"'>"+unit+"</td>");
        var datatype="<select  id='evalue"+value+index+"'>"
        response.data.datatype.forEach(function (val) {
            datatype=datatype+"<option value='"+val.datatype+"'>"+val.datatype+"</option>"
        });
        datatype=datatype+"</select>";
        $("#e"+value).append("<td  id='e"+value+index+"'>"+datatype+"<button type=\"button\" class=\"btn btn-sm btn-danger \" onclick='rm("+value+index+","+value+")'>删除列</button></td>");
        data[value-1].index=data[value-1].index+1;
    });
}
function addrow(value) {
    var tds="";
    for(var i=0;i<data[value-1].col.length;i++){
        var index= data[value-1].col[i];
        if($("#avalue"+value+index).length>0){
            if(i==0){
                tds+="<td id='cs"+value+data[value-1].rowsize+index+i+"h'><input  id='csvalue"+value+data[value-1].rowsize+index+i+"h' type='text' style=\"border-style:none;width: 100px;\" placeholder='参数'></td>";
            }
            tds+="<td id='cs"+value+data[value-1].rowsize+index+i+"'><input  id='csvalue"+value+data[value-1].rowsize+index+i+"' type='text' style=\"border-style:none;width: 100px;\" placeholder='参数'></td>";
        }
    }
    tds+="<td id='cs"+value+data[value-1].rowsize+i+"'><button type=\"button\" class=\"btn btn-sm btn-danger \" onclick=\"rmrow('csrow"+value+data[value-1].rowsize+"')\">删除行</button></td>";
    $("#gridtable"+value).append("<tr ondblclick='yueshu(this,"+value+","+data[value-1 ].rowsize+")'  id=\"csrow"+value+data[value-1 ].rowsize+"\">"+tds+"</tr>");
    data[value-1].rowsize=data[value-1].rowsize+1;
    $("#addth"+value).remove();
    $("#gridtable"+value).append("<tr id=\"addth"+value+"\"><td colspan=\""+data[value-1].colsize+"\"><center><button type=\"button\" class=\"btn btn-sm btn-success \" onclick=\"addrow("+value+")\">添加行</button></center></td></tr>");
}

function rm(obj,value) {
    $("#a"+obj).remove()
    $("#b"+obj).remove()
    $("#c"+obj).remove()
    $("#d"+obj).remove()
    $("#e"+obj).remove()
    var colsize=data[value-1].colsize= data[value-1].colsize-1;
    $("#addth"+value).remove();
    $("#gridtable"+value).append(" <tr id=\"addth"+value+"\">\n" +
        "<td colspan=\""+colsize+"\"><center> <button type=\"button\" class=\"btn btn-sm btn-success \" onclick=\"addrow("+value+")\">添加行</button></center></td></tr>");
}

function rmrow(value) {
    $("#"+value).remove()
}

function rmbds(value) {
    $("#bds"+value).remove();
    $("#gridtable"+value).remove();
    bdsword();
}

function bdsword() {
    var word="   ";
    for (var i=1;i<=data.length;i++){if($("#bdsname"+i).val()!=""&&$("#bdsname"+i).val()!=undefined) {word += $("#bdstype"+i).val() + "  {" + $("#bdsname"+i).val() + "}  ";}}
    $("#bdsword").html(word);
}

var container = document.getElementById('example');
var hot=null;

function save() {
    var bdsdata= new Array();
    var sku= new Array();
    for (var x=0;x<data.length;x++){
        var skudata =  new Array();
        for(var i=0;i<data[x].rowsize;i++){
            var rowdata =  new Array();
            for (var a = 0; a < data[x].col.length; a++) {
                if($("#avalue" +(x+1)+data[x].col[a]).length>0) {
                    var coldata = {};
                    coldata.sku = $("#csvalue" + (x+1)+i+data[x].col[0]+ "0h").val();
                    coldata.value = $("#csvalue" + (x+1)+ i+data[x].col[a]+ a).val();
                    coldata.a=$("#avalue" +(x+1)+data[x].col[a]).val();
                    coldata.b=$("#bvalue" +(x+1)+data[x].col[a]).val();
                    coldata.c=$("#cvalue" +(x+1)+data[x].col[a]).val();
                    coldata.d=$("#dvalue" +(x+1)+data[x].col[a]).val();
                    coldata.e=$("#evalue" +(x+1)+data[x].col[a]).val();
                    coldata.data="csvalue"+(x+1)+ i;
                    rowdata.push(coldata);
                }
            }
            skudata.push(rowdata);
        }
        bdsdata.push(skudata);
    }
    for(var i=0;i<bdsdata.length;i++){
        for (var a=0;a<bdsdata.length;a++){
            if(i<a) {
                for (var b=0;b<bdsdata[i].length;b++){
                    for (var d=0;d<bdsdata[a].length;d++){
                        var isok=0;
                        yss.forEach(function (value) {
                            if(value[0]==bdsdata[i][b][0].data&&value[1]==bdsdata[a][d][0].data||value[0]==bdsdata[a][d][0].data&&value[1]==bdsdata[i][b][0].data){
                                isok++;
                            }
                        });
                        if(isok==0){
                            var tsku = [];
                            var ad = bdsdata[i][b];
                            ad.forEach(function (v, c) {
                                tsku.push(v);
                            });
                            var cd = bdsdata[a][d];
                            cd.forEach(function (v, c) {
                                tsku.push(v);
                            });
                            sku.push(tsku);
                        }
                    }
                }
            }
        }
    }
    var dataa=[];
    dataa.push(["sku名称","","","","","","","","","","","","","","","","","","","","",""])
    sku.forEach(function (value) {
        var datab=[];
        var datac=["中文名称"];
        var datad=["英文名称"];
        var datae=["代码"];
        var dataf=["单位"];
        var datag=["数据类型"];
        var index=0;
        var over=0;
        value.forEach(function (x) {
            if(index==0){
                datab.push(x.sku);
                datab.push(x.value);
            }else{
                if(datab[0]!=x.sku&&over==0){
                    datab[0]=datab[0]+"/"+x.sku;
                    over++;
                }
                datab.push(x.value);
            }
            datac.push(x.a);
            datad.push(x.b);
            datae.push(x.c);
            dataf.push(x.d);
            datag.push(x.e);
            index++;
        })
        dataa.push(datac);
        dataa.push(datad);
        dataa.push(datae);
        dataa.push(dataf);
        dataa.push(datag);
        dataa.push(datab);
    })
    hot = new Handsontable(container, {
        data: dataa,
        minSpareRows:2,//空出多少行
        colHeaders:true,//显示表头　
        contextMenu:true//显示表头下拉菜单
    });
    $("#mydata").append("<button type=\"submit\" id=\"bcsj\" class=\"btn btn-accent\" onclick=\"bcsj()\">保存数据</button>")
    $("#bcsj").remove();
}

function bcsj() {
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
            "url": "/basedata/bdsclassdata?id="+$("#coreid").val()+"&text="+$("#bdsword").html(),
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
