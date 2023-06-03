
window.onload=function (){
    examListVue.getExamList();
    examListVue.getStudentInfo();
    // examListVue.getCourse();
}

var examListVue=new Vue({
    el:"#examList",
    data:{
        examList:[],
        pageInfo:[],
        page:1,
        expands:[],
        studentInfo:[],
        searchExam: {
            clazzId:'',
            courseId:'',
            state:'',
        },
        // courseList:[
        //     {
        //         id:"BC001",
        //         name:"古诗文鉴赏"
        //     },
        //     {
        //         id:"BC002",
        //         name:"高等数学"
        //     },
        //     {
        //         id:"BC003",
        //         name:"跨文化交流"
        //     },
        // ],
        stateList:[
            {
                id:"0",
                info:"未开始"
            },
            {
                id:"1",
                info:"考试中"
            },
            {
                id:"2",
                info:"已结束"
            },
        ],
        dialogVisible: false,
        examId:"",
        examInfo:[
            {
                id:"",
                teacherId:"",
                courseId:"",
                endTime:"",
                startTime:"",
                time:"",
                quantity:"",
                state:"",
            }
        ],
        examCourse:[
            {
                id:"",
                name:"",
            }
        ],
        examTeacher:[
            {
                id:"",
                name:"",
            },
        ],
        hasScore:false,
        hasScoreInfo:false,
        scoreInfo:{
            clazzId:"",
            courseId:"",
            examId:"",
            id:"",
            quantity:"",
            score:"",
            studentId:"",
        },
    },
    methods:{
        getExamList:function (tab) {
            var state;
            if (typeof (tab)=="undefined"){
                state=this.stateList[0].id;
            }else {
                state=this.stateList[tab.index].id;
            }
            axios({
                method: "get",
                url: "/ExamStuConsumer/getExamList",
                params: {
                    state:state,
                }
            }).then(function (result){
                // console.log(result)
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
        getStudentInfo:function (){
            axios({
                method:"get",
                url:"/ExamStuConsumer/getStudent",
            }).then(function (result){
                // console.log(result);
                examListVue.studentInfo=result.data.data
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
                    clazzId:this.studentInfo.clazzId,
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
                method:"post",
                url:"/course/getAll",
            }).then(function (result){
                console.log("开始获取所有课程")
                console.log(result)
                if (result.data===200){
                    examListVue.courseId=result.data.data.list;
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

        getExam:function (id,state){
            // console.log(row);
            examListVue.examId=id;
            axios({
                method:"get",
                url:"/ExamConsumer/getExamById/"+examListVue.examId
            }).then(function (result){
                console.log("获取试卷信息")
                console.log(result)
                if (result.data.code===200){
                    examListVue.examInfo=result.data.data
                    examListVue.examTeacher=result.data.data.teacher;
                    examListVue.examCourse=result.data.data.course;
                    // console.log(examListVue.examInfo.course.name);
                }else {
                    // examListVue.errorMessage=result.data.message;
                    examListVue.examInfo=[];
                    examListVue.examTeacher=[];
                    examListVue.examCourse=[];
                    this.Vue.prototype.$notify.error({
                        title:'失败',
                        showClose: false,
                        message : result.data.message,
                        duration: 1000,
                    });
                }
            });
            axios({
                method:"post",
                url:"/ExamStuConsumer/getScore",
                params:{
                    examId:examListVue.examId,
                }
            }).then(function (result){
                console.log(result)
                if (result.data.code===200){
                    examListVue.hasScore=true;
                    examListVue.hasScoreInfo=true;
                    examListVue.scoreInfo=result.data.data;
                }else{
                    examListVue.hasScore=false;
                    examListVue.hasScoreInfo=false;
                    examListVue.scoreInfo=[];
                }
                if (state==2){
                    examListVue.hasScore=true;
                }
            })
            examListVue.dialogVisible=true;
        },
        tabChange:function (tab){
            console.log(tab)
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