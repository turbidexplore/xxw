var titleValues = ['①', '②', '③', '④', '⑤', '⑥', '⑦', '⑧', '⑨', '⑩', '⑪', '⑫', '⑬', '⑭', '⑮', '⑯', '⑰', '⑱', '⑲', '⑳']
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
                isHidden: true,
                symob: '-',
                index: 0,
                isMainSku: false, // 是否是主SKU(可进行笛卡尔积进行计算SKU)
                name: titleValues[0],
                titles: [[{value: '', placeholder: '中文名称'}], [{value: '', placeholder: '英文名称'}], [{
                    value: '',
                    placeholder: '代码'
                }], [{value: '', placeholder: '单位'}], [{value: '', placeholder: '数据类型'}]],
                values: []
            }],
        ysList: [], // 约束列表
        titless: [{value: '', placeholder: '中文名称'}, {value: '', placeholder: '英文名称'}, {
            value: '',
            placeholder: '代码'
        }, {value: '', placeholder: '单位'}, {value: '', placeholder: '数据类型'}],
        // titless: [{value: '长度', placeholder: '中文名称'}, {value: 'L', placeholder: '英文名称'}, {value: 'L',placeholder: '代码'}, {value: 'mm', placeholder: '单位'}, {value: '实数', placeholder: '数据类型'}],
        totalCount: 0, // 全部sku数量
        mainTotalCount: 0, // 主sku数量
        ysAIndex: '', // 约束A的数组下标
        ysBIndex: '',  //约束B的数组下标
        selectYsBlist: [] // 选中约束1下拉值后，展示的约束2下拉
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
                this.expList[index].values[i].push({value: '', expressIndex: index});
            }
        },
        importCol(index) {
            // 导入excell表格数据
            // 清空原来数据
            this.expList[index].values = [[]]
            for (let rowIndex = 0; rowIndex <= expressModule.inputExcel.countRows(); rowIndex++) {
                if (!expressModule.inputExcel.isEmptyRow(rowIndex)) {
                    let colcount = this.expList[index].titles[0].length;
                    if (colcount < 2) {
                        return;
                    }
                    for (let j = 0; j < colcount; j++) {
                        if (!this.expList[index].values[rowIndex]) {
                            this.expList[index].values[rowIndex] = []
                        }
                        this.expList[index].values[rowIndex].push({
                            value: expressModule.inputExcel.getDataAtCell(rowIndex, j),
                            expressIndex: index
                        })
                    }
                }
            }
        },
        importTitleCol(index) {
            // 导入excell表格数据
            // 清空原来数据
            // this.expList[index].values = [[]]
            for (let rowIndex = 0; rowIndex <= expressModule.inputTitlteExcel.countRows(); rowIndex++) {
                if (!expressModule.inputTitlteExcel.isEmptyRow(rowIndex)) {
                    let colcount = this.expList[index].titles[0].length;
                    if (colcount < 2) {
                        return;
                    }
                    for (let j = 0; j < colcount; j++) {
                        if(this.expList[index].titles[rowIndex][j+1]!=undefined){
                            this.expList[index].titles[rowIndex][j+1].value = expressModule.inputTitlteExcel.getDataAtCell(rowIndex, j);
                        }
                    }
                }
            }
        },
        removeCol(index, index3) {
            // 移除列标题最后一列
            // 标题列数
            // var titleCols = this.expList[index].titles[0].length;
            let titleRows = this.expList[index].titles.length;
            for (let i = 0; i < titleRows; i++) {
                // 把每一行的titleCols列数据删除
                this.expList[index].titles[i].splice(index3, 1);
            }
            //数据列数
            if (this.expList[index].values.length > 0) {
                // var valuesCols = this.expList[index].values[0].length;
                let valuesRows = this.expList[index].values.length;
                for (let i = 0; i < valuesRows; i++) {
                    // 把每一行的valuesCols列数据删除
                    this.expList[index].values[i].splice(index3, 1);
                }
            }
        },
        insertCol(index, index3) {
            // console.log('index='+index+",  index3="+index3)
            // 插入列
            let titleRows = this.expList[index].titles.length;
            for (let i = 0; i < titleRows; i++) {
                // 把每一行的titleCols列数据插入数据
                this.expList[index].titles[i].splice(index3,0, Object.assign({}, this.titless[i]));
            }

            // 添加数据列
            let valuesRows = this.expList[index].values.length;
            for (let i = 0; i < valuesRows; i++) {
                // 把每一行的valuesCols列数据插入数据
                this.expList[index].values[i].splice(index3,0, {value: '', expressIndex: index});
            }
        },
        removeRow(index, index2) {
            this.expList[index].values.splice(index2, 1);
        },
        insertRow(index, index2) {
            // 添加数据列
            let valuesRows = this.expList[index].values.length;
            for (let i = 0; i < valuesRows; i++) {
                // 把每一行的valuesCols列数据插入数据
                this.expList[index].values[i].splice(index3,0, {value: '', expressIndex: index});
            }
        },
        insertExpress(index) {
            let titles = this.titless.map((value, index) => {
                return [value]
            })
            // 添加表达式
            this.expList.splice(index,0,{
                isHidden: true,
                symob: '-',
                isMainSku: false, // 是否是主SKU(可进行笛卡尔积进行计算SKU)
                name: titleValues[index+1],
                index: index+1,
                titles: titles,
                values: []
            })
        },
        addExpress() {
            let titles = this.titless.map((value, index) => {
                return [value]
            })
            // 添加表达式
            this.expList.push({
                isHidden: true,
                symob: '-',
                isMainSku: false, // 是否是主SKU(可进行笛卡尔积进行计算SKU)
                name: titleValues[this.expList.length],
                index: this.expList.length,
                titles: titles,
                values: []
            })
        },
        removeExpress(index) {
            this.expList.splice(index, 1);
            // 重新排序
            for (let i = 0; i < this.expList.length; i++) {
                this.expList[i].name = titleValues[i]
            }
        },
        allDescartes() {
            // 所有sku笛卡儿积
            //  表值笛卡儿积
            let cartesian = new CartesianModule();
            let res = []
            this.expList.map((value, index) => {
                res.push(value.values)
            })
            // 主SKU笛卡儿积
            let skuinfos = cartesian.multiCartesian(res);
            // 格式化笛卡儿积结果
            let formatCartesianArr = this.formatCartesian(skuinfos);
            this.totalCount = formatCartesianArr.length;
        },
        descartes() {
            // 校验数据
            let isError = false;
            for (let i = 0; i < this.expList.length; i++) {
                let expItem = this.expList[i];
                // 对标题校验
                for (let j = 0; j < 1; j++) {
                    let expItemTitles = expItem.titles[j];
                    // 表头列数必须大于2，才表示表达式有数据，
                    if (expItemTitles.length === undefined || expItemTitles.length < 2) {
                        break;
                        alert('表达式：' + expItem.name + '数据不完整！');
                        return;
                    }
                    for (let k = 0; k < expItemTitles.length; k++) {
                        if (k != 0) { // 表头第一列不校验（是文字，不是输入框）
                            let title = this.$refs[`expListTitle${i}_${j}_${k}`][0];
                            if (!title.value) {
                                title.style.borderColor = 'red'
                                isError = true;
                            } else {
                                title.style.borderColor = ''
                            }
                        }
                    }
                }
                //表值校验
                for (let j = 0; j < expItem.values.length; j++) {
                    let expItemValues = expItem.values[j];
                    //表值的列数必须大于2，才表示表达式有数据，
                    if (expItemValues.length === undefined || expItemValues.length < 2) {
                        break;
                        alert('表达式：' + expItem.name + '数据不完整！');
                        return;
                    }
                    for (let k = 0; k < expItemValues.length; k++) {
                        let itemValue = this.$refs[`expListValue${i}_${j}_${k}`][0]
                        if (!itemValue.value) {
                            itemValue.style.borderColor = 'red'
                            isError = true;
                        } else {
                            itemValue.style.borderColor = ''
                        }
                    }
                }
            }
            // 对表里的值校验
            if (isError) {
                alert("请输入必填数据！")
                return [];
            }
            let outExcelData = []
            let res = []
            this.expList.map((value, index) => {
                if (value.isMainSku) {
                    res.push(value.values)
                }

            })
            if (res.length == 0) {
                alert('请选择主SKU表达式！');
                return;
            }

            // 表头合并
            let excellTitles = this.getGridTitle()
            outExcelData = outExcelData.concat(excellTitles)
            //  表值笛卡儿积
            let cartesian = new CartesianModule();

            // 主SKU笛卡儿积
            let skuinfos = cartesian.multiCartesian(res);
            // 格式化笛卡儿积结果
            let formatCartesianArr = this.formatCartesian(skuinfos);
            outExcelData = outExcelData.concat(formatCartesianArr)
            this.mainTotalCount = formatCartesianArr.length
            this.allDescartes()
            return outExcelData;
        },
        formatCartesian(skuinfos) {
            // 格式化笛卡尔积结果
            // 遍历笛卡儿积结果
            let outExcelData = [];
            let listSkuinfos = [];
            for (let k = 0; k < skuinfos.length; k++) {
                // 从结果中抽取型号
                let skuNameItem = this.flattenType(skuinfos[k])
                // 获取每种类型的值
                let skuValueItem = this.flatten(skuinfos[k]);

                listSkuinfos.push({"skuNameItem":skuNameItem,"skuValueItem":skuValueItem});

            }
            // 计算约束的型号
            // 遍历约束，每次遍历进行过滤
            for (let i = 0; i < this.ysList.length; i++) {
                let removeList = [];
                let A = this.ysList[i].A;
                let B = this.ysList[i].B;
                let listAdot = this.ysList[i].listAdot;
                for (let j = 0; j < listAdot.length; j++) {
                    let dot = listAdot[j];
                    if (!dot.checked) {
                        removeList.push({A:A,B:B,dot:dot});
                    }
                }
                listSkuinfos= this.filterSkuinfos(removeList,listSkuinfos);
            }

            for(let l =0;l<listSkuinfos.length;l++){
                let skuNameItem = listSkuinfos[l].skuNameItem;
                let skuValueItem = listSkuinfos[l].skuValueItem;
                var skuifoItem = [];
                skuifoItem.push(skuNameItem.join('').replace("空值",'')) // sku型号放在第一个位置,把空值给替换成空
                for (let j = 0; j < skuValueItem.length; j++) {
                    skuifoItem.push(skuValueItem[j].value)
                }
                outExcelData.push(skuifoItem)
            }


            return outExcelData;
        },
        filterSkuinfos(removeList,skuinfos){
            let filterData = [];
            // 第一遍过滤 skuinfo
            for (let k = 0; k < skuinfos.length; k++) {
                // 从结果中抽取型号
                let skuNameItem = skuinfos[k].skuNameItem; //this.flattenType(skuinfos[k])

                // 获取每种类型的值
                let skuValueItem = skuinfos[k].skuValueItem; //this.flatten(skuinfos[k]);
                // 判断是否在约束中
                let isSureMove = false;
                if (removeList.length > 0) {
                    // let skuName = skuNameItem.join('');
                    for (let j = 0; j < removeList.length; j++) {
                        let A = removeList[j].A;
                        let B = removeList[j].B;
                        let dot = removeList[j].dot;
                        if(skuNameItem[A.index]==(A.symob+'{'+ dot.A[0].value+'}')&&skuNameItem[B.index]==(B.symob+'{'+dot.B[0].value+'}')){
                            isSureMove = true;
                        }
                    }
                }
                if (!isSureMove) {
                    filterData.push(skuinfos[k])
                }
            }
            return filterData;
        },
        getGridTitle() {
            let dataExcellTitle = [];
            let diker = this.expList;
            let zw = [""];
            let yw = [""];
            let dm = [""];
            let dw = [""];
            let sjlx = [""];
            for (let i = 0; i < diker.length; i++) {
                if (diker[i].isMainSku) {
                    for (let k = 1; k < diker[i].titles[0].length; k++) {
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
            let res = [];
            arr.map((item, index) => {
                if (Array.isArray(item)) {
                    res = res.concat(this.flatten(item));
                } else {
                    if (index != 0) {
                        res.push(item);
                    }
                }
            });
            return res;
        },
        flattenType(arr) {
            let res = [];
            arr.map((item, index) => {
                if (Array.isArray(item)) {
                    res = res.concat(this.flattenType(item));
                } else {
                    if (index == 0) {
                        res.push(this.expList[item.expressIndex].symob+'{'+item.value+'}');
                    }
                }
            });
            return res;
        },
        hideExpress(index) {
            this.expList[index].isHidden = !this.expList[index].isHidden;
        },
        createYueSu() {
            // 2个表达式型号的笛卡儿积
            if (this.ysAIndex !== '' && this.ysBIndex !== '') {
                for (let i = 0; i < this.ysList.length; i++) {
                    let ys = this.ysList[i];
                    if (ys.A.index == this.ysAIndex && ys.B.index == this.ysBIndex) {
                        let r=confirm("约束已存在！是否重新生成！")
                        if (r==true) {
                            // alert("约束已存在！")
                        }else {getStatistics
                            // alert("约束已存在！")
                            return;
                        }
                    }
                }
                let ys0 =  this.expList[this.ysAIndex]; // Object.assign({}, this.expList[this.ysAIndex]); //this.expList[this.ysAIndex]; //
                let ys1 = this.expList[this.ysBIndex]; //Object.assign({}, this.expList[this.ysBIndex]); //this.expList[this.ysBIndex]; //

                let listAdot = [];
                for (let i = 0; i < ys0.values.length; i++) {
                    for (let j = 0; j < ys1.values.length; j++) {
                        listAdot.push({checked: true, A: ys0.values[i], B: ys1.values[j]});
                    }
                }
                this.ysList.push({A: ys0, B: ys1, listAdot: listAdot})
            }
        },
        // createYueSu(ysAIndex,ysBIndex) {
        //     // 2个表达式型号的笛卡儿积
        //     let isExist = false;
        //     let existIndex = -1;
        //     if (ysAIndex !== '' && ysBIndex !== '') {
        //         for (let i = 0; i < this.ysList.length; i++) {
        //             let ys = this.ysList[i];
        //             if (ys.A.index == ysBIndex && ys.B.index == ysBIndex) {
        //                 existIndex = i;
        //                 isExist =true;
        //                 let r=confirm("约束已存在！是否重新生成！")
        //                 if (r==false) {
        //                     break;
        //                     return;
        //                 }
        //             }
        //         }
        //         var ys0 = this.expList[ysAIndex]; // Object.assign({}, this.expList[this.ysAIndex]);
        //         var ys1 = this.expList[ysBIndex]; //Object.assign({}, this.expList[this.ysBIndex]);
        //
        //         var listAdot = [];
        //         for (var i = 0; i < ys0.values.length; i++) {
        //             for (var j = 0; j < ys1.values.length; j++) {
        //                 listAdot.push({checked: true, A: ys0.values[i], B: ys1.values[j]});
        //             }
        //         }
        //         if(isExist){
        //             // 表达式改变后，约束也要更新
        //             this.ysList.splice(existIndex,1,{A: ys0, B: ys1, listAdot: listAdot})
        //         }else{
        //             // 添加约束
        //             this.ysList.push({A: ys0, B: ys1, listAdot: listAdot})
        //         }
        //     }
        // },
        selectChageYsA() {
            // 选中约束A后，约束B的可选范围
            // console.log(this.ysAIndex)
            this.selectYsBlist = []
            for (let i = (this.ysAIndex + 1); i < this.expList.length; i++) {
                this.selectYsBlist.push(this.expList[i]);
            }
        },
        deleteYueSu(index) {
            this.ysList.splice(index, 1);
        },
        fullBackYueSu(index) {
            let listAdot = this.ysList[index].listAdot;
            for (let i = 0; i < listAdot.length; i++) {
                listAdot[i].checked = !listAdot[i].checked;
            }
        },
        addYs(index, index1, index2) {
            let dotIndex = this.ysList[index].B.values.length * index1 + index2
            this.ysList[index].listAdot[dotIndex].checked = !this.ysList[index].listAdot[dotIndex].checked
        },
        extendExpress() {
            // 展开全部表达式
            for (let i = 0; i < this.expList.length; i++) {
                this.expList[i].isHidden = true;
            }
        },
        closeExpress() {
            // 合并全部表达式
            for (let i = 0; i < this.expList.length; i++) {
                this.expList[i].isHidden = false;
            }
        },
        saveExpList() {
            // 表达式内容
            let skuRules = [];
            for (let i = 0; i < this.expList.length; i++) {
                let exp = this.expList[i];
                if (exp.isMainSku) {
                    var values = [];
                    for (let j = 0; j < exp.values.length; j++) {
                        values.push(exp.values[j][0].value)
                    }
                    skuRules.push(values);
                }
            }
            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "/basedata/saveskuinfos?id=" + $("#coreid").val(),
                "method": "POST",
                "processData": false,
                "data": JSON.stringify({
                    "expList": JSON.stringify(this.expList),
                    "skuInfos": JSON.stringify(expressModule.outExcel.getData()),
                    "ysList": JSON.stringify(this.ysList),
                    "skuRules": JSON.stringify(skuRules),
                    "mainTotalCount":this.mainTotalCount,
                    "alltotalcount":this.totalCount
                }),
                "contentType": "application/json"
            }
            $.ajax(settings).done(function (response) {
                $('#save_expList').val('保存数据')
                $('#save_expList').attr("disabled", "")
                if(response.status==200){
                    alert("保存成功！")
                }else {
                    alert(response.message)
                }
            });
        },
        saveExpress() {
            let skuRules = [];
            for (let i = 0; i < this.expList.length; i++) {
                let exp = this.expList[i];
                if (exp.isMainSku) {
                    var values = [];
                    for (let j = 0; j < exp.values.length; j++) {
                        values.push(exp.values[j][0].value)
                    }
                    skuRules.push(values);
                }
            }
            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "/basedata/savesExpress?id=" + $("#coreid").val(),
                "method": "POST",
                "processData": false,
                "data": JSON.stringify({
                    "expList": JSON.stringify(this.expList),
                    // "skuInfos":JSON.stringify(expressModule.outExcel.getData()),
                    "ysList": JSON.stringify(this.ysList),
                    "skuRules": JSON.stringify(skuRules)
                }),
                "contentType": "application/json"
            }
            $.ajax(settings).done(function (response) {
                if(response.status==200){
                    alert("保存成功！")
                }
            });
        },
        initData() {
            let _this = this;
            let settings = {
                "async": true,
                "crossDomain": true,
                "url": "/basedata/getexpressbyclassid?classId=" + $("#coreid").val(),
                "method": "GET",
                "processData": false,
                "contentType": "application/json"
            }
            $.ajax(settings).done(function (response) {
                if (response.status == 200 && response.data != null) {
                    _this.expList = JSON.parse(response.data.expressjson);
                    // _this.expList = response.data.skuinfos;
                    _this.ysList = JSON.parse(response.data.ysjson);
                    if (response.data.skuinfos!=undefined&&response.data.skuinfos!='') {
                        let outExcelData = JSON.parse(response.data.skuinfos);
                        expressModule.outExcel.loadData(outExcelData)
                    }
                    this.mainTotalCount = response.data.maintotalcount
                    this.totalCount = response.data.alltotalcount
                }
            });
        },
        changeMainSku(even, index) {
            // 选中主SKU时，选择的index下标前的所有sku必须选中
            for (let i = 0; i < index; i++) {
                if (!this.expList[i].isMainSku) {
                    if(index===this.expList.length-1&&this.expList[index].isMainSku==false){
                        even.preventDefault();
                    }
                    if(index!=this.expList.length-1){
                        even.preventDefault();
                    }
                    this.expList[index].isMainSku = false
                    alert("主SKU必须连续！")
                    break;
                }
            }
        }
    },
    created: function () {
        this.initData();
    }
})

