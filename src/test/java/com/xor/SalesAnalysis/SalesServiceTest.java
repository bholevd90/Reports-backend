package com.xor.SalesAnalysis;

import com.xor.SalesAnalysis.dto.HolidayVsNonHolidayResponseDto;
import com.xor.SalesAnalysis.service.SalesServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.xor.SalesAnalysis.repository.SalesRepository;
import com.xor.SalesAnalysis.service.SalesService;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SalesServiceTest {
    @Mock
    private SalesRepository salesRepository;

    @InjectMocks
    private SalesServiceImpl salesService;

    @Test
    void testGetMonthlyPerformanceReport() {
        // Given
        Integer storeId = 1;
        Integer year = 2010;
        List<Object[]> monthlySales = new ArrayList<>();
        monthlySales.add(new Object[]{5, 4.647605962000001E7});
        monthlySales.add(new Object[]{6, 4.648515115000001E7});
        monthlySales.add(new Object[]{7, 4.703920210000001E7});
        monthlySales.add(new Object[]{8, 5.120398661E7});
        monthlySales.add(new Object[]{9, 3.1198409369999997E7});

        // Mock the behavior of the salesRepository.getMonthlySalesByStoreIdAndYear() method
        when(salesRepository.getMonthlySalesByStoreIdAndYear(storeId, year)).thenReturn(monthlySales);

        // When
        Map<Integer, Double> result = salesService.getMonthlyPerformanceReport(storeId, year);

        // Then
        Map<Integer, Double> expectedPerformance = new HashMap<>();
        expectedPerformance.put(5, 4.647605962000001E7);
        expectedPerformance.put(6, 4.648515115000001E7);
        expectedPerformance.put(7, 4.703920210000001E7);
        expectedPerformance.put(8, 5.120398661E7);
        expectedPerformance.put(9, 3.1198409369999997E7);

        assertEquals(expectedPerformance, result);
    }

    @Test
    void getStoreWiseMaximumSales() {
        // Given
        List<Object[]> sumOfWeeklySales = new ArrayList<>();
        sumOfWeeklySales.add(new Object[]{1, 10000.0});
        sumOfWeeklySales.add(new Object[]{2, 15000.0});

        // Mock the behavior of the salesRepository.getSumOfSalesOfSingleStore() method
        when(salesRepository.getSumOfSalesOfSingleStore()).thenReturn(sumOfWeeklySales);

        // Mock the behavior of the salesRepository.getSumOfSalesOfAllStores() method
        when(salesRepository.getSumOfSalesOfAllStores()).thenReturn(50000.0);

        // When
        Map<Integer, Double> result = salesService.storeWiseMaximumSales();

        // Then
        Map<Integer, Double> expectedPerformance = new HashMap<>();
        expectedPerformance.put(1, 20.0);
        expectedPerformance.put(2, 30.0);

        assertEquals(expectedPerformance, result);
    }

    @Test
    void testGetHolidayVsNonHolidayAnalysis() {
        // Given
        List<Object[]> holidayData = new ArrayList<>();
        holidayData.add(new Object[]{1, "Store 1", 5000.0});
        holidayData.add(new Object[]{2, "Store 2", 6000.0});

        List<Object[]> nonHolidayData = new ArrayList<>();
        nonHolidayData.add(new Object[]{1, "Store 1", 3000.0});
        nonHolidayData.add(new Object[]{2, "Store 2", 4000.0});

        // Mock the behavior of the salesRepository.getHolidayAnalysis() method
        when(salesRepository.getHolidayAnalysis()).thenReturn(holidayData);

        // Mock the behavior of the salesRepository.getNonHolidayAnalysis() method
        when(salesRepository.getNonHolidayAnalysis()).thenReturn(nonHolidayData);

        // When
        List<HolidayVsNonHolidayResponseDto> result = salesService.getHolidayVsNonHolidayAnalysis();

        // Then
        assertEquals(2, result.size());

        HolidayVsNonHolidayResponseDto response1 = result.get(0);
        assertEquals(1, response1.getStore_id());
        assertEquals("Store 1", response1.getStoreName());
        assertEquals(5000.0, response1.getHolidaySales());
        assertEquals(3000.0, response1.getNonHolidaySales());

        HolidayVsNonHolidayResponseDto response2 = result.get(1);
        assertEquals(2, response2.getStore_id());
        assertEquals("Store 2", response2.getStoreName());
        assertEquals(6000.0, response2.getHolidaySales());
        assertEquals(4000.0, response2.getNonHolidaySales());
    }

}
