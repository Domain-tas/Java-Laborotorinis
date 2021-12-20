package com.pirmas.laboratorinis.Utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.pirmas.laboratorinis.DataStructures.Person;

import java.lang.reflect.Type;

public class PersonGsonSerializer implements JsonSerializer<Person> {
    @Override
    public JsonElement serialize(Person person, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject personJson = new JsonObject();
        personJson.addProperty("id", person.getId());
        personJson.addProperty("login", person.getUserName());
        personJson.addProperty("psw", person.getUserPassword());
        personJson.addProperty("dateCreated", person.getDateCreated().toString());
        personJson.addProperty("dateModified", person.getDateModified().toString());
        personJson.addProperty("name", person.getPersonName());
        personJson.addProperty("surname", person.getPersonSurname());
        personJson.addProperty("position", person.getPersonPosition());
        personJson.addProperty("email", person.getPersonEmail());
        //dar json array, kur yra Task serializer, kad nebutu amzino duomenu buildinimo
        return personJson;
    }
}

