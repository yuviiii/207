
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <dirent.h>
#include <getopt.h>
#define MAX_LINE 10


int *Substring_Count(char **filename,int number){
    //read lines
    char *line=malloc(sizeof(char)*(MAX_LINE+5));
    double result;
    //index of first letter in the substring.
    int i,start;
   
    // index of 1024-substring list;
    int index;
    bool left;
    //1024-substring list;
    int *substring=malloc(sizeof(int)*1024);
        for (int k =0; k <1024; k++){
        substring[k] =0;
        }
    //loop through each file.
    for (int k =0; k< number;k++){    
         start =0;
         i =0;
         left = false;
   if (filename[k] != NULL && strcmp(filename[k],"\0") != 0){
    FILE *my_file = fopen(filename[k], "r");

    //index for 5 letters;
    int j;
    //check the letter is correct for genes.
    bool check;
    //loop every 256 letters or line.
    while ((fgets(line+4, MAX_LINE, my_file)!=NULL)){
         if (left){
            i = 0;
            start =0;
            }else{
            i = 4;
            start =4;
                }
        if ((strchr(line+start, '\n')) || (strchr(line+start, '\r'))){
            left = false;
            line[strcspn(line, "\r\n")] = '\0';
        }else{
            left = true;
            }
                line[4+MAX_LINE] = '\0';
        //use loop to check every substrings
        while ((strlen(line+start) >= 5) && (i<strlen(line+start)+start-4)){
            j = 0;
            check = true;
            index = 0;
            //check the 5 letters.
            while ((j<5) && (check)){
                result =1;
                for (int k=0; k < 4-j; k++){
                    result = result *4;
                    }
                if ((line[i+j] == 'A')||(line[i+j] == 'a')){
                    index += 0;
                }else if ((line[i+j] == 'C')||(line[i+j] == 'c')){
                    index += 1 * result;
                }else if ((line[i+j] == 'G')||(line[i+j] == 'g')){
                    index += 2 * result;
                }else if ((line[i+j] == 'T')||(line[i+j] == 't')){
                    index += 3 * result;
                }else{
                    check = false;
                }
                j+=1;
            }
            if (check){
                substring[index] +=1;
                i+=1;
            }else{
                //skip all the sbustrings contain this letter.
                i += j;
            }
        } 
       if (left){
               line[0] = line[MAX_LINE-1];
               line[1] = line[MAX_LINE];
               line[2] = line[MAX_LINE+1];
               line[3] = line[MAX_LINE+2];
               }
    }
    }
    }
    free(line);
    return substring;
}

int main(int argc, char**argv){
 int ch;
 char *outfile = NULL, file_num[5], *dir_name = NULL;
 /* read in arguments */
 while ((ch = getopt(argc, argv, "d:n:o:")) != -1) {
 switch(ch) {
 case 'o':
 outfile = optarg;
 break;
 case 'n':
 strncpy(file_num,optarg,strlen(optarg));
 break;
 case 'd':
  dir_name = optarg;
  break;
 case '?' :
 fprintf(stderr, "Usage: freq5 -d <input dir name> ""-n <number of processes>"
 "-f <output file name>\n");
 exit(1);
 }
}     
    
    char *b = "acgt";
DIR *d;
struct dirent *dir;
int number,max_str,index1,r;
r =0;
max_str =0;
number= 0;
index1 = 0;
d = opendir (dir_name);
if (d) {
  while ((dir = readdir(d)) != NULL) {
  if ((strcmp(dir->d_name,".") != 0)&&(strcmp(dir->d_name,"..") != 0))
   number+=1;
   if (strlen(dir->d_name) > max_str)
    max_str = strlen(dir->d_name);
   }
  closedir (d);
 }  
 int n;
 if (file_num != NULL)
 { n= strtol(file_num,NULL,10);
 }else{
 	
 	n =1;
 }
 char **list = malloc(sizeof(char *)*number);
 for (int k=0; k<number;k++){
     list[k] = malloc(sizeof(char ) *(max_str +1));
    }
d = opendir (dir_name);
if (d) {
  while ((dir = readdir(d)) != NULL) {
  if ((strcmp(dir->d_name,".") != 0)&&(strcmp(dir->d_name,"..") != 0)){
    strncpy(list[index1]+strlen(dir_name),dir->d_name,strlen(dir->d_name));
    strncpy(list[index1],dir_name,strlen(dir_name));
    list[index1][strlen(dir->d_name)+strlen(dir_name)] = '\0';
    index1+=1;
   }
 }
  closedir (d);
 }

    
     int check;
  
     int *substring=malloc(sizeof(int)*1025);
      for (int k =0; k <1024; k++){
        substring[k] =0;
        }
     int *substring2=malloc(sizeof(int)*1025);
      for (int k =0; k <1024; k++){
        substring2[k] =0;
        }
      if (n > 1){
     int fd[2];
      if ((pipe(fd))==-1){
       perror("pipe");
       exit(1);
       }
     
   for (int k=0; k< n; k++){
     r = fork();
    
   if (r == 0){
      
    //receive file list;
    if (close(fd[0]) == -1) {
       perror("close");
       }
    if (k< n-1){
       
    substring = Substring_Count(list+k*(number/n),number/n);
    
      if (write(fd[1], substring, sizeof(int)*1025) == -1){
       perror("write to pipe");
       }
      
    }
    else {
         
    substring = Substring_Count(list+k*(number/n),number-(n-1)*number/n);
       if (write(fd[1], substring, sizeof(int)*1025) == -1){
       perror("write to pipe");
       }
      
    }
      if (close(fd[1]) == -1) {
       perror("close");
       }
    exit(0);
    }else if(r > 0){
   
     }else{
      //error
      perror("fork");
      exit(1);
      }
   }
 
    if (close(fd[1]) == -1){
       perror("close");
       
       }
    for (int k=0; k<n; k++)
      {
      check = read(fd[0],substring2,sizeof(int)*1025);
      if ( check== -1){
       perror("read from pipe");
       }else {
       
        for (int j=0; j <1024; j++)
         substring[j] += substring2[j];
 
        }
      }
        
      if (close(fd[0]) == -1) {
       perror("close");
       }
     }else {
     	 substring = Substring_Count(list,number);
     }
    

    if (outfile == NULL){
    for (int i=0; i<4; i++){
        for (int i2=0; i2<4; i2++){
           for (int i3=0; i3<4; i3++){
                for (int i4=0; i4<4; i4++){ 
                      for (int i5=0; i5<4; i5++){  
                         printf("%c%c%c%c%c,%d\n",b[i],b[i2],b[i3],b[i4],b[i5],substring[i*256+i2*64+i3*16+i4*4+i5*1]);                         
                          }
                    }
                }
            }
        }
        }else {
                FILE *fp;
                fp = fopen(outfile,"w");
    for (int i=0; i<4; i++){
        for (int i2=0; i2<4; i2++){
           for (int i3=0; i3<4; i3++){
                for (int i4=0; i4<4; i4++){ 
                      for (int i5=0; i5<4; i5++){  
                         fprintf(fp,"%c%c%c%c%c,%d\n",b[i],b[i2],b[i3],b[i4],b[i5],substring[i*256+i2*64+i3*16+i4*4+i5*1]);                         
                          }
                    }
                }
            }                
               } 
                fclose(fp);
            }
    free(substring);
     free(list);
    return 0;
    }
