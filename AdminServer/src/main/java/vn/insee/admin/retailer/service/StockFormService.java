package vn.insee.admin.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.jpa.repository.StockFormRepository;

@Service
public class StockFormService {

    @Autowired
    private StockFormRepository stockFormRepository;


}
