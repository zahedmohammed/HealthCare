package com.fxlabs.fxt.codegen.generators.utils;

import com.fxlabs.fxt.codegen.code.AutoCodeConfig;
import com.fxlabs.fxt.codegen.code.Database;
import com.fxlabs.fxt.codegen.code.TestSuite;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 *
 */
public class AutoCodeConfigUtil {

    private AutoCodeConfig config;

    public void setConfig(AutoCodeConfig config) {
        this.config = config;
    }

    public AutoCodeConfig getConfig() {
        return config;
    }

    public boolean isDB(String dbName){

        // For now, returning true for every DB (if config file is not present) so that TestSuites for all DBs are generated.
        //TODO: If we make AutoCodeConfig.yaml mandatory with entries of DB, we need to return false here.
        if (this.config == null ) return true;

        boolean isDBGivenName = false;
        if (! CollectionUtils.isEmpty(this.config.getDatabases())){
            for ( Database db : this.config.getDatabases()){
                if (StringUtils.equalsIgnoreCase(db.getName(), dbName)){
                    if (!db.isInactive())
                        return true;
                }
            }
        }
        return false;
    }

    public String getDBVersion(String dbName){

        if (this.config == null ) return null;

        String version = null;
        if (! CollectionUtils.isEmpty(this.config.getDatabases())){
            for ( Database db : this.config.getDatabases() ){
                if (StringUtils.equalsIgnoreCase(db.getName(), dbName)){
                    version = db.getVersion();
                }
            }
        }
        return version;
    }

    public List<String> getLogForgingPatterns(){
        if (this.config == null ) return null;

        return this.config.getLogForgingPatterns();
    }

    public List<String> getAssertions(String postfix){

        if (this.config == null ) return null;

        String version = null;
        if (! CollectionUtils.isEmpty(this.config.getTestSuites())){
            for ( TestSuite ts : this.config.getTestSuites() ){
                if (StringUtils.equalsIgnoreCase(ts.getPostfix(), postfix)){
                    return ts.getAssertions();
                }
            }
        }
        return null;
    }

}
