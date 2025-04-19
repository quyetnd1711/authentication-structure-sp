package vn.eren.authenticationstructuresp.config.instant;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import vn.eren.authenticationstructuresp.common.constant.DateTimeConstant;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class CustomInstantSerializer extends StdSerializer<Instant> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DateTimeConstant.DD_MM_YYYY_HH_mm_ss)
            .withZone(ZoneId.systemDefault());

    public CustomInstantSerializer() {
        super(Instant.class);
    }

    @Override
    public void serialize(Instant value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(FORMATTER.format(value));
    }
}

