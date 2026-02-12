import java.util.*;

// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);



        // Get value and exponent
        try {
            System.out.println("Main memory representation is A x 2^E");
            System.out.println("Value A: ");
            int A = sc.nextInt();
            System.out.println("Exponent E: ");
            int E = sc.nextInt();
            long mainMemoryBytes = A * (long)Math.pow(2, E);
            System.out.println("Main memory: " + A + " x 2^" + E + " = " + mainMemoryBytes + " bytes");
        }
        catch (InputMismatchException e) {
            System.out.println("An error occurred: Invalid input type. Please enter integers only.");

        }



        try {
            System.out.print("Cache blocks: ");
            int cacheBlocks = sc.nextInt();
            if (!isPowerOfTwo(cacheBlocks)) {
                System.out.println("Cache Blocks is not in Base 2. Program terminated");
                System.exit(1);
            }

        }
        catch (InputMismatchException e) {
            System.out.println("An error occurred: Invalid input type. Please enter integers only.");
        }



        try {
            System.out.print("Bytes in Cache blocks: ");
            int bytesPerBlock = sc.nextInt();
            if (!isPowerOfTwo(bytesPerBlock)) {
                System.out.println("Bytes in Cache blocks is not in Base 2. Program terminated");
                System.exit(1);
            }

        }  catch (InputMismatchException e) {
            System.out.println("An error occurred: Invalid input type. Please enter integers only.");
        }


        try {
            System.out.print("k-set associative value: ");
            int kWay = sc.nextInt();
            if (!isPowerOfTwo(kWay)) {
                System.out.println("k-set associative values is not in Base 2. Program terminated");
                System.exit(1);
            }
        }
        catch (InputMismatchException e) {
            System.out.println("An error occurred: Invalid input type. Please enter integers only.");
        }


        sc.nextLine();
        System.out.print("Memory address in Base 16: ");
        String hexAddress = sc.nextLine();

        if (!isValidHex(hexAddress)) {
            System.out.println("Memory address is not in Base 16. Program terminated");
            System.exit(1);
        }




    }




    private static boolean isPowerOfTwo(int n) {
        return n <= 0 || (n & (n - 1)) == 0;
    }


    private static boolean isValidHex(String hex) {
        String cleanedHex = hex.replaceFirst("^0x", "");
        return cleanedHex.matches("(?i)[0-9A-F]+");

    }

    private static String convertHexToBinary(String hex) {
        String cleanedHex = hex.replaceFirst("^0x", "");
        Map<Character, String> map = new HashMap<>();
        StringBuilder binaryRep = new StringBuilder();


        map.put('0', "0000");
        map.put('1', "0001");
        map.put('2', "0010");
        map.put('3', "0011");
        map.put('4', "0100");
        map.put('5', "0101");
        map.put('6', "0110");
        map.put('7', "0111");
        map.put('8', "1000");
        map.put('9', "1001");
        map.put('A', "1010");
        map.put('B', "1011");
        map.put('C', "1100");
        map.put('D', "1101");
        map.put('E', "1110");
        map.put('F', "1111");


        for (int i = 0; i < cleanedHex.length(); i++) {
            String hexBit = map.get(cleanedHex.charAt(i));
            binaryRep.append(hexBit);
        }


        return binaryRep.toString();

    }
}