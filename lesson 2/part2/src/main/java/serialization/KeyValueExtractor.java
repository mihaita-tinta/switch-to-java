package serialization;

import java.util.*;

public class KeyValueExtractor {
    final char startObject = '{';
    final char endObject = '}';
    final char semicolon = ':';
    final char comma = ',';

    StringBuilder key = new StringBuilder();
    StringBuilder value = new StringBuilder();
    boolean readingKey = true;
    /**
     * @param content
     * @return a map containing key-value pairs for simple JSON objects.
     */
    Map<String, String> map = new HashMap<>();
    public Map<String, String> extractKeyValues(String content) {


        for (int i = 0; i < content.length(); i++) {
            char currentChar = content.charAt(i);
            System.out.println("extractKeyValues - currentChar: " + currentChar);
            switch (currentChar) {
                case startObject: {
                    startNewObject();
                    break;
                }
                case endObject: {
                    completeKeyValue();
                    break;
                }
                case comma: {
                    completeKeyValue();
                    break;
                }
                case semicolon: {
                    readingKey=false;
                    break;
                }
                default: {
                    accumulate(currentChar);
                }
            }

        }
        return map;// TODO 2 this needs to contain the json keys and their values
    }

    public List<String> getKeys(String content) {
        // TODO 2 we need to keys from the JSON
        Map<String, String> map = this.extractKeyValues(content);
        return new ArrayList<String>(map.keySet());
    }

    private void startNewObject() {
        System.out.println("extractKeyValues - startNewObject ");
        readingKey = true;
    }

    private void accumulate(char currentChar) {
        System.out.println("extractKeyValues - accumulate: " + currentChar);
        if(readingKey){
            key.append(currentChar);
        }
        else{
            value.append(currentChar);
        }
    }

    private void completeKeyValue() {
        System.out.println("completeKeyValue - done");
        readingKey = true;
        map.put(key.toString().replace("\"",""),value.toString().replace("\"",""));
        key = new StringBuilder();
        value= new StringBuilder();
    }

}
