package mathlogic_lab1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LexemeDefinitions {
    private static int idCounter = 1;
    private static Map<String, String> idTable = new HashMap<>();
    private static final int MAX_IDENTIFIER_LENGTH = 15;

    public static List<LexemeType> createLexemeTypes() {
        return new ArrayList<>();
    }

    public static String[] getSkipTypes() {
        return new String[] { "COMMENT" };
    }
    
    public static void reset() {
        idTable.clear();
        idCounter = 1;
    }
    
    public static void addIdentifier(String name, String value) {
        if (!idTable.containsKey(name)) {
            idTable.put(name, value);
            idCounter++;
        }
    }
    
    public static boolean hasIdentifier(String name) {
        return idTable.containsKey(name);
    }

    public static String getIdentifierValue(String name) {
        return idTable.get(name);
    }
    
    public static int getIdCounter() {
        return idCounter;
    }
    
    public static Map<String, String> getIdTable() {
        return idTable;
    }
}