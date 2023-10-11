#include <stdio.h>
#include <stdlib.h>
#include "bst.h"



//////////////////////////////////////////////////////////////////// STRUCTURE FOR NODE /////////////////////////////////////////////////////////////////////////////////////

struct _Node {
    int value;                                                                //contains value of the node
    struct _Node *left, *right;                                               //used to get the left and right child of the node
};


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////






////////////////////////////////////////////////////////////////////// INSERTING A NODE /////////////////////////////////////////////////////////////////////////////////////


Node* insertNode(Node *root, int value)
{
    if(root == NULL)                                                         //if the tree is empty
    {
        Node* new =(Node*)malloc(sizeof(Node));                              //make a new node
        new->value = value;                                                  //assign the value to it
        new->left=new->right=NULL;                                           //assign its left and right children as NULL (as it is the only node in the tree)
        return new;                                                          //return the new node
    }
    if(value>root->value)                                                    //if the tree isn't empty and the value to be added is greater than the root
            root->right = insertNode(root->right, value);                    //go to the right subtree of the root
     else if (value<root->value)                                             //else if the value to be added is less than the root
            root->left = insertNode(root->left, value);                      //go to the left subtree of the root
                                                                             //in both cases, eventually the root == NULL case will be found and thus the new node will be added in
    return root;

}

// The idea behind this is simple:
// - First, check if the tree is empty (i.e. there's no root).
// -- If it is, simply allocate memory for a new node using malloc, add the value in and establish both children as NULL.
// -- If it isn't, check to see if the node to be inserted is greater or less than the node's value the function is currently on, and go either right or left.
//    At some point, the function will get to a node that is empty, therefore the first step is done.

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////








////////////////////////////////////////////////////////////////////// DELETING A NODE //////////////////////////////////////////////////////////////////////////////////////

Node* deleteNode(Node *root, int value)
{
    if(root == NULL)                                                          //check if the tree is empty
        return NULL;                                                          //if it is, there's nothing to be deleted
    else                                                                      //if it's not empty
    {
        if(value>root->value)                                                 //if the value of the node to be deleted is greater than the root
            root->right = deleteNode(root->right, value);                     //go to the right subtree of the root and search for it there
        else if (value<root->value)                                           //if the value of the node to be deleted is less than the root
            root->left = deleteNode(root->left, value);                       //go to the left subtree of the root and search for it there
        else if(value == root->value)                                         //if the node has been found
        {
            if(root->left == NULL && root->right == NULL)                     //check if it's a leaf first; if it is, simply delete the node and return NULL
            {
                free(root);
                return NULL;
            }
            else if(root->left == NULL && root->right != NULL)                //if it has only one child (right child case)
            {
                Node *aux = root->right;                                      //save its right child in an auxiliary node
                free(root);                                                   //delete it
                return aux;                                                   //return its child
            }
            else if(root->left != NULL && root->right == NULL)                //left child case
                {
                Node *aux = root->left;
                free(root);
                return aux;
            }
            else if(root->left != NULL && root->right != NULL)                //if it has both children
            {
                Node *lm = root->right;                                       //create a node to store the leftmost node from the right subtree
                while(lm- != NULL && lm != NULL)                              //search for it
                    lm = lm->left;                                            //store it (after the while loop, lm will contain the leftmost node from the right subtree)
                root->value = lm->value;                                      //replace the node's value with the leftmost node's value
                root->right = deleteNode(root->right, lm->value);             //now delete the leftmost node from its original place
            }
        }
    }
    return root;
}

// In order to delete a node, you have to find it first.
// Therefore, the first thing that the function does (ignoring the case where the tree is empty) is compare it to the current root.
// If it's greater, go right, if it's less, go left.
// When the node to be deleted is found, the function checks to see if it's a leaf, has a child or two children:
// - If it's a leaf, simply delete it.
// -- If it has a child, delete the node and replace it with its child, by storing the child in a temporary node first.
// --- If it has two children, we have to find the leftmost value in the right subtree (i.e. the node that comes after the node that has to be deleted).
//     The function searches for it; once it's been found, we replace the node to be deleted with it.
//     But then we have to delete the leftmost node from it's original position. Therefore, we call the function to go look into the right subtree for the leftmost node.

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



////////////////////////////////////////////////////////////////////// PRINTING A SUBTREE ///////////////////////////////////////////////////////////////////////////////////


void printSubtree(Node *N)
{
    if(N == NULL) return;                                                     //if the tree is empty, there's nothing to print
    printSubtree(N->left);                                                    //traverse the left subtree
    printf("%d",N->value);                                                    //print the nodes' values
    printf("\n");                                                             //the values will appear on individual lines
    printSubtree(N->right);                                                   //traverse the right subtree; the recursion will make it so this code will go through the whole subtree
}

// First, we go to the left subtree and print the nodes there.
// Then, we go to the right one and print the nodes there.
// The printSubtree(N->left) is called first so it can get to the first node in the tree.
// Afterwards, the function will print all nodes in order by their value, from left to right.

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





