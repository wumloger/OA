package top.wuml.oa.service;

import top.wuml.oa.entity.Notice;
import top.wuml.oa.mapper.NoticeMapper;
import top.wuml.oa.util.MybatisUtils;

import java.util.List;

public class NoticeService {
    public List<Notice> getNoticeList(Long receiverId){
        return (List) MybatisUtils.executeQuery(sqlSession -> {
            NoticeMapper mapper = sqlSession.getMapper(NoticeMapper.class);
            return mapper.selectByReceiverId(receiverId);
        });
    }
}
