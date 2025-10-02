# Level 1 compiler: CMPT 355, Fall 2025

## Features supported at this level:
- All level 0 features
- Types (`int`, `double`, `string`)
    - Literals for each of these types
- Variable declarations (global & local)
- Function definitions and calls
    - Like Java, functions can return a value (`int`, `double`, or `string`), or nothing (`void`) — if you are not using reference syntax, you can represent these data types however you wish (for example, you could follow Visual Basic and differentiate between functions (declared with `function`), which return values, and subroutines (declared with `sub`) that do not)
    - Return statements
- Implicit conversions (`int`→`double`, anything→`string`)
- String concatenation (with `+`)

## Recommended implementation notes
- This level introduces an intermediate representation in order to support typechecking and declarations. The semantic analysis phase produces an IR from an AST.
- A symbol table is employed to resolve variables and functions.
- "Global" code (outside all functions) is dumped into `main` method. Global variables are implemented as static fields; local variables are JVM locals.
- The only scopes are global (functions and variables) and local (variables). There is no further nesting of scopes. Local variables having the same names as globals shadow the globals.
- Since functions are now in the language, `sqrt` does not any longer need to be a special syntactic form. However, the semantic analysis phase will need to handle it specially.
- `print` and `input` are still special syntactic forms, however:
  - `print` is variadic;
  - the type of `input`'s return value depends on what it is assigned to (in `double x = input("");` it returns a `double`, in `int x = input("");` it returns an `int`). 

## Suggested plan of attack
0. Migrate your code from level 0: copy the grammar over, your AST nodes, `CodeGenerator`, etc. Move everything into the `edu.westminsteru.cmpt355.level1` package/subpackages. (In IntelliJ IDEA you can do this easily by choosing Refactor → Move Class... from the context menu.) Note that **you probably do not want to replace the Main class with your level 0 version** as the one here has stuff you'll need, including an `-ir` flag that prints out the IR (similar to `-ast`).
1. As before, start at the bottom and work your way up: parser rules, then AST nodes. You should be able to use your level 0 grammar and AST nodes as a starting point, adding new parser rules and AST node types for the new language features.
2. You will now need IR nodes as well as AST nodes. The difference between AST and IR nodes can be subtle, but the rules of thumb are that
   1. AST nodes are about what's in the code (syntax), while IR nodes are about what the program will *do* (semantics);
   2. IR nodes know about types (e.g., the `IrExpression` interface should have a `type()` method returning the expression's type), while AST nodes do not.
3. Write code (in the `SemanticAnalyzer` class) to perform *semantic analysis* on your AST resulting in an IR. Semantic analysis is the process of checking that the semantics of the program are internally consistent: for example,
   1. typechecking (making sure the types make sense): we’re not assigning a `string` to an `int` variable, or multiplying a `string` by a double, or returning a `string` from a method of return type `void`;
   2. declaration before use of variables: we are not accessing a variable before it is declared.
4. Implement code generation on your IR. Your `CodeGenerator` class will require some work to get to compile since it's now operating on an IR rather than AST, but you should be able to reuse a lot of the code after suitably adjusted. (You might want to start with a find/replace of `Ast` to `Ir`!)

## Hints and suggestions
- This assignment is provided as an IntelliJ IDEA project. You are *not* required to use IntelliJ and are free to delete the IntelliJ stuff (`level1.iml`, the `.idea` directory) if you wish.
- A `Main` class (`edu.westminsteru.cmpt355.level1.Main`) is already essentially written for you. You just need to fill in code in the `parse()` method to invoke your lexer and parser.
- The same `AstNode`, `AstProgram`, `AstStatement`, and `AstExpression` from level 0 are provided for you again. You can just replace them with your versions from level 0 if you wish.
- A skeleton `SemanticAnalyzer` class is provided for you having a `IrProgram analyze(AstProgram) throws SemanticAnalysisException` method. This method should map the AST to an IR, doing necessary semantic analysis (primarily typechecking) along the way.
- The `CodeGenerator` class now takes an `IrProgram` rather than an `AstProgram`. You will need to do a lot of adjustments to this class, but you should be able to reuse most of the code in it.
- Other structure provided for you:
    - `Variable`, `GlobalVariable`, `LocalVariable`, and `Function` (in `edu.westminsteru.cmpt355.level1.lang`): types representing global/local variables and functions. Please note that these are not IR nodes and hence should not contain IR nodes: they contain just the needed information to access/call a variable/function (intended for inclusion within relevant IR nodes).
     - `Type` and `ReturnType` (in `edu.westminsteru.cmpt355.level1.lang`): the types available in the language at level 1 (`ReturnType` is intended for function return types, consisting of the regular `Type`s plus `void`).
   - `SymbolTable`, `GlobalSymbolTable`, and `LocalSymbolTable`: skeletons of symbol tables that will need to be filled in. Your `SemanticAnalyzer` can use `defineVariable()`/`defineFunction()` when a variable/function is declared/defined and `resolveVariable()`/`resolveFunction()` when one is accessed/called.
- Searching the project for `Not implemented` will show you places that I've left stubs that need to be filled in.

## Running the compiler tester
You can use the provided compiler-tester .jar to automate the process of testing your compiler.
1. Make sure that all the following are in your `CLASSPATH`:
  - `lib/antlr-4.13.2-complete.jar`,
  - `lib/jasm.jar`
  - `lib/compiler-tester-0.2.jar`
  - the compiled (.class) files of your compiler (in IntelliJ IDEA, by default these would be in `out/production/level1`)
2. If necessary, modify the `test-config` file so that it has the correct name of your compiler's main class.
3. Run the `edu.westminsteru.cmpt355.compiler_tester.Main` class, naming one or more test files or directories. To run all tests, you can just name the `test` directory.
