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

### Package coll
**Importing**
```
sys coll
```
Coll contains nine new commands:  
  1) ``coll_create``  
  1) ``coll_add``  
  1) ``coll_prnt``  
  1) ``coll_rm``  
  1) ``coll_iter``  
  1) ``coll_upd``  
  1) ``coll_ins``  
  1) ``coll_get``  
  1) ``coll_fromstr``  
All of these new commands operate on lists (Java's ArrayLists to be specific). All indexes are zero-based.

#### Command ``coll_create``
**Usage**
```
coll_create <type> <name>
```
Create a new collection variable named ``<name>`` that can hold ``<type>`` values.  

**Throws**  
``Syntax error``	-> you need to pass exactly two arguments  

#### Command ``coll_add``
**Usage**
```
coll_add <collection> <value or variable>
```
Appends ``<value or variable>`` to the end of ``<collection>``. If ``<value>`` is of another type than ``<collection>``'s content, nothing happens.  

**Throws**  
``Syntax error``	-> ``coll_add`` requires exactly two arguments  
``Type error``		-> ``<collection>`` is not a collection (not of type ``__coll__``)  
``Name error``		-> ``<collection>`` doesn't exist  

#### Command ``coll_prnt``
**Usage**
```
coll_prnt <collection>
```
Prints ``<collection>`` to stdout formatted as ``[ val1 - val2 - ... - valn - NULL ]``.  

**Throws**  
``Syntax error``	-> ``coll_prnt`` requires exactly one argument  
``Type error``		-> ``<collection>`` is not a collection  
``Name error``		-> ``<collection>`` doesn't exist  

#### Command ``coll_rm``
**Usage**
```
coll_rm <collection> <index>
```
Removes the ``<index>``-th item of ``<collection>``. ``<index>`` is either an int literal or an int variable.  

**Throws**  
``Syntax error``	-> you need to pass exactly two arguments to ``coll_rm``  
``Type error``		-> ``<collection>`` is not a ``__coll__``  
``Type error``		-> ``<index>`` is a non-int variable  
``Type error``		-> ``<index>`` is a non-int literal  
``Name error``		-> ``<collection>`` doesn't exist  
``Collection error``	-> ``<index>`` is out of range  

#### Command ``coll_iter``
**Usage**
```
coll_iter <collection> <function>
```
Passes each element of ``<collection>`` to ``<function>``. ``<function>`` needs to be a Function, not a Routine, and needs to have exactly one argument, of the type specified upon ``<collection>``'s creation.  

**Throws**  
``Syntax error``	-> you need to pass exactly two arguments to ``coll_iter``  
``Type error``		-> ``<collection>`` is not a collection  
``Name error``		-> ``<collection>`` doesn't exist  
``Collection error``	-> ``<function>`` is a routine  
``Syntax error``	-> ``<function>`` needs to take exactly one argument  
``Type error``		-> type mismatch between ``<function>``'s argument type and ``<collection>``'s content type  

#### Command ``coll_upd``
**Usage**
```
coll_upd <collection> <index> <newVal>
```
Replaces the ``<index>``-th item in ``<collection>`` with ``<newVal>``. If ``<value>`` is of another type than ``<collection>``'s content, nothing happens.  

**Throws**  
``Syntax error``	-> you need to pass exactly two arguments to ``coll_upd``  
``Type error``		-> ``<collection>`` is not a collection  
``Type error``		-> ``<index>`` is a non-int variable  
``Type error``		-> ``<index>`` is a non-int literal  
``Name error``		-> ``<collection>`` doesn't exist  
``Collection error``	-> ``<index>`` is out of range  

#### Command ``coll_ins``
**Usage**
```
coll_ins <collection> <index> <value>
```
Inserts ``<value>`` (either a variable or a literal) into ``<collection>`` as ``<index>``-th element.  

**Throws**  
``Syntax error``	-> you need to pass exactly three arguments to ``coll_ins``  
``Type error``		-> ``<collection>`` is not a collection  
``Type error``		-> ``<index>`` is a non-int variable  
``Type error``		-> ``<index>`` is a non-int literal  
``Name error``		-> ``<collection>`` doesn't exist  
``Collection error``	-> ``<index>`` is out of range  

#### Command ``coll_get``
**Usage**
```
coll_get <collection> <index> <out>
```
Copies the ``<index>``-th value of ``<collection>`` into ``<out>``.  

**Throws**  
``Syntax error``	-> you need to pass exactly three arguments to ``coll_get``  
``Name error``		-> ``<out>`` is a nonexistent variable  
``Type error``		-> ``<collection>`` is not a collection  
``Type error``		-> ``<index>`` is a non-int variable  
``Type error``		-> ``<index>`` is a non-int literal  
``Type error``		-> the types of ``<out>`` and ``<collection>``'s content are incompatible  
``Name error``		-> ``<collection>`` doesn't exist  
``Collection error``	-> ``<index>`` is out of range  

#### Command ``coll_fromstr``
**Usage**
```
coll_fromstr <collection> <string>
```
Creates a new collection named ``<collection>`` and fills it with all characters of ``<string>``. ``<collection>``'s content type is char.

**Throws**  
*See coll_create*  
``Syntax error``	-> you need to pass exactly two arguments to ``coll_fromstr``  
``Type error``		-> ``<string>`` is not a string  
