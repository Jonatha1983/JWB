package com.gafner.jwb.client.paint_operation;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.TextNode;
import javafx.scene.paint.Color;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class ColorCombineSerializer {

    public static class ColorJsonSerializer extends JsonSerializer<Color> {

        @Override
        public void serialize(Color color, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(getColorAsWebColor(color));
        }

        private static String getColorAsWebColor(Color color) {
            int r = (int) Math.round(color.getRed() * 255.0);
            int g = (int) Math.round(color.getGreen() * 255.0);
            int b = (int) Math.round(color.getBlue() * 255.0);
            return String.format("#%02x%02x%02x", r, g, b);
        }
    }

    public static class ColorJsonDeserializer extends JsonDeserializer<Color> {

        @Override
        public Color deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException {
            TreeNode treeNode = p.getCodec().readTree(p);
            String value = ((TextNode) treeNode).asText();
            return Color.web(value);


        }
    }
}
