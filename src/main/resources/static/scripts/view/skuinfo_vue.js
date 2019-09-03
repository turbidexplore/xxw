var mapType = new Map();
// inputtype=0表示输入框，inputtype=1表示下拉框，inputtype=2表示上传控件
mapType.set('0',{name:'产地',inputtype:0});
mapType.set('1',{name:'样品单价',inputtype:0});
mapType.set('2',{name:'批量单价',inputtype:0});
mapType.set('3',{name:'最小包装量',inputtype:0});
mapType.set('4',{name:'最小起订量',inputtype:0});
mapType.set('5',{name:'质保时间',inputtype:1,typeValues:[{name:'未提供',value:'未提供'},{name:'12个月',value:'12个月'},{name:'24个月',value:'24个月'},{name:'36个月',value:'36个月'},{name:'36+',value:'36+'},{name:'终身质保',value:'终身质保'}]});
mapType.set('6',{name:'样品',inputtype:1,typeValues:[{name:'免费&免运费',value:'免费&免运费'},{name:'免费&运费到付',value:'免费&运费到付'},{name:'收费&免运费',value:'收费&免运费'},{name:'收费&运费到付',value:'收费&运费到付'}]});
mapType.set('7',{name:'纸质样本',inputtype:1,typeValues:[{name:'免费&免运费',value:'免费&免运费'},{name:'免费&运费到付',value:'免费&运费到付'}]});
mapType.set('8',{name:'pdf样本',inputtype:2});
mapType.set('9',{name:'3D模型',inputtype:2});
mapType.set('10',{name:'2D模型',inputtype:2});
mapType.set('11',{name:'产品视频',inputtype:2});
mapType.set('12',{name:'logo',inputtype:2});

var app = new Vue({
    el: '#app',
    data: {
        skuRules:[],
        skuNamesList: [],
    },
    methods: {
        init(){
            var _this = this;
            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "/basedata/getexpressbyclassid?classId=" + $("#coreid").val(),
                "method": "GET",
                "processData": false,
                "contentType": "application/json"
            }
            $.ajax(settings).done(function (response) {
                if(response.status ==200&&response.data.bisexpressjson!=null&&response.data.bisexpressjson!=''){
                    _this.skuNamesList = JSON.parse(response.data.bisexpressjson);
                }else{
                    _this.initData()
                }
                // console.log(response)
            });
        },
        initData(){
            let rules = [];
            for(let i=0;i<this.skuRules.length;i++){
                let skuRule = this.skuRules[i];
                rules.push('')
            }
            for (let [key, value] of mapType.entries()) {
                let tvalue = '';
                // 质保时间
                if(key==='1'||key==='2'){
                    tvalue='未提供';
                }else if(key==='5'){
                    tvalue='12个月';
                }else if(key==='3'||key==='4'){
                    // 最小包装量,最小起订量,
                    tvalue='1';
                }else if(key==='5'||key==='6'||key==='7'){
                    tvalue='免费&免运费';
                }

                this.skuNamesList.push({
                    index:key, // 序号
                    skuname:value.name, // skuname
                    inputtype:value.inputtype, // skunametype
                    typeValues:value.typeValues, // typeValues
                    typeValue:[tvalue],  // 默认是空
                    rules:[Object.assign({},rules)] // 默认是SKU规则的长度
                })
            }
            // console.log(this.skuNamesList)
        },
        save(){
            console.log(JSON.stringify(this.skuNamesList))
            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "/productclass/saveProductSkuInfos?classId="+$("#coreid").val(),
                "method": "PUT",
                "processData": false,
                "data":JSON.stringify({productskuinfos:JSON.stringify(this.skuNamesList)}),
                "contentType": "application/json"
            }

            $.ajax(settings).done(function (res) {
                alert(res.data);
                // alert("已保存"+(values.length-5)+"条数据！");
                window.open("https://www.lingjianbang.com/level5/"+$("#coreid").val(),'_blank','');
                location.href = "/system/fiveclass?id=0&comid=0";

            });
        },
        rm(index,index2){
            this.skuNamesList[index].rules.splice(index2,1);
            this.skuNamesList[index].typeValue.splice(index2,1);
        },
        addSkuRule(index){
            let rules = [];
            for(let i=0;i<this.skuRules.length;i++){
                let skuRule = this.skuRules[i];
                rules.push('')
            }
            this.skuNamesList[index].typeValue.push('');
            this.skuNamesList[index].rules.push(Object.assign({},rules));
        },
        uploadFile(e,index,index2){
            let _this = this
            let photoFile = e.target
            // 判断是否符合上传条件
            if (photoFile.files.length === 0){
                alert("请选择文件！");
                return false
            }

            var form = new FormData();
            form.append('file', photoFile.files[0]); // 第三个参数为文件名
            $.ajax({
                type: 'POST',
                url : "/file/upload",
                data: form ,
                processData: false,
                contentType: false,
            }).done(function(result) {
                _this.skuNamesList[index].typeValue[index2]=result.data
                alert("上传成功！");
            });
        }
    },
    created: function () {
        _this = this
        var settings = {
            "async": true,
            "crossDomain": true,
            "url": "/basedata/getskurules?id="+$("#coreid").val(),
            "method": "GET",
            "processData": false
        }
        $.ajax(settings).done(function (res) {
            if(res.status==200){
                let skurule= JSON.parse(res.data);
                _this.skuRules = skurule;
                _this.init();
            }else if(res.status==500){
                alert("表达式不存在！请先做表达式！")
            }
        });
    }
})
