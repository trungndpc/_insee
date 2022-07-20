package vn.insee.admin.retailer.mapper;

import org.modelmapper.PropertyMap;
import vn.insee.admin.retailer.controller.dto.GreetingFriendFormDTO;
import vn.insee.admin.retailer.controller.dto.MatchFootballDTO;
import vn.insee.jpa.entity.MatchFootballEntity;
import vn.insee.jpa.entity.form.GreetingFriendFormEntity;

public class GreetingFriendEntity2GreetingFriendDTO extends PropertyMap<GreetingFriendFormEntity, GreetingFriendFormDTO> {

    @Override
    protected void configure() {
        using(Mapper.ZONE_DATE_TIME_2_LONG).map(source.getCreatedTime(), destination.getCreatedTime());
        using(Mapper.ZONE_DATE_TIME_2_LONG).map(source.getUpdatedTime(), destination.getUpdatedTime());
    }
}
