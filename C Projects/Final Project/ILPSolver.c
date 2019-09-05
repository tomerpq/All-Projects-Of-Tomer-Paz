/*this module return solved puzzle  byusing ilp algorithm*/


#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <string.h>
#include "ILPSolver.h"
#include <assert.h>
#include "gurobi_c.h"
#define A NULL


/*ILPsolve function recieve puzzle and return complete puzzle if exist or empty board if puzzle isn't soveable*/

double** ILPSolve(double ** board, int m, int n){
	/* for solving the puzzle using ILP we will define N^3 binary vars that will  represent
   	 * if the cell in the (i,j) place(while i is the col index and j is the row index)
     	 * has the value v (i,j,v in range [0,N-1]). we will define the objective as: 0x
	 * because there is no necessary for objective while solving the puzzle. hence, coeff=0 for every variable
	 * the vars will be stored in a N^3-size  array.
	 * the i cell in this array will represent the var indicates  if the cell in the ((flour(i/N)%N),flour(i/N^2))
	 * has the (i%N)+1 value(which means this var get 1 if indeed, 0 else)
	 */
		 int N=n*m;                 		/*puzzle size*/
		 int i,j,k,l,v,z, down,side, tmp_val;    /*aux vars for iterations*/
		 double ** current_sudoku;
		 double ** ret_board;
		 int num_of_vars; 			/*number of variables=N^3*/
		 GRBenv   *env   = NULL;  			/*creating the envelop*/
		 GRBmodel *model = NULL;  			/*creating new model*/
		 int       error = 0;
		 double *   sol=(double*)malloc(pow(N,3)*sizeof(double));        /*creating the array which the solutions will be stored in*/
		 int  *    ind =(int*)malloc(pow(N,3)*sizeof(int));        /*array for num of vars in every const*/
		 double*    val=(double*)malloc(pow(N,3)*sizeof(double));	 	/*array for the vars coeff in constr*/
		 double *   obj=(double*)malloc(pow(N,3)*sizeof(double));		/*array for the vars coeff in obj*/
		 char    *  vtype=(char*)malloc(pow(N,3)*sizeof(char));		/*define the vars range*/
		 int       optimstatus;				/*status of opt obj*/
		 current_sudoku=board; 				/*to be replaced by right function */
		 num_of_vars=pow(N,3);

		 ret_board = (double**)malloc(sizeof(double*)*N);
		 assert(ret_board);
         	 for(i = 0; i < N; i++){
			 ret_board[i] = (double*)malloc(sizeof(double)*N);
		 assert(ret_board[i]);
		
		 }


		 /* Create environment - log file is mip1.log */
		 error = GRBloadenv(&env, "mip1.log");
		 if (error) {
		 	  printf("ERROR %d GRBloadenv(): %s\n", error, GRBgeterrormsg(env));
		 	  return NULL;
		 }
		 /*cancel printing of optimitation*/
		 error = GRBsetintparam(env, GRB_INT_PAR_LOGTOCONSOLE, 0);		
		 if (error) {
		 	  printf("ERROR %d GRBsetintparam(): %s\n", error, GRBgeterrormsg(env));
		 	  return NULL;
		 }

		 /* Create an empty model named "mip1" */
		 error = GRBnewmodel(env, &model, "mip1", 0, NULL, NULL, NULL, NULL, NULL);
		 if (error) {
		 	  printf("ERROR %d GRBnewmodel(): %s\n", error, GRBgeterrormsg(env));
		 	  return NULL;
		 }


		 /*set the obj coeffs*/
		 for (i=0; i<num_of_vars; i++){
			 obj[i]=0;
		 }

		 /*set vars type as GRB_BINARY*/
		 for (i=0; i<num_of_vars; i++){
			 vtype[i]=GRB_BINARY;

		 }
		 /* add variables to model */
		 error = GRBaddvars(model, num_of_vars, 0, NULL, NULL, NULL, obj, NULL, NULL, vtype, NULL);
		 if (error) {
		 	  printf("ERROR %d GRBaddvars(): %s\n", error, GRBgeterrormsg(env));
		 	  return NULL;
		 }
		 /* Change objective sense to maximization */
		 error = GRBsetintattr(model, GRB_INT_ATTR_MODELSENSE, GRB_MAXIMIZE);
	     if (error) {
		 	  printf("ERROR %d GRBsetintattr(): %s\n", error, GRBgeterrormsg(env));
		 	  return NULL;
		 }

	     /* update the model - to integrate new variables */

	     error = GRBupdatemodel(model);
	     if (error) {
	     	  printf("ERROR %d GRBupdatemodel(): %s\n", error, GRBgeterrormsg(env));
	     	  return NULL;
	     }
	     /*first group of constr: value can appear only once in a row*/
	     /*iterate over the rows*/
	     for (j=0; j<N; j++){
	    	 /*iterate over the values*/
	    	 for (v=0; v<N;v++){
	    		 /*iterate over cells in the row*/
	    	     for (i=0; i<N; i++){
	    			ind[i]=(j*pow(N,2))+(i*N)+v;
	    			val[i]=1;
	    		    }
	    	        /*adding constr to model, without specific constr name*/
	    	       	error = GRBaddconstr(model, N, ind, val, GRB_EQUAL, 1.0, NULL);
	     			if (error) {
					   printf("ERROR %d 1st GRBaddconstr(): %s\n", error, GRBgeterrormsg(env));
					   return NULL;
	     				}
	    	 	 }

	    	 }
	     /*second group of constr: value can appear once in a col*/
	     /*iterate over the cols*/
	     for (j=0; j<N; j++){
	       	 /*iterate over the values*/
	       	 for (v=0; v<N;v++){
	       		 /*iterate over cells in the col*/
	       	     for (i=0; i<N; i++){
	       			ind[i]=(i*pow(N,2))+(j*N)+v;
	       			val[i]=1;
	       	        }
	       	        /*adding constr to model, without specific constr name*/
	       	      	error = GRBaddconstr(model, N, ind, val, GRB_EQUAL, 1.0, NULL);
	       	  		if (error) {
	       	  		   printf("ERROR %d 1st GRBaddconstr(): %s\n", error, GRBgeterrormsg(env));
	       	  		   return NULL;
	       	  		}
	       	 }
	     }
	     /*third group of constr: value can appear only once in a block */

	for (z=0; z< n; z++){
		down=(m*z)*(N*N); /*go down to the block below the current block. */     
		for (j=0; j<m; j++){
	    		 side=(j*n)*N;/*go right to the next block. */
    		 	 for (v=0; v<N; v++){
    			 	for (k=0; k<n; k++){
    					 for (l=0; l<m; l++){
    							 ind[i]=down+side+(pow(N,2)*l)+((k)*N)+v;
    							 val[i]=1;
    							 i++;
    					 }
    		 	}
  				 /*adding constr to model, without specific constr name*/
              	error = GRBaddconstr(model, N, ind, val, GRB_EQUAL, 1.0, NULL);
              	if (error) {
    			    printf("ERROR %d 1st GRBaddconstr(): %s\n", error, GRBgeterrormsg(env));
          			return NULL;
    			 	}
              	i=0;
    			 }
    	 	}

	}
	     /*fourth group of constr: every cell contains only one value */

	     for (j=0; j<N;j++){
	    	 for (i=0;i<N; i++){
	    		 for (v=0; v<N ;v++){
	    			 ind[v]=v+i*N+(j*pow(N,2));
	    			 val[v]=1;
	    		 }
	    		 /*adding constr to model, without specific constr name*/
	    		 error = GRBaddconstr(model, N, ind, val, GRB_EQUAL, 1.0, NULL);
	    		 if (error) {
	    		     printf("ERROR %d 1st GRBaddconstr(): %s\n", error, GRBgeterrormsg(env));
	    		     return NULL;
	    		    }
	    	 }
	     }


	     /*fifth group of constr: if cell is occupied,
	      *  its correspond var must be equal 1*/
	     for (i=0; i<N; i++){
	    	 for (j=0;j<N; j++){
	    		 if (current_sudoku[i][j]!=0){
	    			tmp_val=(int)current_sudoku[i][j];
	    			ind[0]=(j*N)+(pow(N,2)*i)+tmp_val-1;
	    			val[0]=1;
	    			/*adding constr to model, without specific constr name*/
	    			  error = GRBaddconstr(model, 1, ind, val, GRB_EQUAL, 1.0, NULL);
	    	    	  if (error) {
	    			       printf("ERROR %d 1st GRBaddconstr(): %s\n", error, GRBgeterrormsg(env));
	    			       return NULL;
	    			      }
	    		 }
	    	 }
	     }

	     /* Optimize model */
	     error = GRBoptimize(model);
	     if (error) {
	        printf("ERROR %d GRBoptimize(): %s\n", error, GRBgeterrormsg(env));
	     	return NULL;
	       }


	     /* Write model to 'mip1.lp'  */
	       error = GRBwrite(model, "mip1.lp");
	       if (error) {
	     	  printf("ERROR %d GRBwrite(): %s\n", error, GRBgeterrormsg(env));
	     	  return NULL;
	       }
	       /* Get solution information */

	        error = GRBgetintattr(model, GRB_INT_ATTR_STATUS, &optimstatus);
	        if (error) {
	       	  printf("ERROR %d GRBgetintattr(): %s\n", error, GRBgeterrormsg(env));
	       	  return NULL;
	        }
	        /* the puzzle is unsolvable ->return empty board*/
	        if (optimstatus!=GRB_OPTIMAL){
  		       	for(i=0;i<N; i++){
				for (j=0; j<N;j++){
					ret_board[i][j]=0.0;
				}
			}return ret_board;
		

	        }
	        /*if solvable- update the board according to the solution and return the compete board*/
	        else{
	        	error = GRBgetdblattrarray(model, GRB_DBL_ATTR_X, 0, num_of_vars, sol);
	        	if (error) {
	        	   printf("ERROR %d GRBgetdblattrarray(): %s\n", error, GRBgeterrormsg(env));
	        	   return A;
	        	  }
	        	for (k=0; k<num_of_vars; k++){
	        		if(sol[k]==1){
	        			i=(int)(k/pow(N,2));
	        			j=((int)(k/N))%N;
	        			v=(k%N)+1;
	        			ret_board[i][j]=v;
	        		}
	        	}
	        	return ret_board;
	        }





	}
