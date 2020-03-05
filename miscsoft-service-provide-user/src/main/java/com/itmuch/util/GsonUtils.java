//package com.itmuch.util;
//
//import java.lang.annotation.Annotation;
//import java.util.Collection;
//
//import jdk.nashorn.internal.ir.annotations.Ignore;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.google.gson.ExclusionStrategy;
//import com.google.gson.FieldAttributes;
//import com.google.gson.FieldNamingStrategy;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//public class GsonUtils {
//    private static Logger logger = LoggerFactory.getLogger(GsonUtils.class);
//    public static final Gson gson = new GsonBuilder()
//            //.excludeFieldsWithoutExposeAnnotation()
//            .serializeNulls()
//            .addSerializationExclusionStrategy(new ExclusionStrategy() {
//                @Override
//                public boolean shouldSkipField(FieldAttributes fa) {
//                    Collection<Annotation> annotations = fa.getAnnotations();
//                    for (Annotation annotation : annotations) {
//                        if (annotation.annotationType() == Ignore.class) {
//                            return true;
//                        }
//                    }
//                    return false;
//                }
//
//                @Override
//                public boolean shouldSkipClass(Class<?> type) {
//                    return false;
//                }
//            })
//            .setDateFormat("yyyy-MM-dd")
//            .create();
//
//    public static final Gson gsonWithoutAnnotation = new GsonBuilder()
//            .serializeNulls()
//            .setDateFormat("MM-dd-yyyy HH:mm:ss")
//            .create();
//
//    /**
//     * Build json for given object.
//     *
//     * @param object
//     * @return
//     */
//    public static String toJson(Object object) {
//        return toJson(object, gson);
//    }
//
//    /**
//     * Parse Object(T) from given json.
//     *
//     * @param json
//     * @param cls
//     * @return
//     */
//    public static <T> T parse(String json, Class<T> cls) {
//        return parse(json, cls, gson);
//    }
//
//    /**
//     * Build json for given object.
//     *
//     * @param object
//     * @param gson
//     * @return
//     */
//    public static String toJson(Object object, Gson gson) {
//        if (object == null) {
//            return null;
//        }
//
//        return gson.toJson(object);
//    }
//
//    /**
//     * Parse Object(T) from given json.
//     *
//     * @param json
//     * @param cls
//     * @param gson
//     * @return
//     */
//    public static <T> T parse(String json, Class<T> cls, Gson gson) {
//        if (StringUtils.isBlank(json)) {
//            return null;
//        }
//        try {
//            return gson.fromJson(json, cls);
//        } catch (Exception e) {
//            logger.error("Failed to parse json " + json, e);
//            return null;
//        }
//    }
//
//    /**
//     * Build gson with some frequently used parameters.
//     *
//     * @param excludeFiledsWithoutExposeAnnotation
//     * @param dateFormat
//     * @param exclusionStrategy
//     * @param fieldNamingStrategy
//     * @return
//     */
//    public static Gson buildGson(boolean excludeFiledsWithoutExposeAnnotation, String dateFormat,
//                                 ExclusionStrategy exclusionStrategy, FieldNamingStrategy fieldNamingStrategy) {
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        if (excludeFiledsWithoutExposeAnnotation) {
//            gsonBuilder.excludeFieldsWithoutExposeAnnotation();
//        }
//        if (StringUtils.isNotBlank(dateFormat)) {
//            gsonBuilder.setDateFormat(dateFormat);
//        }
//        if (exclusionStrategy != null) {
//            gsonBuilder.setExclusionStrategies(exclusionStrategy);
//        }
//        if (fieldNamingStrategy != null) {
//            gsonBuilder.setFieldNamingStrategy(fieldNamingStrategy);
//        }
//
//        return gsonBuilder.create();
//    }
//
//}
