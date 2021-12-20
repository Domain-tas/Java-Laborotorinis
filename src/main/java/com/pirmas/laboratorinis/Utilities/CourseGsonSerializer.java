package com.pirmas.laboratorinis.Utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.pirmas.laboratorinis.DataStructures.Course;

import java.lang.reflect.Type;

public class CourseGsonSerializer implements JsonSerializer<Course> {
    @Override
    public JsonElement serialize(Course course, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject personJson = new JsonObject();
        personJson.addProperty("id", course.getId());
        personJson.addProperty("projectName", course.getCourseName());
        //personJson.addProperty("projectDescription", course.getProjectDescription());
        personJson.addProperty("dateCreated", course.getLocalDateCreated().toString());
        personJson.addProperty("endDate", course.getEndDate().toString());
        personJson.addProperty("expectedEndDate", course.getExpectedEndDate().toString());
        return personJson;
    }
}
