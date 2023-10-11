pthread_rwlock_t lock = PTHREAD_RWLOCK_INITIALIZER;

void *downtime(){


	sleep(1);
	//TODO: 1st downtime: Do balanceTree here
	root_balanced = balanceTree(root);
	free(root);
	root = root_balanced;
	root_balanced = NULL;

	sleep(1);
	//TODO: 2nd downtime: Do balanceTree here
	root_balanced = balanceTree(root);
	free(root);
	root = root_balanced;
	root_balanced = NULL;

	sleep(1);
	//TODO: 3rd downtime: Do balanceTree here
	root_balanced = balanceTree(root);
	free(root);
        root = root_balanced;
	root_balanced = NULL;

	return NULL;
}



void* ServeClient(char *client){

	// TODO: Open the file and read commands line by line
	FILE *fp;
	fp = fopen(client, "r");
	if (fp == NULL)
    {
        printf("Can't open files. \n");
        exit(-1);
    }



    unsigned MAX_LENGTH = 50;
    char command[MAX_LENGTH];

    while (fgets(command, MAX_LENGTH, fp))
        {
           char *insert = "insertNode";
           char *delet = "deleteNode";
           char *count = "countNodes";
           char *sum = "sumSubtree";
           if(strstr(command, insert) !=NULL)
           {
               pthread_rwlock_wrlock(&lock);
               char *x = command + 11;
               int nr;
               sscanf(x, "%d", &nr);
               root = insertNode(root, nr);
               pthread_rwlock_unlock(&lock);
               printf("[%s]insertNode %d\n", client, nr);
           }

          if(strstr(command, delet) !=NULL) {
               pthread_rwlock_wrlock(&lock);
               char *x = command + 11;
               int nr;
               sscanf(x, "%d", &nr);
               root = deleteNode(root, nr);
               pthread_rwlock_unlock(&lock);
               printf("[%s]deleteNode %d\n", client, nr);
           }
           if(strstr(command, count) !=NULL)
           {
               int c;
               pthread_rwlock_rdlock(&lock);
	       c = countNodes(root);
	       pthread_rwlock_unlock(&lock);
	       printf("[%s]countNodes = %d\n", client, c);
           }
           if(strstr(command, sum) !=NULL)
           {
               int s;
               pthread_rwlock_rdlock(&lock);
               s = sumSubtree(root);
	       pthread_rwlock_unlock(&lock);
	       printf("[%s]sumSubtree = %d\n", client, s);
           }
          

       }
    
        // close the file
        fclose(fp);
	return NULL;
}

