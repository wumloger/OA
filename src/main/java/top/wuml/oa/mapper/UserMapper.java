package top.wuml.oa.mapper;

import top.wuml.oa.entity.User;
import top.wuml.oa.util.MybatisUtils;

public class UserMapper {
    public User selectByUserName(String userName){
        User user =(User)MybatisUtils.executeQuery(sqlSession -> sqlSession.selectOne("top.wuml.oa.mapper.UserMapper.selectByUserName",userName));
        return user;
    }
}
