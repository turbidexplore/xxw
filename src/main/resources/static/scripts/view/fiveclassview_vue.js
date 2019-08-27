var app = new Vue({
    el: '#app',
    data: {
        expList: [ // 表达式列表
            {
                isHidden: true,
                symob: '-',
            }],
        ysList: [], // 约束列表
    },
    methods: {
        addCol(index) {

        }
    },
    created: function () {

    }
})

// 表达式公共操作类
function ExpressModule() {

}
