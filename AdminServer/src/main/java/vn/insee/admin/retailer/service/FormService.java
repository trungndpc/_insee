package vn.insee.admin.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.common.status.StatusForm;
import vn.insee.jpa.entity.FormEntity;
import vn.insee.jpa.repository.FormRepository;

import java.util.List;

@Service
public class FormService {

    @Autowired
    private FormRepository formRepository;

    public FormEntity addGift(int formId, List<Integer> giftIds) {
        FormEntity formEntity = formRepository.getOne(formId);
        formEntity.setGifts(giftIds);
        formEntity.setStatus(StatusForm.SENT_GIFT);
        return formRepository.saveAndFlush(formEntity);
    }
}
