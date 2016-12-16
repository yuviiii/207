#ifndef QTREE_H
#define QTREE_H
#include "questions.h"

typedef enum {
    REGULAR, LEAF
} NodeType;

union Child {
	struct str_node *fchild;
	struct QNode *qchild;
} Child;

typedef struct QNode {
	char *question;
	NodeType node_type;
	union Child children[2];
} QNode;

QNode *add_next_level (QNode *current, Node * list_node);

//void print_qtree (QNode *parent, int level);
void write_users (int fd, Node *parent);
Node *tree_traversal (QNode *current, char* name);
QNode *find_user_category_in_tree (QNode *current, int answer);
void add_user(QNode *current, char *user_name, char **arg);
int question_count(Node *list_node);
void free_tree(QNode *current);
char *answers (QNode *current, char *name);
#endif