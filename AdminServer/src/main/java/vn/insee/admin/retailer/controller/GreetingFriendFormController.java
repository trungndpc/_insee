package vn.insee.admin.retailer.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.insee.admin.retailer.common.BaseResponse;
import vn.insee.admin.retailer.common.ErrorCode;
import vn.insee.admin.retailer.controller.converter.GreetingFriendConverter;
import vn.insee.admin.retailer.controller.dto.GreetingFriendFormDTO;
import vn.insee.admin.retailer.controller.dto.PageDTO;
import vn.insee.admin.retailer.controller.exporter.GreetingFriendExcelExporter;
import vn.insee.admin.retailer.controller.form.UpdateGreetingFriendForm;
import vn.insee.admin.retailer.service.GreetingFriendFormService;
import vn.insee.jpa.entity.form.GreetingFriendFormEntity;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/greeting-friend")
public class GreetingFriendFormController {
    private static final Logger LOGGER = LogManager.getLogger(GreetingFriendFormController.class);

    @Autowired
    private GreetingFriendFormService service;

    @Autowired
    private GreetingFriendConverter converter;

    @GetMapping(path = "/find-by-promotion")
    public ResponseEntity<BaseResponse> list(@RequestParam(required = true) int promotionId, @RequestParam(required = false) Integer city, @RequestParam(required = false) Integer status, @RequestParam(required = false) String search, @RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "10") int pageSize) {
        BaseResponse response = new BaseResponse();
        try {
            Page<GreetingFriendFormEntity> formEntities = service.findByPromotionId(promotionId, status, city, search, page, pageSize);
            PageDTO<GreetingFriendFormDTO> dtoPageDTO = converter.convertToPageDTO(formEntities);
            response.setData(dtoPageDTO);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/get")
    public ResponseEntity<BaseResponse> get(@RequestParam(required = true) int id) {
        BaseResponse response = new BaseResponse();
        try {
            GreetingFriendFormEntity entity = service.get(id);
            response.setData(converter.convert2DTO(entity));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }


    @GetMapping(path = "/export-excel")
    public void exportExcel(@RequestParam(required = false) int promotionId, HttpServletResponse response) {
        try {

            List<GreetingFriendFormEntity> entities = service.findByPromotion(promotionId);
            if (entities == null || entities.isEmpty()) {
                throw new Exception("list form empty");
            }

            response.setContentType("application/octet-stream");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());

            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
            response.setHeader(headerKey, headerValue);
            List<GreetingFriendFormDTO> dtos = converter.convertToListDTO(entities);
            GreetingFriendExcelExporter excelExporter = new GreetingFriendExcelExporter(dtos);
            excelExporter.export(response);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }


    @GetMapping(path = "/update-status")
    public ResponseEntity<BaseResponse> updateStatus(@RequestParam(required = true) int id,
                                                     @RequestParam(required = true) int status,
                                                     @RequestParam(required = false) String note) {
        BaseResponse response = new BaseResponse();
        try{
            service.updateStatus(id, status, note);
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }


    @PostMapping(path = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BaseResponse> update(@RequestParam(required = true) int id, @RequestBody UpdateGreetingFriendForm form)  {
        BaseResponse response = new BaseResponse();
        try {
            GreetingFriendFormEntity entity = service.get(id);
            entity.setNote(form.getNote());
            service.saveOrUpdate(entity);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

}
