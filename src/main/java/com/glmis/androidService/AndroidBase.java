package com.glmis.androidService;

import com.glmis.androidService.androidModel.EmployeeForAndroid;
import com.glmis.androidService.androidModel.UserForAndroid;
import com.glmis.domain.authority.User;
import com.glmis.domain.personnel.Employee;
import com.glmis.service.attendance.*;
import com.glmis.service.authority.UserService;
import com.glmis.service.personnel.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by 杨云召 on 26/05/2017.
 * Android 接口的基类
 */
@Controller
public class AndroidBase {

    @Autowired
    protected UniversityAbsenceDescriptionService universityAbsenceDescriptionService;
    @Autowired
    protected PresenceDescriptionService presenceDescriptionService;
    @Autowired
    protected PrivateLeaveDescriptionService privateLeaveDescriptionService;
    @Autowired
    protected UserService userService;
    @Autowired
    protected AttendanceService attendanceService;
    @Autowired
    protected AttendanceSummaryService summaryService;
    @Autowired
    protected EmployeeService employeeService;
    @Autowired
    PresenceService presenceService;
    @Autowired
    PrivateLeaveService privateLeaveService;
    @Autowired
    UniversityAbsenceService universityAbsenceService;

    protected UserForAndroid getAndroidUserByUser(User userFromWeb) {
        return new UserForAndroid(
                userFromWeb.getId().longValue(),
                userFromWeb.getUserName(),
                userFromWeb.getPassword());
    }

    protected EmployeeForAndroid getAndroidEmployeeByEmployee(Employee employeeFromWeb) {
        //TODO 将图片保存到服务器
//        String url1 = System.getProperty("user.dir");
//        int indexOf = url1.indexOf("bin");
//        String url2= url1.substring(0, indexOf);
//        String imagePath = url2 + "webapps/glmis" + "/WEB-INF/classes/static/picture/";
//        imagePath += "/"+employeeFromWeb.getId();
//        String imageName = employeeFromWeb.getIdNo();
//        File file = null;
//        if(employeeFromWeb.getPhoto()!=null){
//            byte[] imageByte = employeeFromWeb.getPhoto();
//            InputStream in = new ByteArrayInputStream(imageByte);
//            file=new File(imagePath,imageName);//可以是任何图片格式.jpg,.png等
//            if(!file.exists()){
//                try {
//                    file.createNewFile();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                FileOutputStream fos= null;
//                try {
//                    fos = new FileOutputStream(file);
//                    byte[] b = new byte[1024];
//                    int nRead = 0;
//                    while ((nRead = in.read(b)) != -1) {
//                        fos.write(b, 0, nRead);
//                    }
//                    fos.flush();
//                    fos.close();
//                    in.close();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        EmployeeForAndroid employeeForAndroid = new EmployeeForAndroid(employeeFromWeb.getId(), employeeFromWeb.getName(), employeeFromWeb.getDepartment().getDepartmentName());
//        if(file!=null){
//            employeeForAndroid.setImagePath(file.getAbsolutePath());
//        }
        EmployeeForAndroid employeeForAndroid = new EmployeeForAndroid(employeeFromWeb.getId(), employeeFromWeb.getName(), employeeFromWeb.getDepartment().getDepartmentName());
        return employeeForAndroid;
    }

    /**
     * 对字符串进行md5加密
     *
     * @param p 需要加密的字符串
     * @return 加密后的字符串
     */
    public String makeMD5(String p) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("md5");
            md.update(p.getBytes());
            String password = new BigInteger(1, md.digest()).toString(16);
            return password;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }
}
