package vn.insee.admin.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.insee.common.status.StatusPost;
import vn.insee.jpa.entity.PostEntity;
import vn.insee.jpa.entity.PromotionEntity;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.repository.PostRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PromotionService promotionService;

    public PostEntity create(PostEntity postEntity) {
        postEntity.setStatus(StatusPost.INIT);
        postEntity = postRepository.saveAndFlush(postEntity);
        if (postEntity.getPromotionId() != null) {
            PromotionEntity promotionEntity = promotionService.get(postEntity.getPromotionId());
            if (postEntity.getLocations() == null) {
                postEntity.setLocations(promotionEntity.getLocations());
            }

            if (postEntity.getTimeStart() == null) {
                postEntity.setTimeStart(promotionEntity.getTimeStart());
            }

            if (postEntity.getTimeEnd() == null) {
                postEntity.setTimeEnd(promotionEntity.getTimeEnd());
            }
        }
        return postEntity;
    }

    public Page<PostEntity> find(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Specification<UserEntity> specs =  Specification.where(null);
        return postRepository.findAll(specs, pageable);
    }

    public PostEntity get(int id) {
        return postRepository.getOne(id);
    }

}
