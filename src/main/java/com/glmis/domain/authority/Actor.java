//package com.glmis.domain.authority;
//
//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.util.Date;
//
///**
// * Created by dell on 2016/10/17.
// */
//@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@DynamicInsert(true)
//@DynamicUpdate(true)
//public abstract class Actor implements Serializable{
//
//    @Id
//    @GeneratedValue(strategy=GenerationType.TABLE)
//    private Integer id;
//    /**
//     * 编号
//     */
//    @Column(length=12)
//    private String no;
//    /**
//     * 名字
//     */
//    @Column(length=15)
//    private String name;
//    /**
//     * 邮件
//     */
//    @Column(length=40)
//    private String email;
//    /**
//     * 电话
//     */
//    @Column(length=20)
//    private String mobile;
//    /**
//     * QQ
//     */
//    @Column(length=24)
//    private String qq;
//    /**
//     * 性别
//     */
//    private String sex;
//    /**
//     * 出生日
//     * Date表示日期
//     * Time表示时间
//     * TimeStamp表示日期+时间
//     */
//    @Temporal(TemporalType.DATE)
//    private Date birth;
//
//    @OneToOne(cascade=CascadeType.ALL,optional = false)
//    @JoinColumn(name="user_id",unique = true)
//    private User user;
//
//    public Actor(){}
//    public Actor(String no, String name, String email, String mobile, String qq, String sex, Date birth, User user) {
//        this.no = no;
//        this.name = name;
//        this.email = email;
//        this.mobile = mobile;
//        this.qq = qq;
//        this.sex = sex;
//        this.birth = birth;
//        this.user = user;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getNo() {
//        return no;
//    }
//
//    public void setNo(String no) {
//        this.no = no;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getMobile() {
//        return mobile;
//    }
//
//    public void setMobile(String mobile) {
//        this.mobile = mobile;
//    }
//
//    public String getQq() {
//        return qq;
//    }
//
//    public void setQq(String qq) {
//        this.qq = qq;
//    }
//
//    public String getSex() {
//        return sex;
//    }
//
//    public void setSex(String sex) {
//        this.sex = sex;
//    }
//
//    public Date getBirth() {
//        return birth;
//    }
//
//    public void setBirth(Date birth) {
//        this.birth = birth;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//}
