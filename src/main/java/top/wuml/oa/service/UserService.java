package top.wuml.oa.service;

import top.wuml.oa.entity.User;
import top.wuml.oa.mapper.UserMapper;
import top.wuml.oa.service.exception.LoginException;
import top.wuml.oa.util.MD5Utils;

public class UserService {
    private UserMapper userMapper = new UserMapper();

    public User login(String username, String password){

        User user = userMapper.selectByUserName(username);
        if (user == null){
            throw new LoginException("用户名不存在");
        }
        System.out.println(user.getPassword());
        String md5Password = MD5Utils.md5Digest(password,user.getSalt());
        System.out.println(md5Password);
        if (!md5Password.equals(user.getPassword())){
            throw new LoginException("密码错误");
        }
        return user;

    }
}
