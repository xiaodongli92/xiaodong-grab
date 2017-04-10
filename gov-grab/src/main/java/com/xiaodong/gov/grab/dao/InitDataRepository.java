package com.xiaodong.gov.grab.dao;

import com.xiaodong.gov.grab.model.InitData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by xiaodong on 2017/4/9.
 */
public interface InitDataRepository extends CrudRepository<InitData, Long> {

    @Query(value = "select max(id) from init_data", nativeQuery = true)
    Long maxId();
}
