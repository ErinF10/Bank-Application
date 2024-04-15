// each object of this class represents a monetary value,
// such as $45.90 or $-0.67
public class MonetaryValue {
    private final int totalCents;
    public static final MonetaryValue ZERO = new MonetaryValue(0);

    public MonetaryValue(int totalCents) {
        this.totalCents = totalCents;
    }

    public MonetaryValue(int dollars, int cents) {
        totalCents = dollars * 100 + cents;
    }

    // examples of monetaryString:
    // 10, 10.34, -10, -10.34, $10, $10.34, $-10, $-10.34
    public MonetaryValue(String monetaryString) {
        if (monetaryString.startsWith("$")) {
            monetaryString = monetaryString.substring(1); // disregard first character
        }

        boolean isNegative = false;
        if (monetaryString.startsWith("-")) {
            isNegative = true;
            monetaryString = monetaryString.substring(1);
        }

        String[] parts = monetaryString.split("\\."); // split on the decimal (dw about the \\)
        if (parts.length == 1) {
            totalCents = (isNegative ? -1 : 1) * Integer.parseInt(parts[0]) * 100;
        } else {
            totalCents = (isNegative ? -1 : 1)
                    * (Integer.parseInt(parts[0]) * 100 + Integer.parseInt(parts[1]));
        }
    }

    public String toString() {
        return String.format("$%.2f", toDouble());
    }

    // totalCents: 1023 --> 10.23
    public double toDouble() {
        return totalCents / 100.0;
    }

    public boolean isNegative() {
        return this.isLessThan(ZERO); // or: totalCents < 0
    }

    public boolean isPositive() {
        return totalCents > 0;
    }

    // determines if this MV is greater than the other MV
    public boolean isGreaterThan(MonetaryValue other) {
        return this.totalCents > other.totalCents;
    }

    public boolean isLessThan(MonetaryValue other) {
        return this.totalCents < other.totalCents;
    }

    public boolean isEqualTo(MonetaryValue other) {
        return this.totalCents == other.totalCents;
    }

    public MonetaryValue plus(MonetaryValue other) {
        return new MonetaryValue(this.totalCents + other.totalCents);
    }

    public MonetaryValue minus(MonetaryValue other) {
        return new MonetaryValue(this.totalCents - other.totalCents);
    }
}
