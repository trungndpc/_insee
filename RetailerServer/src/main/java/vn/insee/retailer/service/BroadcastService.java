package vn.insee.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.jpa.entity.BroadcastEntity;
import vn.insee.jpa.repository.BroadcastRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BroadcastService {

    @Autowired
    private BroadcastRepository broadcastRepository;

    public void trackingClick(int uid, int broadcastId) {
        Optional<BroadcastEntity> optionalBroadcastEntity = broadcastRepository.findById(broadcastId);
        if (optionalBroadcastEntity.isPresent()) {
            BroadcastEntity broadcastEntity = optionalBroadcastEntity.get();
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
