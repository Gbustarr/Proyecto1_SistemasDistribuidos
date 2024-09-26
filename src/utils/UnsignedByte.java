package utils;

public class UnsignedByte {
    private int value;

    // Rango permitido para un unsigned byte
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 255;

    // Constructor que recibe un int
    public UnsignedByte(int value) {
        if (value < MIN_VALUE || value > MAX_VALUE) {
            throw new IllegalArgumentException("El valor debe estar entre " + MIN_VALUE + " y " + MAX_VALUE);
        }
        this.value = value;
    }

    // Constructor que recibe un byte con signo
    public UnsignedByte(byte signedValue) {
        this.value = signedValue & 0xFF; // Convierte el byte con signo en un valor unsigned
    }

    // Obtiene el valor del unsigned byte
    public int getValue() {
        return value;
    }

    // Suma otro unsigned byte a este y devuelve el resultado en un nuevo UnsignedByte
    public UnsignedByte add(UnsignedByte other) {
        int result = this.value + other.value;
        if (result > MAX_VALUE) {
            throw new ArithmeticException("Desbordamiento al sumar dos UnsignedByte");
        }
        return new UnsignedByte(result);
    }

    // Resta otro unsigned byte de este y devuelve el resultado en un nuevo UnsignedByte
    public UnsignedByte subtract(UnsignedByte other) {
        int result = this.value - other.value;
        if (result < MIN_VALUE) {
            throw new ArithmeticException("Desbordamiento al restar dos UnsignedByte");
        }
        return new UnsignedByte(result);
    }

    // Multiplica este unsigned byte por otro y devuelve el resultado en un nuevo UnsignedByte
    public UnsignedByte multiply(UnsignedByte other) {
        int result = this.value * other.value;
        if (result > MAX_VALUE) {
            throw new ArithmeticException("Desbordamiento al multiplicar dos UnsignedByte");
        }
        return new UnsignedByte(result);
    }

    // Divide este unsigned byte por otro y devuelve el resultado en un nuevo UnsignedByte
    public UnsignedByte divide(UnsignedByte other) {
        if (other.value == 0) {
            throw new ArithmeticException("División por cero");
        }
        return new UnsignedByte(this.value / other.value);
    }

    // Convierte a una representación en string
    @Override
    public String toString() {
        return Integer.toString(value);
    }

    // Métodos de casting
    public byte toByte() {
        return (byte) value;
    }

    public short toShort() {
        return (short) value;
    }

    public int toInt() {
        return value;
    }

    public long toLong() {
        return (long) value;
    }

    public float toFloat() {
        return (float) value;
    }

    public double toDouble() {
        return (double) value;
    }

    // Método estático para crear un UnsignedByte desde un byte con signo
    public static UnsignedByte fromByte(byte signedValue) {
        return new UnsignedByte(signedValue);
    }

    // Método estático para crear un UnsignedByte desde un short
    public static UnsignedByte fromShort(short value) {
        return new UnsignedByte(value);
    }

    // Método estático para crear un UnsignedByte desde un int
    public static UnsignedByte fromInt(int value) {
        return new UnsignedByte(value);
    }

    // Método estático para crear un UnsignedByte desde un long
    public static UnsignedByte fromLong(long value) {
        if (value < MIN_VALUE || value > MAX_VALUE) {
            throw new IllegalArgumentException("El valor debe estar entre " + MIN_VALUE + " y " + MAX_VALUE);
        }
        return new UnsignedByte((int) value);
    }

    // Método estático para crear un UnsignedByte desde un float
    public static UnsignedByte fromFloat(float value) {
        if (value < MIN_VALUE || value > MAX_VALUE) {
            throw new IllegalArgumentException("El valor debe estar entre " + MIN_VALUE + " y " + MAX_VALUE);
        }
        return new UnsignedByte((int) value);
    }

    // Método estático para crear un UnsignedByte desde un double
    public static UnsignedByte fromDouble(double value) {
        if (value < MIN_VALUE || value > MAX_VALUE) {
            throw new IllegalArgumentException("El valor debe estar entre " + MIN_VALUE + " y " + MAX_VALUE);
        }
        return new UnsignedByte((int) value);
    }
}
