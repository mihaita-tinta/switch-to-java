package serialization;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class SimpleDeserializer implements ParameterizedDeserializer {

    KeyValueExtractor keyValueExtractor = new KeyValueExtractor();
    Converter converter = new Converter();

    @Override
    public <T> T deserialize(String content, Class<T> clasz) {
        try {
            T instance = clasz.newInstance();

            Map<String, String> keyValues = keyValueExtractor.extractKeyValues(content);
//            System.out.println("###########################");
//            for(Method m:clasz.getMethods() ){
//                System.out.println(m);
//            }
//            System.out.println("####################");
            Arrays.asList(clasz.getMethods()).stream()
                    .filter(e -> e.getGenericParameterTypes().length == 1)
                    .forEach(method -> {
                        if (method.getName().startsWith("set")) {
                            System.out.println("1st.attempt to call: " + method.getName() + ", parameter: " + Arrays.toString(method.getGenericParameterTypes()));
                            // TODO 3 what could happen if more than one parameter is provided?
                            //am filtrat inainte metodele..trebuie sa aiba un singur argument
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
        Set<String> set = keyValues.keySet();
        String met = method.getName();
        System.out.println("#################");
        System.out.println("metoda "+met);

        String s = set.stream()
                .filter(e-> met.equals("set"+e.substring(0,1).toUpperCase()+e.substring(1)))
                .reduce("",(a,b)->a+b);

        return keyValues.get(s);

    }
}
