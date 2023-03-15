package cn.wr1sw.lottery.interfaces.assembler;

import cn.wr1sw.lottery.domain.strategy.model.vo.DrawAwardVo;
import cn.wr1sw.lottery.rpc.activity.booth.dto.AwardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * @author wr1sw
 * @version 1.0.0
 * @description
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AwardMapping extends IMapping<DrawAwardVo, AwardDTO> {

    @Mapping(target = "userId", source = "uId")
    @Override
    AwardDTO sourceToTarget(DrawAwardVo var1);

    @Override
    DrawAwardVo targetToSource(AwardDTO var1);
}
