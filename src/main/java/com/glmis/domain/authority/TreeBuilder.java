package com.glmis.domain.authority;

import java.util.*;

/**
 * Created by xuling on 2016/11/22.
 * 构造符合easyui tree形式的树模型。。
 * 先将role转换为resource类型的，然后写个方法来 CloneResource ,
 然后要同时维护两个 map，一个是原 resouce map，另一个是 clone 的 map
 然后以角色为单位对数据进行处理，每个角色需要 new 一个 clone 的 map
 通过角色取得关联的3级菜单，然后通过递归向上（parent）把整棵树找出来
 因为登录的时候就已经取了角色了，所以这里取出来的所有角色就是要送给前端的角色
 */

public class TreeBuilder {
    private List<Role> roles;
    private Map<Integer,Resource> resourceMap;
    private  transient  Map<Integer,Resource> cloneMap;//不会被序列化

//    构造函数

    public TreeBuilder(List<Role> roles) {
        this.roles = roles;
        buildResMap();
    }

    //构造菜单集合 调用该方法返回一个菜单
    public List<Resource> build() {
    List<Resource> result = new ArrayList<>();
    for (Role role: roles) {
        result.add(buildRootForRole(role));
    }
    return result;
}
//    从role找到所有的resource（所有的）
    private void  buildResMap(){
//        建立一个队列用来存放所有的resources
        LinkedList<Resource> queue = new LinkedList<>();
        for (Role role:roles){
//            往queue里边添加通过角色得到的resource
            queue.addAll(role.getResource1());
        }
        resourceMap = new HashMap<>();
        while (!queue.isEmpty()){
//            poll():移除并返回队列头部的元素
// 从队列中将resource拿出来
            Resource r = queue.poll();
//            将resource以键为id的形式放到resourceMap集合中
            resourceMap.put(r.getId(),r);
            Resource parent =r.getParent();
//            如果父节点不为空 且resourceMap中不包含父节点的id
            if (parent!=null && !resourceMap.containsKey(parent.getId()) ){
                queue.add(parent);

            }

        }
    }

    //
    private Resource buildRootForRole(Role role){
//        将role转化为Resource类型的对象 根节点root
        Resource root = toResouce(role);
        cloneMap = new HashMap<>();
//        创建一个队列
        LinkedList<Resource> queue = new LinkedList<>();
//        将resource装到queue里
        queue.addAll(role.getResource1());
//        queue如果不为null,
        while (!queue.isEmpty()){
            Resource res = queue.poll();
            Resource r =getClonedResource(res);
            Resource parent = res.getParent();
//            如果父节点为空,则将子节点挂在根节点上
            if (parent==null){
                addChildTo(r,root);
            }else
            {//如果父节点不为空的话，将子节点挂在父节点上，然后在把父节点添加到队列上
                Resource cloneParent = getClonedResource(parent);
                addChildTo(r,cloneParent);
                queue.add(cloneParent);
            }
        }
        cloneMap = null;
//        返回根节点
        return root;
    }
    //给父节点添加子节点
    private static void addChildTo(Resource child,Resource parent){
//        建立一个list存放子节点
        List<Resource> children = parent.getChildren();
        if (children==null){
            children=new ArrayList<>();
            parent.setChildren(children);
        }
//        去掉节点重复问题
        if (!children.contains(child)){
           children.add(child);
        }

        Collections.sort(children, new Comparator<Resource>(){
            @Override
            public int compare(Resource o1, Resource o2) {
                //按照学生的年龄进行升序排列
                if(o1.getId() > o2.getId()){
                    return 1;
                }
                if(o1.getId() == o2.getId()){
                    return 0;
                }
                return -1;
            }
        });
    }
    //得到克隆的菜单
    private Resource getClonedResource(Resource res){
        Resource r = cloneMap.get(res.getId());
        if (r==null){
            r=cloneResource(res);
            cloneMap.put(r.getId(),r);
        }
        return r;
    }
    //    克隆一个菜单的id，text，url
    private static  Resource cloneResource(Resource s){
        Resource r = new Resource();
        r.setId(s.getId());
        r.setState(s.getState());
        r.setText(s.getText());
        r.setUrl(s.getUrl());
        return r;
    }
    //因为Role不符合easyui tree的要求，所以将Role类型的对象转化为Resource类型的
    public static Resource toResouce(Role role) {
        Resource r = new Resource();
//        为了防止变成Resource类型的role的id与原来的resource的id重复，所以对id都加了10
        r.setId(role.getId()+10);
        r.setText(role.getDescription());
        return r;
    }

}
