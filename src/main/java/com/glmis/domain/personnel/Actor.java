package com.glmis.domain.personnel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.glmis.domain.authority.User;
import com.glmis.jsonDeserialize.SexDeserialize;
import com.glmis.jsonDeserialize.UserDeserialize;
import com.glmis.jsonDeserialize.YesOrNoDeserialize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
/**
 * 用户抽象
 */



@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DynamicInsert(true)
@DynamicUpdate(true)
public abstract class Actor implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    protected Long id;
    /**用户编号
     */
    @Column(unique = true)
    protected String no;
    /**用户姓名
     */
    protected String name;
    /**性别
     */
    //protected String sex;
    @ManyToOne
    @JoinColumn(name = "sex_id")
    @JsonDeserialize(using = SexDeserialize.class)
    protected Sex sex;
    /**出生日期
     *   @JsonFormat(pattern = "yyyy-MM-dd")注解是日期转化为json数据时，转化为需要的模式
     *   "yyyy-MM-dd HH-mm-ss"
     */
    @javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected Calendar birth;
    /**年龄
     * 不需要被映射到数据库表中
     */
    @Transient
    protected Integer age;
    /**身份证号
     */
    protected String idNo;
    /**籍贯
     */
    protected String grandpaBirthPlace;
    /**电话
     */
    protected String mobile;
    /**来学校工作的时间
     */
    @javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected Calendar dateToSchool;
    /**参加工作的时间
     */
    @javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected Calendar dateToWork;
    /**退休时间
     */
    @javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected Calendar dateToRetire;
    /**工龄
     */
    @Transient
    protected Integer workAge;
    /**是否华侨
     */
    //protected String overseasChineseOrNot;
    @ManyToOne
    @JoinColumn(name = "overseasChineseOrNot_id")
    @JsonDeserialize(using = YesOrNoDeserialize.class)
    protected YesOrNo overseasChineseOrNot;
    /**紧急电话
     */
    protected Long emergencyMobile;
    /**家庭地址
     */
    protected String address;
    /**邮箱
     */
    protected String email;
    /**QQ
     */
    protected String qq;
    /**微信
     */
    protected String weChat;
    /**用户
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id",unique = true)
    @JsonDeserialize(using = UserDeserialize.class)
    private User user;

    public Actor(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public YesOrNo getOverseasChineseOrNot() {
        return overseasChineseOrNot;
    }

    public void setOverseasChineseOrNot(YesOrNo overseasChineseOrNot) {
        this.overseasChineseOrNot = overseasChineseOrNot;
    }

    public Calendar getBirth() {
        return birth;
    }

    public void setBirth(Calendar birth) {
        this.birth = birth;
    }

    public Integer getAge() {
        Calendar calendar= Calendar.getInstance();
        int nowYear=calendar.get(Calendar.YEAR);
        if (this.getBirth()!=null){
        int birthYear = this.getBirth().get(Calendar.YEAR);
            this.age = (nowYear-birthYear);
            return age;
        }
        else {
            return 0;
        }
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getGrandpaBirthPlace() {
        return grandpaBirthPlace;
    }

    public void setGrandpaBirthPlace(String grandpaBirthPlace) {
        this.grandpaBirthPlace = grandpaBirthPlace;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Calendar getDateToSchool() {
        return dateToSchool;
    }

    public void setDateToSchool(Calendar dateToSchool) {
        this.dateToSchool = dateToSchool;
    }

    public Calendar getDateToWork() {
        return dateToWork;
    }

    public void setDateToWork(Calendar dateToWork) {
        this.dateToWork = dateToWork;
    }

    public Calendar getDateToRetire() {
        return dateToRetire;
    }

    public void setDateToRetire(Calendar dateToRetire) {
        this.dateToRetire = dateToRetire;
    }

    public Integer getWorkAge() {
        Calendar calendar= Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
            if(this.getDateToWork()!=null){
                int startYear=dateToWork.get(Calendar.YEAR);//开始时间
                if(this.getDateToRetire()!=null){
                    int endYear = dateToRetire.get(Calendar.YEAR);//退休时间
                    if(nowYear>endYear){
                        nowYear = endYear;
                    }
                }
                this.workAge = (nowYear-startYear);//当前日期-工作时间
                return workAge;
            }
            else{
                return 0;
            }
    }
    /***
     *计算工龄，默认当前时间-第一次工作时间
     * 当当前时间大于退休时间时，就另当前时间等于退休时间。
     */
    public void setWorkAge(Integer workAge) {
        this.workAge=workAge;
    }

    public Long getEmergencyMobile() {
        return emergencyMobile;
    }

    public void setEmergencyMobile(Long emergencyMobile) {
        this.emergencyMobile = emergencyMobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
