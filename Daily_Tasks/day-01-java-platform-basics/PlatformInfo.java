public class PlatformInfo {

    public static void main(String[] args) {

        System.out.println("===== JAVA PLATFORM INFORMATION =====");

        System.out.println("Java Version    : " +
                System.getProperty("java.version"));

        System.out.println("Operating System: " +
                System.getProperty("os.name"));

        System.out.println("Processors      : " +
                Runtime.getRuntime().availableProcessors());

        System.out.println("Max Heap Memory : " +
                Runtime.getRuntime().maxMemory() / (1024 * 1024) + " MB");

        System.out.println("Free Heap Memory: " +
                Runtime.getRuntime().freeMemory() / (1024 * 1024) + " MB");
    }
}