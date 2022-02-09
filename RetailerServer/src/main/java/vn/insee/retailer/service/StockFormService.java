package vn.insee.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.common.status.StatusForm;
import vn.insee.common.type.TypePromotion;
import vn.insee.jpa.entity.form.StockFormEntity;
import vn.insee.jpa.repository.StockFormRepository;

@Service
public class StockFormService {

    @Autowired
    private StockFormRepository stockFormRepository;

    public StockFormEntity create(StockFormEntity stockFormEntity) {
        stockFormEntity.setStatus(StatusForm.INIT);
        stockFormEntity.setType(TypePromotion.STOCK_PROMOTION_TYPE);
        stockFormEntity = stockFormRepository.saveAndFlush(stockFormEntity);
        return stockFormEntity;
    }

}
