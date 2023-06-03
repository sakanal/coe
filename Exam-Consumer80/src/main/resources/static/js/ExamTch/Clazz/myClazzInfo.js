window.onload=function (){
    clazzInfoVue.getClazzInfo();
}
var clazzInfoVue=new Vue({
    el:"#clazzInfo",
    data:{
        //该班级的基本信息
        clazzInfo:[
            {
                id:"",
                studentList:[],
                studentNumber:"",
                teacherList:[],
                teacherNumber:"",
            }
        ],
        //用于展示学生
        studentList:[
            {
                id:"",
                name:"",
                age:"",
                sex:"",
                birthday:"",
                clazzId:"",
                phone:"",
            }
        ],
        //用于展示教师
        teacherList:[
            {
                id:"",
                name:"",
                age:"",
                sex:"",
                birthday:"",
                phone:"",
            },
        ],
        //用于搜索学生
        searchStudent:{
            id:"",
            name:"",
        },
        //用于搜索教师
        searchTeacher:{
            id:"",
            name:"",
        },

        //开启添加学生的模态框
        addStudentDialogVisible:false,
        //开启添加教师的模态框
        addTeacherDialogVisible:false,
        //未加入班级的学生列表
        addStudentList:[
            {
                id:"",
                name:"",
                age:"",
                sex:"",
                birthday:"",
                clazzId:"",
                phone:"",
            }
        ],
        //添加信息
        addStudentInfo:{
            id:"",
        },
        //加入班级的教师列表
        addTeacherList:[
            {
                id:"",
                name:"",
                age:"",
                sex:"",
                birthday:"",
                phone:"",
            },
        ],
        //添加教师
        addTeacher:{
            id:"",
        },
    },
    methods:{
        //获取班级详细信息
        getClazzInfo:function (){
            axios({
                method:"get",
                url:"/coe/class/getClazzInfo"
            }).then(function (result){
                console.log(result)
                if (result.data.code===200){
                    clazzInfoVue.clazzInfo=result.data.data;
                    clazzInfoVue.studentList=result.data.data.studentList;
                    clazzInfoVue.teacherList=result.data.data.teacherList;
                }else {
                    alert(result.data.message)
                }


            })
        },

        //搜索学生
        onStudentSearch:function (){
            alert("发送搜索学生请求")
        },
        //重置学生搜索条件
        resetStudentForm:function (){
            clazzInfoVue.searchStudent.id="";
            clazzInfoVue.searchStudent.name="";
        },
        //搜索教师
        onTeacherSearch:function (){
            alert("发送搜索教师请求")
        },
        //重置教师搜索条件
        resetTeacherForm:function (){
            clazzInfoVue.searchTeacher.id="";
            clazzInfoVue.searchTeacher.name="";
        },

        //开启新增学生模态框
        openAddStudentDialogVisible:function (){
            axios.get("/coe/class/getNotInClassStudentList").then(result=>{
                if (result.data.code===200){
                    clazzInfoVue.addStudentList=result.data.data;
                    // console.log(this)
                    this.$message({
                        message: result.data.message,
                        type: 'success',
                        duration:800,
                    });
                    clazzInfoVue.addStudentDialogVisible=true;
                }else {
                    this.$message({
                        message: result.data.message,
                        type: 'error',
                        duration:1000,
                    });
                }
                // console.log(result)
            })
        },
        //为班级新增学生
        addStudent: function (){
            // var clazzId=clazzInfoVue.clazzInfo.id;
            // var studentId=clazzInfoVue.addStudentInfo.id;
            // console.log("班级id--》"+clazzId)
            // console.log("学生id--》"+studentId)
            axios({
                method:"post",
                url:"/coe/class/addStudentToClass",
                params:{
                    clazzId:clazzInfoVue.clazzInfo.id,
                    studentId:clazzInfoVue.addStudentInfo.id,
                },
            }).then(function (result){
                if (result.data.code===200){
                    clazzInfoVue.addStudentDialogVisible = false
                    console.log(this)
                    this.Vue.prototype.$message({
                        message:result.data.message,
                        type:'success',
                        duration:800,
                    })
                    location.reload();
                }else {
                    this.Vue.prototype.$message({
                        message:result.data.message,
                        type:'error',
                        duration:1000,
                    })
                }
            })


        }
    },
    watch:{},
})