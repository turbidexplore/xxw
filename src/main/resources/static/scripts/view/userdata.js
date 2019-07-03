
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
        $("#page").val(($("#page").val()*1)+1);
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
    $("#allpage").html(Math.ceil($("#allcount").val()/$("#size").val()));
    $("#pgshow").html(($("#page").val()*1)+1);
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/user/getAllByPage?page="+$("#page").val()+"&size="+$("#size").val()+"&text="+$("#searchtext").val(),
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
            var name=v.name;
            if(v.name==undefined){
                name="<span style='color: #6c757d;font-size: 12px;'>暂无名称</span>";
            }

            var type=v.type;
            if(v.type=="SeniorAdministrator"){
                type=" <button type=\"button\" style='padding: 2px 10px' class=\"mb-2 btn btn-sm btn-success mr-1\">高级管理员</button>";
            }else if(v.type=="GeneralAdministrator"){
                type="<button type=\"button\" style='padding: 2px 10px' class=\"mb-2 btn btn-sm btn-primary mr-1\">普通管理员</button>";
            }else if(v.type=="GeneralPersonal"){
                type="<button type=\"button\" style='padding: 2px 10px' class=\"mb-2 btn btn-sm btn-info mr-1\">个人用户</button>";
            }else if(v.type=="AdvancedPersonal"){
                type="<button type=\"button\" style='padding: 2px 10px' class=\"mb-2 btn btn-sm btn-secondary mr-1\">高级个人用户</button>";
            }else if(v.type=="GeneralCompany"){
                type="<button type=\"button\" style='padding: 2px 10px' class=\"mb-2 btn btn-sm btn-danger mr-1\">企业用户</button>";
            }else if(v.type=="AdvancedCompany"){
                type="<button type=\"button\" style='padding: 2px 10px' class=\"mb-2 btn btn-sm btn-warning mr-1\">高级企业用户</button>";
            }

            var status=v.status;
            if(v.status=="Normal"){
                status="<button type=\"button\" style='padding: 2px 10px' class=\"mb-2 btn btn-sm btn-outline-primary mr-1\">状态正常</button>";
            }else if(v.status=="Abnormal"){
                status="<button type=\"button\" style='padding: 2px 10px' class=\"mb-2 btn btn-sm btn-outline-danger mr-1\">状态异常</button>";
            }else if(v.status=="Checking"){
                status="<button type=\"button\" style='padding: 2px 10px' class=\"mb-2 btn btn-sm btn-outline-warning mr-1\">检查中</button>";
            }else if(v.status=="auth"){
                status="<button type=\"button\" style='padding: 2px 10px' class=\"mb-2 btn btn-sm btn-outline-success mr-1\">已认证</button>";
            }
            $("#data").append("<tr>" +
                "                          <td style='padding: 5px'><img class=\"user-avatar rounded-circle mr-2\" style='width: 30px;max-height: 30px;margin-right: 10px;' src='"+v.headportrait+"'>"+v.nikename+"</td>" +
                "                          <td style='padding: 5px'>"+name+"</td>" +
                "                          <td style='padding: 5px'>"+v.phonenumber+"</td>" +
                "                          <td style='padding: 5px'>"+type+"</td>" +
                "                          <td style='padding: 5px'>"+status+"</td>" +
                "                          <td style='padding: 5px'>"+v.create_time+"</td>" +
                "                          <td style='padding: 5px'>test</td>" +
                "                        </tr>");
        });
    });
}

Highcharts.setOptions({
    global: {
        useUTC: false
    }
});
function activeLastPointToolip(chart) {
    var points = chart.series[0].points;
    chart.tooltip.refresh(points[points.length -1]);
}
var chart = Highcharts.chart('container', {
    chart: {
        type: 'spline',
        marginRight: 10,
        events: {
            load: function () {
                var series = this.series[0],
                    chart = this;
                activeLastPointToolip(chart);
                setInterval(function () {
                    var x = (new Date()).getTime();
                    $.ajax({
                        url: '/usersecurity/userdata_count',
                        type: 'GET',     // 请求类型，常用的有 GET 和 POST
                        data: {
                            // 请求参数
                        },
                        success: function(res) { // 接口调用成功回调函数
                            // data 为服务器返回的数据
                            series.addPoint([x, res.data], true, true);
                        }
                    });
                    activeLastPointToolip(chart);
                }, 1000*60);
            }
        }
    },
    title: {
        text: '用户注册实时数据'
    },
    xAxis: {
        type: 'datetime',
        tickPixelInterval: 150
    },
    yAxis: {
        title: {
            text: null
        }
    },
    tooltip: {
        formatter: function () {
            return '<b>' + this.series.name + '</b><br/>' +
                Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                Highcharts.numberFormat(this.y, 0)+'人';
        }
    },
    legend: {
        enabled: false
    },
    series: [{
        name: '用户增长数据',
        data: (function () {
            // 生成随机值
            var data = [],
                time = (new Date()).getTime(),
                i;
            for (i = -19; i <= 0; i += 1) {
                data.push({
                    x: time + i * 1000,
                    y: (function () {
                        $.ajax({
                            url: '/usersecurity/userdata_count',
                            type: 'GET',     // 请求类型，常用的有 GET 和 POST
                            data: {
                                // 请求参数
                            },
                            success: function(res) { // 接口调用成功回调函数
                                // data 为服务器返回的数据
                                return  res.data;
                            }
                        });
                    })
                });
            }
            return data;
        }())
    }]
});

$.ajax({
    url: '/user/getUserCount',
    type: 'GET',     // 请求类型，常用的有 GET 和 POST
    data: {
        // 请求参数
    },
    success: function(res) { // 接口调用成功回调函数
        $("#allpage").html(Math.ceil(res.data[0]/$("#size").val()));
        $("#allpagecount").val(Math.ceil(res.data[0]/$("#size").val()));
        $("#allcount").val(res.data[0]);
        var chart = Highcharts.chart('container1', {
            chart: {
                type: 'column',
                margin: 10,
                options3d: {
                    enabled: true,
                    alpha: 10,
                    beta: 25,
                    depth: 70,
                    viewDistance: 100,      // 视图距离，它对于计算角度影响在柱图和散列图非常重要。此值不能用于3D的饼图
                    frame: {                // Frame框架，3D图包含柱的面板，我们以X ,Y，Z的坐标系来理解，X轴与 Z轴所形成
                        // 的面为bottom，Y轴与Z轴所形成的面为side，X轴与Y轴所形成的面为back，bottom、
                        // side、back的属性一样，其中size为感官理解的厚度，color为面板颜色
                        bottom: {
                            size: 10
                        },
                        side: {
                            size: 1,
                            color: 'transparent'
                        },
                        back: {
                            size: 1,
                            color: 'transparent'
                        }
                    }
                },
            },
            title: {
                text: '用户注册人数概述统计'
            },
            subtitle: {
                text: '零件邦用户'
            },
            xAxis: {
                categories: ['所有注册人数','今年注册人数', '本月注册人数', '今日注册人数']
            },
            series: [{
                type: 'column',
                colorByPoint: true,
                data: res.data,
                showInLegend: false
            }]
        });
    }
});
