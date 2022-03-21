package vn.insee.admin.retailer.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.insee.admin.retailer.common.BaseResponse;
import vn.insee.admin.retailer.common.ErrorCode;
import vn.insee.admin.retailer.controller.converter.LQFormConverter;
import vn.insee.admin.retailer.controller.dto.lq.LQFormDTO;
import vn.insee.admin.retailer.controller.exporter.LightingQuizGameExcelExporter;
import vn.insee.admin.retailer.service.LightingQuizFormService;
import vn.insee.jpa.entity.form.LightingQuizFormEntity;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lq-form")
public class LightingQuizFormController {
    private static final Logger LOGGER = LogManager.getLogger(LightingQuizFormController.class);

    @Autowired
    private LightingQuizFormService formService;

    @Autowired
    private LQFormConverter formConverter;

    @GetMapping(path = "/find")
    public ResponseEntity<BaseResponse> list( @RequestParam(required = true) int promotionId,
            @RequestParam(required = true) String topicId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {
        BaseResponse response = new BaseResponse();
        try{
            Page<LightingQuizFormEntity> pageForm = formService.findByPromotionIdAndTopicId(promotionId, topicId, page, pageSize);
            response.setData(formConverter.convertToPageDTO(pageForm));
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/get")
    public ResponseEntity<BaseResponse> get(@RequestParam(required = true) int id) {
        BaseResponse response = new BaseResponse();
        try{
            LightingQuizFormEntity quizFormEntity = formService.get(id);
            response.setData(formConverter.covert2DTO(quizFormEntity));
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/update-status")
    public ResponseEntity<BaseResponse> updateStatus(@RequestParam(required = true) int id, @RequestParam(required = true) int status) {
        BaseResponse response = new BaseResponse();
        try{
            boolean is = formService.updateStatus(id, status);
            if (is) {
                response.setError(ErrorCode.FAILED);
            }else {
                response.setError(ErrorCode.FAILED);
            }
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/export-excel")
    public void exportExcel(
            @RequestParam(required = false) int promotionId,
            HttpServletResponse response) {
        try{
            List<LightingQuizFormEntity> lqFormDTOS = formService.findByPromotionId(promotionId);
            if (lqFormDTOS == null || lqFormDTOS.isEmpty()) {
                throw new Exception("list form empty");
            }

            response.setContentType("application/octet-stream");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());

            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
            response.setHeader(headerKey, headerValue);
            List<LQFormDTO> stockFormDTOS = lqFormDTOS.stream().map(s -> formConverter
                    .covert2DTO(s)).collect(Collectors.toList());
            LightingQuizGameExcelExporter excelExporter = new LightingQuizGameExcelExporter(stockFormDTOS);
            excelExporter.export(response);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }






}
