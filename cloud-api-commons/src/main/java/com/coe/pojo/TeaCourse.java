package com.coe.pojo;

//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeaCourse {
//    @TableId(type = IdType.AUTO)
    private Integer id;
    private String courseId;
    private Long teacherId;
}
