package vn.insee.retailer.controller.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.insee.jpa.entity.PostEntity;
import vn.insee.retailer.controller.dto.PostDTO;
import vn.insee.retailer.mapper.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostConverter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Mapper mapper;

    public PostDTO convert2DTO(PostEntity postEntity) {
        return mapper.map(postEntity, PostDTO.class);
    }

    public List<PostDTO> covert2DTOs(List<PostEntity> postEntities) {
        return postEntities.stream().map(this::convert2DTO).collect(Collectors.toList());
    }
}
