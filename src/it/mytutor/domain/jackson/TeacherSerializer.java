package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it.mytutor.domain.Teacher;

import java.io.IOException;

public class TeacherSerializer extends StdSerializer<Teacher> {
    public TeacherSerializer() { this(null); }

    public TeacherSerializer(Class<Teacher> t) { super(t); }

    @Override
    public void serialize(Teacher teacher, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject("teacher");

        jsonGenerator.writeNumberField("idTeacher", teacher.getIdTeacher());

        jsonGenerator.writeNumberField("postCode", teacher.getPostCode());

        jsonGenerator.writeStringField("City", teacher.getCity());

        jsonGenerator.writeStringField("region", teacher.getRegion());

        jsonGenerator.writeStringField("street", teacher.getStreet());

        jsonGenerator.writeStringField("streetNumber", teacher.getStreetNumber());

        jsonGenerator.writeStringField("byography", teacher.getByography());

        jsonGenerator.writeStringField("crateDateTeacher", teacher.getCrateDateTeacher().toString());
        jsonGenerator.writeStringField("updateDateTeacher",teacher.getUpdateDateTeacher().toString());

        jsonGenerator.writeEndObject();
    }
}
