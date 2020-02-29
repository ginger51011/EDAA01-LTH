package queue_singlelinkedlist;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.*;


public class TestAppendFifoQueue {

	@Test
	public void testEmpyQueues() {
		FifoQueue q1 = new FifoQueue();
		FifoQueue q2 = new FifoQueue();
		
		try {
			q1.append(q2);
			assertTrue("q1 är inte tom", q1.isEmpty());
		} catch (IllegalArgumentException e){
			// Lyckat test
		}	
	}
	
	@Test
	public void testEmptyToNonEmpty() {
		FifoQueue<Integer> q1 = new FifoQueue<Integer>();
		FifoQueue<Integer> q2 = new FifoQueue<Integer>();
		q1.offer(1);
		
		try {
			q1.append(q2);
			assertTrue("q1 har inte rätt storlek", q1.size() == 1);
			assertEquals("q1 har inte rätt innehåll.", (int) q1.peek(), 1);
		} catch (Exception e) {
			fail("Throws " + e);
		} 
	}
	
	@Test
	public void testNonEmptyToEmpty() {
		FifoQueue<Integer> q1 = new FifoQueue<Integer>();
		FifoQueue<Integer> q2 = new FifoQueue<Integer>();
		q2.offer(1);
		q2.offer(2);
		
		q1.append(q2);
		
		assertEquals("q1 har fel size", 2, q1.size());
		assertEquals("q2 har fel size", 0, q2.size());
		assertEquals("q1 är har fel head", 1, (int) q1.poll());
		assertEquals("q1 har fel andraplats", 2, (int) q1.poll());
	}
	
	@Test
	public void testTwoQueues() {
		FifoQueue<Integer> q1 = new FifoQueue<Integer>();
		FifoQueue<Integer> q2 = new FifoQueue<Integer>();
		q2.offer(3);
		q2.offer(4);
		q1.offer(1);
		q1.offer(2);
		
		q1.append(q2);
		
		assertEquals("q1 har inte rätt size", 4, q1.size());
		assertEquals("q2 har fel size", 0, q2.size());
		
		for (int i = 1; i < 5; i++) {
			assertEquals("q1 har fel innehåll", i, (int) q1.poll());
		}
	}

	
	@Test
	public void testSameQueue() {
		FifoQueue<Integer> q = new FifoQueue<Integer>();
		q.offer(1);
		q.offer(2);
		
		try {
			q.append(q);
			fail("Ska throwa IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			// Funkar bra
		}
	}
}
