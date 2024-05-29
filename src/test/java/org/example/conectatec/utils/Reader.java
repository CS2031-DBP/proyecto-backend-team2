package org.example.conectatec.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.Files;

@Component
public class Reader {

    @Autowired
    private ObjectMapper mapper;

    public static String readJsonFile(String filePath) throws IOException {
        ClassPathResource resource = new ClassPathResource(filePath);
        byte[] byteArray = Files.readAllBytes(resource.getFile().toPath());
        return new String(byteArray);
    }

    public String updateJsonField(String json, String key, String value) throws JsonProcessingException {
        JsonNode jsonNode = mapper.readTree(json);
        ((ObjectNode) jsonNode).put(key, value);
        return mapper.writeValueAsString(jsonNode);
    }

    public String updateNestedField(String json, String parentKey, String key, String newValue) throws JsonProcessingException {
        JsonNode jsonNode = mapper.readTree(json);
        ((ObjectNode) jsonNode.get(parentKey)).put(key, newValue);
        return mapper.writeValueAsString(jsonNode);
    }



    public String updateJsonField(String json, String key, Long value) throws JsonProcessingException {
        JsonNode jsonNode = mapper.readTree(json);
        ((ObjectNode) jsonNode).put(key, value);
        return mapper.writeValueAsString(jsonNode);
    }

    public String updateEmail(String json, String key, String email) throws JsonProcessingException {
        JsonNode jsonNode = mapper.readTree(json);
        ((ObjectNode) jsonNode).put(key, email);
        return mapper.writeValueAsString(jsonNode);
    }
}
