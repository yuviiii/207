#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <stdlib.h>
#include "qtree.h"

Node *tree_traversal (QNode *current, char *name){
    Node *result_left = NULL;
    Node *result_right = NULL;
    if (current->node_type == REGULAR){
        result_left = tree_traversal(current->children[0].qchild, name);
        if (result_left == NULL){
            result_right = tree_traversal(current->children[1].qchild, name);
            return result_right;
        }else{
            return result_left;
        }
    }else{
        Node *left = current->children[0].fchild;
        while ((left != NULL)&&(strcmp(left->str,name) != 0)){
            left = left->next;
        }
        if (left != NULL){
            return current->children[0].fchild;
        }else{
            Node *right = current->children[1].fchild;
            while ((right != NULL)&&(strcmp(right->str,name) != 0)){
                right = right->next;
            }
            if (right != NULL){
                return current->children[1].fchild;
            }else{
                return NULL;
            }
        }
    }
    
}

// For ASSIGNMENT4
char *answers (QNode *current, char *name){
    char *result = malloc(sizeof(char *));
    int i=0;
    while (current->node_type == REGULAR ){
        if (tree_traversal(current->children[0].qchild, name)==NULL){
            result[i]='1';
            current = current->children[1].qchild;
        }else{
            result[i]='0';
            current = current->children[0].qchild;
        }
        i++;
    }
    Node *left = current->children[0].fchild;
        while ((left != NULL)&&(strcmp(left->str,name) != 0)){
            left = left->next;
        }
        if (left != NULL){
            result[i]='0';
        }else{
            Node *right = current->children[1].fchild;
            while ((right != NULL)&&(strcmp(right->str,name) != 0)){
                right = right->next;
            }
            if (right != NULL){
                result[i]='1';
            }
        }
    
    return result;
}

//based on the answer, search for next Qnode.
QNode *find_user_category_in_tree (QNode *current, int answer){
    return current->children[answer].qchild;
    
}


void add_user(QNode *current, char *user_name, char **arg){
    int str_len;
    int index_of_answer;
    int answer;
    Node *cur_node = NULL;
    //construct user node;
    str_len = strlen(user_name);
    Node *user=NULL;
    user = (Node *) calloc (1, sizeof(Node));
    user->str = (char *) calloc (str_len +1, sizeof(char ));
    strncpy (user->str, user_name, str_len);
    user->str [str_len] = '\0';
    //attach to tree.
    index_of_answer = 3;
    while (current->node_type == 0) {
        answer = strtoll(arg[index_of_answer], NULL, 10);
        current = find_user_category_in_tree(current, answer);
        index_of_answer +=1;
    }
    answer = strtoll(arg[index_of_answer], NULL, 10);
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
//count for question num.
int question_count(Node *list_node){
    int count;
    count = 0;
    while (list_node != NULL) {
        count +=1;
        list_node = list_node->next;
    }
    return count;
}


QNode *add_next_level (QNode *current, Node *list_node) {
	int str_len;
	
	str_len = strlen (list_node->str);
	current = (QNode *) calloc (1, sizeof(QNode));

	current->question =  (char *) calloc (str_len +1, sizeof(char ));
	strncpy ( current->question, list_node->str, str_len );
	current->question [str_len] = '\0';  
	current->node_type = REGULAR;
	
	if (list_node->next == NULL) {
		current->node_type = LEAF;
		return current;
	}
	
	current->children[0].qchild = add_next_level ( current->children[0].qchild, list_node->next);
	current->children[1].qchild = add_next_level ( current->children[1].qchild, list_node->next);

	return current;
}
void free_tree(QNode *current){
	if (current->node_type == REGULAR){
		free_tree(current->children[0].qchild);
		free_tree(current->children[1].qchild);
		free(current);
	}else {
	   free(current->children[0].fchild);
	   free(current->children[1].fchild);
	   free(current);
	}
	}
// void print_qtree (QNode *parent, int level) {
// 	int i;
// 	for (i=0; i<level; i++)
// 		printf("\t");
	
// 	printf ("%s type:%d\n", parent->question, parent->node_type);
// 	if(parent->node_type == REGULAR) {
// 		print_qtree (parent->children[0].qchild, level+1);
// 		print_qtree (parent->children[1].qchild, level+1);
// 	}
// 	else { //leaf node
// 		for (i=0; i<(level+1); i++)
// 			printf("\t");
// 		print_users (parent->children[0].fchild);
// 		for (i=0; i<(level+1); i++)
// 			printf("\t");
// 		print_users (parent->children[1].fchild);
// 	}
// }

void write_users (int fd, Node *parent) {
    char *comma = ", ";
	if (parent == NULL){
		write(fd, "No completing personalities found. Please try again later\r\n", sizeof(char)*59);
    }
	else {
		write(fd, parent->str, strlen(parent->str));
        write(fd, comma, strlen(comma));
		while (parent->next != NULL) {
			parent = parent->next;
			write(fd, parent->str, strlen(parent->str));
            write(fd, comma, strlen(comma));
		}
		write (fd, "\r\n", sizeof(char)*2);
	}
}