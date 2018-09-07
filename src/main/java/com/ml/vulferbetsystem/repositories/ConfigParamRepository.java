package com.ml.vulferbetsystem.repositories;

import com.ml.vulferbetsystem.domain.ConfigParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;

@Repository
public interface ConfigParamRepository extends JpaRepository<ConfigParam, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query(value = "SELECT cp FROM ConfigParam cp WHERE cp.name  = ?1")
    @Transactional
    ConfigParam findByNameLock(String name);

    ConfigParam findByName(String name);

    //    @Query(value = "SELECT cp FROM ConfigParam cp WHERE cp.name  = ?1")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT cp FROM ConfigParam cp WHERE cp.name  = ?1")
    @Transactional
    ConfigParam findByNameForUpdate(String name);
}
