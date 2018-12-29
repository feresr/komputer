// push CONSTANT 6
@6
D=A
@SP
A=M
M=D
@SP
M=M+1
// push CONSTANT 5
@5
D=A
@SP
A=M
M=D
@SP
M=M+1
// and
@SP
AM=M-1
D=M
A=A-1
D=M&D
@SP
A=M-1
M=D
