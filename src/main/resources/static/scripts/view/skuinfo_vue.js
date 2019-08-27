var mapType = new Map();
// inputtype=0表示输入框，inputtype=1表示下拉框，inputtype=2表示上传控件
mapType.set('0',{name:'产地',inputtype:0});
mapType.set('1',{name:'样品单价',inputtype:0});
mapType.set('2',{name:'批量单价',inputtype:0});
mapType.set('3',{name:'最小包装量',inputtype:0});
mapType.set('4',{name:'最小起订量',inputtype:0});
mapType.set('5',{name:'质保时间',inputtype:1,typeValues:[{name:'不提供',value:'不提供'},{name:'12个月',value:'12个月'},{name:'24个月',value:'24个月'},{name:'36个月',value:'36个月'},{name:'36+',value:'36+'},{name:'终身质保',value:'终身质保'}]});
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
        save(){
          // console.log('save()')
            for(let i=0;i<this.skuNamesList.length;i++){
                let item = this.skuNamesList[i];

            }
            console.log(this.skuNamesList)
        },
        addCol(index) {

        },
        rm(index,index2){
            this.skuNamesList[index].rules.splice(index2,1);
            this.skuNamesList[index].rules.splice(index2,1);
        },
        addSkuRule(index){
            let rules = [];
            for(let i=0;i<this.skuRules.length;i++){
                let skuRule = this.skuRules[i];
                rules.push({value:''})
            }
            this.skuNamesList[index].typeValue.push('');
            this.skuNamesList[index].rules.push(Object.assign({},rules));
        },
        initData(){
            let rules = [];
            for(let i=0;i<this.skuRules.length;i++){
                let skuRule = this.skuRules[i];
                rules.push({value:''})
            }
            for (let [key, value] of mapType.entries()) {
                this.skuNamesList.push({
                    index:key, // 序号
                    skuname:value.name, // skuname
                    inputtype:value.inputtype, // skunametype
                    typeValues:value.typeValues, // typeValues
                    typeValue:[''],  // 默认是空
                    rules:[Object.assign({},rules)] // 默认是SKU规则的长度
                })
            }
            console.log(this.skuNamesList)
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
            let skurule= JSON.parse(res.data);
            _this.skuRules = skurule;
            _this.initData();
            console.log(skurule);
        });
        console.log("mapType.get('0')="+mapType.get('0'))
    }
})

//笛卡儿积算法
function CartesianModule() {
}

CartesianModule.prototype.Cartesian = function (a, b) {
}
