package top.wuml.oa.mapper;

import top.wuml.oa.entity.Employee;
import top.wuml.oa.entity.Node;
import top.wuml.oa.util.MybatisUtils;

import java.util.List;

public class NodeMapper {
    public List<Node> selectNodeByUId(Long uid){
        List<Node> nodes = (List<Node>) MybatisUtils.executeQuery(sqlSession -> sqlSession.selectList("top.wuml.oa.mapper.NodeMapper.selectNodeByUId",uid));
        return nodes;
    }
}
