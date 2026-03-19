package mathlogic_lab1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.print("Введите имя файла (или 'exit'): ");
            String fileName = scanner.nextLine().trim();
            
            if (fileName.equalsIgnoreCase("exit")) break;
            
            Path filePath = Paths.get(fileName);
            if (!Files.exists(filePath)) {
                System.out.println("Файл не найден");
                continue;
            }
            
            try {
                String code = Files.readString(filePath);
                analyzeAndPrint(code);
                LexemeDefinitions.reset();
            } catch (IOException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
        scanner.close();
    }
    
    private static void analyzeAndPrint(String code) {
        System.out.println("\nАнализируемый текст:");
        System.out.println(code);
        System.out.println();
        
        Lexer lexer = new Lexer(
            LexemeDefinitions.createLexemeTypes(),
            LexemeDefinitions.getSkipTypes()
        );
        
        List<Lexem> lexems = lexer.analyze(code);
        
        System.out.println("\n-------------------------------------------------------");
        System.out.printf("%-15s | %-25s | %s\n", "Лексема", "Тип", "Значение");
        System.out.println("---------------------------------------------------------");
        
        for (Lexem l : lexems) {
            String type = switch (l.getTypeName()) {
                case "IDENTIFIER" -> "Идентификатор";
                case "HEX_NUMBER" -> "Hex число";
                case "ASSIGN" -> ":=";
                case "ARITHMETIC_OP" -> "Оператор";
                case "LPAREN" -> "(";
                case "RPAREN" -> ")";
                case "SEPARATOR" -> "|";
                case "ERROR" -> "Ошибка";
                default -> l.getTypeName();
            };
            
            String value = (l.getTypeName().equals("IDENTIFIER") || 
                           l.getTypeName().equals("HEX_NUMBER")) ? 
                           l.getValue() : "";
            
            if (l.getTypeName().equals("ERROR") && !l.getValue().isEmpty()) {
                value = l.getValue();
            }
            
            System.out.printf("%-15s | %-25s | %s\n", 
                l.getText(), type, value);
        }
        System.out.println("---------------------------------------------------------\n");
    }
}