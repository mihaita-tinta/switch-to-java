package serialization;

import serialization.Convertors.*;

public class Converter {

    public Object convert(String from, Class<?> target) {
        System.out.println("Name: " + target.getSimpleName());
        IGenericConverter genericConverter;
        String objectName = target.getSimpleName();

        switch (objectName) {
            case "String": {
                genericConverter  = new StringConverter();
                return genericConverter.GetConvertedValue(from);
            }
            case "double":
            {
                genericConverter = new DoubleConverter();
                return genericConverter.GetConvertedValue(from);
            }
            case "int":{
                genericConverter = new IntConverter();
                return genericConverter.GetConvertedValue(from);
            }
            case "boolean":{
                genericConverter = new BooleanConverter();
                return genericConverter.GetConvertedValue(from);
            }

            default:
                return null;
        }
    }
}