// 表达式公共操作类
function ExpressModule() {

}

ExpressModule.prototype.cleanInputExcel = function () {
    this.inputExcel.clear();
}

ExpressModule.prototype.cleanOutExcel = function () {
    this.outExcel.clear();
}

ExpressModule.prototype.cleanInputTitlteExcel = function () {
    this.inputTitlteExcel.clear();
}
// 生成skuinfo
ExpressModule.prototype.descartes = function () {
    this.cleanOutExcel();
    let outExcelData = app.descartes();
    if (outExcelData.length > 0) {
        this.outExcel.loadData(outExcelData)
    }
}
ExpressModule.prototype.initData = function () {

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
        minSpareCols: 0,
        colHeaders: true,//显示表头　
        contextMenu: true//显示表头下拉菜单
    });

    var containerInputTitlteExcel = document.getElementById('inputTitlteExcel');
    this.inputTitlteExcel = new Handsontable(containerInputTitlteExcel, {
        data: [["", "", "", "", ""]],
        minSpareRows: 2,//空出多少行
        minSpareCols: 2,
        colHeaders: true,//显示表头　
        contextMenu: true//显示表头下拉菜单
    });
}
ExpressModule.prototype.saveExpList = function () {
    $('#save_expList').val('数据正在保存中!请勿关闭浏览器！')
    $('#save_expList').attr("disabled", "disabled")
    app.saveExpList();
}

