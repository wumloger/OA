package top.wuml.oa.mapper;

import lombok.Data;
import org.junit.jupiter.api.Test;
import top.wuml.oa.entity.Notice;
import top.wuml.oa.util.MybatisUtils;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NoticeMapperTest {

    @Test
    void insert() {
        Notice notice = new Notice();
        notice.setCreateTime(new Date());
        notice.setContent("ccccccc");
        notice.setReceiverId(5L);

        MybatisUtils.executeUpdate(sqlSession -> {
            sqlSession.getMapper(NoticeMapper.class).insert(notice);

            return null;
        });
    }

    @Test
    void selectByReceiverId() {
        MybatisUtils.executeQuery(sqlSession -> {
            List<Notice> notices = sqlSession.getMapper(NoticeMapper.class).selectByReceiverId(5L);
            System.out.println(notices);
            return null;
        });
    }
}