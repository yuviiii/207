#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <stdlib.h>
#include "qtree.h"
#include <stdbool.h>


#ifndef PORT 
  #define PORT 56186
#endif


typedef struct client {
	int fd;
	int *answer;
  int answernumber;
	int state;
	char name [128];
	char buf [30];
	int inbuf;
	struct client *next;
  // QNode *current;
  Node *current;
  bool testdone;
} Client;


Client *clientlist;
QNode *root=NULL;
Node *interests=NULL;

int setup(void) {
  int on = 1, status;
  struct sockaddr_in self;
  int listenfd;
  if ((listenfd = socket(AF_INET, SOCK_STREAM, 0)) == -1) {
    perror("socket");
    exit(1);
  }

  // Make sure we can reuse the port immediately after the
  // server terminates.
  status = setsockopt(listenfd, SOL_SOCKET, SO_REUSEADDR,
                      (const char *) &on, sizeof(on));
  if(status == -1) {
    perror("setsockopt -- REUSEADDR");
  }

  self.sin_family = AF_INET;
  self.sin_addr.s_addr = INADDR_ANY;
  self.sin_port = htons(PORT);
  memset(&self.sin_zero, 0, sizeof(self.sin_zero));  // Initialize sin_zero to 0

  printf("Listening on %d\n", PORT);

  if (bind(listenfd, (struct sockaddr *)&self, sizeof(self)) == -1) {
    perror("bind"); // probably means port is in use
    exit(1);
  }

  if (listen(listenfd, 5) == -1) {
    perror("listen");
    exit(1);
  }
  return listenfd;
}


int find_network_newline (char *buf, int inbuf) {
	int i;
	for (i = 0; i < inbuf - 1; i++)
		if ((buf[i] == '\r') && (buf[i+1] == '\n'))
			return i;
	return -1;
}

void add_client(int c_fd){
	Client *new_client = NULL;
	new_client = (Client *)malloc(sizeof(Client));
	new_client->fd = c_fd;
	new_client->next = clientlist;
	clientlist = new_client;
  clientlist->answer = malloc(sizeof(int*));
  clientlist->answernumber=0;
  clientlist->current=interests;
  clientlist->testdone = false;
}

void add_to_group(QNode *current, char *user_name, int *arg){
    int str_len;
    int index_of_answer;
    int answer;
    Node *cur_node;
    //construct user node;
    str_len = strlen(user_name);
    Node *user=NULL;
    user = (Node *) calloc (1, sizeof(Node));
    user->str = (char *) calloc (str_len +1, sizeof(char ));
    strncpy (user->str, user_name, str_len);
    user->str [str_len] = '\0';
    //attach to tree.
    index_of_answer = 0;
    while (current->node_type == 0) {
        answer = arg[index_of_answer];
        current = find_user_category_in_tree(current, answer);
        index_of_answer +=1;
    }
    answer = arg[index_of_answer];
    if(current->children[answer].fchild == NULL){
        current->children[answer].fchild = user;
    }else{
        cur_node = current->children[answer].fchild;
        while (cur_node->next != NULL){
            cur_node = cur_node->next;
        }
        cur_node->next = user;
    }
    
}

Node *get_oppsite_user(QNode *current,int *arg){
    int answer;
    int i;
    for (i = 0; i < 3; i++){
        if (arg[i] == 1){
            answer = 0;
        } else {
            answer = 1;
        }
        printf("%d\n",current->node_type);
        current=find_user_category_in_tree(current,answer);
    }
    if (arg[i] == 1){
        answer = 0;
    }else if (arg[i] == 0){
        answer = 1;
    }
    printf("%d\n",current->node_type);
    return current->children[answer].fchild;
}

void execute_command(int fd, char *command){
  char *dotest = "do_test";
  char *getall = "get_all";
  char *quit = "quit";

    // when command is do_test.
    if (strcmp(command, dotest)==0){
      char *askq = "Collecting your interests\r\nDo you like ";
      write(fd, askq, strlen(askq));
      write(fd, clientlist->current->str, strlen(clientlist->current->str));
      write(fd, "?\r\n", sizeof(char)*3);
    }

    // when command is yes/no.
    if (command[0]=='y' || command[0]=='Y'){
      clientlist->answer[clientlist->answernumber] = 1;
      clientlist->answernumber += 1;
      if (clientlist->current->next != NULL){
        clientlist->current=clientlist->current->next;
        write(fd, "Do you like ", sizeof(char)*12);
        write(fd, clientlist->current->str, strlen(clientlist->current->str));
        write(fd, "?\r\n", sizeof(char)*3);
      }
      else{
        clientlist->testdone = true;
        add_to_group(root, clientlist->name, clientlist->answer);
        write(fd, "Test complete.\r\n", sizeof(char)*16);
      }
    }
    if (command[0]=='n' || command[0]=='N'){
      clientlist->answer[clientlist->answernumber] = 0;
      clientlist->answernumber += 1;
      if (clientlist->current->next != NULL){
        clientlist->current=clientlist->current->next;
        write(fd, "Do you like ", sizeof(char)*12);
        write(fd, clientlist->current->str, strlen(clientlist->current->str));
        write(fd, "?\r\n", sizeof(char)*3);
      }
      else{
        clientlist->testdone = true;
        add_to_group(root, clientlist->name, clientlist->answer);
        write(fd, "Test complete.\r\n", sizeof(char)*16);
      }
    } 

    // when command is get_all.
    if (strcmp(command, getall)==0){
      if (clientlist->testdone){
        printf("mmpig\r\n");
        Node *oppo = get_oppsite_user(root, clientlist->answer);
        write_users(fd, oppo);
      }
      else{
        write(fd, "Sorry, you have not completed the test.\r\n", sizeof(char)*41);
      }
    }

    // when command is quit.
    if (strcmp(command, quit)==0){
      close(fd);
    }
}

int main(int argc, char **argv){

    if (argc < 2){
      printf("To run the program ./mismatch_server <name of input file>\n");
    }
    interests = get_list_from_file (argv[1]);
    if (interests == NULL)
      return 1;
    root = add_next_level (root, interests);


    int listenfd = setup();
    int inbuf=0;
    char *after = clientlist->buf + inbuf;
    int room = 30 - inbuf;
    int nbytes;
    int where;

    while (1) {
    	int fd;
    	struct sockaddr_in r;
    	socklen_t socklen = sizeof(r);

    	if ((fd = accept(listenfd, (struct sockaddr *)&r, &socklen)) < 0 )
    	{
    		perror("accept");
    		exit(1);
    	}

      add_client (fd);

      char *askname = "What is your user name?\r\n";
      write(fd, askname, strlen(askname));

      room = sizeof(clientlist->buf);
      after = clientlist->buf;

      while ((nbytes = read(fd, after, room)) > 0){
        inbuf += nbytes;
        where = find_network_newline(clientlist->buf, inbuf);

        if (where >= 0) {
          clientlist->buf[where] = '\0';

          if (strcmp(clientlist->name, "")==0){
            char *welcome = "welcome.\r\nGo ahead and enter user commands>\r\n";
            write(fd, welcome, strlen(welcome));
            strcpy(clientlist->name, clientlist->buf);
          }
          else{
            execute_command(fd, clientlist->buf);
          }
          printf("Next message: %s\n", clientlist->buf);
          inbuf = inbuf - where -2;
          memmove(clientlist->buf, clientlist->buf + where + 2, inbuf);
        }
        room = sizeof(clientlist->buf) - inbuf;
        after = &clientlist->buf[inbuf];

      }


    }
}

