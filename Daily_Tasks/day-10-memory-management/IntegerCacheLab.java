public class IntegerCacheLab {

    public static void main(String[] args) {
        Integer a = 127;
        Integer b = 127;

        Integer c = 128;
        Integer d = 128;

        System.out.println("127 == 127: " + (a == b));
        System.out.println("128 == 128: " + (c == d));

        System.out.println("128 equals(): " + c.equals(d));

        System.out.println("127 values equal: " + a.equals(b));
    }
}