package top.wuml.oa.entity;

import lombok.Data;

@Data
public class Node {
    private Long nodeId;
    private int nodeType;
    private String nodeName;
    private String url;
    private int nodeCode;
    private Long parentId;
}
