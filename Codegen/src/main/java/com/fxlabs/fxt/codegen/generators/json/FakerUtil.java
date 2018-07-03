package com.fxlabs.fxt.codegen.generators.json;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Mohammed Luqman Shareef
 * @since 6/26/2018
 */public class FakerUtil {

    public static String getFakerString(String propName){
        if (StringUtils.isBlank(propName)) return null;

        String fakerString = null;
        String eVal = null;

        eVal = evaluate(propName);
        switch (eVal){
//            case "name":
//                fakerString = "Faker.name.lastName";
//                break;
            case "fullname":
                fakerString = "Faker.name.fullName";
                break;
            case "firstname":
                fakerString = "Faker.name.firstName";
                break;
            case "lastname":
                fakerString = "Faker.name.lastName";
                break;
            case "company":
                fakerString = "Faker.company.name";
                break;
            case "phone":
                fakerString = "Faker.PhoneNumber.phoneNumber";
                break;
            case "currency":
                fakerString = "Faker.currency.name";
                break;
            case "currencycode":
                fakerString = "Faker.currency.code";
                break;
            case "email":
                fakerString = "Faker.internet.emailAddress";
                break;
            case "ipv4":
                fakerString = "Faker.internet.ipV4Address";
                break;
            case "street":
                fakerString = "Faker.address.streetName";
                break;
            case "zip":
                fakerString = "Faker.address.zipCode";
                break;
            case "country":
                fakerString = "Faker.address.country";
                break;
            case "address":
                fakerString = "Faker.address.fullAddress";
                break;
            case "job":
                fakerString = "Faker.job.title";
                break;
            case "username":
                fakerString = "Faker.name.username";
                break;
            default:
                fakerString = null;
                break;
        }
        return fakerString;
    }

    public static String evaluate(String propName){

        String eVal = propName.toLowerCase();

        if (StringUtils.containsAny(eVal, "firstname", "first_name", "name_first")){
            return "firstname";
        }
        if (StringUtils.containsAny(eVal, "lastname", "last_name", "name_last")){
            return "lastname";
        }
        if (StringUtils.containsAny(eVal,  "fullname", "full_name")){
            return "fullname";
        }
        if (StringUtils.containsAny(eVal,  "city", "city_name", "cityname")){
            return "city";
        }
        if (StringUtils.containsAny(eVal,  "company", "company_name", "companyname")){
            return "company";
        }
        if (StringUtils.containsAny(eVal,  "phno", "ph_no", "phone", "phone_no", "phonenumber", "phone_number",
                "mobile" , "mobileno", "mobile_no", "mobilenumber", "mobile_number")){
            return "phone";
        }
        if (StringUtils.containsAny(eVal,  "street", "street_name", "streetname")){
            return "street";
        }
        if (StringUtils.containsAny(eVal,  "zip", "zip_code", "zip_code")){
            return "zip";
        }
        if (StringUtils.containsAny(eVal,  "country", "country_name", "country_name")){
            return "country";
        }
        if (StringUtils.containsAny(eVal,  "job", "jobtitle", "job_title")){
            return "job";
        }
        if (StringUtils.containsAny(eVal,  "username", "user_name")){
            return "username";
        }

        //Imp Note: If there is only One search String, DON'T use StringUtils.containsAny() method (it is giving weird results),
        // instead use StringUtils.contains() method.
        if (StringUtils.contains(eVal,  "address")){
            return "address";
        }

        return eVal;
    }
}
