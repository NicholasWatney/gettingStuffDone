import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Your implementation of an AVL.
 *
 * @author Ernest Locke
 * @version 2.1
 * @userid ELocke69
 * @GTID 123 456 789
 *
 * Collaborators: George Burdell and Buzz.
 *
 * Resources: Treasure chest.
 */
public class AVL<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     * <p>
     * This constructor should initialize an empty AVL.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     * <p>
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {

        if (data == null) {
            throw new IllegalArgumentException("The collection is null.");
        }

        for (T element : data) {
            add(element);
        }
    }

    /**
     * Adds the element to the tree.
     * <p>
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     * <p>
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     * <p>
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {

        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }

        root = rAdd(data, root);
    }

    /**
     * Recursively add to the AVL.
     * @param data data to add
     * @param node current node to keep track
     * @return return the current node
     */
    private AVLNode<T> rAdd(T data, AVLNode<T> node) {

        if (node == null) {
            size++;
            return rotation(new AVLNode<T>(data));

        } else {

            int compare = data.compareTo(node.getData());

            if (compare < 0) {
                node.setLeft(rAdd(data, node.getLeft()));

            } else if (compare > 0) {
                node.setRight(rAdd(data, node.getRight()));
            }
        }

        return rotation(node);
    }

    /**
     * Performs a left rotation.
     * @param node current parent node
     * @return returns new parent node
     */
    private AVLNode<T> leftRotation(AVLNode<T> node) {

        AVLNode<T> child = node.getRight();
        node.setRight(child.getLeft());
        child.setLeft(node);
        update(node);
        update(child);
        return child;
    }

    /**
     * Performs a right rotation.
     * @param node current parent node
     * @return returns new parent node
     */
    private AVLNode<T> rightRotation(AVLNode<T> node) {

        AVLNode<T> child = node.getLeft();
        node.setLeft(child.getRight());
        child.setRight(node);
        update(node);
        update(child);
        return child;
    }

    /**
     * Helper method that performs the correction rotation
     * @param node current parent node
     * @return returns new parent node
     */
    private AVLNode<T> rotation(AVLNode<T> node) {

        update(node);

        if (Math.abs(node.getBalanceFactor()) < 2) {
            return node;

        } else if (node.getBalanceFactor() == -2) {

            if (node.getRight().getBalanceFactor() == 1) {
                node.setRight(rightRotation(node.getRight()));
                return leftRotation(node);

            } else {
                return leftRotation(node);
            }

        } else {

            if (node.getLeft().getBalanceFactor() == -1) {
                node.setLeft(leftRotation(node.getLeft()));
                return rightRotation(node);

            } else {
                return rightRotation(node);
            }

        }
    }

    /**
     * Updates the height and balancefactor.
     * @param node updates node's height and balancefactor
     */
    private void update(AVLNode<T> node) {

        node.setHeight(1 + Math.max(rHeight(node.getLeft()), rHeight(node.getRight())));
        node.setBalanceFactor(rHeight(node.getLeft()) - rHeight(node.getRight()));
    }

    /**
     * Helper method to get height
     * @param node node to get height
     * @return returns height of node
     */
    private int rHeight(AVLNode<T> node) {

        if (node == null) {
            return -1;

        } else {
            return node.getHeight();
        }
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {

        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }

        AVLNode<T> dummy = new AVLNode<T>(null);
        root = rRemove(data, root, dummy);

        return dummy.getData();

    }

    /**
     * Helper method that removes data from the AVL
     * @param data data to remove from tree
     * @param node track of current node traversing down the tree
     * @param dummy dummy node to hold the data that will be returned
     * @return data that is removed from the AVL
     */
    private AVLNode<T> rRemove(T data, AVLNode<T> node, AVLNode<T> dummy) {

        if (node == null) {
            throw new NoSuchElementException("Data does not exist.");
        }

        int compare = data.compareTo(node.getData());

        if (compare < 0) {
            node.setLeft(rRemove(data, node.getLeft(), dummy));

        } else if (compare > 0) {
            node.setRight(rRemove(data, node.getRight(), dummy));

        } else {

            dummy.setData(node.getData());
            size--;

            if ((node.getLeft() == null) && (node.getRight() == null)) {
                return null;

            } else if ((node.getLeft() != null) && (node.getRight() == null)) {
                return node.getLeft();

            } else if ((node.getLeft() == null) && (node.getRight() != null)) {
                return node.getRight();

            } else {
                AVLNode<T> predecessor = new AVLNode<T>(null);
                node.setLeft(rPredecessor(node.getLeft(), predecessor));
                node.setData(predecessor.getData());
            }

        }

        return rotation(node);
    }

    /**
     * Predecessor helper method that returns the predecessor
     * @param node keeps track of the current node
     * @param predecessor stores the predecessor
     * @return returns the current node
     */
    private AVLNode<T> rPredecessor(AVLNode<T> node, AVLNode<T> predecessor) {

        if (node.getRight() == null) {
            predecessor.setData(node.getData());
            return node.getLeft();

        } else {
            node.setRight(rPredecessor(node.getRight(), predecessor));
            return rotation(node);
        }
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {

        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }

        return rGet(data, root);

    }

    /**
     * Helper method to recursively call itself until it finds the data.
     * @param data data to find
     * @param node node to keep track
     * @return returns the data got
     */
    private T rGet(T data, AVLNode<T> node) {

        if (node == null) {
            throw new NoSuchElementException("The data is not in the tree");
        }

        int compare = data.compareTo(node.getData());

        if (compare < 0) {
            return rGet(data, node.getLeft());

        } else if (compare > 0) {
            return rGet(data, node.getRight());

        } else {
            return node.getData();
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {

        try {
            get(data);
            return true;

        } catch (NoSuchElementException e) {
            return false;
        }
    }
    /**
     * Returns the height of the root of the tree.
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {

        return rHeight(root);
    }



    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {

        root = null;
        size = 0;

    }

    /**
     * Ex:
     * Given the following AVL composed of Integers
     *              50
     *            /    \
     *         25      75
     *        /  \     / \
     *      13   37  70  80
     *    /  \    \      \
     *   12  15    40    85
     *  /
     * 10
     *
     * elementsWithinDistance(37, 3) should return the set {12, 13, 15, 25,
     * 37, 40, 50, 75}
     * elementsWithinDistance(85, 2) should return the set {75, 80, 85}
     * elementsWithinDistance(13, 1) should return the set {12, 13, 15, 25}
     *
     * @param data     the data to begin calculating distance from
     * @param distance the maximum distance allowed
     * @return the set of all data within a certain distance from the given data
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   is the data is not in the tree
     * @throws java.lang.IllegalArgumentException if distance is negative
     */
    public Set<T> elementsWithinDistance(T data, int distance) {

        if (data == null || distance < 0) {
            throw new IllegalArgumentException("The data is null");
        }

        Set<T> set = new HashSet<T>(size);
        rFind(data, root, 0, distance, set);

        return set;
    }

    /**
     * A helper method that finds the node and add other nodes to the set
     * @param data data to find
     * @param current current node to keep track
     * @param distance current distance away from node
     * @param maximumDistance maximum distance way from node
     * @param set set to add data with distance smaller than maximumDistance
     * @return current distance away from data
     */
    private int rFind(T data, AVLNode<T> current, int distance, int maximumDistance, Set<T> set) {

        if (current == null) {
            throw new NoSuchElementException("The data is not in the tree");
        }

        int compare = data.compareTo(current.getData());

        if (compare < 0) {
            distance = rFind(data, current.getLeft(), distance, maximumDistance, set);

            if (distance > 0 && distance <= maximumDistance) {
                set.add(current.getData());
                both(current.getRight(), distance + 1, maximumDistance, set);
            }

        } else if (compare > 0) {
            distance = rFind(data, current.getRight(), distance, maximumDistance, set);

            if (distance > 0 && distance <= maximumDistance) {
                set.add(current.getData());
                both(current.getLeft(), distance + 1, maximumDistance, set);
            }

        } else {

            set.add(current.getData());
            both(current.getLeft(), distance + 1, maximumDistance, set);
            both(current.getRight(), distance + 1, maximumDistance, set);
        }

        return distance + 1;
    }


    /**
     * A helper method that adds children that qualify to the set.
     * @param current current node to keep track
     * @param distance current distance away from data node
     * @param maximumDistance maximumDistance that allows data to be added to Set
     * @param set set to keep track of data added
     */
    private void both(AVLNode<T> current, int distance, int maximumDistance, Set<T> set) {

        if (current == null) {
            return;

        } else {

            if (distance <= maximumDistance) {

                set.add(current.getData());
                both(current.getRight(), distance + 1, maximumDistance, set);
                both(current.getLeft(), distance + 1, maximumDistance, set);

            }
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
