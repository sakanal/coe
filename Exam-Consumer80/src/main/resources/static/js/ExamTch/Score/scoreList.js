window.onload=function (){
    scoreListVue.getBaseInfo();
}
var scoreListVue=new Vue({
    el:"#scoreList",
    data:{
        clazzId:0,
        examId:0,
        scoreList:[],
    },
    methods:{
        //获取基本信息--班级id和试卷id
        getBaseInfo:function (){
            axios.get("/Session/getClazzId").then(result=>{
                // console.log("获取班级id")
                scoreListVue.clazzId=result.data;
                axios.get("/Session/getExamId").then(result=>{
                    // console.log("获取试卷id")
                    scoreListVue.examId=result.data;
                    scoreListVue.getScoreList();
                })
            });
        },
        getScoreList:function (){
            axios({
                method:"get",
                url:"/Score/getScoreList",
                params:{
                    clazzId:scoreListVue.clazzId,
                    examId:scoreListVue.examId,
                }
            }).then(function (result){
                // console.log("获取该班级的该试卷的成绩列表")
                console.log(result)
                if (result.data.code===200){
                    scoreListVue.scoreList=result.data.data;
                }
            })
        },
        toScore:function (row){
            console.log(row);
            window.open("/ExamStuConsumer/toScore?examId="+row.examId+"&studentId="+row.studentId);
        }
    },
})