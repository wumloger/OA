package top.wuml.oa.mapper;

import org.apache.ibatis.annotations.Param;
import top.wuml.oa.entity.Notice;

import java.util.List;

public interface NoticeMapper {
    void insert(Notice notice);
    List<Notice> selectByReceiverId(@Param("receiverId") Long receiverId);
}
