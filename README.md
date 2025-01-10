## Getting Started

This project requires Java 1.8. Please ensure you have Java 1.8 installed on your system.

## Compilation

To compile the project without an IDE, navigate to the `src` directory and run the following command:

```sh
javac -source 1.8 -target 1.8 -d ../bin Conteneurs/*.java Graphes/GrapheListe/*.java Graphes/GrapheMatrice/*.java Utils/*.java KruskalM.java KruskalL.java
```

This will compile the source files and place the compiled output in the `bin` directory.

## Execution

After compilation, navigate to the `bin` directory to execute the program. You can run the program with either `KruskalL` or `KruskalM` and one or two parameters. The first parameter is the name of the source file, and the second parameter is the name of the save file. These files can be found in the `res/f_in` and `res/f_out` directories, respectively.

Example usage:

```sh
cd ../bin
java KruskalL sourceFileName saveFileName
java KruskalM sourceFileName saveFileName
```

Replace `sourceFileName` with the name of the input file (without the `.txt` extension) located in `res/f_in`, and `saveFileName` with the name of the output file (without the `.txt` extension) to be saved in `res/f_out`.

## Folder Structure

The workspace contains the following folders:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies
- `bin`: the folder where compiled output files will be generated