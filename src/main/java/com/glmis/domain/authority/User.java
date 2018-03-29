package com.glmis.domain.authority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.glmis.domain.personnel.Actor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by xuling on 2016/10/11.
 */

@Entity
@Table(name = "a_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<UserRole> userRoles;
    /**
     * optional = false    Person不可以为空
     * unique = true    该列值不可以重复
     * 不能弄级联保存，如果在数据库里已经存在与需要保存的Actor相同的id记录，则级联保存出错
     */
    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private Actor actor;

    public User(){}
    public User(Integer id){
        this.id=id;
    }
    public User(User user) {
        this.id = user.id;
        this.userName = user.userName;
        this.password = user.password;
        this.userRoles = user.userRoles;
        this.actor = user.actor;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }
}
