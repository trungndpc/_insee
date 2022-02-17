package vn.insee.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.jpa.entity.BroadcastEntity;
import vn.insee.jpa.repository.BroadcastRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class BroadcastService {

    @Autowired
    private BroadcastRepository broadcastRepository;

    public void trackingClick(int uid, int broadcastId) {
        BroadcastEntity broadcastEntity = broadcastRepository.getOne(broadcastId);
        if (broadcastEntity != null) {
            List<Integer> clickers = broadcastEntity.getClickers();
            if (clickers == null) {
                clickers = new ArrayList<>();
            }
            if (!clickers.contains(uid)) {
                clickers.add(uid);
            }
            broadcastEntity.setClickers(clickers);
            broadcastRepository.saveAndFlush(broadcastEntity);
        }
    }
}
