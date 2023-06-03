window.onload=function (){
    clazzListVue.getAllClazzInfo();
}
var clazzListVue=new Vue({
    el:"#clazzList",
    data:{
        clazzList:[]
    },
    methods:{
        //获取所有班级信息：班级号，该班级教师数，该班级学生数
        getAllClazzInfo:function (){
            axios({
                method:"get",
                url:"/coe/class/getAllClazzInfo"
            }).then(function (result){
                console.log(result);
                if (result.data.code===200){
                    clazzListVue.clazzList=result.data.data;
                }
            })
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