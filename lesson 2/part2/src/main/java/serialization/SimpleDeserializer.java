package serialization;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

public class SimpleDeserializer implements ParameterizedDeserializer {

    KeyValueExtractor keyValueExtractor = new KeyValueExtractor();
    Converter converter = new Converter();

    @Override
    public <T> T deserialize(String content, Class<T> clasz) {
        try {
            T instance = clasz.newInstance();

            Map<String, String> keyValues = keyValueExtractor.extractKeyValues(content);

            Arrays.asList(clasz.getMethods())
                    .forEach(method -> {
                        if (method.getName().startsWith("set")) {
                            System.out.println("attempt to call: " + method.getName() + ", parameter: " + Arrays.toString(method.getGenericParameterTypes()));
                            // TODO 3 what could happen if more than one parameter is provided?

                            try {
                                String setterParameterString = extractSetterParameter(keyValues, method);
                                System.out.println("attempt to call: " + method.getName() + ", setterParameterString: " + setterParameterString);
                                Object setterParameter = converter.convert(setterParameterString, method.getParameterTypes()[0]);
                                System.out.println("attempt to call: " + method.getName() + ", parameter: " + setterParameter);
                                method.invoke(instance, setterParameter);
                            } catch (IllegalAccessException| InvocationTargetException e) {
                                throw new IllegalArgumentException("Could not set method:  " + method, e);
                            }
                        }
                    });

            return instance;
        } catch (InstantiationException|IllegalAccessException e) {
            throw new IllegalArgumentException("Could not instantiate " + clasz, e);
        }
    }

    String extractSetterParameter(Map<String, String> keyValues, Method method) {
        System.out.println("extractSetterParameter: " + method.getName() + ", keyValues: " + keyValues);
        // TODO 3 get the value from the map. Is the method name equal with the properties?
        return keyValues.get(method.getName());
    }
}
