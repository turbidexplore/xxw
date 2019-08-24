docker hub账号和密码：zcl1024126430/zcl1024126430
github账号和密码：1024126430@qq.com/Z*l1024126430
gitee账号和密码：zcl1024126430/zcl1024126430

docker操作：
docker build -t zcl1024126430/xxw:120 .  
docker login;  
docker image push zcl1024126430/xxw:120  
docker run -it --rm zcl1024126430/xxw:120  

约束上全黑，全白按钮

总表达式:
{
    A1:{
       表达式名称：A1
       表达式符号：-
        {
            title:[
                    [中文名称,英文名称,代码,单位,数据类型],
                    [螺杆驱动,lgqd,lgqd,克,默认],
                ],
                rows:[
                    [N,普通级,-0.02,+0.02],
                ]
            }          
    },
    A2:{
            表达式名称：A2
            表达式符号：-
            {
                title:[
                    [中文名称,英文名称,代码,单位,数据类型],
                    [精密等级,jmdj,jmdj,默认,勾选],
                    [重复定位精度,cfdwjd,cfdwjd,毫米(mm),公差],
                ],
                rows:[
                    [N,普通级,-0.02,+0.02],
                    [P,精密级,-0.005,+0.005]
                ]
             }           
        }
}

约束：

skuinfo

中大力德

TODO:
加入验证
加入约束
对主SKU进行表达式计算


计算总数
加入数据类型(x)
将约束的数据移除
保存表达式
保存约束

页面初始化