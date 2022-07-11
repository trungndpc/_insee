package vn.insee.retailer.controller.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.insee.jpa.entity.form.GreetingFriendFormEntity;
import vn.insee.retailer.controller.form.GreetingFriendForm;
import vn.insee.retailer.mapper.Mapper;

@Component
public class GreetingFriendFormConverter {
    private static final Logger LOGGER = LogManager.getLogger(GreetingFriendFormConverter.class);
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Mapper mapper;

    public GreetingFriendFormEntity convert2Entity(GreetingFriendForm form) {
        GreetingFriendFormEntity greetingFriendForm = mapper.map(form, GreetingFriendFormEntity.class);
        greetingFriendForm.setJsonImage(form.getDetail().toString());
        return greetingFriendForm;
    }
}
