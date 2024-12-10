package com.bpi.model.entity.mongodb;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Description;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

/**
 * bpi mongo db entity
 */
@Data
@Builder
@Document(collection = "BPI_MONGO")
public class BpiMongoEntity {

    @Description("mongoDB id ")
    @Id
    String id;

    @Description("貨幣名稱")
    String code;

    @Description("貨幣中文名稱")
    String codeChineseName;

    @Description("金錢符號")
    String symbol;

    @Description("匯率(千分位,)")
    String rate;

    @Description("匯率")
    BigDecimal rateFloat;

    @Description("描述")
    String description;

    @Description("創建時間")
    @NotNull
    @Field("createDateTime")
    String createDateTime;

    @Description("更新時間")
    @Field("updateDateTime")
    String updateDateTime;


}
