package vn.insee.retailer.controller.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.retailer.controller.dto.UserDTO;
import vn.insee.retailer.controller.form.RegisterForm;
import vn.insee.retailer.mapper.Mapper;

@Component
public class UserConverter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Mapper mapper;

    public UserEntity convert2Entity(RegisterForm registerForm) {
        return mapper.map(registerForm, UserEntity.class);
    }

    public UserDTO convert2DTO(UserEntity userEntity) {
        return mapper.map(userEntity, UserDTO.class);
    }

}
