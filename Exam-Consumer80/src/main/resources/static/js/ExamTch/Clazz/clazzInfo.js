window.onload=function (){
    clazzInfoVue.getClazzInfo();
}
var clazzInfoVue=new Vue({
    el:"#clazzInfo",
    data:{
        clazzInfo:[
            {
                id:"",
                studentList:[],
                studentNumber:"",
                teacherList:[],
                teacherNumber:"",
            }
        ],
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
        teacherList:[
            {
                id:"",
                name:"",
                age:"",
                sex:"",
                birthday:"",
                phone:"",
            },
        ]
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
        }
    },
})