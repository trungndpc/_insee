package vn.insee.admin.retailer.controller.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import vn.insee.admin.retailer.controller.dto.PageDTO;
import vn.insee.admin.retailer.controller.dto.PromotionDTO;
import vn.insee.admin.retailer.mapper.Mapper;
import vn.insee.jpa.entity.PromotionEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PromotionConverter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Mapper mapper;


    public PromotionDTO convert2DTO(PromotionEntity promotionEntity) {
        return mapper.map(promotionEntity, PromotionDTO.class);
    }

    public PageDTO<PromotionDTO> convertToPageDTO(Page<PromotionEntity> promotionEntities)  {
        List<PromotionEntity> content = promotionEntities.getContent();
        List<PromotionDTO> dtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(content)) {
            for (PromotionEntity entity: content) {
                dtos.add(convert2DTO(entity));
            }
        }
        return new PageDTO<PromotionDTO>(promotionEntities.getNumber(), dtos.size(), promotionEntities.getTotalPages(), dtos);
    }

    public List<PromotionDTO> covert2DTOOnlyIdAndName(List<PromotionEntity> postEntities) {
        return postEntities.stream().map((post) -> {
            PromotionDTO promotionDTO = new PromotionDTO();
            promotionDTO.setId(post.getId());
            promotionDTO.setTitle(post.getTitle());
            return promotionDTO;
        }).collect(Collectors.toList());
    }
}
