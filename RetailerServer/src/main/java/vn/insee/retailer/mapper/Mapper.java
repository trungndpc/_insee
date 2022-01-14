package vn.insee.retailer.mapper;

import org.modelmapper.Condition;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapper {
    private ModelMapper mapper;
    public static final Converter<Long, ZonedDateTime> LONG_2_ZONE_DATE_TIME =
            mappingContext -> ZonedDateTime.ofInstant(Instant.ofEpochSecond(mappingContext.getSource()), ZoneId.systemDefault());
    public static final Converter<ZonedDateTime, Long> ZONE_DATE_TIME_2_LONG =
            mappingContext -> mappingContext.getSource().toEpochSecond();
    public static final Condition<Long, Long> NOT_ZERO_LONG = mappingContext -> !mappingContext.getSource().equals(0L);
    public static final Condition<Integer, Integer> NOT_ZERO_INT = mappingContext -> !mappingContext.getSource().equals(0);
    public static final Condition NOT_NULL = mappingContext -> mappingContext.getSource() != null;

    public Mapper() {

        this.mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.getConfiguration().setSkipNullEnabled(true);

    }
    public <S, D> void map(S source, D destination) {
        mapper.map(source, destination);
    }

    public <S, D> List<D> mapToList(List<S> list, Type type) {
        return mapper.map(list, type);
    }

    public <S, D> D map(S source, Class<D> destinationClass) {
        return mapper.map(source, destinationClass);
    }

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> this.mapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
}
