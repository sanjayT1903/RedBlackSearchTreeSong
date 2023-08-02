import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
//
/**
 * Red-Black Tree implementation with a Node inner class for representing
 * the nodes of the tree. Currently, this implements a Binary Search Tree that
 * we will turn into a red black tree by modifying the insert functionality.
 * In this activity, we will start with implementing rotations for the binary
 * search tree insert algorithm.
 */
public class RedBlackTree<T extends Comparable<T>> implements RedBlackTreeInterface<T> {

    /**
     * This class represents a node holding a single value within a binary tree.
     */
    protected static class Node<T extends Comparable<T>> {
        public List<T> data;
        // The context array stores the context of the node in the tree:
        // - context[0] is the parent reference of the node,
        // - context[1] is the left child reference of the node,
        // - context[2] is the right child reference of the node.
        // The @SupressWarning("unchecked") annotation is used to supress an unchecked
        // cast warning. Java only allows us to instantiate arrays without generic
        // type parameters, so we use this cast here to avoid future casts of the
        // node type's data field.
        @SuppressWarnings("unchecked")
        public Node<T>[] context = (Node<T>[])new Node[3];

        public int blackHeight=0;
        public Node(T data) {
            this.data = new LinkedList<>();
            this.data.add(data);
        }

        public T getNode(){
            return data.get(0);
        }
        /**
         * @return true when this node has a parent and is the right child of
         * that parent, otherwise return false
         */
        public boolean isRightChild() {
            return context[0] != null && context[0].context[2] == this;
        }


    }

    protected Node<T> root; // reference to root node of tree, null when empty
    protected int size = 0; // the number of values in the tree

    protected Node<T> NIL;

    public RedBlackTree(){
        NIL=new Node<>(null);
        NIL.blackHeight=1;
        root=null;
    }

    /**
     * Performs a naive insertion into a binary search tree: adding the input
     * data value to a new node in a leaf position within the tree. After  
     * this insertion, no attempt is made to restructure or balance the tree.
     * This tree will not hold null references, nor duplicate data values.
     * @param data to be added into this binary search tree
     * @return true if the value was inserted, false if not
     * @throws NullPointerException when the provided data argument is null
     * @throws IllegalArgumentException when data is already contained in the tree
     */
    public boolean insert(T data) throws NullPointerException, IllegalArgumentException {
        // null references cannot be stored within this tree
        if(data == null) throw new NullPointerException(
                "This RedBlackTree cannot store null references.");

        Node<T> newNode = new Node<>(data);
        newNode.context[1]=NIL;
        newNode.context[2]=NIL;
        if (this.root == null) {
            // add first node to an empty tree
            root = newNode;
            size++;
            enforceRBTreePropertiesAfterInsert(newNode);
            return true;
        } else {
            // insert into subtree
            Node<T> current = this.root;
            while (true) {
                int compare = newNode.data.get(0).compareTo(current.data.get(0));
                if (compare == 0) {
                    current.data.add(data);
                    this.size++;
                    return true;
                } else if (compare < 0) {
                    // insert in left subtree
                    if (current.context[1] == NIL) {
                        // empty space to insert into
                        current.context[1] = newNode;
                        newNode.context[0] = current;
                        this.size++;
                        enforceRBTreePropertiesAfterInsert(newNode);
                        return true;
                    } else {
                        // no empty space, keep moving down the tree
                        current = current.context[1];
                    }
                } else {
                    // insert in right subtree
                    if (current.context[2] == NIL) {
                        // empty space to insert into
                        current.context[2] = newNode;
                        newNode.context[0] = current;
                        this.size++;
                        enforceRBTreePropertiesAfterInsert(newNode);
                        return true;
                    } else {
                        // no empty space, keep moving down the tree
                        current = current.context[2];
                    }
                }
            }
        }
    }

