PORT=56187
CFLAGS = -DPORT=\$(PORT) -Wall -g -D_GUN_SOURCE -std=c99 -Werror

mismatch_server: 
	gcc ${CFLAGS} questions.c qtree.c mismatch_server.c