package top.wuml.oa.mapper;

import org.apache.ibatis.annotations.Param;
import top.wuml.oa.entity.LeaveForm;
import top.wuml.oa.util.MybatisUtils;

import java.util.List;
import java.util.Map;

public interface LeaveFormMapper {
    void insert(LeaveForm leaveForm);

    void update(LeaveForm form);
    LeaveForm selectById(Long formId);

    List<Map<String, Object>> selectByParams(@Param("state") String state, @Param("operatorId") Long operatorId);
}
