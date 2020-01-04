# JayScript
### A small, and actually quite useless, programming language, written in Java
### Project made in Eclipse (on Arch Linux)

Main method is in the src/Interpreter.java file.
To interprete a ``*.jay`` file, just run a compiled version with the file as argument

## Programming in JayScript...
...can be done in a simple text editor. It's the text that matters, after all.

### Examples
The ``examples`` directory contains JayScript examples.
Current examples:
  1) Fibonacci (recursive)  

### How to?
Each line in JayScript is a command or comment.
Each command starts with a keyword, then a space (' '), and then it's arguments, also separated by spaces.
For strings, multiple words can be grouped by enclosing them in double quotes (").
Indentation doesn't actually matter, only the keywords.

### Comments
Comments (one-line), start with a ``~``.
Comments cannot be started mid-line, only at the beginning of a line.

### Builtin types
JayScript provides four basic data types:
  1) Integer (declared as int)
  1) Character (declared as char)
  1) Floating point number (declared as float)
  1) String (declared as string)  
You can create your own types using the Type command.

### Commands
Each command has, in the source files, its own class, which extends the Command class.
Commands are parsed by the Statement class, which uses a ``switch-case`` loop to detect which command to call.
After the system has decided which command to call, its ``.execute(State program)`` method is executed.
All errors are thrown as ``JayInterpreterException``s, which are printed to the screen via ``System.err``.
Next up are all commands, listed alphabetically (some commands are not written, but have a single symbol).
Commands are always written in lowercase.

#### ArgCall
**Usage**  
```
argcall <function> <arg0> <arg1> ...
```
ArgCall calls a function (with arguments). The first argument is the name of the function, the others are its arguments.
For more on functions and routines, see their respective parts.

**Throws**  
``Syntax error``	-> you need to give at least one argument to ArgCall  
``Name error``		-> the function needs to exist  
``Syntax error``	-> you can't give a routine to ArgCall  

#### Call
**Usage**  
``
call <routine>
``
Call calls a routine (without arguments). Its only argument is the routine it should call.
For more on functions and routines, see their respective parts.

**Throws**  
``Syntax error``        -> you need to give exactly one argument to Call  
``Name error``          -> the function needs to exist  

#### Convert
**Usage**  
```
convert <orig type> <new type> <orig var> <new var>
```
Convert attempts to convert the variable ``<orig var>`` from its type (``<orig type>``) to a new type (``<new type>``), which is stored in another variable (``<new var>``).
See IfConv for safe conversions.

**Throws**  
``Syntax error``	-> you need to give exactly four arguments to Convert  
``Argument error``	-> ``<orig type>`` or ``<new type>`` is not a builtin type  
``Type error``		-> variables are not of the type specified  
``Type error``		-> can't convert from ``<orig type>`` to ``<new type>``  
``Name error``		-> one of the variables (or both) does not exist  

#### Declare
**Usage** 
```
declare <type> <identifier>
```
Declares a new variable of type ``<type>``. Variables have to be declared before they can be set.
Re-declaring a variable causes an error.
See Set or Keep for setting variables.

**Throws**  
``Syntax error``        -> you need to give exactly two arguments to Declare  

#### Dump
**Usage**  
```
__debug::dump__
```
Dump gives an overview of stored flags, functions/routines, variables and enabled options.
See Declare, Flag, Function, Routine and Opt for more info.
Dump can only be called/used when the debug flag is enabled.

**Throws**  
``Syntax error``	-> trying to call Dump without enabling debug  

#### Else
**Usage**  
```
else <other command> <other arg0> <other arg> ...
```
Else, if right after an If or IfConv statement, executes only if the If(Conv) hasn't redirected the flow of control.
When Else executes, it creates another statement substituting itself.
See If, IfConv and Jump.

**Throws**  
``Syntax error``	-> trying to call Else not right after an If(Conv)  

#### Exit
**Usage**  
```
exit
```
Exit stops the program, and should be the last command called.
If the program reaches EOF without an exit, a ``Syntax error`` is thrown.

**Throws**  
``Syntax error``	-> Exit requires exactly zero arguments  

#### Function
**Usage**  
```
function <return type> <name> <arg0 type>:<arg0 name> <arg1 type>:<arg1 name> ...
```
Function creates a named (stored) function from the position of its declaration until it reaches a line with ``end <name>``.
You can return values (exactly one) from a function, and you can give arguments to a function.
Once invoked (by ArgCall), it checks if all arguments are set, if all types are correct and creates a substate in which it executes.
You can use flow-of-control in functions, including jumps (only within that function, relative to its first command) and if's.
See Routine, ArgCall and Keep.

**Throws**  
*Upon declaration*  
``Syntax error``	-> less than two arguments given  
``Syntax error``	-> defined argument is not of the form ``<type>:<name>``  
``Type error``		-> argument type is unknown  
*Upon invocation*  
``Syntax error``	-> amount of argument passed is not correct  
``Type error``		-> the type of one of the arguments is incorrect  

#### If
**Usage**  
```
if <var1> <var2> <jump>
```
If checks whether the values of ``<var1>`` and ``<var2>`` are equal. If this is the case, it jumps to the line specified by ``<jump>``.
See Jump, Flag, Else, Import.

**Throws**  
``Argument error``	-> If requires exactly three arguments  
``Type error``		-> ``<jump>`` is neither a flag nor an int  
``Type error``		-> the variables are not of the same type  

