package vn.insee.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.jpa.entity.PostEntity;
import vn.insee.jpa.repository.PostRepository;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<PostEntity> getPosts(int userId) {
        return postRepository.findAll();
    }

    public PostEntity get(int id) {
        return postRepository.getOne(id);
    }

}
