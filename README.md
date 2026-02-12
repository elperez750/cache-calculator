# Cache Memory Calculator

A Java-based cache memory address mapping calculator that demonstrates three different cache mapping strategies: Direct Mapping, Associative Mapping, and Set-Associative Mapping.

## 📋 Project Overview

This program calculates how memory addresses map to cache locations using different mapping strategies. It's designed for understanding fundamental computer architecture concepts related to cache memory organization.

## 🎯 Features

- **Three Mapping Strategies:**
  - Direct Mapping
  - Fully Associative Mapping  
  - k-Way Set-Associative Mapping

- **Input Validation:**
  - Power-of-2 verification for cache parameters
  - Hexadecimal address validation
  - Address bounds checking against main memory size
  - Immediate error feedback with descriptive messages

- **Address Processing:**
  - Hexadecimal to binary conversion
  - Automatic bit field extraction (Tag, Line/Set, Word)
  - Support for configurable memory and cache sizes

## 🚀 Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Any Java IDE (IntelliJ IDEA, Eclipse, VS Code) or command line

### Running the Program
```bash
# Compile
javac Main.java CacheCalculator.java

# Run
java Main
```

## 💻 Usage

The program prompts for the following inputs:

1. **Main Memory Size:**
   - Value A (base value)
   - Exponent E (for A × 2^E bytes format)

2. **Cache Configuration:**
   - Number of cache blocks (must be power of 2)
   - Bytes per cache block (must be power of 2)
   - k-way set associative value (must be power of 2)

3. **Memory Address:**
   - Hexadecimal address (with or without 0x prefix)

### Example Input
```
Main memory representation is A x 2^E
Value A: 1
Exponent E: 20
Main memory: 1 x 2^20 = 1048576 bytes

Cache blocks: 32
Bytes in Cache blocks: 16
k-set associative value: 4

Memory address in Base 16: 0x326A0
Address in binary: 00110010011010100000
```

### Example Output
```
Direct Cache mapping of 0x326A0 address
[TAG] 11 : [LINE] 5 : [WORD] 4
[TAG] 00110010011 : [LINE] 01010 : [WORD] 0000

Associative Cache mapping of 0x326A0 address
[TAG] 16 : [WORD] 4
[TAG] 0011001001101010 : [WORD] 0000

4-way Cache mapping of 0x326A0 address
[TAG] 13 : [SET] 3 : [WORD] 4
[TAG] 0011001001101 : [SET] 010 : [WORD] 0000
```

## 🧮 How It Works

### Direct Mapping
Each memory block maps to exactly one cache line.

**Bit Fields:**
- **Tag:** Identifies which memory block is stored
- **Line:** Determines which cache line to use
- **Word:** Identifies byte offset within the block
```
Address bits = Tag bits + Line bits + Word bits
Line bits = log₂(cache blocks)
Word bits = log₂(bytes per block)
Tag bits = Total address bits - Line bits - Word bits
```

### Associative Mapping
Any memory block can go in any cache line (maximum flexibility).

**Bit Fields:**
- **Tag:** Uniquely identifies the memory block
- **Word:** Identifies byte offset within the block
```
Tag bits = Total address bits - Word bits
Word bits = log₂(bytes per block)
```

### Set-Associative Mapping
Hybrid approach - memory blocks map to a specific set, but can occupy any line within that set.

**Bit Fields:**
- **Tag:** Identifies which memory block
- **Set:** Determines which set of cache lines
- **Word:** Identifies byte offset within the block
```
Number of sets = Cache blocks ÷ k-way value
Set bits = log₂(number of sets)
Word bits = log₂(bytes per block)
Tag bits = Total address bits - Set bits - Word bits
```

## ⚠️ Error Handling

The program validates inputs and terminates with descriptive messages for:

- **Non-power-of-2 values** for cache blocks, bytes, or k-way
```
  Cache Blocks is not in Base 2. Program terminated
```

- **Invalid hexadecimal addresses**
```
  Memory address is not in Base 16. Program terminated
```

- **Address exceeding memory size**
```
  Size of address is larger than main memory size. Program terminated
```

## 🏗️ Project Structure
```
cache-calculator/
├── Main.java              # Entry point, handles I/O and validation
├── CacheCalculator.java   # Core calculation logic for all mapping strategies
└── README.md             # Project documentation
```

## 🧪 Test Cases

### Test Case 1
```
Main memory: 1 × 2^20 (1 MB)
Cache blocks: 32
Bytes per block: 16
k-way: 4
Address: 0x326A0
```

### Test Case 2
```
Main memory: 2 × 2^20 (2 MB)
Cache blocks: 32
Bytes per block: 16
k-way: 4
Address: 0x326A0
```

## 📚 Learning Objectives

This project demonstrates:
- Cache memory organization and addressing
- Bit manipulation and binary operations
- Trade-offs between different cache mapping strategies
- The impact of cache design on performance

## 🔧 Technical Implementation

### Key Algorithms

**Power-of-2 Check:**
```java
private static boolean isPowerOfTwo(int n) {
    return n > 0 && (n & (n - 1)) == 0;
}
```

**Hex Validation:**
```java
private static boolean isValidHex(String hex) {
    String cleaned = hex.replaceFirst("^0x", "");
    return cleaned.matches("(?i)[0-9A-F]+");
}
```

**Bit Calculation:**
```java
int wordBits = (int)(Math.log(bytesPerBlock) / Math.log(2));
int lineBits = (int)(Math.log(cacheBlocks) / Math.log(2));
int tagBits = totalAddressBits - lineBits - wordBits;
```

## 🎓 Course Context

**Course:** CS 312 - Computer Architecture  
**Institution:** Central Washington University  
**Lab:** Cache Calculator (Lab 3)

## 👨‍💻 Author

**Sway**  
Computer Science Major | Economics Minor  
Central Washington University | Class of 2027

## 📝 License

This project is part of academic coursework at Central Washington University.

## 🙏 Acknowledgments

- Computer Architecture course materials
- CS 311 (Computer Architecture/Assembly) prerequisite knowledge
- Cache memory hierarchy concepts from modern processor design

---

*Built with ☕ and a deep appreciation for how CPUs actually work*
