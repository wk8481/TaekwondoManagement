//package be.kdg.programming3.projectwilliamkasasa.util;
//
//import com.google.gson.JsonElement;
//import com.google.gson.JsonPrimitive;
//import com.google.gson.JsonSerializationContext;
//import com.google.gson.JsonSerializer;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Type;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//@Component
//public class LocalDateTimeSerializer implements JsonSerializer<Object> {
//    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("d-MMM-yyyy hh:mm");
//
//    @Override
//    public JsonElement serialize(Object obj, Type typeOfSrc, JsonSerializationContext context) {
//        if (obj instanceof LocalDateTime localDateTime) {
//            return new JsonPrimitive(FORMATTER.format(localDateTime));
//        } else if (obj instanceof LocalDate localDate) {
//            return new JsonPrimitive(FORMATTER.format(localDate.atStartOfDay()));
//        }
//        return null; // Handle other cases or return null if not LocalDateTime or LocalDate
//    }
//}
//
//
