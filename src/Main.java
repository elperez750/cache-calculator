import java.util.*;

public class Main {


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);



        // Get value and exponent
        // This is wrapped around a try in case user types in anything other than an integer.
        int A = 0;
        int E = 0;
        int blockSize = 0;
        int cacheBlocks = 0;
        int kWay = 0;


        try {
            System.out.println("Main memory representation is A x 2^E");
            System.out.println("Value A: ");
            A = sc.nextInt();
            System.out.println("Exponent E: ");
            E = sc.nextInt();


            // Calculate total bytes
            long mainMemoryBytes = A * (long)Math.pow(2, E);

            int totalAddressBits = (int)(Math.log(A) / Math.log(2)) + E;

            System.out.println("Main memory: " + A + " x 2^" + E + " = " + mainMemoryBytes + " bytes");
            System.out.println("Total Address Bits: " + totalAddressBits);

        }
        catch (InputMismatchException e) {
            System.out.println("An error occurred: Invalid input type. Please enter integers only.");
            System.exit(1);

        }


        // Get amount of cache blocks in base 2
        // If user attempts to input different type, it will be caught.
        try {
            System.out.print("Cache blocks: ");
            cacheBlocks = sc.nextInt();
            if (!isPowerOfTwo(cacheBlocks)) {
                System.out.println("Cache Blocks is not in Base 2. Program terminated");
                System.exit(1);
            }

        }
        catch (InputMismatchException e) {
            System.out.println("An error occurred: Invalid input type. Please enter integers only.");
        }



        // Get amount of bytes in a cache block in base 2
        try {
            System.out.print("Bytes in Cache blocks: ");
            blockSize = sc.nextInt();
            if (!isPowerOfTwo(blockSize)) {
                System.out.println("Bytes in Cache blocks is not in Base 2. Program terminated");
                System.exit(1);
            }

        }  catch (InputMismatchException e) {
            System.out.println("An error occurred: Invalid input type. Please enter integers only.");
        }


        // Get k-set associative value. Only used when dealing with Set-associative mapping
        try {
            System.out.print("k-set associative value: ");
            kWay = sc.nextInt();
            if (!isPowerOfTwo(kWay)) {
                System.out.println("k-set associative values is not in Base 2. Program terminated");
                System.exit(1);
            }
        }
        catch (InputMismatchException e) {
            System.out.println("An error occurred: Invalid input type. Please enter integers only.");
        }


        // Get memory address from user in base 16
        sc.nextLine();
        System.out.print("Memory address in Base 16: ");
        String hexAddress = sc.nextLine();


        // Check to see if it is valid or not.
        if (!isValidHex(hexAddress)) {
            System.out.println("Memory address is not in Base 16. Program terminated");
            System.exit(1);
        }


        if(cleanHex(hexAddress).length() * 4 > E) {
            System.out.println("Memory address is greater than main memory. Program terminated");
            System.exit(1);
        }


        String binaryRepresentation = convertHexToBinary(hexAddress);

        // Call functions here.
        calculateDirectMapping(blockSize, cacheBlocks, E, hexAddress, binaryRepresentation);
        calculateAssociativeMapping(blockSize, E, hexAddress, binaryRepresentation);
        calculateSetAssociativeMapping(kWay, blockSize, cacheBlocks, E, hexAddress, binaryRepresentation);


    }


    private static void calculateDirectMapping(int blockSize, int blockAmount, int addressSize, String address, String binary) {
        // Calculate word bits
        // Calculate index bits
        // Calculate tag bits
        // Print out

        int wordBits = Integer.numberOfTrailingZeros(blockSize);
        int lineBits = Integer.numberOfTrailingZeros(blockAmount);
        int tagBits = addressSize - (wordBits + lineBits);

        String tagPart = binary.substring(0, tagBits);
        String linePart = binary.substring(tagBits, tagBits + lineBits);
        String wordPart = binary.substring(lineBits, lineBits + wordBits);

        System.out.printf("Direct Cache mapping of %s%n", address);
        System.out.printf("[ TAG: %d bits ] [ LINE: %d bits ] [ WORD: %d bits ]%n", tagBits, lineBits, wordBits);

        System.out.printf("[%s] | [%s] | [%s]%n", tagPart, linePart, wordPart);


    }



    private static void calculateAssociativeMapping(int blockSize, int addressSize, String address, String binary) {
        int wordBits = Integer.numberOfTrailingZeros(blockSize);
        int tagBits = addressSize - wordBits;


        String tagPart = binary.substring(0, tagBits);
        String wordPart = binary.substring(tagBits, tagBits + wordBits);



        System.out.printf("Associative Mapping of %s address%n", address);
        System.out.printf("[ TAG: %d bits ] [ WORD: %d bits ]%n", tagBits, wordBits);
        System.out.printf("[%s] | [%s]%n", tagPart, wordPart);



    }


    private static void calculateSetAssociativeMapping(int k, int blockSize, int blockAmount, int addressSize, String address, String binary) {
        int wordBits = Integer.numberOfTrailingZeros(blockSize);
        int setBits = Integer.numberOfTrailingZeros(blockAmount / k);
        int tagBits = addressSize - (wordBits + setBits);


        String tagPart = binary.substring(0, tagBits);
        String setPart = binary.substring(tagBits, tagBits + setBits);
        String wordPart = binary.substring(setBits, setBits + wordBits);

        System.out.printf("4 way Cache mapping of %s address%n", address);
        System.out.printf("[ TAG: %d bits ] [ SET: %d bits ] [ WORD: %d bits ]%n", tagBits, setBits, wordBits);
        System.out.printf("[%s] | [%s] | [%s]%n", tagPart, setPart, wordPart);


    }




    // Checks if a number is a power of 2
    // This is important for getting nubmer of cache blocks and size of cache blocks
    private static boolean isPowerOfTwo(int n) {
        return n <= 0 || (n & (n - 1)) == 0;
    }

    // Checks if a hex string is valid.
    // A string is valid hex if it contains any base 10 number or has any letter A-F
    private static boolean isValidHex(String hex) {
        String cleanedHex = cleanHex(hex);
        return cleanedHex.matches("(?i)[0-9A-F]+");

    }

    private static String cleanHex(String hex) {
        return hex.replaceFirst("^0x", "");
    }


    // This converts hex into binary
    // This is needed in order to partition memory space.
    private static String convertHexToBinary(String hex) {

        // Remove first two characters, as these are not part of the hex string
        String cleanedHex = cleanHex(hex);

        // Create a mapping for each hex bit that maps to its corresponding binary number.
        Map<Character, String> map = new HashMap<>();

        // Define string builder for efficiency.
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


        // 1. Check every bit in the cleaned hex.
        // 2. Get the corresponding binary number
        // 3. Append to binary representation string

        for (int i = 0; i < cleanedHex.length(); i++) {
            String hexBit = map.get(cleanedHex.charAt(i));
            binaryRep.append(hexBit);
        }


        // Convert the string builder object to a string.

        return binaryRep.toString();

    }
}