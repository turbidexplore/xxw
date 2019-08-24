var titleValues = ['①','②','③','④','⑤','⑥','⑦','⑧','⑨','⑩','⑪','⑫','⑬','⑭','⑮','⑯','⑰','⑱','⑲','⑳']
var excelData = [
    ["MSG21", "1", "2", "3", "4"],
    ["MSG22", "1", "2", "3", "4"],
    ["MSG23", "1", "2", "3", "4"],
    ["MSG24", "1", "2", "3", "4"]
];
// var titless = [[{value: '', placeholder: '中文名称'}],[{value: '', placeholder: '英文名称'}],[{value: '', placeholder: '代码'}],[{value: '', placeholder: '单位'}]]
var app = new Vue({
    el: '#app',
    data: {
        expList: [ // 表达式列表
            {
                isHidden:true,
                symob: '-',
                isMainSku: false, // 是否是主SKU(可进行笛卡尔积进行计算SKU)
                name: titleValues[0],
                titles: [[{value: '', placeholder: '中文名称'}], [{value: '', placeholder: '英文名称'}], [{
                    value: '',
                    placeholder: '代码'
                }], [{value: '', placeholder: '单位'}]],
                values: [[]]
            }],
        ysList:[], // 约束列表
        titless: [{value: '长度', placeholder: '中文名称'}, {value: 'L', placeholder: '英文名称'}, {
            value: 'L',
            placeholder: '代码'
        }, {value: 'mm', placeholder: '单位'}],
        totalCount:0,
        ysA:'', // 约束A的数组下标
        ysB:''  //约束B的数组下标
    },
    methods: {
        addCol(index) {

            // 添加标题列
            for (var i = 0; i < this.titless.length; i++) {
                this.expList[index].titles[i].push(Object.assign({}, this.titless[i]));
            }

            // 添加数据列
            var valuesRows = this.expList[index].values.length;
            for (var i = 0; i < valuesRows; i++) {
                // 把每一行的valuesCols列数据删除
                this.expList[index].values[i].push({value: ''});
            }
        },
        importCol(index) {
            // 导入excell表格数据
            // 清空原来数据
            this.expList[index].values = [[]]
            for (var rowIndex = 0; rowIndex <= expressModule.inputExcel.countRows(); rowIndex++) {
                if (!expressModule.inputExcel.isEmptyRow(rowIndex)) {
                    var colcount = this.expList[index].titles[0].length;
                    if (colcount < 2) {
                        return;
                    }
                    for (var j = 0; j < colcount; j++) {
                        if (!this.expList[index].values[rowIndex]) {
                            this.expList[index].values[rowIndex] = []
                        }
                        this.expList[index].values[rowIndex].push({value: expressModule.inputExcel.getDataAtCell(rowIndex, j)})
                    }
                }
            }
        },
        removeCol(index,index3) {
            // 移除列标题最后一列
            // 标题列数
            // var titleCols = this.expList[index].titles[0].length;
            var titleRows = this.expList[index].titles.length;
            for (var i = 0; i < titleRows; i++) {
                // 把每一行的titleCols列数据删除
                this.expList[index].titles[i].splice(index3, 1);
            }
            //数据列数
            if(this.expList[index].values.length>0){
                // var valuesCols = this.expList[index].values[0].length;
                var valuesRows = this.expList[index].values.length;
                for (var i = 0; i < valuesRows; i++) {
                    // 把每一行的valuesCols列数据删除
                    this.expList[index].values[i].splice(index3, 1);
                }
            }
        },
        removeRow(index,index2) {
            this.expList[index].values.splice(index2, 1);
        },
        addExpress() {
            var titles = this.titless.map((value, index) => {
                return [value]
            })
            // 添加表达式
            this.expList.push({
                isHidden:true,
                symob: '-',
                isMainSku: false, // 是否是主SKU(可进行笛卡尔积进行计算SKU)
                name: titleValues[this.expList.length],
                titles: titles,
                values: []
            })
        },
        removeExpress(index) {
            this.expList.splice(index, 1);
            // 重新排序
            for (var i = 0; i < this.expList.length; i++) {
                this.expList[i].name = titleValues[i]
            }
        },
        descartes() {
            // 校验数据
            let isError = false;
            for(var i=0;i<this.expList.length;i++){
                var expItem = this.expList[i];
                // 对标题校验
                for(var j=0;j<expItem.titles.length;j++){
                    var expItemTitles = expItem.titles[i];
                    for(var k=0;k<expItemTitles.length;k++){
                        if(k!=0){ // 表头第一列不校验（是文字，不是输入框）
                            let title = this.$refs[`expListTitle${i}_${j}_${k}`][0];
                            if(!title.value) {
                                title.style.borderColor = 'red'
                                isError = true;
                            }else{
                                title.style.borderColor=''
                            }
                        }
                    }
                }
                //表值校验
                for(var j=0;j<expItem.values.length;j++){
                    var expItemValues = expItem.values[i];
                    for(var k=0;k<expItemValues.length;k++){
                        let itemValue = this.$refs[`expListValue${i}_${j}_${k}`][0]
                        if(!itemValue.value){
                            itemValue.style.borderColor = 'red'
                            isError = true;
                        }else{
                            itemValue.style.borderColor=''
                        }
                        // this.$refs[`expListValue${i}_${j}_${k}`][0].style.color='red'
                    }
                }
            }
            // 对表里的值校验
            if(isError){
                return [];
            }
            var outExcelData = []
            // 表头合并
            var excellTitles = this.getGridTitle()
            outExcelData = outExcelData.concat(excellTitles)
            //  表值笛卡儿积
            var cartesian = new CartesianModule();
            var res = []
            this.expList.map((value, index) => {
                res.push(value.values)
            })

            var skuinfos = cartesian.multiCartesian(res);
            this.totalCount = skuinfos.length

            // 遍历笛卡儿积结果
            for (var i = 0; i < skuinfos.length; i++) {
                // 获取每种类型的值
                var skuValueItem= this.flatten(skuinfos[i]);
                // 从结果中抽取型号
                var skuNameItem = this.flattenType(skuinfos[i])
                var skuifoItem = []
                skuifoItem.push(skuNameItem.join( )) // sku型号放在第一个位置
                for(var j=0;j<skuValueItem.length;j++){
                    skuifoItem.push(skuValueItem[j].value)
                }
                outExcelData.push(skuifoItem)
            }
            return outExcelData;
        },
        getGridTitle(){
            var dataExcellTitle = [];
            var diker = this.expList;
            var zw =[""];
            var yw =[""];
            var dm =[""];
            var dw =[""];
            for(var i=0;i<diker.length;i++){
                // for(var j=0;j<diker[i].titles.length;j++){
                for( var k=1;k<diker[i].titles[0].length;k++){
                    zw.push(diker[i].titles[0][k].value);
                    yw.push(diker[i].titles[1][k].value);
                    dm.push(diker[i].titles[2][k].value);
                    dw.push(diker[i].titles[3][k].value);
                }
            }
            dataExcellTitle.push(zw);
            dataExcellTitle.push(yw);
            dataExcellTitle.push(dm);
            dataExcellTitle.push(dw);
            return dataExcellTitle;
        },
        flatten(arr) {
            var res = [];
            arr.map((item,index) => {
                if(Array.isArray(item)) {
                    res = res.concat(this.flatten(item));
                } else {
                    if(index!=0){
                        res.push(item);
                    }
                }
            });
            return res;
        },
        flattenType(arr) {
            var res = [];
            arr.map((item,index) => {
                if(Array.isArray(item)) {
                    res = res.concat(this.flattenType(item));
                } else {
                    if(index==0){
                        res.push(item.value);
                    }
                }
            });
            return res;
        },
        hideExpress(index){
            console.log('hideExpress='+this.expList[index].isHidden)
            this.expList[index].isHidden =!this.expList[index].isHidden;
        },
        createYueSu(){
            // 2个表达式型号的笛卡儿积
            var ys0 = Object.assign({},this.expList[this.ysA]);
            var ys1 = Object.assign({},this.expList[this.ysB]);

            var listAdot= [];
            for(var i=0;i<ys0.values.length;i++){
                for(var j=0;j<ys1.values.length;j++){
                    listAdot.push({checked:true,A:ys0.values[i],B:ys1.values[j]});
                }
            }
            this.ysList.push({A:ys0,B:ys1,listAdot:listAdot})
            // console.log(JSON.stringify(result))
        },
        addYs(index,index1,index2){
            var dotIndex=this.ysList[index].A.values.length*index1+index2
            this.ysList[index].listAdot[dotIndex].checked=!this.ysList[index].listAdot[dotIndex].checked
        },
        extendExpress(){
            // 展开全部表达式
            for(let i=0;i<this.expList.length;i++){
                this.expList[i].isHidden = true;
            }
        },
        closeExpress(){
            // 合并全部表达式
            for(let i=0;i<this.expList.length;i++){
                this.expList[i].isHidden = false;
            }
        },
        saveExpList(){
            // 表达式内容
            console.log(JSON.stringify(this.expList))
            // sku数据
            console.log(JSON.stringify(expressModule.outExcel.getData()))
            // 约束数据
            console.log(JSON.stringify(this.ysList))

            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "/basedata/saveskuinfos?id=93604",
                "method": "POST",
                "processData": false,
                "data":JSON.stringify({
                    "expList":JSON.stringify(this.expList),
                    "skuInfos":JSON.stringify(expressModule.outExcel.getData()),
                    "ysList":JSON.stringify(this.ysList)
                }),
                "contentType": "application/json"
            }
            $.ajax(settings).done(function (response) {
                console.log(response)
            });
        }
    }
})

