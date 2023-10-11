#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <assert.h>
#include "bst.h"


/*
   Returns the parent of an either existing or hypotetical node with the given value
 */
Node * find_parent(Node * root, int data) {
  assert(root != NULL);
  assert(data != root->data);

  Node * next = data < root->data ? root->left : root->right;

  if (next == NULL || next->data == data)
    return root;
  else
    return find_parent(next, data);
}

/*
   Constructs a new node
 */
Node * mk_node(int data) {
  Node * node = (Node *) malloc(sizeof(Node));
  node->data = data;
  node->left = node->right = NULL;
  return node;
}

Node * insertNode(Node * root, int data) {
  if (root == NULL)
    return mk_node(data);

  if (data == root->data)
    return NULL;

  Node * parent = find_parent(root, data);
  Node * child = data < parent->data ? parent->left : parent->right;
  assert(child == NULL || child->data == data);

  if (child == NULL) {
    // value not found, then insert and return node
    child = mk_node(data);
    if (data < parent->data)
      parent->left = child;
    else
      parent->right = child;

    return root;
  } else {
    // value found, then return null
    return NULL;
  }
}

bool is_ordered(Node * root) {
  if (root == NULL)
    return true;
  if (root->left && root->left->data > root->data)
    return false;
  if (root->right && root->right->data < root->data)
    return false;
  return true;
}

Node * deleteNode(Node * root, int data) {
  assert(is_ordered(root));

  // empty tree
  if (root == NULL)
    return NULL;

  // find node with value 'value' and its parent node
  Node * parent, * node;
  if (root->data == data) {
    parent = NULL;
    node = root;
  } else {
    parent = find_parent(root, data);
    node = data < parent->data ? parent->left : parent->right;
  }
  assert(node == NULL || node->data == data);

  // value not found
  if (node == NULL)
    return root;

  // re-establish consistency
  Node * new_node;
  if (node->left == NULL) {
    // node has only right child, then make right child the new node
    new_node = node->right;
  } else {
    // otherwise make right child the rightmost leaf of the subtree rooted in the left child
    // and make the left child the new node
    Node * rightmost = new_node = node->left;
    while (rightmost->right != NULL)
      rightmost = rightmost->right;
    rightmost->right = node->right;
  }

  free(node);

  Node * new_root;
  if (parent == NULL) {
    // if deleted node was root, then return new node as root
    new_root = new_node;
  } else {
    // otherwise glue new node with parent and return old root
    new_root = root;
    if (data < parent->data)
      parent->left = new_node;
    else
      parent->right = new_node;
  }

  assert(is_ordered(new_root));

  return new_root;
}

void printSubtree(Node * N) {
  if (N == NULL) return;

  printSubtree(N->left);
  printf("%d \n", N->data);
  printSubtree(N->right);
}

int countLeaves(Node * N) {
  if (N == NULL)
    return 0;

  if (N->left == NULL && N->right == NULL)
    return 1;

  return countLeaves(N->left) + countLeaves(N->right);
}

/*
   Frees the entire subtree rooted in 'root' (this includes the node 'root')
 */
Node* freeSubtree(Node * root) {
  if (root == NULL)
    return NULL;

  freeSubtree(root->left);
  freeSubtree(root->right);
  free(root);
  return NULL;
}

/*
   Deletes all nodes that belong to the subtree (of the tree of rooted in 'root')
   whose root node has value 'value'
 */
Node * deleteSubtree(Node * root, int data) {
  assert(is_ordered(root));

  // empty tree
  if (root == NULL)
    return NULL;

  // entire tree
  if (root->data == data) {
    root = freeSubtree(root);
    return NULL;
  }

  // free tree rooted in the left or right node and set the respective pointer to NULL
  Node * parent = find_parent(root, data);
  if (data < parent->data) {
    assert(parent->left == NULL || parent->left->data == data);
    parent->left = freeSubtree(parent->left);
    parent->left = NULL;
  } else {
    assert(parent->right == NULL || parent->right->data == data);
    parent->right = freeSubtree(parent->right);
    parent->right = NULL;
  }

  return root;
}

/*
   Compute the depth between root R and node N

   Returns the number of edges between R and N if N belongs to the tree rooted in R,
   otherwise it returns -1
 */
int depth (Node * R, Node * N) {
  if (R == NULL || N == NULL)
    return -1;
  if (R == N)
    return 0;

  int sub_depth = depth(R->data > N->data ? R->left : R->right, N);

  if (sub_depth >= 0)
    return sub_depth + 1;
  else
    return -1;
}


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

int sumSubtree(Node *N)                                                                                //sum of the nodes
{
  if (N == NULL) return 0;                                                                             //if tree is empty/node is null, return 0
  return (N->data + sumSubtree(N->left) + sumSubtree(N->right));                                       //otherwise sum up the root + nodes on the left + nodes on the right
}


int i = 0;                                                                                             //index used for storing the nodes

void storeNodes(Node* root, int nodes[])                                                               //stores nodes in an array
{
    if (root == NULL) return;                                                                          //if tree is empty -> return NULL
    storeNodes(root->left, nodes);                                                                     //store nodes on the left first
    nodes[i++] = root->data;                                                                           //then the root
    storeNodes(root->right, nodes);                                                                    //then the nodes on the right

}

int countNodes(Node* root)                                                                             //get the number of nodes in a tree
{
    int c = 0;                                                                                         //used for storing the number of nodes
    if (root == NULL) return 0;                                                                        //tree/root is empty, return 0
    return 1 + countNodes(root->left) + countNodes(root->right);                                       //add 1 and the number of nodes on the left and right subtree
}

Node* makeBalanced(int nodes[], int s, int e)                                                          //used in the main function for making a balanced tree
{                                                                                                      //using an array with the stored nodes that are in order, a start and an end to the array
    if(s > e)return NULL;                                                                              //transform the array into a balanced tree; if the start of the array is greater than the end, the array is invalid
    int mid = (s + e)/2;                                                                               //find the middle of the array
    Node *balanced;                                                                                    //declare a new node where it will be stored
    balanced = mk_node(nodes[mid]);                                                                    //store the middle of the array in the root of the new tree
    balanced->left=makeBalanced(nodes, s, mid-1);                                                      //recursively do the same for the left half
    balanced->right=makeBalanced(nodes, mid+1, e);                                                     //and the right half
    return balanced;                                                                                   //return the new tree
}


Node* balanceTree(Node* root)                                                                          //main function for making a balanced tree
{
    if (root == NULL) return NULL;                                                                          //if the given tree is empty return NULL
    int n = countNodes(root);                                                                          //store the number of nodes in a tree to use it as a size for the array
    int nodes[n];                                                                                      //declare the array for storing nodes
    storeNodes(root, nodes);                                                                           //store the nodes
    return makeBalanced(nodes, 0, n-1);                                                                //return the tree made by the makeBalanced function
}