#### IfConv
**Usage**  
```
ifconv <new type> <var> <jump>
```
Tests whether a variable can be converted to another type. If so, redirects the flow of control.
See If.

**Throws**  
``Type error``          -> ``<jump>`` is neither a flag nor an int  

#### Jump
**Usage**  
```
jump <lineno>
```
Jumps to line ``<lineno>``. Line numbers are altered by imports.
```
jump <flag>
```
Jumps to flag ``<flag>``. Jumping to flags in functions is undefined behaviour.

**Throws**  
``Argument error``      -> Jump requires exactly one argument  
``Type error``          -> ``<lineno/flag>`` is neither a flag nor an int  

### Keep
**Usage**  
```
keep <var> <function> <arg0> <arg1> ...
```
Performs an ArgCall, but instead of discarding any return values, stores them in ``<var>``.
See ArgCall, Function

**Throws**  
``Syntax error``        -> you need to give at least one argument to ArgCall  
``Name error``          -> the function needs to exist  
``Syntax error``        -> you can't give a routine to ArgCall  
``Type error``		-> the type of the variable doesn't match the return type of the function  

#### Not
**Usage**  
```
if <var1> <var2> <jump>
```
If checks whether the values of ``<var1>`` and ``<var2>`` are not equal. If this is the case, it jumps to the line specified by ``<jump>``.
See Jump, Flag, Else, Import.

**Throws**  
``Argument error``      -> Not requires exactly three arguments  
``Type error``          -> ``<jump>`` is neither a flag nor an int  
``Type error``          -> the variables are not of the same type  

#### Opt
**Usage**  
```
opt <option>
```
Enables an option.
Causes an error if you enable an option that's already been enabled.

**Current options**  
  1) debug

**Throws**  
``Argument error``	-> you need to give Opt exactly one argument  

#### Print
**Usage**  
```
print <arg0> <arg1> ...
```
Prints all arguments as literals to System.out. To pass an argument as a variable, use ``&varname``.
See Println

**Throws**  
Nothing (the system/State may throw for nonexistent variables)  

#### Println
**Usage**  
```
println <arg0> <arg1> ...
```
Prints its arguments, followed by a newline.
See Print.

**Throws**  
Nothing  

#### Read
**Usage**  
```
read <varname>
```
Reads the next line of input from System.in into ``<varname>``.
The given variable should be a string.

**Throws**  
``Argument error``	-> Read requires exactly one argument.  

#### Return
**Usage**  
```
return <varname>
```
Sets the return value for a function. Should only be used inside a function, otherwise causes undefined behaviour.
After setting the value, returns control to the caller.
See Function.

**Throws**  
``Syntax error``	-> Return expects exactly one argument  

#### Routine
**Usage**  
```
routine <name>
```
Upon creation, Routine reads until it encounters ``end <name>``. All lines in between are stored as a procedure.
Routine creates, upon invocation, a substate in which commands are executed.
Routines can't have argument, nor a return type, for those, see Function.
Variables aren't passed between the parent state and the substate.
See Function, Call.

**Throws**  
``Syntax error``	-> you need to give Routine exactly one argument  

#### Set
**Usage**  
```
set <varname> <newval>
```
Sets a given variable to a new value.
Fails (throws an error) if the variable isn't declared or the value isn't compatible with the variable's type.
See Declare.

**Throws**  
``Argument error``	-> you need to give Set exactly two arguments (strings can contain spaces as long as they're enclosed in double quotes (")  

#### Sys
**Usage**  
```
sys <package>
```
Imports a system package. Packages are described in packages.md.
All packages available:
  1) math
  1) io
  1) coll (in development)

**Throws**  
*Sys call*  
``Syntax error``	-> Sys requires exactly one argument  
``Name error``		-> the package specified doesn't exist  
``Syntax error``	-> the package specified is not imported  
*Package usage*  
``Name error``		-> the package doesn't exist  
``Package error``	-> ``<command>`` requires a package to be imported  

#### Type
**Usage**  
```
type <name> <field0 type>:<field0 name> <field1 type>:<field1 name> ...
```
Creates a user-defined type.
Each field should be of an existing type (builtin or custom).
Once a type is defined, instances of it can be created.
See Declare.

**Throws**  
``Syntax error``	-> Type requires at least one argument  
``Syntax error``	-> can't define an empty type  
``Syntax error``	-> one of the field definitions isn't declared as ``<field type>:<field name>``  
``Type error``		-> one of the fields is of an unknown type  

### Non-word commands
All non-word commands are parsed before anything else.
This is done in the following order:
  1) Imports (which alter line numbers)
  1) Flags
  1) Comments (which are skipped)

#### Comment
**Usage**  
```
~ <comment>
```
While not exactly a command, the ``~`` at the beginning of a line allows for a one-line comment.

**Throws**  
Nothing  

#### Flag
**Usage**
```
@<flagname>
```
Creates a named flag. The flag name should be exactly one word, but multiple words work as well:
```
~ flag definition
@multi word flag

~ other lines of code

~ jump
jump "multi word flag"
```

**Throws**  
Nothing

#### Import
**Usage**
```
!<filename>
```
Imports another file (which is referenced relative to the current file).
Imported files can't contain other imports (as for now).

**Throws**  
``Import error``	-> the file specified doesn't exist or is a directory  
``Import error``	-> the file is not readable  
