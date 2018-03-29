package com.glmis.domain.authority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by dell on 2016/8/22.
 * 菜单表（存放一级菜单和二级菜单的内容）
 */

@Entity
@Table(name = "a_resource")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Resource implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    //easyui tree结点显示的文字
    private String text;
    private String no;
    /**
     * 结点的状态，有两个值open close
     */
    private String state;
    /**
     * 菜单路径
     */
    private String url;
    /**
     * 自关联
     * 子类
     */

    @OneToMany(mappedBy = "parent",cascade = CascadeType.ALL)
    private List<Resource> children;
    /**
     * JPA的自关联
     * 父类
     */
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Resource parent;
    @JsonIgnore
    @ManyToMany(mappedBy = "resource")
    private Set<Role> role1Set = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Resource> getChildren() {
        return children;
    }

    public void setChildren(List<Resource> children) {
        this.children = children;
    }
    @JsonIgnore
    public Resource getParent() {
        return parent;
    }

    public void setParent(Resource parent) {
        this.parent = parent;
    }

    public Set<Role> getRole1Set() {
        return role1Set;
    }

    public void setRole1Set(Set<Role> role1Set) {
        this.role1Set = role1Set;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }



}
