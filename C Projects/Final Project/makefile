all: sudoku-console
CC = gcc
OBJS = main.o Game.o ILPSolver.o Stack.o ParserAux.o Parser.o MainAux.o Lists.o FILEInputOutput.o EXBackTracking.o INFO.o


EXEC = sudoku-console
COMP_FLAGS = -ansi -Wall -Wextra \
-Werror -pedantic-errors

GUROBI_COMP = -I/usr/local/lib/gurobi563/include
GUROBI_LIB = -L/usr/local/lib/gurobi563/lib -lgurobi56

$(EXEC): $(OBJS)
	$(CC) $(OBJS) $(GUROBI_LIB) -o $@ -lm
main.o: main.c Game.h ILPSolver.h
	$(CC) $(COMP_FLAGS) -c $*.c
Game.o: Game.c Game.h
	$(CC) $(COMP_FLAG) -c $*.c
ILPSolver.o: ILPSolver.c ILPSolver.h
	$(CC) $(COMP_FLAG) $(GUROBI_COMP)  -c $*.c
Stack.o: Stack.c Stack.h
	$(CC) $(COMP_FLAG) -c $*.c
ParserAux.o: ParserAux.c ParserAux.h INFO.h
	$(CC) $(COMP_FLAG) -c $*.c
Parser.o: Parser.c Parser.h ParserAux.h 
	$(CC) $(COMP_FLAG) -c $*.c
MainAux.o: MainAux.c MainAux.h Parser.h ILPSolver.h Game.h Lists.h EXBackTracking.h FILEInputOutput.h 
	$(CC) $(COMP_FLAG) -c $*.c
Lists.o: Lists.c Lists.h Game.h
	$(CC) $(COMP_FLAG) -c $*.c

FILEInputOutput.o: FILEInputOutput.c FILEInputOutput.h Game.h
	$(CC) $(COMP_FLAG) -c $*.c
EXBackTracking.o: EXBackTracking.c EXBackTracking.h Stack.c
	$(CC) $(COMP_FLAG) -c $*.c
INFO.o: INFO.c INFO.h
	$(CC) $(COMP_FLAG) -c $*.c





clean:
	rm -f *.o $(EXEC)
