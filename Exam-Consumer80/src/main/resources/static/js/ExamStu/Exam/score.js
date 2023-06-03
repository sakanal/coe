window.onload=function (){
    container_headerVue.getUser();
}

var containerMainVue=new Vue({
    el:"#container_main",
    data:{
        studentInfo:[],
    },
    methods:{
        getStudentInfo:function (){
            axios({
                method:"get",
                url:"/ExamStuConsumer/getStudent",
            }).then(function (result){
                containerMainVue.studentInfo=result.data.data
            })
        },
    }
})




var container_headerVue=new Vue({
    el:"#container_header",
    data:{
        userId:0,
        userName:"",
        identity:0,
        //false表示学生，true表示教师
        identityFlag:false,
        userFlag:false,
    },
    methods:{
        getUser:function (){
            axios({
                method:"get",
                url:"/getUserId",
            }).then(function (result){
                console.log("获取用户id");
                console.log(result)
                if (result.data===""){
                    console.log("用户未登录")
                    container_headerVue.userFlag=false;
                }else {
                    container_headerVue.userId=result.data;
                    container_headerVue.userFlag=true;

                    axios({
                        method:"get",
                        url:"/getUserIdentity"
                    }).then(function (result){
                        // console.log("获取用户身份")
                        // console.log(result)
                        if (result.data===""){
                            // console.log("用户身份错误")
                            container_headerVue.identity=0;
                        }else {
                            container_headerVue.identity=result.data;
                            if (result.data===1){
                                container_headerVue.identityFlag=false;
                            }else if (result.data===2){
                                container_headerVue.identityFlag=true;
                            }

                            console.log("身份是"+container_headerVue.identityFlag);
                        }
                    })

                    axios({
                        method:"get",
                        url:"/getUserName"
                    }).then(function (result){
                        container_headerVue.userName=result.data;
                    })
                }
                console.log(container_headerVue.userFlag);
            });
        },
        logout:function (){
            axios({
                method: "get",
                url: "/logout"
            })
            location.reload();
        }
    }
})
var containerFooterVue=new Vue({
    el:"#container_footer"
})