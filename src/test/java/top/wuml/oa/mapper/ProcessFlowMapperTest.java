package top.wuml.oa.mapper;

import org.junit.jupiter.api.Test;
import top.wuml.oa.entity.ProcessFlow;
import top.wuml.oa.util.MybatisUtils;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProcessFlowMapperTest {

    @Test
    void update(){
        MybatisUtils.executeUpdate(sqlSession -> {
            ProcessFlow processFlow = new ProcessFlow();
            processFlow.setFormId(1L);
            processFlow.setOperatorId(3L);
            processFlow.setAction("apply");
            processFlow.setCreateTime(new Date());
            processFlow.setAuditTime(new Date());
            processFlow.setOrderNo(1);
            processFlow.setState("测试成功");
            processFlow.setIsLast(0);
            processFlow.setProcessId(25L);
            sqlSession.getMapper(ProcessFlowMapper.class).update(processFlow);
            return null;
        });
    }

    @Test
    void selectByFormId(){
        MybatisUtils.executeQuery(sqlSession -> {
            List<ProcessFlow> processFlows = sqlSession.getMapper(ProcessFlowMapper.class).selectByFormId(12L);
            System.out.println(processFlows);
            return null;
        });
    }
}