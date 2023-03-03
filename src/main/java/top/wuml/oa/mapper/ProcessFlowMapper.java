package top.wuml.oa.mapper;

import top.wuml.oa.entity.ProcessFlow;
import top.wuml.oa.util.MybatisUtils;

import java.util.List;

public interface ProcessFlowMapper {
    void insert(ProcessFlow processFlow);
    void update(ProcessFlow processFlow);
    List<ProcessFlow> selectByFormId(Long formId);
}
