import cn.hutool.json.JSONUtil;
import com.allweb.questions.QuestionsMain;
import com.allweb.questions.service.IQuestionsService;
import com.allweb.questions.entities.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = QuestionsMain.class)
public class T1 {
    @Autowired
    private IQuestionsService questionsService;

    @Test
    public void test1(){
        List<Integer> list=new ArrayList<>();
        list.add(5);
        list.add(8);
        List<Question> batchByIds = questionsService.getBatchByIds(list);
        List<Question> list1 = questionsService.list();
//        questionsService.addCourse(list1);
        for (Question question : list1) {
            question.StrToMap();
            question.setAnswer(null);
            question.setCourseId(null);
        }
        String jsonStr = JSONUtil.toJsonStr(list1);
        System.out.println(jsonStr);
    }
}
