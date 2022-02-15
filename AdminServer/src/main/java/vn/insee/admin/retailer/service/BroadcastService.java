package vn.insee.admin.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.insee.jpa.entity.BroadcastEntity;
import vn.insee.jpa.repository.BroadcastRepository;


@Service
public class BroadcastService {

    @Autowired
    private BroadcastRepository broadcastRepository;

    public BroadcastEntity create(BroadcastEntity entity) {
        return broadcastRepository.saveAndFlush(entity);
    }

    public Page<BroadcastEntity> find(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        return broadcastRepository.findAll(pageable);
    }


}
