package com.ml.vulferbetsystem.utils;


import org.apache.commons.configuration.ConfigurationUtils;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class ConfigUtils {
    private static final Logger log = LoggerFactory.getLogger(ConfigUtils.class);
    private static final String PROPERTIES_PATH = "application.yml";//TODO:para externalizar

    public static final int GEOMETRY_UTILS_PRECISION;

    static {
        int geometryUtilsPrecision = 0;

        try {
            PropertiesConfiguration config = null;
            if (new File(PROPERTIES_PATH).exists()) {
                config = new PropertiesConfiguration(ConfigurationUtils.locate(PROPERTIES_PATH));
            } else {
                config = new PropertiesConfiguration(ConfigurationUtils.locate("application.yml"));
            }
            geometryUtilsPrecision = config.getInt("utils.geometry.precision");
        } catch (Exception e) {
            log.error("Error al cargar ConfigUtils ", e);
        } finally {
            GEOMETRY_UTILS_PRECISION = geometryUtilsPrecision;
        }
    }

}
