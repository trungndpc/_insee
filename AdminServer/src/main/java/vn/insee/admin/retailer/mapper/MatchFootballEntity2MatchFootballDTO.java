package vn.insee.admin.retailer.mapper;

import org.modelmapper.PropertyMap;
import vn.insee.admin.retailer.controller.dto.MatchFootballDTO;
import vn.insee.jpa.entity.MatchFootballEntity;

public class MatchFootballEntity2MatchFootballDTO extends PropertyMap<MatchFootballEntity, MatchFootballDTO> {

    @Override
    protected void configure() {
        using(Mapper.ZONE_DATE_TIME_2_LONG).map(source.getTimeStart(), destination.getTimeStart());
//        using(Mapper.ZONE_DATE_TIME_2_LONG).map(source.getTimeEnd(), destination.getTimeEnd());
    }
}
