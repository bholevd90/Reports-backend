package com.xor.SalesAnalysis;

import com.xor.SalesAnalysis.controller.SalesController;
import com.xor.SalesAnalysis.service.SalesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SalesController.class)
public class SalesControllerTest {

    @Autowired
    MockMvc mockMvc; // simulate http requests

//    @Autowired
//    ObjectMapper objectMapper; // to convert java object to json and vice versa
    @MockBean
    SalesController salesController;

    @Autowired
    SalesService salesService;

    @Test
    void whenGetAllSales_thenReturnJsonArray() throws Exception{
    this.mockMvc.perform(get("/api/v1/sales")).andExpect(status().isOk());
    }

    @Test
    void whenGetMonthlySales_withStoreIdAndYear() throws Exception {
        int storeId = 1;
        int year = 2010;
        // Mock the behavior of the salesService.getMonthlySalesData() method
        when(salesService.getMonthlyPerformanceReport(storeId, year)).thenReturn(null);

        this.mockMvc.perform(get("/api/v1/sales/get?storeId=1&year=2019")).andExpect(status().isOk());
    }

    @Test
    void whenGetMonthlySales_withStoreId() throws Exception {
        this.mockMvc.perform(get("/api/v1/sales/get?storeId=1")).andExpect(status().isOk());
    }

    @Test
    void whenGetMonthlySales_withYear() throws Exception {
        this.mockMvc.perform(get("/api/v1/sales/get?year=2010")).andExpect(status().isBadRequest());
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
        this.mockMvc.perform(get("/api/v1/sales/get?year=100")).andExpect(status().isBadRequest());
    }

    @Test
    void whenGetHolidayVsNonHolidaySales() throws Exception {
        this.mockMvc.perform(get("/api/v1/sales/holiday-vs-nonholiday-analysis")).andExpect(status().isOk());
    }
}
