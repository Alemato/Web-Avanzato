package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import it.mytutor.domain.Chat;
import it.mytutor.domain.Student;
import it.mytutor.domain.Teacher;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChatDeserializer extends StdDeserializer<Chat> {

    public ChatDeserializer(){
        this(null);
    }

    public ChatDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Chat deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Chat chat = new Chat();
        if (node.get("idChat") != null) {
            chat.setIdChat(node.get("idChat").asInt());
        }
        List<Object> userList = new ArrayList<>();
        ArrayNode users = (ArrayNode) node.get("userListser");
        Iterator<JsonNode> usersIterator = users.elements();

        while (usersIterator.hasNext()) {
            JsonNode userNode = usersIterator.next();

            if (userNode.get("roles").asInt() == 1) {

                Student student = new Student();
                if (userNode.get("idStudent")!=  null) {
                    student.setIdStudent(userNode.get("idStudent").asInt());
                }
                student.setStudyGrade(userNode.get("studyGrade").asText());
                student.setIdUser(userNode.get("idUser").asInt());
                student.setEmail(userNode.get("email").asText());
                student.setRoles(userNode.get("roles").asInt());
                student.setName(userNode.get("name").asText());
                student.setSurname(userNode.get("surname").asText());
                Date bDateStudent = new Date(userNode.get("birthday").asLong());
                student.setBirthday(bDateStudent);
                student.setLanguage(Boolean.getBoolean(userNode.get("language").asText()));
                if(userNode.get("image").asText().equals("null") || userNode.get("image").asText().equals("")){
                    student.setImage(null);
                } else student.setImage(userNode.get("image").asText());
                userList.add(0, student);

            } else if (userNode.get("roles").asInt() == 2) {

                Teacher teacher = new Teacher();
                if (userNode.get("idTeacher") != null) {
                    teacher.setIdTeacher(userNode.get("idTeacher").asInt());
                }
                teacher.setPostCode(userNode.get("postCode").asInt());
                teacher.setCity(userNode.get("city").asText());
                teacher.setRegion(userNode.get("region").asText());
                teacher.setStreet(userNode.get("street").asText());
                teacher.setStreetNumber(userNode.get("streetNumber").asText());
                teacher.setByography(userNode.get("byography").asText());
                teacher.setIdUser(userNode.get("idUser").asInt());
                teacher.setEmail(userNode.get("email").asText());
                teacher.setName(userNode.get("name").asText());
                teacher.setSurname(userNode.get("surname").asText());
                Date bDateTeacher = new Date(userNode.get("birthday").asLong());
                teacher.setBirthday(bDateTeacher);
                teacher.setLanguage(Boolean.getBoolean(userNode.get("language").asText()));
                if(userNode.get("image").asText().equals("null") || userNode.get("image").asText().equals("")){
                    teacher.setImage(null);
                } else teacher.setImage(userNode.get("image").asText());
                userList.add(1, teacher);
            }
        }
        chat.setUserListser(userList);


        return chat;
    }
}