// 表达式公共操作类
function ExpressModule() {

}

ExpressModule.prototype.cleanInputExcel=function(){
    this.inputExcel.clear();
}

ExpressModule.prototype.cleanOutExcel=function(){
    this.outExcel.clear();
}
// 生成skuinfo
ExpressModule.prototype.descartes=function() {
    this.cleanOutExcel();
    let outExcelData = app.descartes();
    if(outExcelData.length>0){
        this.outExcel.loadData(outExcelData)
    }
}
ExpressModule.prototype.initData=function(){

    // var dd = [["", "", "", "", ""]];
    var containerinputExcel = document.getElementById('inputExcel');
    this.inputExcel = new Handsontable(containerinputExcel, {
        data: excelData,
        minSpareRows: 2,//空出多少行
        minSpareCols: 2,
        colHeaders: true,//显示表头　
        contextMenu: true//显示表头下拉菜单
    });
    var containerOutExcel = document.getElementById('outExcel');
    this.outExcel = new Handsontable(containerOutExcel, {
        data: [["", "", "", "", ""]],
        minSpareRows: 0,//空出多少行
        minSpareCols:0,
        colHeaders: true,//显示表头　
        contextMenu: true//显示表头下拉菜单
    });
}
ExpressModule.prototype.saveExpList=function(){
    app.saveExpList();
}

window.expressModule = new ExpressModule();
expressModule.initData();

//笛卡儿积算法
function CartesianModule() {
}
CartesianModule.prototype.Cartesian = function(a, b) {
    var ret = [];
    for (var i = 0; i < a.length; i++) {
        for (var j = 0; j < b.length; j++) {
            ret.push(this.ft(a[i], b[j]));
        }
    }
    return ret;
}
CartesianModule.prototype.ft = function(a, b) {
    if (! (a instanceof Array)) a = [a];
    var ret = Array.call(null, a);
    ret.push(b);
    return ret;
}

CartesianModule.prototype.multiCartesian = function(data) {
        var len = data.length;
        if (len == 0) return [];
        else if (len == 1) return data[0];
        else {
            var r = data[0];
            for (var i = 1; i < len; i++) {
                r = this.Cartesian(r, data[i]);
            }
            return r;
        }
    }
