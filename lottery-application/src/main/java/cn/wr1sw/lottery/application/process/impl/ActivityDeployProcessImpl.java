package cn.wr1sw.lottery.application.process.impl;

import cn.wr1sw.lottery.application.process.deploy.IActivityDeployProcess;
import cn.wr1sw.lottery.domain.activity.model.aggregates.ActivityInfoLimitPageRich;
import cn.wr1sw.lottery.domain.activity.model.req.ActivityInfoLimitPageReq;
import cn.wr1sw.lottery.domain.activity.service.deploy.IActivityDeploy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wr1sw
 * @version 1.0.0
 * @description
 */
@Service
public class ActivityDeployProcessImpl implements IActivityDeployProcess {

    @Resource
    private IActivityDeploy activityDeploy;

    @Override
    public ActivityInfoLimitPageRich queryActivityInfoLimitPage(ActivityInfoLimitPageReq req) {
        return activityDeploy.queryActivityInfoLimitPage(req);
    }


}
