package be.kdg.programming3.projectwilliamkasasa.presentation.converters;

import be.kdg.programming3.projectwilliamkasasa.domain.Type;
import org.springframework.core.convert.converter.Converter;

public class StringToTypeConverter implements Converter<String, Type> {

    @Override
    public Type convert(String source) {
        if (source.toLowerCase() .startsWith("k")) return Type.KICK;
        if (source.toLowerCase().startsWith("p")) return Type.PUNCH;
        if (source.toLowerCase().startsWith("b")) return Type.BLOCK;
        if (source.toLowerCase().startsWith("t")) return Type.THROW;
        if (source.toLowerCase().startsWith("g")) return Type.GRAPPLE;
        return Type.KICK; // default value

    }
}
