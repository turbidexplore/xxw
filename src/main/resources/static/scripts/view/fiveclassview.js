var yss=[];
var data=[];

function inityueshu() {
    $("#yueshu").html("");
    for(var i =0;i<data.length;i++){
        for(var j =0;j<data.length;j++){
            if(i<j){
                if($("#codename"+(j+1)).length>0){
                var table=" <table  class=\"gridtable\" style='float: left;margin: 10px;' >";
                table+="<tr><td colspan=\"2\" rowspan=\"2\"></td><td colspan=\""+(data[j].rowsize+2)+"\">"+$("#codename"+(j+1)).html()+"</td></tr>";
                table+="<tr>";
                for (var a=0;a<data[j].rowsize;a++){
                    if($("#csvalue"+(j+1)+a+"10h").length>0) {
                        table += "<td>" + $("#csvalue" + (j + 1) + a + "10h").val() + "</td>";
                    }
                }
                table+="</tr>";
                for (var a=0;a<data[i].rowsize;a++){
                    table+="<tr>";
                    if(a==0){
                        table+="<td rowspan=\""+(data[i].rowsize)+"\">"+$("#codename"+(i+1)).html()+"</td>";
                    }
                    if($("#csvalue"+(i+1)+a+"10h").length>0) {
                        table += "<td>" + $("#csvalue" + (i + 1) + a + "10h").val() + "</td>";
                        for (var x = 0; x < data[j].rowsize; x++) {
                            table += "<td style='font-weight: bold;' onclick=\"seleced(this,'csvalue" + (j + 1) + x + "','csvalue" + (i + 1) + a + "')\">◎</td>";
                        }
                    }
                    table+="</tr>";
                }
                table+=" </table>";
                $("#yueshu").append(table);
                }
            }
        }
    }
}

function seleced(obj,value1,value2) {
    if($(obj).html()=="◎"){
        $(obj).css("color","white");
        $(obj).html(yss.length);
        yss.push([value1,value2]);
    }else {
        yss[$(obj).html()]=["0","0"];
        $(obj).css("color","black");
        $(obj).html("◎");
    }
}

function changecodename(obj,value) {
    $("#codename"+value).html(obj.value);
    bdsword();
}
addtable();
function addtable() {
    inityueshu()
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
        "<button type=\"button\" class=\"btn btn-sm btn-success \"  style='height: 20px;line-height: 0px;' onclick=\"addcs('"+data.length+"')\">添加列</button>\n" +
        "</td>\n" +
        "</tr>\n" +
        "<tr id=\"b"+data.length+"\"><td>英文名称</td></tr>\n" +
        "<tr id=\"c"+data.length+"\"><td>代码</td></tr>\n" +
        "<tr id=\"d"+data.length+"\"><td>单位</td></tr>\n" +
        "<tr id=\"e"+data.length+"\"><td>数据类型</td></tr>\n" +
        "<tr id=\"addth"+data.length+"\">\n" +
        "<td colspan=\"2\"><center> <button type=\"button\" class=\"btn btn-sm btn-success \"  style='height: 20px;line-height: 0px;' onclick=\"addrow('"+data.length+"')\" >添加行</button></center></td>\n" +
        "</tr>\n" +
        "</table>");
        bdsword();
}

function addcs(value) {
    inityueshu()
    var colsize=data[value-1].colsize= data[value-1].colsize+1;
    var index=data[value-1].index;
    data[value-1].col.push(index);
    $("#addtd"+value).remove();
    $("#addth"+value).remove();
    $("#gridtable"+value).append(" <tr id=\"addth"+value+"\"><td colspan=\""+colsize+"\"><center> <button type=\"button\"  style='height: 20px;line-height: 0px;' class=\"btn btn-sm btn-success \" onclick=\"addrow("+value+")\">添加行</button></center></td></tr>");
    $("#a"+value).append("<td  id='a"+value+index+"'><input  id='avalue"+value+index+"' type='text' style=\"border-style:none;width: 100px;\" placeholder='中文名称'></td> <td rowspan=\"5\" id=\"addtd"+value+"\"><button type=\"button\"  style='height: 20px;line-height: 0px;' class=\"btn btn-sm btn-success \" onclick=\"addcs("+value+")\">添加列</button></td>");
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
        var unit="<select id='dvalue"+value+index+"' style='height: 20px;line-height: 0px;'>"
        response.data.unit.forEach(function (val) {
            unit=unit+"<option value='"+val.unit_name+"("+val.unit+")"+"'>"+val.unit_name+"("+val.unit+")"+"</option>"
        });
        unit=unit+"</select>";
        $("#d"+value).append("<td  id='d"+value+index+"'>"+unit+"</td>");
        var datatype="<select  id='evalue"+value+index+"' style='height: 20px;line-height: 0px;'>"
        response.data.datatype.forEach(function (val) {
            datatype=datatype+"<option value='"+val.datatype+"'>"+val.datatype+"</option>"
        });
        datatype=datatype+"</select>";
        $("#e"+value).append("<td  id='e"+value+index+"'>"+datatype+"<button type=\"button\"  style='height: 20px;line-height: 0px;' class=\"btn btn-sm btn-danger \" onclick='rm("+value+index+","+value+")'>删除列</button></td>");
        data[value-1].index=data[value-1].index+1;
    });
}
function addrow(value) {
    inityueshu()
    var tds="";
    for(var i=0;i<data[value-1].col.length;i++){
        var index= data[value-1].col[i];
        if($("#avalue"+value+index).length>0){
            if(i==0){
                tds+="<td id='cs"+value+data[value-1].rowsize+index+i+"h'><input oninput='inityueshu()' id='csvalue"+value+data[value-1].rowsize+index+i+"h' type='text' style=\"border-style:none;width: 100px;\" placeholder='参数'></td>";
            }
            tds+="<td id='cs"+value+data[value-1].rowsize+index+i+"'><input  id='csvalue"+value+data[value-1].rowsize+index+i+"' type='text' style=\"border-style:none;width: 100px;\" placeholder='参数'></td>";
        }
    }
    tds+="<td id='cs"+value+data[value-1].rowsize+i+"'><button type=\"button\" style='height: 20px;line-height: 0px;' class=\"btn btn-sm btn-danger \" onclick=\"rmrow('csrow"+value+data[value-1].rowsize+"')\">删除行</button></td>";
    $("#gridtable"+value).append("<tr id=\"csrow"+value+data[value-1 ].rowsize+"\">"+tds+"</tr>");
    data[value-1].rowsize=data[value-1].rowsize+1;
    $("#addth"+value).remove();
    $("#gridtable"+value).append("<tr id=\"addth"+value+"\"><td colspan=\""+data[value-1].colsize+"\"><center><button type=\"button\"  style='height: 20px;line-height: 0px;' class=\"btn btn-sm btn-success \" onclick=\"addrow("+value+")\">添加行</button></center></td></tr>");
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
        "<td colspan=\""+colsize+"\"><center> <button type=\"button\"  style='height: 20px;line-height: 0px;' class=\"btn btn-sm btn-success \" onclick=\"addrow("+value+")\">添加行</button></center></td></tr>");
}

