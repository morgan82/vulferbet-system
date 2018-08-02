package com.ml.vulferbetsystem.config;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigParamRepository extends JpaRepository<ConfigParam, Long> {
    ConfigParam findByName(String name);
}
