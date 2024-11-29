package com.bpi.controller;

import com.bpi.model.RqType;
import com.bpi.model.rq.BpiRateRq;
import com.bpi.model.rq.BpiRq;
import com.bpi.model.rs.ApiResponse;
import com.bpi.model.rs.BpiRs;
import com.bpi.model.rs.Coindesk;
import com.bpi.model.rs.NewBpiRs;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Bpi幣別")
@CrossOrigin(origins = "*", allowedHeaders = "*") // 跨域的問題
@RequestMapping(value = "/api/bpi", produces = MediaType.APPLICATION_JSON_VALUE)
interface BpiApi {

    @Operation(summary = "查詢所有幣別")
    @GetMapping("/findAllBpis")
    ApiResponse<List<BpiRs>> findAllBpis();

    @Operation(summary = "查詢單一幣別")
    @GetMapping("/findBpi/code")
    ApiResponse<BpiRs> findBpiByPk(
            @Parameter(name = "code", description = "英文幣別", required = true, in = ParameterIn.QUERY, schema = @Schema(implementation = String.class))
            @RequestParam(name = "code", defaultValue = "") String code);

    @Operation(summary = "查詢單一幣別")
    @GetMapping("/findBpi/codeChineseName")
    ApiResponse<BpiRs> findBpiByCodeChineseName(
            @Parameter(name = "codeChineseName", description = "中文幣別", required = true, in = ParameterIn.QUERY, schema = @Schema(implementation = String.class))
            @RequestParam(name = "codeChineseName", defaultValue = "") String codeChineseName);

    @Operation(summary = "新增幣別")
    @PostMapping("/addBpi")
    @RqType(BpiRq.class)
    ApiResponse<BpiRs> addBpi(@RequestBody BpiRq rq);

    @Operation(summary = "修改幣別")
    @PutMapping("/updateBpi")
    @RqType(BpiRq.class)
    ApiResponse<BpiRs> updateBpi(@RequestBody BpiRq rq);

    @Operation(summary = "修改幣別匯率")
    @PatchMapping("/updateBpiRate")
    @RqType(BpiRateRq.class)
    ApiResponse<BpiRs> updateBpiRate(@RequestBody BpiRateRq rq);

    @Operation(summary = "刪除幣別")
    @DeleteMapping("/deleteBpi/code")
    @RqType(BpiRq.class)
    ApiResponse<BpiRs> deleteBpi(@RequestBody BpiRq rq);

    @Operation(summary = "呼叫外部coindesk API")
    @GetMapping("/call/coindesk")
    ApiResponse<Coindesk> callCoindeskAPI();

    /**
     * 呼叫 coindesk API 在 format成自定義的資料 return
     */
    @Operation(summary = "呼叫外部coindesk API 後進行資料處理 return")
    @GetMapping("/call/coindesk/transform")
    ApiResponse<NewBpiRs> transformNewBpi();
    
}
