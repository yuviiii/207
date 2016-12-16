#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "questions.h"

Node * get_list_from_file (char *input_file_name) {
    Node * result=NULL;
    Node *head = result;
    FILE *my_file = fopen(input_file_name, "r");
    //read lines and add to the list
    char *line=malloc(sizeof(char)*MAX_LINE);
    if (fgets (line, MAX_LINE, my_file)!=NULL){
        line [strcspn (line, "\r\n")] = '\0';
        head = malloc(sizeof(Node));
        head->str= line;
        result = head;
        line=malloc(sizeof(char)*MAX_LINE);
    }
    while ((fgets (line, MAX_LINE, my_file)!=NULL)){
        line [strcspn (line, "\r\n")] = '\0';
        Node *next=malloc(sizeof(Node));
        next->str = line;
        head->next = next;
        head = next;
        line=malloc(sizeof(char)*MAX_LINE);
    }
    
    return result;
}

void print_list (Node *head) {
    
    while (head!=NULL){
        printf("%s\n", head->str);
        head=head->next;
    }
}

void free_list (Node *head) {
    free(head);
    
}
