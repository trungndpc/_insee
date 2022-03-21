package vn.insee.admin.retailer.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.insee.admin.retailer.common.BaseResponse;
import vn.insee.admin.retailer.common.ErrorCode;
import vn.insee.admin.retailer.controller.converter.FormConverter;
import vn.insee.admin.retailer.controller.dto.StockFormDTO;
import vn.insee.admin.retailer.controller.exporter.StockExcelExporter;
import vn.insee.admin.retailer.controller.form.UpdateStockForm;
import vn.insee.admin.retailer.service.FormService;
import vn.insee.jpa.entity.form.StockFormEntity;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stock-form")
public class StockFormController {
    private static final Logger LOGGER = LogManager.getLogger(StockFormController.class);

    @Autowired
    private FormService formService;

    @Autowired
    private FormConverter formConverter;


    @GetMapping(path = "/export-excel")
    public void exportExcel(
            @RequestParam(required = false) int promotionId,
            HttpServletResponse response) {
        try{
            List<StockFormEntity> stockFormEntityList = formService.findByPromotionId(promotionId);
            if (stockFormEntityList == null || stockFormEntityList.isEmpty()) {
                throw new Exception("list form empty");
            }

            response.setContentType("application/octet-stream");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());

            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
            response.setHeader(headerKey, headerValue);
            List<StockFormDTO> stockFormDTOS = stockFormEntityList.stream().map(s -> formConverter
                    .convert2StockFormDTO(s)).collect(Collectors.toList());
            StockExcelExporter excelExporter = new StockExcelExporter(stockFormDTOS);
            excelExporter.export(response);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @PostMapping(path = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BaseResponse> update(@RequestParam(required = true) int id, @RequestBody UpdateStockForm form) {
        BaseResponse response = new BaseResponse();
        try{
            StockFormEntity formEntity = (StockFormEntity) formService.getById(id);
            formEntity.setNote(form.getNote());
            formService.update(formEntity);
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }


}
