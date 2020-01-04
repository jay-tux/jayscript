# Packages for JayScript
Currently, all packages are hard-coded and need to be imported/enabled with the ``sys`` command.

### Package math
**Importing**  
```
sys math
```
Math contains four new commands:
  1) ``math_set``
  1) ``math_print``
  1) ``mathf_set``
  1) ``mathf_print``  
The ``mathf_`` versions do the same as their ``math_`` counterpart, but whereas ``math_`` works with integers, ``mathf_`` works with floats.
That being said, only the ``math_`` will be explained, since the ``mathf_``'s are the same.

#### Command ``math_set``
**Usage**  
```
math_set <varname> <expr>
```
Sets the variable ``<varname>`` (which should be an int) to the result of the expression ``<expr>``.

**Throws**  
``Syntax error``	-> ``<expr>`` is empty  
``Expression error``	-> ``<expr>`` isn't formatted correctly  
``Expression error``	-> one of the parts of ``<expr>`` is not an operator, int or variable  

#### Command ``math_print``
**Usage**
```
math_print <expr>
```
Prints the result of the expression ``<expr>`` to System.out.

**Throws**     
``Syntax error``        -> ``<expr>`` is empty  
``Expression error``    -> ``<expr>`` isn't formatted correctly  
``Expression error``    -> one of the parts of ``<expr>`` is not an operator, int or variable  

#### Expressions
Each part of an expression is separated from the others by a space (' '). The possible parts are:
  1) An operator: ``+`` for addition, ``-`` for subtraction, ``*`` for muliplication or ``/`` for division
  1) Braces: ``(`` or ``)``, to force an order of execution
  1) A variable name
  1) An integer (or float) constant  

The normal order of execution is:
  1) Everything between braces (in the same order)
  1) Multiplication/Division
  1) Addition/Subtraction  

Calculation of the result is done in the following steps:
  1) Convert the infix expression to postfix
  1) Convert constants to their numeric value
  1) Convert variables to their numeric value
  1) Evaluate the postfix

### Package io
**Importing**
```
sys io
```
IO contains three new commands:
  1) ``io_create``  
  1) ``io_read``  
  1) ``io_write``    
All of these commands operate on files.

#### Command ``io_create``
**Usage**
```
io_create <filepath>
```
Creates a new file or directory at ``<filepath>``.

**Throws**  
``Syntax error``	-> ``io_create`` requires exactly one argument  
``IO error``		-> the file/directory already exists  
``IO error``		-> something else went wrong while creating the file (permissions, ...)  

#### Command ``io_read``
**Usage**
```
io_read <filepath> <out>
```
Reads the data from the file ``<filepath>`` into the (string) variable ``<out>``.

**Throws**  
``Syntax error``	-> ``io_read`` requires exactly two arguments  
``IO error``		-> ``<filepath>`` doesn't exist  
``IO error``		-> ``<filepath>`` points to a directory  
``IO error``		-> something else went wrong while attempting to read  

#### Command ``io_write``
**Usage**
```
io_write <filepath> <data>
```
Writes the contents of (the string) ``<data>`` to the file ``<filepath>``.

**Throws**  
``Syntax error``        -> ``io_write`` requires exactly two arguments  
``IO error``            -> ``<filepath>`` doesn't exist   
``IO error``            -> ``<filepath>`` points to a directory  
``IO error``            -> something else went wrong while attempting to write  