    protected void enforceRBTreePropertiesAfterInsert(Node<T> node){
        while (node.context[0]!=null&&node.context[0].blackHeight==0){
            if(node.context[0].isRightChild()){
                Node<T> uncle=node.context[0].context[0].context[1];
                //Parent and Uncle Nodes Are Red
                if(uncle!=null && uncle.blackHeight==0){
                    uncle.blackHeight=1;
                    node.context[0].blackHeight=1;
                    node.context[0].context[0].blackHeight=0;
                    node=node.context[0].context[0];
                    continue;
                }
                //Parent Node Is Red, Uncle Node Is Black, Inserted Node Is "Inner Grandchild"
                if(!node.isRightChild()){
                    node=node.context[0];
                    rotate(node.context[1],node); //right rotate
                }
                //Parent Node Is Red, Uncle Node Is Black, Inserted Node Is "Outer Grandchild"
                node.context[0].blackHeight=1;
                node.context[0].context[0].blackHeight=0;
                rotate(node.context[0].context[0].context[2],node.context[0].context[0]); //left rotate
            }else{
                Node<T> uncle=node.context[0].context[0].context[2];
                //Parent and Uncle Nodes Are Red
                if(uncle!=null && uncle.blackHeight==0){
                    uncle.blackHeight=1;
                    node.context[0].blackHeight=1;
                    node.context[0].context[0].blackHeight=0;
                    node=node.context[0].context[0];
                    continue;
                }
                //Parent Node Is Red, Uncle Node Is Black, Inserted Node Is "Inner Grandchild"
                if(node.isRightChild()){
                    node=node.context[0];
                    rotate(node.context[2],node); //left rotate
                }
                //Parent Node Is Red, Uncle Node Is Black, Inserted Node Is "Outer Grandchild"
                node.context[0].blackHeight=1;
                node.context[0].context[0].blackHeight=0;
                rotate(node.context[0].context[0].context[1],node.context[0].context[0]); //right rotate
            }
        }
        this.root.blackHeight=1;
    }

    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a left child of the provided parent, this
     * method will perform a right rotation. When the provided child is a
     * right child of the provided parent, this method will perform a left rotation.
     * When the provided nodes are not related in one of these ways, this method
     * will throw an IllegalArgumentException.
     * @param child is the node being rotated from child to parent position
     *      (between these two node arguments)
     * @param parent is the node being rotated from parent to child position
     *      (between these two node arguments)
     * @throws IllegalArgumentException when the provided child and parent
     *      node references are not initially (pre-rotation) related that way
     */
    private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
        // TODO: Implement this method.
        if( child == parent.context[1] ){
            // right rotation
            Node<T> grand = parent.context[0];
            child.context[0] = grand;

            parent.context[1] = child.context[2];
            if( child.context[2] != NIL ) child.context[2].context[0] = parent;

            child.context[2] = parent;
            parent.context[0] = child;

            if( grand == null ){
                // rotation at the root
                this.root = child;
            }else{
                if( grand.context[1]==parent ) grand.context[1] = child;
                else grand.context[2] = child;
            }
        }else if( child == parent.context[2] ){
            // left rotation
            Node<T> grand = parent.context[0];
            child.context[0] = grand;

            parent.context[2] = child.context[1];
            if( child.context[1] != NIL ) child.context[1].context[0] = parent;

            child.context[1] = parent;
            parent.context[0] = child;

            if( grand == null ){
                // rotation at the root
                this.root = child;
            }else{
                if( grand.context[1]==parent ) grand.context[1] = child;
                else grand.context[2] = child;
            }
        }else{
            // error
            throw new IllegalArgumentException("The provided child is not a child of the provided parent");
        }
    }

    /**
     * Get the size of the tree (its number of nodes).
     * @return the number of nodes in the tree
     */
    public int size() {
        return size;
    }

    /**
     * Method to check if the tree is empty (does not contain any node).
     * @return true of this.size() return 0, false if this.size() > 0
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public T get(Comparable<T> predicate) {
        Node<T> nodeWithData = this.findNodeWithData(predicate);
        if (nodeWithData==null){
            return null;
        }
        return nodeWithData.getNode();
    }

    
    
    @Override
    public List<T> getAll(Comparable<T> predicate) {
        List<T> returner = new ArrayList<T>();
       returner = this.findNodeWithDataEorL(root,predicate);
        if (returner==null){
            return new ArrayList<>();
        }
        return returner;
    }


    
    
    
    protected List<T> findNodeWithDataEorL(Node<T> nodeC ,Comparable<T> data) {
    	
        List<T> finish = new ArrayList<T>();
       
        if(nodeC.getNode() ==null) {
        	//  System.out.println("A");
        	
        	  return finish;
          }
        Node<T> current = nodeC;
        Node<T> holder;
        holder = new Node<> ((T) data);
      
        int compare = holder.getNode().compareTo(nodeC.getNode());
       
      finish.addAll(findNodeWithDataEorL(current.context[1],data));
     
            if (compare == 0 || compare == 1) {
    
            	
                finish.add(nodeC.getNode());
            }
           
                // keep looking in the right subtree
       
                finish.addAll(findNodeWithDataEorL(current.context[2],data));
        
        // we're at a null node and did not find data, so it's not in the tree
                
        return finish;
    }

    /**
     * Removes the value data from the tree if the tree contains the value.
     * This method will not attempt to rebalance the tree after the removal and
     * should be updated once the tree uses Red-Black Tree insertion.
     * @return true if the value was remove, false if it didn't exist
     * @throws NullPointerException when the provided data argument is null
     * @throws IllegalArgumentException when data is not stored in the tree
     */
    public boolean remove(T data) throws NullPointerException, IllegalArgumentException {
        // null references will not be stored within this tree
        if (data == null) {
            throw new NullPointerException("This RedBlackTree cannot store null references.");
        } else {
            Node<T> nodeWithData = this.findNodeWithData(data);
            // throw exception if node with data does not exist
            if (nodeWithData == null) {
                throw new IllegalArgumentException("The following value is not in the tree and cannot be deleted: " + data.toString());
            }
            nodeWithData.data.remove(0);
            if(!nodeWithData.data.isEmpty()){
                this.size--;
                return true;
            }
            Node<T> deleteNode=nodeWithData;
            Node<T> fixedNode=null;
            int originalColor=nodeWithData.blackHeight;
            boolean hasRightChild = (nodeWithData.context[2] != NIL);
            boolean hasLeftChild = (nodeWithData.context[1] != NIL);
            if (hasRightChild && hasLeftChild) {
                // has 2 children
                Node<T> successorNode = this.findMinOfRightSubtree(nodeWithData);
                originalColor=successorNode.blackHeight;
                fixedNode=successorNode.context[2];
                if (successorNode.context[0]==nodeWithData){
                    fixedNode.context[0]=successorNode;
                }else{
                    this.replaceNode(successorNode,successorNode.context[2]);
                    successorNode.context[2]=nodeWithData.context[2];
                    successorNode.context[2].context[0]=successorNode;
                }
                this.replaceNode(nodeWithData,successorNode);
                successorNode.context[1]=nodeWithData.context[1];
                successorNode.context[1].context[0]=successorNode;
                successorNode.blackHeight=nodeWithData.blackHeight;
            } else if (hasRightChild) {
                // only right child, replace with right child
                fixedNode=nodeWithData.context[2];
                this.replaceNode(nodeWithData, nodeWithData.context[2]);
            } else if (hasLeftChild) {
                // only left child, replace with left child
                fixedNode=nodeWithData.context[1];
                this.replaceNode(nodeWithData, nodeWithData.context[1]);
            } else {
                // no children, replace node with a null node
                fixedNode=NIL;
                this.replaceNode(nodeWithData, NIL);
            }
            if (originalColor==1){
                deleteFixup(fixedNode);
            }
            this.size--;
            return true;
        }
    }

    private List<T> recursiveSystem(Node<T> node){

            List<T> finish = new ArrayList<T>();

           if(node ==null|| node.getNode() == null){
               return finish;
           }
             //   int compare = holder.getNode().compareTo(current.getNode());
               finish.addAll( recursiveSystem( node.context[1]));

            finish.add(node.getNode());

        finish.addAll( recursiveSystem( node.context[2]));
            // we're at a null node and did not find data, so it's not in the tree
            return finish;

    }
    @Override
    public List<T> getList(List<T> list) {
        return recursiveSystem(this.root);
       // return list;
    }

    private void deleteFixup(Node<T> node){
        Node<T> tmp;
        while(node!=root && node.blackHeight>0){
            if(node==node.context[0].context[1]){
                tmp=node.context[0].context[2];
                if(tmp.blackHeight==0){
                    tmp.blackHeight=1;
                    node.context[0].blackHeight=0;
                    rotate(node.context[0].context[2],node.context[0]);
                    tmp=node.context[0].context[2];
                }
                if(tmp.context[1].blackHeight==1 && tmp.context[2].blackHeight==1){
                    tmp.blackHeight=0;
                    node=node.context[0];
                }else if(tmp.context[2].blackHeight==1){
                    tmp.context[1].blackHeight=1;
                    tmp.blackHeight=0;
                    rotate(tmp.context[1],tmp);
                    tmp=node.context[0].context[2];

                    tmp.blackHeight=node.context[0].blackHeight;
                    node.context[0].blackHeight=1;
                    tmp.context[2].blackHeight=1;
                    rotate(node.context[0].context[2],node.context[0]);
                    node=root;
                }else{
                    tmp.blackHeight=node.context[0].blackHeight;
                    node.context[0].blackHeight=1;
                    tmp.context[2].blackHeight=1;
                    rotate(node.context[0].context[2],node.context[0]);
                    node=root;
                }


            }else{
                tmp=node.context[0].context[1];
                if(tmp.blackHeight==0){
                    tmp.blackHeight=1;
                    node.context[0].blackHeight=0;
                    rotate(node.context[0].context[1],node.context[0]);
                    tmp=node.context[0].context[1];
                }
                if(tmp.context[1].blackHeight==1 && tmp.context[2].blackHeight==1){
                    tmp.blackHeight=0;
                    node=node.context[0];
                }else if(tmp.context[1].blackHeight==1){
                    tmp.context[2].blackHeight=1;
                    tmp.blackHeight=0;
                    rotate(tmp.context[2],tmp);
                    tmp=node.context[0].context[1];

                    tmp.blackHeight=node.context[0].blackHeight;
                    node.context[0].blackHeight=1;
                    tmp.context[1].blackHeight=1;
                    rotate(node.context[0].context[1],node.context[0]);
                    node=root;
                }else{
                    tmp.blackHeight=node.context[0].blackHeight;
                    node.context[0].blackHeight=1;
                    tmp.context[1].blackHeight=1;
                    rotate(node.context[0].context[1],node.context[0]);
                    node=root;
                }
            }
        }
        node.blackHeight=1;
    }

    /**
     * Checks whether the tree contains the value *data*.
     * @param data the data value to test for
     * @return true if *data* is in the tree, false if it is not in the tree
     */
    public boolean contains(T data) {
        // null references will not be stored within this tree
        if (data == null) {
            throw new NullPointerException("This RedBlackTree cannot store null references.");
        } else {
            Node<T> nodeWithData = this.findNodeWithData(data);
            // return false if the node is null, true otherwise
            return (nodeWithData != null);
        }
    }

    /**
     * Helper method that will replace a node with a replacement node. The replacement
     * node may be null to remove the node from the tree.
     * @param nodeToReplace the node to replace
     * @param replacementNode the replacement for the node (may be null)
     */
    protected void replaceNode(Node<T> nodeToReplace, Node<T> replacementNode) {
        if (nodeToReplace == null) {
            throw new NullPointerException("Cannot replace null node.");
        }
        if (nodeToReplace.context[0] == null) {
            // we are replacing the root
            if (replacementNode != null)
                replacementNode.context[0] = null;
            this.root = replacementNode;
        } else {
            // set the parent of the replacement node
            replacementNode.context[0] = nodeToReplace.context[0];
            // do we have to attach a new left or right child to our parent?
            if (nodeToReplace.isRightChild()) {
                nodeToReplace.context[0].context[2] = replacementNode;
            } else {
                nodeToReplace.context[0].context[1] = replacementNode;
            }

        }
    }
