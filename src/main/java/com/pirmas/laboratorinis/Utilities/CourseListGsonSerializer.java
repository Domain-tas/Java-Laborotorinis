package com.pirmas.laboratorinis.Utilities;

import com.google.gson.*;
import com.pirmas.laboratorinis.DataStructures.Course;

import java.lang.reflect.Type;
import java.util.List;

public class CourseListGsonSerializer implements JsonSerializer<List<Course>> {
    @Override
    public JsonElement serialize(List<Course> projects, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();
        Gson parser = new GsonBuilder().create();

        for (Course p : projects) {
            jsonArray.add(parser.toJson(p));
        }
        System.out.println(jsonArray);
        return jsonArray;
    }
}
