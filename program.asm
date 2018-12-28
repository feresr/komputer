// push CONSTANT 111
@111
D=A
@SP
A=M
M=D
@SP
M=M+1
// push CONSTANT 333
@333
D=A
@SP
A=M
M=D
@SP
M=M+1
// push CONSTANT 888
@888
D=A
@SP
A=M
M=D
@SP
M=M+1
// pop STATIC 8
@SP
AM=M-1
D=M
@program.8
M=D
// pop STATIC 3
@SP
AM=M-1
D=M
@program.3
M=D
// pop STATIC 1
@SP
AM=M-1
D=M
@program.1
M=D
// push STATIC 3
@program.3
D=M
@SP
A=M
M=D
@SP
M=M+1
// push STATIC 1
@program.1
D=M
@SP
A=M
M=D
@SP
M=M+1
// sub
@SP
AM=M-1
D=M
A=A-1
M=M-D
// push STATIC 8
@program.8
D=M
@SP
A=M
M=D
@SP
M=M+1
// add
@SP
AM=M-1
D=M
A=A-1
M=M+D
