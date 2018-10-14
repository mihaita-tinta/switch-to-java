package serialization;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Converter {

    public Object convert(String from, Class<?> target) {
        // TODO 1 based on the target class you need to convert it to desired type
        if( Boolean.class == target | boolean.class == target ) {
            if (from != null) {
                return Boolean.parseBoolean( from );
            }else{
                return false;
            }
        }
        if( Byte.class == target | byte.class == target ) {
            if(from != null){
                return Byte.parseByte( from );
            }else{
                return (byte)0;
            }
        }
        if( Short.class == target | short.class == target) {
            if(from !=null){
                return Short.parseShort( from );
            }else{
                return (short)0;
            }
        }

        if( Integer.class == target | int.class == target) {
            if(from !=null){
                return Integer.parseInt( from );
            }else{
                return (int)0;
            }
        }
        if( Long.class == target | long.class == target) {
            if(from !=null){
                return Long.parseLong( from );
            }else{
                return (long)0;
            }
        }
        if( Float.class == target | float.class == target) {
            if(from !=null){
                return Float.parseFloat( from );
            }else{
                return (float)0;
            }
        }
        if( Double.class == target | double.class == target) {
            if(from !=null){
                return Double.parseDouble( from );
            }else{
                return (double)0;
            }
        }

        if( LocalDate.class == target) {
            if(from !=null){
                return LocalDate.parse( from );
            }
        }

        if( LocalDateTime.class == target) {
            if(from !=null){
                return LocalDateTime.parse( from );
            }
        }

//        if( int.class == target ) return Integer.parseInt( from );
//        if( long.class == target ) return Long.parseLong( from );
        return from;
    }

}