function rmrow(value) {
    inityueshu()
    $("#"+value).remove()
}

function rmbds(value) {
    inityueshu()
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
                    coldata.sku =$("#bdstype"+(x+1)).val()+ $("#csvalue" + (x+1)+i+data[x].col[0]+ "0h").val();
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

   var dke= descartes(bdsdata);
    dke.forEach(function (val) {
        var isok=0;
        val.forEach(function (da) {
            val.forEach(function (db) {
                yss.forEach(function (value) {
                    if(value[0]==da[0].data&&value[1]==db[0].data||value[0]==da[0].data&&value[1]==db[0].data){
                        isok++;
                    }
                });
            })
        })
        if(isok==0){
            var tsku = [];
            val.forEach(function (v) {
                if(v!=undefined) {
                    v.forEach(function (t) {
                        tsku.push(t);
                    })
                }
            })
            sku.push(tsku);
        }
    })

    var dataa=[];
    dataa.push(["sku名称","","","","","","","","","","","","","","","","","","","","",""])
    var dw=0;
    sku.forEach(function (value) {
        var datab=[];
        var datac=["中文名称"];
        var datad=["英文名称"];
        var datae=["代码"];
        var dataf=["单位"];
        var datag=["数据类型"];
        var index=0;
        var skunames=[];
        value.forEach(function (x) {
            skunames.push(x.sku);
            if(index==0){
                datab.push("");
            }else{
                var skuname=""
                distinct(skunames,skunames).forEach(function (name) {
                    skuname+=name;
                })
                datab[0]=skuname;
            }
            datab.push(x.value);
            if(dw==0) {
                datac.push(x.a);
                datad.push(x.b);
                datae.push(x.c);
                dataf.push(x.d);
                datag.push(x.e);
            }
            index++;
        })
        if(dw==0) {
            dataa.push(datac);
            dataa.push(datad);
            dataa.push(datae);
            dataa.push(dataf);
            dataa.push(datag);
            dw++;
        }
            dataa.push(datab);

    })
    $("#example").html("");
    hot = new Handsontable(container, {
        data: dataa,
        minSpareRows:2,//空出多少行
        colHeaders:true,//显示表头　
        contextMenu:true//显示表头下拉菜单
    });
    $("#cz").html("<button type=\"submit\" id=\"bcsj\" class=\"btn btn-accent\" onclick=\"save()\">生成数据</button><button type=\"submit\" id=\"bcsj\" class=\"btn btn-accent\" onclick=\"bcsj()\">保存数据</button>")
}

function distinct(a, b) {
    var arr = a.concat(b);
    for (var i=0, len=arr.length; i<len; i++) {
        for (var j=i+1; j<len; j++) {
            if (arr[i] == arr[j]) {
                arr.splice(j, 1);
                // splice 会改变数组长度，所以要将数组长度 len 和下标 j 减一
                len--;
                j--;
            }
        }
    }
    return arr
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
            "url": "/basedata/bdsclassdata?id="+$("#coreid").val(),
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



//笛卡儿积组合
function descartes(list)
{
    //parent上一级索引;count指针计数
    var point  = {};

    var result = [];
    var pIndex = null;
    var tempCount = 0;
    var temp   = [];

    //根据参数列生成指针对象
    for(var index in list)
    {
        if(typeof list[index] == 'object')
        {
            point[index] = {'parent':pIndex,'count':0}
            pIndex = index;
        }
    }

    //单维度数据结构直接返回
    if(pIndex == null)
    {
        return list;
    }

    //动态生成笛卡尔积
    while(true)
    {
        for(var index in list)
        {
            tempCount = point[index]['count'];
            temp.push(list[index][tempCount]);
        }

        //压入结果数组
        result.push(temp);
        temp = [];

        //检查指针最大值问题
        while(true)
        {
            if(point[index]['count']+1 >= list[index].length)
            {
                point[index]['count'] = 0;
                pIndex = point[index]['parent'];
                if(pIndex == null)
                {
                    return result;
                }

                //赋值parent进行再次检查
                index = pIndex;
            }
            else
            {
                point[index]['count']++;
                break;
            }
        }
    }
}

