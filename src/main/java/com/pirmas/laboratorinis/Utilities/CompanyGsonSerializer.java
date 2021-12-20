package com.pirmas.laboratorinis.Utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.pirmas.laboratorinis.DataStructures.Company;

import java.lang.reflect.Type;

public class CompanyGsonSerializer implements JsonSerializer<Company> {
    @Override
    public JsonElement serialize(Company company, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject companyJson = new JsonObject();
        companyJson.addProperty("id", company.getId());
        companyJson.addProperty("login", company.getUserName());
        companyJson.addProperty("psw", company.getUserPassword());
        companyJson.addProperty("dateCreated", company.getDateCreated().toString());
        companyJson.addProperty("dateModified", company.getDateModified().toString());
        companyJson.addProperty("name", company.getCompanyName());
        companyJson.addProperty("rep", company.getCompanyRepresentative());
        return companyJson;
    }
}