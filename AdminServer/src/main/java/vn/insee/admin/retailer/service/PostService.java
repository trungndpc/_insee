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
import vn.insee.jpa.specification.PostSpecification;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private PostSpecification postSpecification;

    public PostEntity create(PostEntity postEntity) {
        postEntity.setStatus(StatusPost.INIT);
        postEntity = postRepository.saveAndFlush(postEntity);
        if (postEntity.getPromotionId() != null) {
            PromotionEntity promotionEntity = promotionService.get(postEntity.getPromotionId());
            if (postEntity.getCityIds() == null) {
                postEntity.setCityIds(promotionEntity.getCityIds());
            }

            if (postEntity.getDistrictIds() == null) {
                postEntity.setDistrictIds(promotionEntity.getDistrictIds());
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

    public PostEntity updateStatus(int id, int status) {
        PostEntity postEntity = postRepository.getOne(id);
        postEntity.setStatus(status);
        postEntity = postRepository.saveAndFlush(postEntity);
        return postEntity;
    }

    public Page<PostEntity> find(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Specification<PostEntity> specs =  Specification.where(null);
        specs = specs.and(postSpecification.isNotStatus(StatusPost.DELETED));
        return postRepository.findAll(specs, pageable);
    }

    public List<PostEntity> getAll() {
        return postRepository.findAll();
    }

    public PostEntity get(int id) {
        return postRepository.getOne(id);
    }

    public PostEntity findPost(int promotionId, UserEntity userEntity) {
        long currentTime = System.currentTimeMillis();
        List<PostEntity> postEntities = postRepository.findAllByOrderByUpdatedTimeDesc();
        Optional<PostEntity> first = postEntities.stream().filter(post -> post.getPromotionId() == promotionId)
                .filter(post -> {
                    if (post.getDistrictIds() != null) {
                        return post.getDistrictIds().contains(userEntity.getDistrictId());
                    }
                    if (post.getCityIds() != null) {
                        return post.getCityIds().contains(userEntity.getCityId());
                    }
                    return true;
                })
                .filter(post -> post.getTimeStart() <= currentTime)
                .filter(post -> post.getTimeEnd() >= currentTime)
                .findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        return null;
    }

}
