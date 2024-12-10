package com.bpi.repository.mongodb;

import com.bpi.model.entity.mongodb.BpiMongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * mongo db jpa
 */
@Repository
public interface BpiMongoRepository extends MongoRepository<BpiMongoEntity, String> {

    @Query("{$and: [{code: ?0}]}")
    List<BpiMongoEntity> findByCode(String code);

    @Query("{$and:  [{code: ?0}, {createDateTime: {$gte: ?1, $lte: ?2}}]}")
    List<BpiMongoEntity> findByCodeAndBetweenCreateDateResult(String code, String startDate, String endDate);

}
