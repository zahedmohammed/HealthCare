package com.fxlabs.fxt.codegen.generators.utils;

import com.fxlabs.fxt.codegen.code.AutoCodeConfig;
import com.fxlabs.fxt.codegen.code.Database;
import com.fxlabs.fxt.codegen.code.TestSuite;
import com.fxlabs.fxt.dto.project.TestSuiteCategory;
import com.fxlabs.fxt.dto.project.TestSuiteSeverity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static List<String> defaultAssertions = new ArrayList<>();
    private static TestSuiteCategory defaultCategory = TestSuiteCategory.Functional;
    private static TestSuiteSeverity defaultSeverity = TestSuiteSeverity.Major;
    private static boolean defaultInactive = false;

    private static Map<String,TestSuiteCategory> typeCategoryMap = new HashMap<>();

    static{
        defaultAssertions.add("@StatusCode != 200");

        typeCategoryMap.put("anonymous_invalid", TestSuiteCategory.UnSecured);
        typeCategoryMap.put("auth_invalid", TestSuiteCategory.UnSecured);
        typeCategoryMap.put("DDOS", TestSuiteCategory.DDOS);
        typeCategoryMap.put("XSS_Injection", TestSuiteCategory.XSS_Injection);
        typeCategoryMap.put("sql_injection", TestSuiteCategory.SQL_Injection);
        typeCategoryMap.put("Log_Forging", TestSuiteCategory.Log_Forging);
        typeCategoryMap.put("invalid_datatype", TestSuiteCategory.Negative);
        typeCategoryMap.put("special_chars", TestSuiteCategory.Negative);
        typeCategoryMap.put("null_value", TestSuiteCategory.Negative);
        typeCategoryMap.put("empty_value", TestSuiteCategory.Negative);
        typeCategoryMap.put("create", TestSuiteCategory.Functional);

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

    public List<String> getAssertions(String type){

        if (this.config == null ) return defaultAssertions;

        String version = null;
        if (! CollectionUtils.isEmpty(this.config.getTestSuites())){
            for ( TestSuite ts : this.config.getTestSuites() ){
                if (StringUtils.equalsIgnoreCase(ts.getType(), type)){
                    if ( CollectionUtils.isEmpty(ts.getAssertions()) ||
                            ts.getAssertions().get(0) == null ||
                            ts.getAssertions().get(0).equalsIgnoreCase("null") ){
                        return defaultAssertions;
                    }else {
                        return ts.getAssertions();
                    }
                }
            }
        }
        return defaultAssertions;
    }

    public TestSuiteSeverity getTestSuiteSeverity(String type){

        TestSuiteSeverity severity = defaultSeverity;
        if (this.config == null ) return severity;

        String version = null;
        if (! CollectionUtils.isEmpty(this.config.getTestSuites())){
            for ( TestSuite ts : this.config.getTestSuites() ){
                if (StringUtils.equalsIgnoreCase(ts.getType(), type)){
                    try {
                        severity = TestSuiteSeverity.valueOf(ts.getSeverity());
                    }catch(Exception ex) {}
                    break;
                }
            }
        }
        return severity;
    }

    public TestSuiteCategory getTestSuiteCategory(String type){
        return typeCategoryMap.get(type) != null ? typeCategoryMap.get(type) : defaultCategory;
    }

    public String getTestSuitePostfix(String type){

        if (this.config == null ) return type;

        String version = null;
        if (! CollectionUtils.isEmpty(this.config.getTestSuites())){
            for ( TestSuite ts : this.config.getTestSuites() ){
                if (StringUtils.equalsIgnoreCase(ts.getType(), type)){
                    return ts.getPostfix() != null ? ts.getPostfix() : ts.getType();
                }
            }
        }
        return type;
    }

    public boolean isInactive(String type){

        if (this.config == null ) return defaultInactive;

        if (! CollectionUtils.isEmpty(this.config.getTestSuites())){
            for ( TestSuite ts : this.config.getTestSuites() ){
                if (StringUtils.equalsIgnoreCase(ts.getType(), type)){
                    return ts.isInactive();
                }
            }
        }

        return defaultInactive;
    }

}
