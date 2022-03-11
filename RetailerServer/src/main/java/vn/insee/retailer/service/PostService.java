package vn.insee.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.insee.common.status.StatusPost;
import vn.insee.jpa.entity.PostEntity;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.repository.PostRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<PostEntity> findPost(UserEntity userEntity) {
        long currentTime = System.currentTimeMillis();
        List<PostEntity> postEntities = postRepository.findAllByOrderByUpdatedTimeDesc();
        postEntities = postEntities.stream().filter(post -> post.getStatus() == StatusPost.PUBLISHED)
        .filter(post -> {
            if (post.getCityIds() != null) {
                return post.getCityIds().contains(userEntity.getCityId());
            }
            return true;
        }).filter(post -> {
            if (post.getDistrictIds() != null) {
                return post.getDistrictIds().contains(userEntity.getDistrictId());
            }
            return true;
        }).filter(post -> {
            return currentTime >= post.getTimeStart();
        }).filter(post -> {
            return currentTime <= post.getTimeEnd();
        }).collect(Collectors.toList());
        return postEntities;
    }

    public PostEntity findPost(int promotionId, UserEntity userEntity) {
        long currentTime = System.currentTimeMillis();
        List<PostEntity> postEntities = postRepository.findAllByOrderByUpdatedTimeDesc();
        Optional<PostEntity> first = postEntities.stream().filter(post -> {
                    return post.getPromotionId() != null && post.getPromotionId() == promotionId;
                })
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

    public PostEntity get(int id) {
        return postRepository.getOne(id);
    }

}
