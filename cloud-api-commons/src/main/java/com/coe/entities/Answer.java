package com.coe.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    private String Id;//对于Question类为答案号（ABCD）/对于Score类为题号（123...question类的Id）
    private String answer;//答案
}
