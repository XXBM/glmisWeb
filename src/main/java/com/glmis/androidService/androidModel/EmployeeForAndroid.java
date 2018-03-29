package com.glmis.androidService.androidModel;

/**
 * Created by 杨云召 on 26/05/2017.
 * 为android创建的Employee实体类
 */
public class EmployeeForAndroid {
    private long id;
    private String name;//员工姓名
    private String department;//员工所在教研室
    private boolean isAttendant;
    //TODO 增加员工头像
    private String imagePath;

    public EmployeeForAndroid() {
    }

    public EmployeeForAndroid(long id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }
    public EmployeeForAndroid(long id, String name, String department,String imagePath) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.imagePath = imagePath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public boolean isAttendant() {
        return isAttendant;
    }

    public void setAttendant(boolean attendant) {
        isAttendant = attendant;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}

