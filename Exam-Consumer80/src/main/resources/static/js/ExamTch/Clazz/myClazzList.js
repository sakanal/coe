window.onload=function (){
    clazzListVue.getAllClazzInfo();
    clazzListVue.getTeacherId();
}
var clazzListVue=new Vue({
    el:"#clazzList",
    data:{
        teacherInfo:[],
        teacherId:0,
        clazzList:[],
        dialogFormVisible:false,
        formLabelWidth: '120px',
        createClazzInfo:{
            clazzId:"",
        }
    },
    methods:{
        //获取所有班级信息：班级号，该班级教师数，该班级学生数
        getAllClazzInfo:function (){
            axios({
                method:"get",
                url:"/coe/class/getMyClazzList"
            }).then(function (result){
                // console.log(result);
                if (result.data.code===200){
                    clazzListVue.clazzList=result.data.data;
                }
            })
        },
        //获取登录的教师id
        getTeacherId:function (){
            axios({
                method: "get",
                url:"/teachers/getId",
            }).then(function (result){
                console.log("获取教师id完成")
                console.log(result);
                clazzListVue.teacherId=result.data;
            })
        },
        //开启添加班级的模态框，并重置数据
        openDialogForCreateClazz:function (){
            clazzListVue.createClazzInfo.clazzId="";
            clazzListVue.dialogFormVisible=true;
        },
        //创建班级
        createClazz:function (){
          alert("发送请求创建班级")
        },





        //表格所需
        handleEdit(index, row) {
            console.log(index, row);
        },
        //表格所需
        handleDelete(index, row) {
            console.log(index, row);
        }
    },
})