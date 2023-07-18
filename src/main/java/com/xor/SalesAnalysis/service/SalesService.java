package com.xor.SalesAnalysis.service;

import com.xor.SalesAnalysis.dto.HolidayVsNonHolidayResponseDto;

import java.util.List;
import java.util.Map;

public interface SalesService {
    Map<Integer, Double> storeWiseMaximumSales();

    void test();

    Map<Integer, Double> getMonthlyPerformanceReport(Integer storeId, Integer year);

    List<HolidayVsNonHolidayResponseDto> getHolidayVsNonHolidayAnalysis();
}
