package com.glmis.controller.booking;

import com.glmis.domain.authority.User;
import com.glmis.domain.booking.Adress;
import com.glmis.domain.booking.Appointment;
import com.glmis.domain.booking.Equipment;
import com.glmis.domain.booking.Time;
import com.glmis.service.authority.UserService;
import com.glmis.service.booking.AdressService;
import com.glmis.service.booking.AppointmentService;
import com.glmis.service.booking.EquipmentService;
import com.glmis.service.booking.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xuling on 2016/11/8.
 * 有关于预约的
 */

@RestController
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private AdressService adressService;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private TimeService timeService;
    @Autowired
    private UserService userService;
    public static final DateFormat formatWithTime = new SimpleDateFormat("yyyy-MM-dd");
    //这里是显示预约房间的信息等
    @RequestMapping(value = "/displayRoomInf", method = RequestMethod.GET)
    public Map<String, Object> displayRoom(@RequestParam(value = "page") Integer page,
                                           @RequestParam(value = "rows") Integer size) {
        Pageable pageable = new PageRequest(page - 1, size);
        Page<Time> times = this.timeService.findAllT(pageable);
        int total = this.timeService.findAllT().size();
        List<Adress> adresses = this.adressService.findAllT();
        Map<String, Object> map = new HashMap<>();
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userService.findByUserName(userName);
        Integer userId = user.getId();
        map.put("userId", userId);
        map.put("adresses", adresses);
        map.put("total", total);
        map.put("rows", times.getContent());
        return map;
    }
    //这里显示预约设备的信息
    @RequestMapping(value = "/displayEquipmentInf", method = RequestMethod.GET)
    public Map<String, Object> displayEquipmentInf(@RequestParam(value = "page") Integer page, @RequestParam(value = "rows") Integer size) {
        Pageable pageable = new PageRequest(page - 1, size);
        Page<Time> times = this.timeService.findAllT(pageable);
        int total = this.timeService.findAllT().size();
        List<Equipment> equipments = this.equipmentService.findAllT();
        Map<String, Object> map = new HashMap<>();
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userService.findByUserName(userName);
        Integer userId = user.getId();
        map.put("userId", userId);
        map.put("equipments", equipments);
        map.put("total", total);
        map.put("rows", times.getContent());
        return map;
    }
//    @RequestMapping(value = "/bookRoom",method = RequestMethod.POST)
//    public void bookRoom(@RequestBody Map bookingMap) {
//        //前台的日期
//        String dayReqStr = (String) bookingMap.get("day");
//        Date dueDate = null;
//        try {
//            dueDate = formatWithTime.parse(dayReqStr);
//            //时间
//            Integer timeIdReq = (Integer) bookingMap.get("time");
//            Time time = this.timeService.findOne(timeIdReq);
//            //房间
//            Integer bookAdressIdReq = (Integer) bookingMap.get("bookingAdress");
//            BookingResources bookingAdress = null;
//            //如果前台的执行的是借用投影仪时，她传过来的房间的id就是空的，只有当前台传过房间的id来时，才通过房间的id去获取相应的对象
//                bookingAdress = this.adressService.findOne(bookAdressIdReq);
//            System.out.println(dayReqStr + "," + time + "," + bookingAdress);
//            Appointment appointment = new Appointment();
//            appointment.setDay(dueDate);
//            appointment.setTime(time);
//            appointment.setBookingAdress(bookingAdress);
//            this.appointmentService.add(appointment);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
    @RequestMapping(value = "/saveAppointment",method = RequestMethod.POST)
     public Map<String,Object>  saveAppointment(@RequestBody Appointment appointment)
    {
        this.appointmentService.add(appointment);
        Map<String,Object> map = new HashMap<>();
        map.put("appointments",appointment);
        return map;
   }

    //这里显示预约表里的信息
    @RequestMapping(value = "/displayAppointmentInf", method = RequestMethod.GET)
    public Map<String, Object> displayAppointmentInf(@RequestParam(value = "page") Integer page, @RequestParam(value = "rows") Integer size) {
        Pageable pageable = new PageRequest(page - 1, size);
        Page<Appointment> appointments = this.appointmentService.findAllT(pageable);
        int total = this.appointmentService.findAllT().size();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", appointments.getContent());
        return map;
    }

}
