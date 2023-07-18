package com.xor.SalesAnalysis.repository;

import com.xor.SalesAnalysis.model.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Integer> {
    @Query(value="select store_id, sum(weekly_sales_amount) from sales group by store_id",nativeQuery = true)
    List<Object[]> getSumOfSalesOfSingleStore();

    @Query(value="select sum(weekly_sales_amount) from sales ",nativeQuery = true)
    Double getSumOfSalesOfAllStores();

    @Query(value="select month(date),sum(weekly_sales_amount) from sales where year(date)=?2 and store_id=?1 group by month(date)",nativeQuery = true)
    List<Object[]> getMonthlySalesByStoreIdAndYear(Integer storeId, Integer year);

    @Query(value="select store_id,year(date),sum(weekly_sales_amount) from sales where holiday_flag=1 group by store_id",nativeQuery = true)
    List<Object[]> getHolidayAnalysis();

    @Query(value = "select store_id,year(date),sum(weekly_sales_amount) from sales where holiday_flag=0 group by store_id",nativeQuery = true)
    List<Object[]> getNonHolidayAnalysis();
}
