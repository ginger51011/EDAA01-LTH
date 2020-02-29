package queue_singlelinkedlist;

import java.util.*;

public class FifoQueue<E> extends AbstractQueue<E> implements Queue<E> {
	private QueueNode<E> last;
	private int size;

	public FifoQueue() {
		super();
		last = null;
		size = 0;
	}

	/**
	 * Inserts the specified element into this queue, if possible post: The
	 * specified element is added to the rear of this queue
	 * 
	 * @param e the element to insert
	 * @return true if it was possible to add the element to this queue, else false
	 */
	public boolean offer(E e) { // Ska här vara en try-'n'-catch?
		QueueNode<E> qn = new QueueNode<E>(e);

		// Måste ha fall för om last e null eller ej
		if (last != null) {
			// Ser först till att qn trycks in mellan last och den första
			qn.next = last.next;
			last.next = qn;
			// Nya last blir qn när gamla last är tryckt åt sidan
			last = qn;
			size++;
			return true;
		} else if (last == null) {
			last = qn;
			last.next = last;
			size++;
			return true;
		}
		return false;
	}

	/**
	 * Returns the number of elements in this queue
	 * 
	 * @return the number of elements in this queue
	 */
	public int size() {
		return size;
	}

	/**
	 * Retrieves, but does not remove, the head of this queue, returning null if
	 * this queue is empty
	 * 
	 * @return the head element of this queue, or null if this queue is empty
	 */
	public E peek() {
		if (size == 0) {
			return null;
		}
		// Next från last är ju första, vi har en cirkulär lista
		return last.next.element;
	}

	/**
	 * Retrieves and removes the head of this queue, or null if this queue is empty.
	 * post: the head of the queue is removed if it was not empty
	 * 
	 * @return the head of this queue, or null if the queue is empty
	 */
	public E poll() {
		if (size == 0) {
			return null;
		} else if (size == 1) { // Om vi bara har ett element.
			E e = last.element;
			last = null;
			size--;
			return e;
		}
		QueueNode<E> head = last.next; // Så vi har reda på head
		last.next = head.next; // Så vi hoppar över head
		size--;
		return head.element; // Returnerar elementet från head
	}

	/**
	 * Returns an iterator over the elements in this queue
	 * 
	 * @return an iterator over the elements in this queue
	 */
	public Iterator<E> iterator() {
		return new QueueIterator();
	}

	/*
	 * Returns a QueueIterator
	 * 
	 * @return a QueueIterator over the elements
	 */
	private class QueueIterator implements Iterator<E> {
		private QueueNode<E> pos;
		private boolean hasStarted = false;

		/* Konstruktor */
		private QueueIterator() {
			if (last != null) {
				pos = last; // Börjar på första
			} else {
				pos = null;
			}
		}

		public boolean hasNext() { // Om något ollon skapar iterator på en tom lista
			if (pos == null) {
				return false;
			} else if (pos == last && hasStarted) { // Om vi gått igenom hela listan en gång
				return false;
			} else if (pos.next != null) {
				return true;
			}
			return false;
		}

		public E next() {
			if (pos == last && hasStarted) {
				throw new NoSuchElementException("Du har använt hela listan ditt ägg");
			} else if (pos != null) { // Om något ollon skapar iterator på en tom lista
				pos = pos.next;
				hasStarted = true;
				return pos.element;
			} else {
				throw new NoSuchElementException("Du har en tom lista ditt ägg");
			}
		}
	}
	
	public void append(FifoQueue<E> q) {
		if (this == q) { // Kollar så de inte refererar till samma objekt
			throw new IllegalArgumentException("Det är samma lista din toffel.");
		} else if (q.last == null) { // Om q är tom
			//Gör ingenting
		} else if (this.last == null) { // Om this är tom så blir this q
			this.last = q.last;
			this.size = q.size;
		} else { // Annars appenda normalt
			this.size = this.size + q.size; // Ändrar storlek först
			QueueNode<E> temp = this.last.next; // Team är head av this
			this.last.next = q.last.next;
			this.last = q.last;
			q.last.next = temp;
		}
		q.last = null;
		q.size = 0;
	}

	private static class QueueNode<E> {
		E element;
		QueueNode<E> next;

		private QueueNode(E x) {
			element = x;
			next = null;
		}
	}

}
