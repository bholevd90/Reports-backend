package com.xor.SalesAnalysis.service;

import com.xor.SalesAnalysis.dto.HolidayVsNonHolidayResponseDto;
import com.xor.SalesAnalysis.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SalesServiceImpl implements SalesService {

    @Autowired
    SalesRepository salesRepository;

    @Override
    public Map<Integer, Double> storeWiseMaximumSales() {
        Map<Integer,Double> storeWisePerformance=new HashMap<>();
        List<Object[]> sumOfWeeklySales=salesRepository.getSumOfSalesOfSingleStore();
        for(Object[] row:sumOfWeeklySales){
            storeWisePerformance.put((Integer)row[0],((Double)row[1]/salesRepository.getSumOfSalesOfAllStores())*100);
        }
        storeWisePerformance.forEach((k,v)->{
            System.out.println(k+" "+v);
        });
        return storeWisePerformance;
    }



    @Override
    public Map<Integer, Double> getMonthlyPerformanceReport(Integer storeId, Integer year) {
        Map<Integer,Double> monthlyPerformance=new HashMap<>();
        List<Object[]> monthlySales=salesRepository.getMonthlySalesByStoreIdAndYear(storeId,year);
        for(Object[] sales:monthlySales){
            monthlyPerformance.put((Integer)sales[0],(Double)sales[1]);
        }
        return monthlyPerformance;
    }

    @Override
    public List<HolidayVsNonHolidayResponseDto> getHolidayVsNonHolidayAnalysis() {

        List<HolidayVsNonHolidayResponseDto> responseList=new ArrayList<>();
        List<Object[]> holiday=salesRepository.getHolidayAnalysis();
        List<Object[]> nonHoliday=salesRepository.getNonHolidayAnalysis();

        for (Object[] row:holiday){
            HolidayVsNonHolidayResponseDto response=new HolidayVsNonHolidayResponseDto();
            response.setStore_id((Integer) row[0]);
            response.setHolidaySales((Double) row[2]);
            response.setStoreName((String) row[1]);
            responseList.add(response);
        }
        for(Object[] row:nonHoliday){
            for (HolidayVsNonHolidayResponseDto response : responseList) {
                if (response.getStore_id()==(Integer)row[0]) {
                    response.setNonHolidaySales((Double) row[2]);
                }
            }
        }

        return responseList;
    }
}
