package top.wuml.oa.mapper;

import org.junit.jupiter.api.Test;
import top.wuml.oa.entity.ProcessFlow;
import top.wuml.oa.util.MybatisUtils;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ProcessFlowMapperTest {
    @Test
    void insert(){
        MybatisUtils.executeUpdate(sqlSession -> {
            ProcessFlowMapper mapper = sqlSession.getMapper(ProcessFlowMapper.class);
            ProcessFlow processFlow = new ProcessFlow();
            processFlow.setFormId(1L);
            processFlow.setOperatorId(3L);
            processFlow.setAction("apply");
            processFlow.setCreateTime(new Date());
            processFlow.setAuditTime(new Date());
            processFlow.setOrderNo(1);
            processFlow.setState("processing");
            processFlow.setIsLast(0);
            mapper.insert(processFlow);
            return null;
        });
    }
}