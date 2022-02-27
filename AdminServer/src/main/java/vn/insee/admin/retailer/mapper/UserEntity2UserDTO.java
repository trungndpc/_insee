package vn.insee.admin.retailer.mapper;

import org.modelmapper.PropertyMap;
import vn.insee.admin.retailer.controller.dto.UserDTO;
import vn.insee.jpa.entity.UserEntity;

public class UserEntity2UserDTO  extends PropertyMap<UserEntity, UserDTO> {

    @Override
    protected void configure() {
        using(Mapper.ZONE_DATE_TIME_2_LONG).map(source.getCreatedTime(), destination.getCreatedTime());
        using(Mapper.ZONE_DATE_TIME_2_LONG).map(source.getUpdatedTime(), destination.getUpdatedTime());
    }
}
