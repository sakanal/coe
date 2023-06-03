
window.onload=function (){
    examListVue.getClazzList();
    examListVue.getCourse();
}

var examListVue=new Vue({
    el:"#myExamList",
    data:{
        examList:[],
        pageInfo:[],
        expands:[],
        clazzList:[
            {
                clazzId:"1",
            },
            {
                clazzId:"2",
            },
            {
                clazzId:"3",
            },
        ],
        choiceClazz:"0",

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

        dialogFormVisible:false,
        createExam: {
            clazzId:'',
            courseId:'',
            quantity:'',
            startTime:'',
            endTime:'',
        },
        formLabelWidth: '120px'
    },
    methods:{
        //试卷信息初始化
        getExamList:function (tab) {
            if (typeof (tab)=="undefined"){
                examListVue.choiceClazz=this.clazzList[0].clazzId;
            }else {
                examListVue.choiceClazz=this.clazzList[tab.index].clazzId;
            }
            // alert(examListVue.choiceClazz)
            axios({
                method:"post",
                url:"/ExamConsumer/getExamsByCondition",
                params: {
                    clazzId:examListVue.choiceClazz,
                    // pageNo: key,
                }
            }).then(function (result){
                // console.log(result)
                if (result.data.code===200){
                    examListVue.pageInfo=result.data.data;
                    examListVue.examList=result.data.data.list;
                    this.Vue.prototype.$notify.success({
                        title: '成功',
                        showClose: false,
                        message: result.data.message,
                        duration: 800,
                    })
                }else {
                    this.Vue.prototype.$notify.error({
                        title:'失败',
                        showClose: false,
                        message : result.data.message,
                        duration: 1000,
                    });
                }
            })
        },
        getClazzList:function (){
            // alert("getClazzList")
            axios({
                method:"get",
                url:"/coe/class/tid"
            }).then(function (result){
                console.log(result)
                examListVue.clazzList=result.data;
                examListVue.choiceClazz=examListVue.clazzList[0].clazzId;
                examListVue.getExamList();
            })
        },

        //实现翻页功能
        getExamListPageInfo:function (key){
            // console.log(key)
            // alert(key)
            if (typeof (key)=="undefined"){
                key=1;
            }
            axios({
                method: "post",
                url: "/ExamConsumer/getExamsByCondition",
                params: {
                    clazzId:examListVue.choiceClazz,
                    courseId:this.searchExam.courseId,
                    state:this.searchExam.state,
                    pageNo: key
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
                    this.Vue.prototype.$notify.error({
                        title:'失败',
                        showClose: false,
                        message : result.data.message,
                        duration: 1000,
                    });
                }
            })
        },

        //实现根据试卷id删除试卷的功能
        deleteExamById:function (examId){
            // alert(examId)
            axios({
                method:"post",
                url:"/ExamConsumer/deleteExamById",
                params:{
                    _method:"delete",
                    examId:examId,
                }
            }).then(function (result){
                // console.log(result)
                if (result.data.code===200){
                    this.Vue.prototype.$notify.success({
                        title: '成功',
                        showClose: false,
                        message: result.data.message,
                        duration: 800,
                    })
                    // alert(examListVue.choiceClazz)
                    examListVue.onSearch(examListVue.choiceClazz);
                }else {
                    this.Vue.prototype.$notify.error({
                        title:'失败',
                        showClose: false,
                        message : result.data.message,
                        duration: 1000,
                    });
                }
            })
        },

        //辅助实现手风琴效果？
        getRowKeys: function (row) {
            return row.id
        },
        //实现手风琴效果
        expandSelect: function (row, expandedRows) {
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
        //搜索
        onSearch:function (clazzId){
            if (typeof (clazzId)=="undefined"){
                examListVue.choiceClazz=this.clazzList[0].clazzId;
            }else {
                examListVue.choiceClazz=clazzId;
            }
            axios({
                method:"post",
                url:"/ExamConsumer/getExamsByCondition",
                params: {
                    clazzId:examListVue.choiceClazz,
                    courseId:this.searchExam.courseId,
                    state:this.searchExam.state,
                }
            }).then(function (result){
                if (result.data.code===200){
                    examListVue.pageInfo=result.data.data;
                    examListVue.examList=result.data.data.list;
                    // console.log(this)
                    // this.Vue.prototype.$message({
                    //     showClose: true,
                    //     message: '这是一条消息提示'
                    // });
                    this.Vue.prototype.$notify.success({
                        title: '成功',
                        showClose: false,
                        message : result.data.message,
                        duration : 800,
                    });
                    examListVue.dialogFormVisible=false;
                }else {
                    // examListVue.errorMessage=result.data.message;
                    examListVue.pageInfo=[];
                    examListVue.examList=[];
                    // this.Vue.prototype.$notify.error({
                    //     title:'失败',
                    //     showClose: false,
                    //     message : result.data.message,
                    //     duration: 1000,
                    // });
                }
            })
        },
        //清空表单信息
        resetSearchForm(formName) {
            for (var i = 0; i < this.$refs[formName].length; i++) {
                this.$refs[formName][i].resetFields()
            }
        },

        //创建试卷
        onCreate:function (formName){
            // alert(examListVue.createExam.startTime)
            // alert(examListVue.createExam.endTime)
            axios({
                method:"post",
                url:"/ExamConsumer/Exam",
                params:{
                    clazzId:examListVue.choiceClazz,
                    courseId:examListVue.createExam.courseId,
                    quantity:examListVue.createExam.quantity,
                    startTime:examListVue.createExam.startTime,
                    endTime:examListVue.createExam.endTime,
                },
            }).then(function (result){
                if (result.data.code===200){
                    this.Vue.prototype.$notify.success({
                        title: '成功',
                        showClose: false,
                        message : result.data.message,
                        duration : 800,
                    });
                    // location.reload();
                    examListVue.getExamListPageInfo();
                    examListVue.dialogFormVisible=false;
                    examListVue.resetCreateForm(formName)
                }else {
                    this.Vue.prototype.$notify.error({
                        title:'失败',
                        showClose: false,
                        message : result.data.message,
                        duration: 1000,
                    });
                }
            })

        },
        resetCreateForm(formName){
            this.$refs[formName].resetFields()
        },

        //获取所有课程
        getCourse:function (){
            axios({
                method:"get",
                url:"/course/getCourse",
            }).then(function (result){
                console.log(result)
                examListVue.courseList=result.data;
            })
        },

        //前往班级试卷详细成绩页面
        toScoreList:function (examId,row){
            // alert("前往成绩详细页面")
            // window.location.href="/ExamTch/success.html";
            window.location.href="/Score/toScoreList/"+examListVue.choiceClazz+"/"+examId;
        },





        handleEdit(index, row) {
            console.log(index, row);
        },
        handleDelete(index, row) {
            console.log(index, row);
        },


        testClick:function(event){
            console.log(event)
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
    // watch:{
    //     choiceClazz:function (val){
    //         alert(val);
    //     }
    // }
})