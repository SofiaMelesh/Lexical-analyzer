package mathlogic_lab1;

import java.util.List;

public class LexerTablePrinter {
    
    public static void printTable(List<Lexem> lexems) {
        // Находим максимальную длину для каждой колонки
        int maxTextLen = "Лексема".length();
        int maxTypeLen = "Тип лексемы".length();
        int maxValueLen = "Значение".length();
        
        for (Lexem lexem : lexems) {
            if (lexem.getTypeName().equals("COMMENT")) continue;
            
            maxTextLen = Math.max(maxTextLen, lexem.getText().length());
            maxTypeLen = Math.max(maxTypeLen, formatType(lexem.getTypeName()).length());
            
            String value = formatValue(lexem);
            maxValueLen = Math.max(maxValueLen, value.length());
        }
        
        // Добавляем отступы
        maxTextLen += 2;
        maxTypeLen += 2;
        maxValueLen += 2;
        
        // Создаем шаблон для разделителей
        String textSeparator = repeat("--", maxTextLen);
        String typeSeparator = repeat("--", maxTypeLen);
        String valueSeparator = repeat("--", maxValueLen);
        
        // Верхняя граница таблицы
        System.out.println("|" + textSeparator + "|" + typeSeparator + "|" + valueSeparator + "|");
        
        // Заголовок
        System.out.printf("| %-" + (maxTextLen-1) + "s| %-" + (maxTypeLen-1) + "s| %-" + (maxValueLen-1) + "s|\n", 
            "Лексема", "Тип лексемы", "Значение");
        
        // Разделитель после заголовка
        System.out.println("|" + textSeparator + "|" + typeSeparator + "|" + valueSeparator + "|");
        
        // Строки таблицы
        for (Lexem lexem : lexems) {
            if (lexem.getTypeName().equals("COMMENT")) continue;
            
            String text = lexem.getText();
            String type = formatType(lexem.getTypeName());
            String value = formatValue(lexem);
            
            System.out.printf("| %-" + (maxTextLen-1) + "s| %-" + (maxTypeLen-1) + "s| %-" + (maxValueLen-1) + "s|\n", 
                text, type, value);
        }
        
        // Нижняя граница таблицы
        System.out.println("|" + textSeparator + "|" + typeSeparator + "|" + valueSeparator + "|");
        System.out.println();
    }
    
    private static String repeat(String str, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
    
    private static String formatType(String typeName) {
        switch (typeName) {
            case "IDENTIFIER":
                return "Идентификатор";
            case "HEX_NUMBER":
                return "Шестнадцатеричное число";
            case "ASSIGN":
                return "Знак присваивания";
            case "ARITHMETIC_OP":
                return "Знак арифметической операции";
            case "LPAREN":
                return "Левая скобка";
            case "RPAREN":
                return "Правая скобка";
            case "SEPARATOR":
                return "Разделитель";
            case "ERROR":
                return "Ошибка";
            default:
                return typeName;
        }
    }
    
    private static String formatValue(Lexem lexem) {
        if (lexem.getTypeName().equals("IDENTIFIER")) {
            return lexem.getValue();
        } else if (lexem.getTypeName().equals("HEX_NUMBER")) {
            return lexem.getValue();
        } else {
            return "";
        }
    }
}