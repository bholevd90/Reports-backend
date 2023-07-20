package com.xor.SalesAnalysis.controller;

import com.xor.SalesAnalysis.dto.HolidayVsNonHolidayResponseDto;
import com.xor.SalesAnalysis.exception.EmptyResponseHandler;
import com.xor.SalesAnalysis.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.Year;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/sales")
@CrossOrigin(origins = "*")
public class SalesController {

    @Autowired
    private SalesService salesService;


    @GetMapping
    ResponseEntity<Map<Integer,Double>> getStoreWiseMaximumSales(){
        Map<Integer,Double> storeWisePerformance=salesService.storeWiseMaximumSales();
//        storeWisePerformance=salesService.storeWiseMaximumSales();
        return ResponseEntity.ok(storeWisePerformance);
    }

    @GetMapping("/get")
    public ResponseEntity<Map<Integer,Double>> getMonthlyPerformanceReport(@RequestParam(value = "storeId", required = true) Integer storeId, @RequestParam(value = "year", required = false) Integer year) {
        if(storeId==null){
            throw new NullPointerException("Please provide storeId");
        }
        if (year == null) {
            Year current = Year.now(ZoneId.systemDefault());
            year = current.getValue();
        }
        Map<Integer,Double> monthlyPerformance =salesService.getMonthlyPerformanceReport(storeId,year);
//        monthlyPerformance=
        if(monthlyPerformance.isEmpty()){
            throw new EmptyResponseHandler("No data found for storeId "+storeId+" and year "+year);
        }
        return ResponseEntity.ok(monthlyPerformance);
    }

    @GetMapping("/holiday-vs-nonholiday-analysis")
    ResponseEntity<List<HolidayVsNonHolidayResponseDto>> getHolidayVsNonHolidayAnalysis(){
        List<HolidayVsNonHolidayResponseDto> holidayVsNonHolidayAnalysis=salesService.getHolidayVsNonHolidayAnalysis();
        return ResponseEntity.ok(holidayVsNonHolidayAnalysis);
    }


}
