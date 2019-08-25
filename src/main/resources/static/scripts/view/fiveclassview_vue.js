var titleValues = ['①','②','③','④','⑤','⑥','⑦','⑧','⑨','⑩','⑪','⑫','⑬','⑭','⑮','⑯','⑰','⑱','⑲','⑳']
var excelData = [["", "", "", "", ""]];
// var excelData = [
//     ["MSG21", "1", "2", "3", "4"],
//     ["MSG22", "1", "2", "3", "4"],
//     ["MSG23", "1", "2", "3", "4"],
//     ["MSG24", "1", "2", "3", "4"],
//     ["MSG25", "1", "2", "3", "4"],
//     ["MSG26", "1", "2", "3", "4"],
//     ["MSG27", "1", "2", "3", "4"],
//     ["MSG28", "1", "2", "3", "4"],
//     ["MSG29", "1", "2", "3", "4"],
//     ["MSG30", "1", "2", "3", "4"],
//     ["MSG31", "1", "2", "3", "4"],
//     ["MSG32", "1", "2", "3", "4"],
//     ["MSG33", "1", "2", "3", "4"],
//     ["MSG34", "1", "2", "3", "4"]
// ];
// var titless = [[{value: '', placeholder: '中文名称'}],[{value: '', placeholder: '英文名称'}],[{value: '', placeholder: '代码'}],[{value: '', placeholder: '单位'}]]
var app = new Vue({
    el: '#app',
    data: {
        expList: [ // 表达式列表
            {
                isHidden:true,
                symob: '-',
                index:0,
                isMainSku: false, // 是否是主SKU(可进行笛卡尔积进行计算SKU)
                name: titleValues[0],
                titles: [[{value: '', placeholder: '中文名称'}], [{value: '', placeholder: '英文名称'}], [{value: '',placeholder: '代码'}], [{value: '', placeholder: '单位'}], [{value: '', placeholder: '数据类型'}]],
                values: []
            }],
        ysList:[], // 约束列表
        titless: [{value: '', placeholder: '中文名称'}, {value: '', placeholder: '英文名称'}, {value: '',placeholder: '代码'}, {value: '', placeholder: '单位'}, {value: '', placeholder: '数据类型'}],
        // titless: [{value: '长度', placeholder: '中文名称'}, {value: 'L', placeholder: '英文名称'}, {value: 'L',placeholder: '代码'}, {value: 'mm', placeholder: '单位'}, {value: '实数', placeholder: '数据类型'}],
        totalCount:0, // 全部sku数量
        mainTotalCount:0, // 主sku数量
        ysAIndex:'', // 约束A的数组下标
        ysBIndex:'',  //约束B的数组下标
        selectYsBlist:[] // 选中约束1下拉值后，展示的约束2下拉
    },
    methods: {
        addCol(index) {

            // 添加标题列
            for (let i = 0; i < this.titless.length; i++) {
                this.expList[index].titles[i].push(Object.assign({}, this.titless[i]));
            }

            // 添加数据列
            let valuesRows = this.expList[index].values.length;
            for (let i = 0; i < valuesRows; i++) {
                // 把每一行的valuesCols列数据删除
                this.expList[index].values[i].push({value: '',expressIndex:index});
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
                        this.expList[index].values[rowIndex].push({value: expressModule.inputExcel.getDataAtCell(rowIndex, j),expressIndex:index})
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
                index:this.expList.length,
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
        allDescartes(){
            dikerCount = 0;
            // 所有sku笛卡儿积
            //  表值笛卡儿积
            var cartesian = new CartesianModule();
            var res = []
            this.expList.map((value, index) => {
               res.push(value.values)
            })
            // 主SKU笛卡儿积
            var skuinfos = cartesian.multiCartesian(res);
            // 格式化笛卡儿积结果
            let formatCartesianArr= this.formatCartesian(skuinfos);
            this.totalCount = formatCartesianArr.length;
        },
        descartes() {
            dikerCount = 0;
            // 校验数据
            let isError = false;
            for(var i=0;i<this.expList.length;i++){
                var expItem = this.expList[i];
                // 对标题校验
                for(var j=0;j<expItem.titles.length;j++){
                    var expItemTitles = expItem.titles[i];
                    // 表头列数必须大于2，才表示表达式有数据，
                    if(expItemTitles.length===undefined||expItemTitles.length<2){
                        break;
                        alert('表达式：'+expItem.name+'数据不完整！');
                        return ;
                    }
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
                    let expItemValues = expItem.values[j];
                    if(expItemValues.length===undefined){
                        //表值的列数必须大于2，才表示表达式有数据，
                        if(expItemValues.length===undefined||expItemValues.length<2){
                            break;
                            alert('表达式：'+expItem.name+'数据不完整！');
                            return;
                        }
                        console.log(expItem.name)
                        console.log(j+'行')
                    }
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
            var res = []
            this.expList.map((value, index) => {
                if(value.isMainSku){
                    res.push(value.values)
                }

            })
            if(res.length==0){
                alert('请选择主SKU表达式！');
                return;
            }

            // 表头合并
            var excellTitles = this.getGridTitle()
            outExcelData = outExcelData.concat(excellTitles)
            //  表值笛卡儿积
            var cartesian = new CartesianModule();

            // 主SKU笛卡儿积
            var skuinfos = cartesian.multiCartesian(res);
            // 格式化笛卡儿积结果
            let formatCartesianArr= this.formatCartesian(skuinfos);
            outExcelData = outExcelData.concat(formatCartesianArr)
            this.mainTotalCount = formatCartesianArr.length
            this.allDescartes()
            return outExcelData;
        },
        formatCartesian(skuinfos){
            // 格式化笛卡尔积结果
            // 遍历笛卡儿积结果
            let outExcelData = [];
            // 计算约束的型号
            let removeList = this.removeList();
            for (var i = 0; i < skuinfos.length; i++) {

                // 从结果中抽取型号
                var skuNameItem = this.flattenType(skuinfos[i])

                // 获取每种类型的值
                var skuValueItem= this.flatten(skuinfos[i]);
                // 判断是否在约束中
                let isSureMove = false;
                if(removeList.length>0){
                    let skuName = skuNameItem.join('');
                    for(let j = 0;j<removeList.length;j++){
                        console.log('skuName='+skuName+',removeList[j]='+removeList[j]+'skuName.indexOf(removeList)!=-1='+skuName.indexOf(removeList)!=-1)
                        if(skuName.indexOf(removeList[j])!=-1){
                            isSureMove = true;
                        }
                    }
                }
                if(!isSureMove){
                    var skuifoItem = []
                    skuifoItem.push(skuNameItem.join('')) // sku型号放在第一个位置
                    for(var j=0;j<skuValueItem.length;j++){
                        skuifoItem.push(skuValueItem[j].value)
                    }
                    outExcelData.push(skuifoItem)
                }
            }
            return outExcelData;
        },
        removeList(){
          // 计算约束型号
            let removeList = [];
            for(let i=0;i<this.ysList.length;i++){
                let A = this.ysList[i].A;
                let B = this.ysList[i].B;
                let listAdot = this.ysList[i].listAdot;
                // A:ys0.values[i],B:ys1.values[j]
                for(let j=0;j<listAdot.length;j++){
                    let dot = listAdot[j];
                    if(!dot.checked){
                        console.log('dot.A='+JSON.stringify(dot.A))
                        console.log('dot.B='+JSON.stringify(dot.B))
                        let dotAB = A.symob+dot.A[0].value+B.symob+dot.B[0].value;
                        console.log('A.index='+A.index+",B.index="+B.index+' dotAB='+dotAB)
                        removeList.push(dotAB);
                    }
                }
            }
            return removeList;
        },
        getGridTitle(){
            var dataExcellTitle = [];
            var diker = this.expList;
            var zw =[""];
            var yw =[""];
            var dm =[""];
            var dw =[""];
            var sjlx =[""];
            for(var i=0;i<diker.length;i++){
                // for(var j=0;j<diker[i].titles.length;j++){
                if(diker[i].isMainSku){
                    for( var k=1;k<diker[i].titles[0].length;k++){
                        zw.push(diker[i].titles[0][k].value);
                        yw.push(diker[i].titles[1][k].value);
                        dm.push(diker[i].titles[2][k].value);
                        dw.push(diker[i].titles[3][k].value);
                        sjlx.push(diker[i].titles[4][k].value);
                    }
                }
            }
            dataExcellTitle.push(zw);
            dataExcellTitle.push(yw);
            dataExcellTitle.push(dm);
            dataExcellTitle.push(dw);
            dataExcellTitle.push(sjlx);
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
                        res.push(this.expList[item.expressIndex].symob+item.value);
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
            if(this.ysAIndex!==''&&this.ysBIndex!==''){
                for(let i=0;i<this.ysList.length;i++){
                    let ys = this.ysList[i];
                    if(ys.A.index==this.ysAIndex&&ys.B.index==this.ysBIndex){
                        alert("约束已存在！")
                        return;
                    }
                }
                var ys0 = Object.assign({},this.expList[this.ysAIndex]);
                var ys1 = Object.assign({},this.expList[this.ysBIndex]);

                var listAdot= [];
                for(var i=0;i<ys0.values.length;i++){
                    for(var j=0;j<ys1.values.length;j++){
                        listAdot.push({checked:true,A:ys0.values[i],B:ys1.values[j]});
                    }
                }
                this.ysList.push({A:ys0,B:ys1,listAdot:listAdot})
            }
            // console.log(JSON.stringify(result))
        },
        selectChageYsA(){
            // 选中约束A后，约束B的可选范围
            console.log(this.ysAIndex)
            this.selectYsBlist = []
            for(let i=(this.ysAIndex+1);i<this.expList.length;i++){
                console.log('this.selectYsBlist.push i='+i)
                this.selectYsBlist.push(this.expList[i]);
            }
        },
        deleteYueSu(index){
            this.ysList.splice(index,1);
        },
        fullBackYueSu(index){
            let listAdot = this.ysList[index].listAdot;
            for(let i=0;i<listAdot.length;i++){
                listAdot[i].checked = !listAdot[i].checked;
            }
            // this.ysList.push({A:ys0,B:ys1,listAdot:listAdot})
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
            // console.log(JSON.stringify(this.expList))
            // sku数据
            // console.log(JSON.stringify(expressModule.outExcel.getData()))
            // 约束数据
            // console.log(JSON.stringify(this.ysList))
            // sku规则
            let skuRules = [];
            for(let i=0;i<this.expList.length;i++){
                let exp = this.expList[i];
                if(exp.isMainSku){
                    var values = [];
                    for(let j = 0;j<exp.values.length;j++){
                        values.push(exp.symob+exp.values[j][0].value)
                    }
                    skuRules.push(values);
                }
            }
            // if(true){
            //  console.log('skuRules'+JSON.stringify(skuRules))
            //  return;
            // }
            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "/basedata/saveskuinfos?id="+$("#coreid").val(),
                "method": "POST",
                "processData": false,
                "data":JSON.stringify({
                    "expList":JSON.stringify(this.expList),
                    "skuInfos":JSON.stringify(expressModule.outExcel.getData()),
                    "ysList":JSON.stringify(this.ysList),
                    "skuRules":JSON.stringify(skuRules)
                }),
                "contentType": "application/json"
            }
            $.ajax(settings).done(function (response) {
                $('#save_expList').val('保存数据！')
                $('#save_expList').attr("disabled","")
                console.log(response)
            });
        },
        initData(){
            var _this = this;
            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "/basedata/getexpressbyclassid?classId="+$("#coreid").val(),
                "method": "GET",
                "processData": false,
                "contentType": "application/json"
            }
            $.ajax(settings).done(function (response) {
                if(response.status==200&&response.data!=null){
                    _this.expList = JSON.parse(response.data.expressjson);
                    // _this.expList = response.data.skuinfos;
                    _this.ysList = JSON.parse(response.data.ysjson);
                    let outExcelData = JSON.parse(response.data.skuinfos);
                    if(outExcelData.length>0){
                        expressModule.outExcel.loadData(outExcelData)
                    }
                    // expressModule.descartes();
                }
                console.log(response)
            });
        },
        changeMainSku(even,index){
            console.log('changeMainSku index='+index)
            // 选中主SKU时，选择的index下标前的所有sku必须选中
            // if(this.expList[index].isMainSku){
                for(let i=0;i<index;i++){
                    if(!this.expList[i].isMainSku){
                        this.expList[index].isMainSku=false
                        even.preventDefault();
                        alert("主SKU必须连续！")
                        break;
                    }
                }
            // }
        }
    },
    created: function () {
        this.initData();
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
    $('#save_expList').val('数据正在保存中!请勿关闭浏览器！')
    $('#save_expList').attr("disabled","disabled")
    app.saveExpList();
}
function goback() {
    location.href = "/system/fiveclass?id="+$("#coreid").val()+"&comid=0";
}

function next() {
    location.href = "/system/skuinfo?id="+$("#coreid").val();
}

window.expressModule = new ExpressModule();
expressModule.initData();
var dikerCount = 0;
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
    dikerCount = ret.length;
    console.log('dikerCount='+dikerCount)
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
