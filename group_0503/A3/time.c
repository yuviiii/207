#include <time.h>
#include <sys/time.h>
#include <stdlib.h>
#include <stdio.h>

struct timeval starttime, endtime;
double timediff;
if ((gettimeofday(&starttime, NULL))==-1){
	perror("gettimeofday");
	exit(1);
}


if ((gettimeofday(&endtime, NULL))==-1){
	perror("gettimeofday");
	exit(1);
}

timediff=(endtime.tv_sec - starttime.tv_sec) + (endtime.tv_usec - starttime.tv_usec) / 1000000.0;
fprintf(stderr, "%.4f\n",timediff);}