package cn.lcl.mapper;

import cn.lcl.pojo.QuestionOption;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface QuestionOptionMapper extends BaseMapper<QuestionOption> {
}
