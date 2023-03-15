package cn.wr1sw.lottery.interfaces.assembler;
import cn.wr1sw.lottery.domain.activity.model.vo.ActivityVO;
import cn.wr1sw.lottery.rpc.activity.deploy.dto.ActivityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * @author wr1sw
 * @version 1.0.0
 * @description 活动对象转换配置
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ActivityMapping extends IMapping<ActivityVO, ActivityDTO>{
    @Override
    List<ActivityDTO> sourceToTarget(List<ActivityVO> var1);

}
