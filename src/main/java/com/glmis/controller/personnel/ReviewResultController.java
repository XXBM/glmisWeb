package com.glmis.controller.personnel;

import com.glmis.domain.personnel.ReviewResult;
import com.glmis.service.personnel.ReviewResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by dell on 2016/11/17.
 */

@RestController
public class ReviewResultController {
    @Autowired
    ReviewResultService reviewResultService;

    /**
     * 获取到所有的审核结果
     */
    @RequestMapping(value = "/findAllCheck", method = RequestMethod.GET)
    public List<ReviewResult> findAllCheck()throws Exception {
        List<ReviewResult> reviewResult = reviewResultService.findAllT();
        return reviewResult;
    }
}