////////////////////////////////////////////////////////////////////// DELETING A SUBTREE ///////////////////////////////////////////////////////////////////////////////////

Node* deleteWholeTree(Node* root)                                             //used for deleteSubtree
{
    if(root == NULL) return NULL;                                             //if the tree is empty, there's nothing to be deleted
    deleteWholeTree(root->right);                                             //traverse the right subtree
    deleteWholeTree(root->left);                                              //traverse the left subtree
    free(root);                                                               //delete the nodes there
    return NULL;                                                              //return NULL (an empty tree)
}


Node* deleteSubtree(Node* root, int value)  //works
{
    if(root==NULL) return NULL;                                               //if the root is empty, there's nothing to be deleted
    else                                                                      //if it's not empty
    {                                                                         //search for the value by comparing it with the current root's value
        if(value>root->value)                                                 //if it's greater than the root's value
            root->right = deleteSubtree(root->right, value);                  //search for it in the right subtree
        else if (value<root->value)                                           //if it's less than the root's value
            root->left = deleteSubtree(root->left, value);                    //search for it in the left subtree
        else if(value == root->value)                                         //if it was found
        {
            if(root->right == NULL && root->left == NULL)                     //check if it's just a leaf
                {
                    free(root);                                               //in this case we just delete it
                    return NULL;
                }
            else                                                              //if it has children
            {
                deleteWholeTree(root->left);                                  //use the deleteWholeTree function to delete the tree where the left child is the root
                deleteWholeTree(root->right);                                 //use the deleteWholeTree function to delete the tree where the right child is the root
                free(root);                                                   //and then delete the root
                return NULL;
            }
        }
    }
    return root;
}

// A problem that I kept encountering while testing the code for this function was the fact that it would delete only one of the root's subtrees, not both.
// Previously, in the last else case, I would call deleteSubtree(root->right, value) and deleteSubtree(root->left, value), but that did not work.
// I assume this is because the root would change as the function would go through one of the subtrees and could not go back to the original one.
// Therefore, the other subtree could not be accessed.
// In order to rectify this, I've made an additional function named "deleteWholeTree" which deletes the whole tree of the given root.
// This way, the original root of the deleteSubtree function is saved (and deleted too!) and both of its subtrees are removed.

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////






/////////////////////////////////////////////////////////////////////// COUNTING LEAVES /////////////////////////////////////////////////////////////////////////////////////

int countLeaves(Node *N)
{
    if( N == NULL) return 0;                                                  //if the tree is empty, there are no leaves; therefore it will return 0 leaves
    int nrLeaves = 0;                                                         //nrLeaves is where the number of leaves will be stored
    if(N->right == NULL && N->left == NULL) nrLeaves = nrLeaves + 1;          //check if the node is a leaf (it doesn't have any children) if it is, then count it in
    else                                                                      //if it's not, then look for leaves in the left and right subtree
        nrLeaves = countLeaves(N->left) + countLeaves(N->right);              //and count how many leaves are in both of them; afterwards, sum them up to get the total number of leaves
    return nrLeaves;
}

// Declare an integer variable nrLeaves where the number of leaves will be stored in.
// Then, check if the current node is a leaf.
// - If it is, add 1 to the number of leaves.
// -- If it's not, then go in the left subtree, count the number of leaves there, go to the right subtree, count the number of leaves there too, and sum them up.

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////// DEPTH BETWEEN ROOT AND NODE //////////////////////////////////////////////////////////////////////////////

int Max(int a, int b)                                                         //used in the depth function
{
    if(a>b) return a;
    else return b;
}


int depth(Node* root, Node* N)
{
   if(root == NULL) return -1;                                                //if the tree is empty, return -1
   if(root->value == N->value) return 0;                                      //if the node is found, return 0
   int dR = depth(root->right, N);                                            //used for searching the right subtree for the node
   int dL = depth(root->left, N);                                             //used for searching the left subtree for the node
   if(dR >= 0)return dR+1;                                                    //if the node is in said subtree, add 1 to the variable
   if(dL >= 0)return dL+1;
   return(Max(dR,dL));                                                        //compare the number of each variable and return the greater one.
}

// The original plan was to search for the node in the tree.
// If it was greater than the root, I would call depth(root->right, N) + 1.
// If it was less than the root, I would call depth(root->left, N) + 1.
// This code worked as long as the node N was in the tree.
// If it wasn't, it wouldn't return -1; instead, it would return the depth of the node that the function previously checked.
// Basically, everytime the function searched for the node N in the tree, it would add 1, in order to count the edges.
// But, at the end, when node N was not found, it wouldn't revert back to -1; it would keep the same amount it had before.
// Therefore, I had to implement a new idea.
// Instead of searching for it whenever it was greater or less than the root, I made two variables called:
// - dR (which stands for depth Right)
// - dL (which stands for depth Left)
// And initialized them with the amount of edges found in the right and left subtrees between the root and node.
// Then, if the number of edges in the left / right subtrees were greater than 0 (i.e. the node was in said subtree), they would get 1 added to them.
// At the end, both variables are compared and the one that has the most edges (or rather, the one that does have edges) is returned.

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
