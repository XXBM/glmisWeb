package com.glmis.service.personnel;

import com.glmis.domain.personnel.Reward;
import com.glmis.repository.personnel.RewardRepository;
import com.glmis.service.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ibs on 16/11/6.
 */

@Service
public class RewardService extends BasicService<Reward,Long>{
    @Autowired
    RewardRepository rewardRepository;
    /**通过选中的下拉框实体id获取所有的自己的所有属性*/
    public List<Reward> findByEmployeeId(Long id){
        return this.rewardRepository.findByEmployeeId(id);
    }
    /**通过选中的下拉框实体id获取所有的自己的所有属性   +  分页显示*/
    public  Page<Reward> findByEmployeeId(Long id,Pageable pageable){
        return this.rewardRepository.findByEmployeeId(id,pageable);
    }
}
