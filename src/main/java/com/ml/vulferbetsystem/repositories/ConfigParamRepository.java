package com.ml.vulferbetsystem.repositories;

import com.ml.vulferbetsystem.domain.ConfigParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

@Repository
public interface ConfigParamRepository extends JpaRepository<ConfigParam, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query(value = "SELECT cp FROM ConfigParam cp WHERE cp.name  = ?1")
    ConfigParam findByNameLock(String name);

    ConfigParam findByName(String name);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT cp FROM ConfigParam cp WHERE cp.name  = ?1")
    ConfigParam findByNameForUpdate(String name);
}
