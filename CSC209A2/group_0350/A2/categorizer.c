#include "qtree.h"

void print_greetings();

int main (int argc, char **argv) {
	
	QNode *root = NULL;	
	char answer[MAX_LINE];	
	char name[MAX_LINE];
	

	Node * interests = NULL;

	if (argc < 2) {
        printf ("To run the program ./categorizer <name of input file>\n");
        return 1;
    }
	
    interests = get_list_from_file ( argv[1]);
	
    if (interests == NULL)
        return 1;

	root = add_next_level (root,  interests);
	free_list (interests);

	//main application loop
	
    //end of main loop - the user tyoped "q"
    print_qtree (root, 0);
	return 0;
}

void print_greetings () {
	printf ("----------------------------------------------\n");
	printf ("Friend recommender system. Find people who are just like you!\n");
	printf ("CSC209 fall 2016 team. All rights reserved\n");
	printf ("----------------------------------------------\n");
}