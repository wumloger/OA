package top.wuml.oa.mapper;

import top.wuml.oa.entity.LeaveForm;
import top.wuml.oa.util.MybatisUtils;

public interface LeaveFormMapper {
    void insert(LeaveForm leaveForm);

    void update(LeaveForm form);
    LeaveForm selectById(Long formId);
}
