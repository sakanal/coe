window.onload=function (){
    //examListVue.getExamByExamId();
}

var container_headerVue=new Vue({
    el:"#container_header",
    data:{
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
        clazzList:[
            {
              id:1921804
            },
            {
              id:1921805
            },
            {
              id:1921806
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
        formLabelWidth: '80px'
    },
    methods: {
        //创建试卷
        onCreate:function (formName){

            // console.log(this)
            // console.log(this.Vue)
            // this.prototype.$notify.error({
            //     title:'失败',
            //     showClose: false,
            //     message : "功能尚未实现",
            //     duration: 1000,
            // });
            console.log(examListVue.examInfo)
            // alert(examListVue.createExam.startTime)
            // alert(examListVue.createExam.endTime)
            // axios({
            //     method:"post",
            //     url:"/ExamConsumer/Exam",
            //     params:{
            //         clazzId:examListVue.choiceClazz,
            //         courseId:examListVue.createExam.courseId,
            //         quantity:examListVue.createExam.quantity,
            //         startTime:examListVue.createExam.startTime,
            //         endTime:examListVue.createExam.endTime,
            //     },
            // }).then(function (result){
            //     if (result.data.code===200){
            //         this.Vue.prototype.$notify.success({
            //             title: '成功',
            //             showClose: false,
            //             message : result.data.message,
            //             duration : 800,
            //         });
            //         examListVue.getExamListPageInfo();
            //         examListVue.dialogFormVisible=false;
            //         examListVue.resetCreateForm(formName)
            //     }else {
            //         this.Vue.prototype.$notify.error({
            //             title:'失败',
            //             showClose: false,
            //             message : result.data.message,
            //             duration: 1000,
            //         });
            //     }
            // })
        },
        resetCreateForm(formName){
            this.$refs[formName].resetFields()
        },

        //获取所有课程
        getCourse:function (){
            axios({
                method:"get",
                url:"/ExamConsumer/getCourse",
            }).then(function (result){
                console.log(result)
                // if (result.data===200){
                //     examListVue.courseId=result.data.data.list;
                // }else {
                //     this.Vue.prototype.$notify.error({
                //         title:'失败',
                //         showClose: false,
                //         message : result.data.message,
                //         duration: 1000,
                //     });
                // }
            })
        },



    }
})
var examListVue=new Vue({
    el:"#container_main",
    data:{
        examInfo:[
            {
                id:"",
                teacherId:"",
                courseId:"",
                clazzId:"",
                state:"",
                endTime:"",
                startTime:"",
                time:"",
                quantity:"",
            },
        ],
        questionMap:[
            {
                id:"",
                analysis:"",
                courseId:"",
                question:"",
                realAnswer:"",
                teacherId:"",
                answersMap:[
                    {
                        A:[
                            {
                                id:"",
                                answer:"",
                            }
                        ],
                        B:[
                            {
                                id:"",
                                answer:"",
                            }
                        ],
                        C:[
                            {
                                id:"",
                                answer:"",
                            }
                        ],
                        D:[
                            {
                                id:"",
                                answer:"",
                            }
                        ],
                    }
                ],
            },
        ],
        teacherInfo:[
            {
                id:"",
                name:"",
                age:"",
                sex:"",
                birthday:"",
                phone:"",
            }
        ],
        course:[
            {
                id:"",
                name:""
            },
        ]

    },
    methods: {
        getExamByExamId:function (){
            axios({
                method:"get",
                url:"/ExamConsumer/getExamByExamId"
            }).then(function (result){
                console.log(result)
                if (result.data.code===200){
                    // console.log(result.data.data)
                    // console.log(result.data.data.questionMap)
                    examListVue.examInfo=result.data.data;
                    examListVue.questionMap=result.data.data.questionMap;
                    examListVue.teacherInfo=result.data.data.teacher;

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




        handleOpen(key, keyPath) {
            console.log(key, keyPath);
        },
        handleClose(key, keyPath) {
            console.log(key, keyPath);
        },
        toHref: function (event) {
            // console.log(event)
            // console.log(event.currentTarget)
            window.parent.iframe.location.href=event.$attrs.href;
            // console.log(child.href)
        },
    }
})
// var container_asideVue=new Vue({
//     el:"#container_aside",
//     data:{},
//     methods: {
//         handleOpen(key, keyPath) {
//             console.log(key, keyPath);
//         },
//         handleClose(key, keyPath) {
//             console.log(key, keyPath);
//         }
//     }
// })
var container_footerVue=new Vue({
    el:"#container_footer",
})
