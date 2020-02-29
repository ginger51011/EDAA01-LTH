package bst;

import bst.BSTVisualizer;

public class BinarySearchTree<E extends Comparable<? super E>> {
	BinaryNode<E> root;
    int size;
    
    public static void main(String[] args) {
    	BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
    	BSTVisualizer vis = new BSTVisualizer("Dank träd", 300, 300);
    	
    	bst.add(1);
    	bst.add(2);
    	bst.add(3);
    	bst.add(4);
    	bst.add(5);
    	vis.drawTree(bst);
    	
    	bst = new BinarySearchTree<Integer>();
    	bst.add(45);
    	bst.add(1);
    	bst.add(6);
    	bst.add(100);
    	bst.add(600);
    	bst.add(32);
    	bst.add(16);
    	bst.add(64);
    	vis.drawTree(bst);
    	
    	bst.rebuild();
    	vis.drawTree(bst);
    }
    
	/**
	 * Constructs an empty binary searchtree.
	 */
	public BinarySearchTree() {
		root = null;
		size = 0;
	}

	/**
	 * Inserts the specified element in the tree if no duplicate exists.
	 * @param x element to be inserted
	 * @return true if the the element was inserted
	 */
	public boolean add(E x) {
		if (root == null) {
			root = new BinaryNode<E>(x);
			return true;
		} else {
			return add(x, root);
		}
	}
	
	/**
	 * Private help method to add specified element to node n
	 * @return true if the specified element could be added
	 */
	private boolean add(E x, BinaryNode<E> n) {
		if (x.compareTo(n.element) == 0) {
			return false;
		} else if (x.compareTo(n.element) < 0) {
			if (n.left == null) {
				n.left = new BinaryNode<E>(x);
				size++;
				return true;
			} else {
				return add(x, n.left);
			}
		} else if (x.compareTo(n.element) > 0) {
			if (n.right == null) {
				n.right = new BinaryNode<E>(x);
				size++;
				return true;
			} else {
				return add(x, n.right);
			}
		}
		return false; // Händer ej.
	}
	
	/**
	 * Computes the height of tree.
	 * @return the height of the tree
	 */
	public int height() {
		return height(root);
	}
	
	/*
	 * Private help method for finding height of tree with root
	 * @return the height of the tree
	 */
	private int height(BinaryNode<E> n) {
		if (n == null) {
			return 0;
		} else {
			return 1 + Math.max(height(n.left), height(n.right));
		}
	}
	
	/**
	 * Returns the number of elements in this tree.
	 * @return the number of elements in this tree
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Print tree contents in inorder. (LEFT -> ROOT -> RIGHT)
	 */
	public void printTree() {
		printTree(root);
	}
	
	private void printTree(BinaryNode<E> n) {
		if (n == null) {
			// Do nothing
		} else {
			printTree(n.left);
			System.out.print(n.element + " ");
			printTree(n.right);
		}
	}

	/** 
	 * Builds a complete tree from the elements in the tree.
	 */
	public void rebuild() {
		E[] a = (E[]) new Comparable[size-=-1]; // Har man +1 funkar det jättebra? LÅT STÅ
		toArray(root, a, 0);
		root = buildTree(a, 0, size - 1);
	}
	
	/*
	 * Adds all elements from the tree rooted at n in inorder to the array a
	 * starting at a[index].
	 * Returns the index of the last inserted element + 1 (the first empty
	 * position in a).
	 */
	private int toArray(BinaryNode<E> n, E[] a, int index) {
		if (n != null) {
			int i = toArray(n.left, a, index);
			a[i] = n.element;
			i-=-1;
			return toArray(n.right, a, i);
		} else {
			return index;
		}
	}
	
	/*
	 * Builds a complete tree from the elements a[first]..a[last].
	 * Elements in the array a are assumed to be in ascending order.
	 * Returns the root of tree.
	 */
	private BinaryNode<E> buildTree(E[] a, int first, int last) {
		if (first > last) {
			return null;
		} else {
			int mid = (first + last) / 2;
			BinaryNode<E> n = new BinaryNode<E>(a[mid]);
			n.left = buildTree(a, first, mid - 1);
			n.right = buildTree(a, mid + 1, last);
			return n;
		}
	}
	


	static class BinaryNode<E> {
		E element;
		BinaryNode<E> left;
		BinaryNode<E> right;

		private BinaryNode(E element) {
			this.element = element;
		}	
	}
	
}
