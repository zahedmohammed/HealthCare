package com.fxlabs.fxt.codegen.generators.utils;

import com.fxlabs.fxt.codegen.code.AutoCodeConfig;
import com.fxlabs.fxt.codegen.code.Database;
import com.fxlabs.fxt.codegen.code.TestSuite;
import com.fxlabs.fxt.dto.project.TestSuiteCategory;
import com.fxlabs.fxt.dto.project.TestSuiteSeverity;
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

    public List<String> getAssertions(String scanario){

        if (this.config == null ) return null;

        String version = null;
        if (! CollectionUtils.isEmpty(this.config.getTestSuites())){
            for ( TestSuite ts : this.config.getTestSuites() ){
                if (StringUtils.equalsIgnoreCase(ts.getScenario(), scanario)){
                    return ts.getAssertions();
                }
            }
        }
        return null;
    }

    public TestSuiteSeverity getTestSuiteSeverity(String scanario){

        if (this.config == null ) return null;

        String version = null;
        if (! CollectionUtils.isEmpty(this.config.getTestSuites())){
            for ( TestSuite ts : this.config.getTestSuites() ){
                if (StringUtils.equalsIgnoreCase(ts.getScenario(), scanario)){
                    return TestSuiteSeverity.valueOf(ts.getSeverity());
                }
            }
        }
        return null;
    }

    public TestSuiteCategory getTestSuiteCategory(String scanario){

        if (this.config == null ) return null;

        String version = null;
        if (! CollectionUtils.isEmpty(this.config.getTestSuites())){
            for ( TestSuite ts : this.config.getTestSuites() ){
                if (StringUtils.equalsIgnoreCase(ts.getScenario(), scanario)){
                    return TestSuiteCategory.valueOf(ts.getCategory());
                }
            }
        }
        return null;
    }

    public String getTestSuitePostfix(String scanario){

        if (this.config == null ) return "";

        String version = null;
        if (! CollectionUtils.isEmpty(this.config.getTestSuites())){
            for ( TestSuite ts : this.config.getTestSuites() ){
                if (StringUtils.equalsIgnoreCase(ts.getScenario(), scanario)){
                    return ts.getPostfix() != null ? ts.getPostfix() : ts.getScenario();
                }
            }
        }
        return "";
    }

}
