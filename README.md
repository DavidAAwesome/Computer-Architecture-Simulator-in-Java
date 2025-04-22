# Computer-Architecture-Simulator-in-Java

The **Computer Architecture Simulator** is a Java-based project that emulates a simplified computer architecture along with an assembler. It replicates essential CPU operations such as arithmetic calculations, Boolean logic, and bit manipulation. The system also features an assembler that converts high-level instructions into 32-bit binary representations and loads them into a simulated memory space for execution.

  ## 🧠 Features

### 🧮 Arithmetic Logic Unit (ALU)
- Supports integer operations: **add**, **subtract**, **multiply**
- Logical operations: **AND**, **OR**, **NOT**, **XOR**
- Bitwise shifts: **left**, **right**
- Comparison operations: **greater than**, **less than**, **equals**, etc.

### 🛠 Assembler and Instruction Parser
- Parses a custom assembly language using `Lexer` and `Parser`
- Translates assembly into 32-bit binary machine code
- Supports various instruction types: `COPY`, `MATH`, `BRANCH`, `CALL`, `PUSH`, `POP`, `LOAD`, `STORE`, `HALT`, etc.

### 🧾 Memory Management
- Simulates **main memory** (DRAM) and **multi-level caching** (`InstructionCache` and `L2Cache`)
- Handles instruction loading and data storage
- Simulated `Word` and `Bit` types to represent binary values

### 🧪 Testing
- Includes **JUnit test cases** for most components: `Bit`, `Word`, `ALU`, `MainMemory`, `Processor`, and assembler functionality

### 🖥 Processor Simulation
- Implements a basic CPU pipeline with `fetch`, `decode`, `execute`, and `store` stages
- Supports stack operations via a `StackPointer`
- Maintains internal registers and handles control flow

