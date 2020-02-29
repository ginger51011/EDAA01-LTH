package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bst.BinarySearchTree;

public class TestBinarySearchTree {
	BinarySearchTree<Integer> bst;
	
	@Before
	public void setUp() throws Exception {
		bst = new BinarySearchTree<Integer>();
	}
	
	@After
	public void tearDown() throws Exception {
		bst = null;
	}
	
	/**
	 * Testar add
	 */
	@Test
	public void testAdd() {
		try {
			assertTrue(bst.add(1));
			assertTrue(bst.add(13));
			assertTrue(bst.add(0));
			assertTrue(bst.add(6));
			assertFalse(bst.add(13), "Dublett blev adderad, ska ej ske");
		} catch (Exception e) {
			fail("Exception " + e + " raised");
		}
	}
	
	/**
	 * Testar height()
	 */
	@Test
	public void testHeight() {
		try {
			assertTrue(bst.height() == 0, "Height does not return correct value for empty tree");
			assertTrue(bst.add(1), "Tree could not add number");
			assertTrue(bst.add(13));
			assertTrue(bst.add(0));
			assertTrue(bst.add(6));
			assertTrue(bst.height() == 3, "Fel, höjden ska vara 3 men var " + bst.height());
		} catch  (Exception e) {
			fail("Exception " + e + " raised");
		}
	}
	
	/**
	 * Testar printTree()
	 */
	@Test
	public void testPrintTree() {
		try {
			assertTrue(bst.height() == 0, "Height does not return correct value for empty tree");
			assertTrue(bst.add(1), "Tree could not add number");
			assertTrue(bst.add(13));
			assertTrue(bst.add(0));
			assertTrue(bst.add(6));
			bst.printTree();
		} catch (Exception e) {
			fail("Exception: " + e + " raised");
		}
	}

}
