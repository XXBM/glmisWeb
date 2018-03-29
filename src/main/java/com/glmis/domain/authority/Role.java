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
 * Created by dell on 2016/10/16.
 */

@Entity
@Table(name = "a_role")
@DynamicUpdate(true)
@DynamicInsert(true)
public class Role implements Serializable {

    /**
     * 角色id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    /**
     * 角色描述，
     * 目前有teacher、student、manager
     */
    private String description;
    /**
     * 英文描述角色名字
     */
    @Column(name = "role_name")
    private String roleName;
    /**
     * 角色和菜单之间是多对多关系，
     * 生成中间表确定角色和菜单之间的权限
     */

    @JoinTable(name = "role_resource",
            joinColumns = {@JoinColumn(name = "Role_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "Resource_ID", referencedColumnName = "ID")})
    @ManyToMany
    private Set<Resource> resource = new HashSet<>();
    /**
     * 角色多对应的用户
     * 一种角色对应多个用户
     */
    @JsonIgnore
    @OneToMany(mappedBy = "role",cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<UserRole> userRoles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    @JsonIgnore
    public Set<Resource> getResource1() {
        return resource;
    }

    public void setResource1(Set<Resource> resource1) {
        this.resource = resource1;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
