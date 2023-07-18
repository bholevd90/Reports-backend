package com.xor.SalesAnalysis.dto;

import lombok.Data;

import java.util.List;

@Data
public class HolidayVsNonHolidayResponseDto {
    int store_id;
    Double holidaySales;
    Double nonHolidaySales;

}
