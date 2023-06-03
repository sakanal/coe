
window.onload=function (){
    examListVue.getExamList();
    examListVue.getCourse();
}

var examListVue=new Vue({
    el:"#examList",
    data:{
        examList:[],
        pageInfo:[],
        page:1,
        expands:[],
        searchExam: {
            clazzId:'',
            courseId:'',
            state:'',
        },
        courseList:[
            {
                id:"BC001",
                name:"古诗文鉴赏"
            },
            {
                id:"BC002",
                name:"高等数学"
            },
            {
                id:"BC003",
                name:"跨文化交流"
            },
        ],
    },
    methods:{
        getExamList:function () {
            // alert(key)
            // if (typeof (key)=="undefined"){
            //     // alert("null")
            //     key=1;
            // }else {
            //     // alert(key)
            // }
            axios({
                method: "get",
                url: "/ExamConsumer/getExamList",
            }).then(function (result){
                if (result.data.code===200){
                    examListVue.pageInfo=result.data.data;
                    examListVue.examList=result.data.data.list;
                    this.Vue.prototype.$notify.success({
                        title: '成功',
                        showClose: false,
                        message : result.data.message,
                        duration : 800,
                    });
                }else {
                    // examListVue.errorMessage=result.data.message;
                    this.Vue.prototype.$notify.error({
                        title:'失败',
                        showClose: false,
                        message : result.data.message,
                        duration: 1000,
                    });
                }
            })
        },
        getRowKeys: function (row) {
            // alert("getRowKeys"+row.id)
            // examListVue.expands=row.id
            return row.id
        },
        expandSelect: function (row, expandedRows) {
            // alert("expandSelect"+row)
            // alert("expandSelect"+expandedRows)
            var that = this
            if (expandedRows.length) {
                that.expands = []
                if (row) {
                    that.expands.push(row.id)
                }
            } else {
                that.expands = []
            }
        },
        onSearch:function (key){

            // alert(key)
            if (typeof (key)=="undefined"){
                // alert("null")
                key=1;
            }
            examListVue.page=key;
            // alert(this.page)
            axios({
                method:"post",
                url:"/ExamConsumer/getExamsByCondition",
                params: {
                    clazzId:this.searchExam.clazzId,
                    courseId:this.searchExam.courseId,
                    state:this.searchExam.state,
                    pageNo:key,
                }
            }).then(function (result){
                if (result.data.code===200){
                    examListVue.pageInfo=result.data.data;
                    examListVue.examList=result.data.data.list;
                    this.Vue.prototype.$notify.success({
                        title: '成功',
                        showClose: false,
                        message : result.data.message,
                        duration : 800,
                    });
                }else {
                    // examListVue.errorMessage=result.data.message;
                    examListVue.pageInfo=[];
                    examListVue.examList=[];
                    this.Vue.prototype.$notify.error({
                        title:'失败',
                        showClose: false,
                        message : result.data.message,
                        duration: 1000,
                    });
                }
            })
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        },
        getCourse:function (){
            axios({
                method:"get",
                url:"/course/getAll",
            }).then(function (result){
                // console.log(result)
                examListVue.courseList=result.data;
            })
        },







        handleEdit(index, row) {
            console.log(index, row);
        },
        handleDelete(index, row) {
            console.log(index, row);
        },
        testClick:function(){
            console.log(this)
            this.$message({
                message: '恭喜你，这是一条成功消息',
                type: 'success',
                showClose:true,
            });
        },
        testAlert:function (key){
            alert(key)
        }
    },
})