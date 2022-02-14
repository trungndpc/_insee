package vn.insee.retailer.mapper;

import org.modelmapper.PropertyMap;
import vn.insee.jpa.entity.FormEntity;
import vn.insee.retailer.controller.dto.FormHistoryDTO;

public class FormEntity2FormHistoryDTO extends PropertyMap<FormEntity, FormHistoryDTO> {
    @Override
    protected void configure() {
//        using(Mapper.ZONE_DATE_TIME_2_LONG).map(source.getUpdatedTime(), destination.getUpdatedTime());
    }
}
