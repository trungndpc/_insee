package vn.insee.admin.retailer.controller.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import vn.insee.admin.retailer.controller.dto.PageDTO;
import vn.insee.admin.retailer.controller.dto.UserDTO;
import vn.insee.admin.retailer.controller.dto.metric.UserDataMetricDTO;
import vn.insee.admin.retailer.controller.form.CustomerForm;
import vn.insee.admin.retailer.mapper.Mapper;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.metric.UserCityMetric;
import vn.insee.jpa.metric.UserDataMetric;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserConverter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Mapper mapper;

    public UserEntity convert2Entity(CustomerForm registerForm) {
        return mapper.map(registerForm, UserEntity.class);
    }

    public UserDTO convert2DTO(UserEntity userEntity) {
        return mapper.map(userEntity, UserDTO.class);
    }

    public PageDTO<UserDTO> convertToPageDTO(Page<UserEntity> userEntityPage)  {
        List<UserEntity> content = userEntityPage.getContent();
        List<UserDTO> dtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(content)) {
            for (UserEntity entity: content) {
                dtos.add(convert2DTO(entity));
            }
        }
        return new PageDTO<UserDTO>(userEntityPage.getNumber(), dtos.size(), userEntityPage.getTotalPages(), dtos);
    }

    public UserDataMetricDTO convert2DTO(UserDataMetric dataMetricDTO) {
        return mapper.map(dataMetricDTO, UserDataMetricDTO.class);
    }

    public UserCityMetric convert2DTO(UserCityMetric userCityMetric) {
        return mapper.map(userCityMetric, UserCityMetric.class);
    }

}
