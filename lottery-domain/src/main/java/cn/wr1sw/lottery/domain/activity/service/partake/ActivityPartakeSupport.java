package cn.wr1sw.lottery.domain.activity.service.partake;

import cn.wr1sw.lottery.domain.activity.model.req.PartakeReq;
import cn.wr1sw.lottery.domain.activity.model.vo.ActivityBillVO;
import cn.wr1sw.lottery.domain.activity.repository.IActivityRepository;

import javax.annotation.Resource;

/**
 * @author wr1sw
 * @version 1.0.0
 * @description
 */
public class ActivityPartakeSupport {

    @Resource
    protected IActivityRepository activityRepository;

    protected ActivityBillVO queryActivityBill(PartakeReq req){
        return activityRepository.queryActivityBill(req);
    }

}
