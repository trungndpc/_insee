package vn.insee.admin.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.jpa.entity.GiftEntity;
import vn.insee.jpa.repository.GiftRepository;

@Service
public class GiftService {
    @Autowired
    private GiftRepository giftRepository;

    public GiftEntity create(GiftEntity form) {
        return giftRepository.saveAndFlush(form) ;
    }

}