/////////////////////////////////////////////////
    /**
     * Helper method that will return the inorder successor of a node with two children.
     * @param node the node to find the successor for
     * @return the node that is the inorder successor of node
     */
    protected Node<T> findMinOfRightSubtree(Node<T> node) {
        if (node.context[1] == NIL && node.context[2] == NIL) {
            throw new IllegalArgumentException("Node must have two children");
        }
        // take a steop to the right
        Node<T> current = node.context[2];
        while (true) {
            // then go left as often as possible to find the successor
            if (current.context[1] == NIL) {
                // we found the successor
                return current;
            } else {
                current = current.context[1];
            }
        }
    }

    /**
     * Helper method that will return the node in the tree that contains a specific
     * value. Returns null if there is no node that contains the value.
     * @return the node that contains the data, or null of no such node exists
     */
    protected Node<T> findNodeWithData(Comparable<T> data) {
        Node<T> current = this.root;
        Node<T> holder;
        holder = new Node<> ((T) data);
        while (current != NIL||current.getNode()!=null||current!=null) {
            int compare = holder.getNode().compareTo(current.getNode());
            if (compare == 0) {
                // we found our value
                return current;
            } else if (compare < 0) {
                // keep looking in the left subtree
                current = current.context[1];
            } else {
                // keep looking in the right subtree
                current = current.context[2];
            }
        }
        // we're at a null node and did not find data, so it's not in the tree
        return null;
    }

    /**
     * This method performs an inorder traversal of the tree. The string 
     * representations of each data value within this tree are assembled into a
     * comma separated string within brackets (similar to many implementations 
     * of java.util.Collection, like java.util.ArrayList, LinkedList, etc).
     * @return string containing the ordered values of this tree (in-order traversal)
     */
    public String toInOrderString() {
        // generate a string of all values of the tree in (ordered) in-order
        // traversal sequence
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        if (this.root != null) {
            Stack<Node<T>> nodeStack = new Stack<>();
            Node<T> current = this.root;
            while (!nodeStack.isEmpty() || current != NIL) {
                if (current == NIL) {
                    Node<T> popped = nodeStack.pop();
                    sb.append(popped.data.get(0).toString());
                    if(!nodeStack.isEmpty() || popped.context[2] != NIL) sb.append(", ");
                    current = popped.context[2];
                } else {
                    nodeStack.add(current);
                    current = current.context[1];
                }
            }
        }
        sb.append(" ]");
        return sb.toString();
    }

    /**
     * This method performs a level order traversal of the tree. The string
     * representations of each data value
     * within this tree are assembled into a comma separated string within
     * brackets (similar to many implementations of java.util.Collection).
     * This method will be helpful as a helper for the debugging and testing
     * of your rotation implementation.
     * @return string containing the values of this tree in level order
     */
    public String toLevelOrderString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        if (this.root != null) {
            LinkedList<Node<T>> q = new LinkedList<>();
            q.add(this.root);
            while(!q.isEmpty()) {
                Node<T> next = q.removeFirst();
                if(next.context[1] != NIL) q.add(next.context[1]);
                if(next.context[2] != NIL) q.add(next.context[2]);
                sb.append(next.data.get(0).toString());
                if(!q.isEmpty()) sb.append(", ");
            }
        }
        sb.append(" ]");
        return sb.toString();
    }

    public String toString() {
        return "level order: " + this.toLevelOrderString() +
                "\nin order: " + this.toInOrderString();
    }
}