ExpressModule.prototype.saveExpress = function () {
    app.saveExpress();
}




function goback() {
    location.href = "/system/fiveclass?id=" + $("#coreid").val() + "&comid=0";
}

function next() {
    location.href = "/system/skuinfo_vue?id=" + $("#coreid").val();
}

window.expressModule = new ExpressModule();
expressModule.initData();
setInterval(function(){
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/basedata/getskuinfosCount?classId=" + $("#coreid").val(),
        "method": "GET",
        "processData": false,
        "contentType": "application/json"
    }
    $.ajax(settings).done(function (response) {
        if (response.status == 200 && response.data != null) {
            // console.log(JSON.stringify(response))
            // expressModule.descartes();
            $("#hasSavedCount").html('已保存'+response.data)
        }
        // console.log(response)
    });
    // hasSavedCount
}, 5000);


var dikerCount = 0;

//笛卡儿积算法
function CartesianModule() {
}

CartesianModule.prototype.Cartesian = function (a, b) {
    var ret = [];
    for (var i = 0; i < a.length; i++) {
        for (var j = 0; j < b.length; j++) {
            ret.push(this.ft(a[i], b[j]));
        }
    }
    dikerCount = ret.length;
    // console.log('dikerCount=' + dikerCount)
    return ret;
}
CartesianModule.prototype.ft = function (a, b) {
    if (!(a instanceof Array)) a = [a];
    var ret = Array.call(null, a);
    ret.push(b);
    return ret;
}

CartesianModule.prototype.multiCartesian = function (data) {
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
