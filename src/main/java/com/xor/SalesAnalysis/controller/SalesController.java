package com.xor.SalesAnalysis.controller;

import com.xor.SalesAnalysis.dto.HolidayVsNonHolidayResponseDto;
import com.xor.SalesAnalysis.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    ResponseEntity<Map<Integer,Double>> getMonthlyPerformanceReport(@RequestParam("storeId") Integer storeId,@RequestParam("year") Integer year){
        Map<Integer,Double> monthlyPerformance =salesService.getMonthlyPerformanceReport(storeId,year);
//        monthlyPerformance=
        return ResponseEntity.ok(monthlyPerformance);
    }

    @GetMapping("/holiday-vs-nonholiday-analysis")
    ResponseEntity<List<HolidayVsNonHolidayResponseDto>> getHolidayVsNonHolidayAnalysis(){
        List<HolidayVsNonHolidayResponseDto> holidayVsNonHolidayAnalysis=salesService.getHolidayVsNonHolidayAnalysis();
        return ResponseEntity.ok(holidayVsNonHolidayAnalysis);
    }
    @GetMapping("/test")
    void test(){
        salesService.test();
    }

}
