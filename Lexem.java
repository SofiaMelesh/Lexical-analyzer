package mathlogic_lab1;

public class Lexem {
    private String text;
    private String typeName;
    private String value;

    public Lexem(String text, String typeName, String value) {
        this.text = text;
        this.typeName = typeName;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("Lexem{text='%s', type='%s', value='%s'}", text, typeName, value);
    }
}
