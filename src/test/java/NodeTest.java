import org.junit.jupiter.api.Test;
import top.wuml.oa.mapper.NodeMapper;
import top.wuml.oa.service.NodeService;

public class NodeTest {
    private NodeMapper nodeMapper = new NodeMapper();
    private NodeService nodeService = new NodeService();
    @Test
    void testSelectNodeByUid(){
        System.out.println(nodeService.getNode(1l));

    }
}
