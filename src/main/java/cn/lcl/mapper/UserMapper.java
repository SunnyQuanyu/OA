package cn.lcl.mapper;

import cn.lcl.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {
    Integer deleteUsers(Integer userId);

    User selectUserMessage(Integer userId);
}
