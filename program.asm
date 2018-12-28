// push CONSTANT 1
@1
D=A
@SP
A=M
M=D
@SP
M=M+1
// push CONSTANT 1
@1
D=A
@SP
A=M
M=D
@SP
M=M+1
// eq
@SP
AM=M-1
D=M
A=A-1
D=M-D
@TRUE.1
D;JEQ
@SP
A=M-1
M=0
@END.1
0;JMP
(TRUE.1)
@SP
A=M-1
M=-1
(END.1)
// push CONSTANT 2
@2
D=A
@SP
A=M
M=D
@SP
M=M+1
// push CONSTANT 6
@6
D=A
@SP
A=M
M=D
@SP
M=M+1
// eq
@SP
AM=M-1
D=M
A=A-1
D=M-D
@TRUE.2
D;JEQ
@SP
A=M-1
M=0
@END.2
0;JMP
(TRUE.2)
@SP
A=M-1
M=-1
(END.2)
