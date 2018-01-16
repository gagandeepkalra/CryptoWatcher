package com.cryptocurrency.watcher.repository;

import com.cryptocurrency.watcher.entity.HistoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by gkalra on 1/13/18.
 */
public interface HistoryRepository extends CrudRepository<HistoryEntity, Long> {

  List<HistoryEntity> findByCodeOrderByDateDesc(String code);

  @Query("select H1 from HistoryEntity H1 where H1.date = (select max(H2.date) from HistoryEntity H2 where H1.code=H2.code)")
  List<HistoryEntity> getLatestPricesForAllCurrencies();

  @Query("select H1 from HistoryEntity H1 where H1.date = ?1 and H1.code = ?2")
  Optional<HistoryEntity> getLastUpdatedRecord(java.sql.Date date, String code);
}
