package nl.han.ica.icss.ast.literals;

import nl.han.ica.icss.ast.Literal;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.Objects;

public class PixelLiteral extends Literal {
    public int value;

    public PixelLiteral(int value) {
        this.value = value;
    }
    public PixelLiteral(String text) {
        this.value = Integer.parseInt(text.substring(0, text.length() - 2));
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getTextValue() {
        return value + "px";
    }

    @Override
    public ExpressionType getExpressionType() {
        return ExpressionType.PIXEL;
    }

    @Override
    public String getNodeLabel() {
        return "Pixel literal (" + value + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PixelLiteral that = (PixelLiteral) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
