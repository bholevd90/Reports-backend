package com.xor.SalesAnalysis;

import com.xor.SalesAnalysis.controller.SalesController;
import com.xor.SalesAnalysis.service.SalesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SalesController.class)
public class SalesControllerTest {

    @Autowired
    MockMvc mockMvc; // simulate http requests

//    @Autowired
//    ObjectMapper objectMapper; // to convert java object to json and vice versa


    @MockBean
    SalesService salesService;

    @Test
    void whenGetAllSales_thenReturnJsonArray() throws Exception{
    this.mockMvc.perform(get("/api/v1/sales")).andExpect(status().isOk());
    }

    @Test
    void whenGetMonthlySales_withStoreIdAndYear() throws Exception {
        int storeId = 1;
        int year = 2010;
        Map<Integer,Double> expectedData = Map.of(1, 100.0, 2, 200.0, 3, 300.0);
        // Mock the behavior of the salesService.getMonthlySalesData() method
        when(salesService.getMonthlyPerformanceReport(storeId, year)).thenReturn(expectedData);
        // When & Then
        mockMvc.perform(get("/api/v1/sales/get")
                        .param("storeId", String.valueOf(storeId))
                        .param("year", String.valueOf(year))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());


    }

    @Test
    void whenGetMonthlySales_withStoreId() throws Exception {
        int storeId = 1;
        int year = 2010;
        Map<Integer,Double> expectedData = Map.of(1, 100.0, 2, 200.0, 3, 300.0);
        // Mock the behavior of the salesService.getMonthlySalesData() method
        when(salesService.getMonthlyPerformanceReport(storeId, year)).thenReturn(expectedData);
        // When & Then
        mockMvc.perform(get("/api/v1/sales/get?storeId=1")
                        .param("storeId", String.valueOf(storeId))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetMonthlySales_withYear() throws Exception {
        int storeId = 1;
        int year = 2010;
        Map<Integer,Double> expectedData = Map.of(1, 100.0, 2, 200.0, 3, 300.0);
        when(salesService.getMonthlyPerformanceReport(storeId, year)).thenReturn(expectedData);
        // When & Then
        mockMvc.perform(get("/api/v1/sales/get?year=2010")
                        .param("year", String.valueOf(2010))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetMonthlySales_withNoParams() throws Exception {
        this.mockMvc.perform(get("/api/v1/sales/get")).andExpect(status().isBadRequest());
    }

    @Test
    void whenGetMonthlySales_withInvalidStoreId() throws Exception {
        this.mockMvc.perform(get("/api/v1/sales/get?storeId=100")).andExpect(status().isBadRequest());
    }

    @Test
    void whenGetMonthlySales_withInvalidYear() throws Exception {
        int storeId = 1;
        int year = 2010;
        Map<Integer,Double> expectedData = Map.of(1, 100.0, 2, 200.0, 3, 300.0);
        when(salesService.getMonthlyPerformanceReport(storeId, year)).thenReturn(expectedData);
        // When & Then
        mockMvc.perform(get("/api/v1/sales/get?year=100")
                        .param("year", String.valueOf(100))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
//        this.mockMvc.perform(get("/api/v1/sales/get?year=100")).andExpect(status().isBadRequest());
    }

    @Test
    void whenGetHolidayVsNonHolidaySales() throws Exception {
        this.mockMvc.perform(get("/api/v1/sales/holiday-vs-nonholiday-analysis")).andExpect(status().isOk());
    }
}
