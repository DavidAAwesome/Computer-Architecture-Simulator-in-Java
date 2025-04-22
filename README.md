# Computer-Architecture-Simulator-in-Java

The **Computer Architecture Simulator** is a Java-based project that emulates a simplified computer architecture along with an assembler. It replicates essential CPU operations such as arithmetic calculations, Boolean logic, and bit manipulation. The system also features an assembler that converts high-level instructions into 32-bit binary representations and loads them into a simulated memory space for execution.

## Features

### 🔢 Arithmetic Logic Unit (ALU)
- Performs core arithmetic operations: addition, subtraction, multiplication, and bit shifting.
- Supports Boolean logic: AND, OR, NOT, XOR, and comparison operators (e.g., equal to, greater than).

### 🛠️ Assembler
- **Tokenization**: Parses lines of code into tokens (operations, registers, values).
- **Binary Conversion**: Converts tokenized instructions into 32-bit binary words to be loaded into memory.

### ⚙️ Binary & Base Conversions
- Handles conversion between base-10 and base-2 number formats.

### 🧠 Simulated Memory
- Loads and stores binary instructions in a virtual memory array for simulated execution.

### ✅ JUnit Testing
- Includes unit tests to verify the correctness of arithmetic operations, instruction parsing, and memory loading.
