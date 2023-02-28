package top.wuml.oa.service;

import top.wuml.oa.entity.Node;
import top.wuml.oa.mapper.NodeMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeService {
    private NodeMapper nodeMapper = new NodeMapper();
    public List<Map<String,Object>> getNode(Long uid){
        List<Node> nodes = nodeMapper.selectNodeByUId(uid);
        List<Map<String ,Object>> treeList = new ArrayList<>();
        Map<String ,Object> module = null;

        for (Node node:nodes) {
            if (node.getNodeType() == 1){
                module = new HashMap<>();
                module.put("node",node);
                module.put("child",new ArrayList<>());
                treeList.add(module);

            }else if(node.getNodeType() == 2){
                List<Node> children = (List<Node>)module.get("child");
                children.add(node);
            }
        }
        return treeList;
    }
}
