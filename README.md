# Level 0 compiler: CMPT 355, Fall 2025

## Features supported at this level:
- Variables (all of type `double`)
  - No declarations: any mention of a variable "declares" it
  - Assignments (`x = ...`)
- Print statements: `print("There are ", 4, " lights");`
- Input statements: `x = input("Enter a number: ");`
- Arithmetic expressions:
  - `+` `-` `*` `/` `%`
  - `( )`
  - `sqrt()`
- Line comments: `// ...`
- Block comments: `/* ... */`
- Assembly comments: `/// ...`
  - Assembly comments become comments in the generated assembly code to aid in debugging the compiler

## Recommended implementation notes
- No semantic analysis or intermediate representation (the only type is `double` so typechecking isn't really necessary): code generated directly from the AST
- All program code is dumped into the `main` method.
- All variables are static fields of the class.
- No general concept of a function/method is needed. `print`, `input`, and `sqrt` are special syntactic forms.

## Suggested plan of attack
1. Working closely with your partner, develop a grammar covering the features mentioned above. This is a good time to make sure that you agree on the details of your language's syntax.
   1. It may be tempting to add in parser rules for other features too — variable declarations, if statements, etc. — but please restrain yourself: this will just make your job harder in the long run! Stick to just the level 0 features for now.
2. Implement an ANTLR grammar for your language and test it (looking at parse trees) to make sure that your grammar works.
   1. Make sure your lexer and parser are being generated into an appropriate package (perhaps `edu.westminsteru.cmpt355.level0.parse`). If using IntelliJ, you can specify a package by right-clicking on the grammar and choosing Configure ANTLR...
3. Make needed AST node types. (The grammar is often helpful in deciding what node types are needed.) Add semantic actions to your grammar to generate AST nodes.
4. At this point you should be able to run the `Main` class. Give it some input files and use the `-ast` flag to have it save ASTs; examine these and make sure they are as expected.
5. Finish by filling in the `generateCode()` method in `CodeGenerator`. You'll need a "preamble" (`.class`, `.source`, etc.), `.field` directives for the variables, and a `main` method, which will hold the actual code of your program.
   1. Remember that you can always write Java code, compile it, and then `javap -c` it to see what instructions the Java compiler uses to do something!
6. Run lots of test cases through your compiler (I will provide some but you should test a bunch of your own as well). Make sure you are hitting all the different AST node types in your tests!

## Hints and suggestions
- This assignment is provided as an IntelliJ IDEA project. You are *not* required to use IntelliJ and are free to delete the IntelliJ stuff (`level0.iml`, the `.idea` directory) if you wish.
- A `Main` class (`edu.westminsteru.cmpt355.level0.Main`) is already essentially written for you. You just need to fill in code in the `parse()` method to invoke your lexer and parser.
- Already started for you is the `AstNode` interface (representing an AST node, of course) along with `AstProgram`, `AstStatement`, and `AstExpression`. intended to be the root node of the AST. I *strongly* recommend that you name AST node classes like `AstXxx`. (In later levels, when we have both an AST and IR, we will have nodes named `IrXxx` to differentiate from AST nodes.)
  - `AstProgram`, a record, is intended to represent the program as a whole (the root node of the AST).
  - `AstStatement`, a sealed interface, which AST nodes representing statements/"lines" of code should implement.
  - `AstExpression`, another sealed interface, to be implemented by AST nodes representing expressions (i.e., ones that will result in a value).
  - For example, you are likely to have an `AstAssignment` (or whatever you wish to call it) record that implements `AstStatement`, and `AstAdd` implementing `AstExpression`. All or nearly all the AST nodes you make at this level should implement one of these two interfaces.
- The `CodeGenerator` class is already started for you; you just need to fill in code in its `generateCode()` method. 
- Line and block comments can be dropped by the lexer (` -> skip`). Assembly comments (`/// ...`) need to go into the AST since they should be included in the jasm code. (They don't do anything in the code, but can be helpful to you as you debug your compiler: by putting assembly comments in the code being compiled, you can make "guideposts" showing which lines of assembly resulted from which lines of source code.)

