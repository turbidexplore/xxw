
var canGetCookie = 1;//是否支持存储Cookie 0 不支持 1 支持

$(document).keypress(function (e) {
    // 回车键事件
    if (e.which == 13) {
        $('input[type="button"]').click();
    }
});
//粒子背景特效
$('body').particleground({
    dotColor: '#E8DFE8',
    lineColor: '#1b3273'
});
$('input[name="pwd"]').focus(function () {
    $(this).attr('type', 'password');
});
$('input[type="text"]').focus(function () {
    $(this).prev().animate({ 'opacity': '1' }, 200);
});
$('input[type="text"],input[type="password"]').blur(function () {
    $(this).prev().animate({ 'opacity': '.5' }, 200);
});
$('input[name="login"],input[name="pwd"]').keyup(function () {
    var Len = $(this).val().length;
    if (!$(this).val() == '' && Len >= 5) {
        $(this).next().animate({
            'opacity': '1',
            'right': '30'
        }, 200);
    } else {
        $(this).next().animate({
            'opacity': '0',
            'right': '20'
        }, 200);
    }
});
var open = 0;
layui.use('layer', function () {

    //非空验证
    $('input[type="button"]').click(function () {
        var login = $('.username').val();
        var pwd = $('.passwordNumder').val();
        // var code = $('.ValidateNum').val();
        if (login == '') {
            ErroAlert('请输入您的账号');
            return false;
        } else if (pwd == '') {

            ErroAlert('请输入密码');
            return false;
        } else {
            //认证中..
            $('.login').addClass('test'); //倾斜特效
            setTimeout(function () {
                $('.login').addClass('testtwo'); //平移特效
            }, 300);
            setTimeout(function () {
                $('.authent').show().animate({ right: -320 }, {
                    easing: 'easeOutQuint',
                    duration: 600,
                    queue: false
                });
                $('.authent').animate({ opacity: 1 }, {
                    duration: 200,
                    queue: false
                }).addClass('visible');
            }, 500);
            //登陆
            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "/usersecurity/loginx",
                "method": "POST",
                "headers": {
                    "Content-Type": "application/json",
                    "cache-control": "no-cache",
                    "Postman-Token": "d70bd28b-ff25-46ef-9dd3-20de37945efe"
                },
                "processData": false,
                "data": JSON.stringify({
                    "username":login,
                    "password":pwd,
                    "login_type":"password"
                })
            }

            $.ajax(settings).done(function (response) {
                if (response.status==200) {
                    var token = response.data.access_token||response.data.value;
                    $.cookie("ACCESS_TOKEN","", {expires: -1,path:"/",domain:"chuanqi.lingjianbang.com"});
                    $.cookie("ACCESS_TOKEN", token, {expires: 7,path:"/",domain:"chuanqi.lingjianbang.com"});
                    $.cookie("ACCESS_TOKEN", token,{expires: 7,path:"/"});
                    setTimeout(function () {
                        window.location.href="/system/index";
                    }, 1000);
                }else{
                    var msgalert = '登录失败！帐号或密码错误';
                    var index = layer.alert(msgalert, { icon: 5, time: 4000, offset: 't', closeBtn: 0, title: '友情提示', btn: [], anim: 2, shade: 0 });
                    layer.style(index, {
                        color: '#777'
                    });
                    $('.authent').hide();
                    $('.login').removeClass("testtwo");
                    $('.login').removeClass('test');
                    setTimeout(function () {
                        window.location.href="/";
                    },500);
                }
                //跳转页面
            });
        }
        return false;
    })
})


