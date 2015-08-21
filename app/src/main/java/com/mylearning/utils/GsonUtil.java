package com.mylearning.utils;

import com.google.jtm.Gson;
import com.google.jtm.GsonBuilder;
import com.google.jtm.TypeAdapter;
import com.google.jtm.TypeAdapterFactory;
import com.google.jtm.reflect.TypeToken;
import com.google.jtm.stream.JsonReader;
import com.google.jtm.stream.JsonToken;
import com.google.jtm.stream.JsonWriter;

import java.io.IOException;

/**
 * json 格式处理工具集
 */
public class GsonUtil {
    private static Gson gson = null;
    /**
     * 创建可解析泛型与boolean 类型转化的GsonBuilder
     * @return GsonBuilder
     * */
    public static Gson createBuilder(){
        if (gson == null) {
            synchronized (GsonUtil.class) {
                if (gson == null) {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.registerTypeAdapter(Boolean.class, booleanAsIntAdapter);
                    gsonBuilder.registerTypeAdapter(boolean.class, booleanAsIntAdapter);
                    gsonBuilder.registerTypeAdapterFactory(factory);
                    gson = gsonBuilder.create();
                }
            }
        }
        return gson;
    }

    static TypeAdapterFactory factory = new TypeAdapterFactory() {
        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> tTypeToken) {
            final Class<? super T> rawType = tTypeToken.getRawType();
            if (rawType.isEnum()) {
                final Object[] types = rawType.getEnumConstants();
                return new TypeAdapter<T>() {
                    public void write(JsonWriter out, T value) throws IOException {
                        if (value == null) {
                            out.nullValue();
                        } else {
                            int index = 0;
                            for (int i = 0; i < types.length; i++) {
                                if (types[i].toString().equals(value.toString())) {
                                    index = i;
                                    break;
                                }
                            }
                            out.value(index);
                        }
                    }

                    public T read(JsonReader reader) throws IOException {
                        if (reader.peek() == JsonToken.NULL) {
                            reader.nextNull();
                            return null;
                        } else {
                            return (T) types[reader.nextInt()];
                        }
                    }
                };
            }
            return null;
        }
    };


    private static final TypeAdapter<Boolean> booleanAsIntAdapter = new TypeAdapter<Boolean>() {
        @Override
        public void write(JsonWriter jsonWriter, Boolean aBoolean) throws IOException {
            if (null == aBoolean) {
                jsonWriter.nullValue();
            } else {
                jsonWriter.value(aBoolean ? 1 : 0);
            }
        }

        @Override
        public Boolean read(JsonReader jsonReader) throws IOException {
            JsonToken peek = jsonReader.peek();
            switch (peek) {
                case BOOLEAN:
                    return jsonReader.nextBoolean();
                case NULL:
                    jsonReader.nextNull();
                    return Boolean.FALSE;
                case NUMBER:
                    return jsonReader.nextInt() != 0;
                case STRING:
                    return Boolean.parseBoolean(jsonReader.nextString());
                default:
                    throw new IllegalStateException("Expected BOOLEAN or NUMBER but was " + peek);
            }
        }
    };
}
