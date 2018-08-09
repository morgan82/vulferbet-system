package com.ml.vulferbetsystem.repositories;

import com.ml.vulferbetsystem.domain.ConfigParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigParamRepository extends JpaRepository<ConfigParam, Long> {

    ConfigParam findByName(String name);
}
