package com.xuehai.impl;

import java.util.NoSuchElementException;

/**
 * Created by 340312 on 2017/11/10.
 */
public class LinkedListList<E> {
    private class Node<E> {
        E item;
        Node<E> next;
        Node<E> pre;

        Node(Node<E> pre, E item, Node<E> next) {
            this.item = item;
            this.next = next;
            this.pre = pre;
        }
    }

    transient Node<E> first;
    transient Node<E> last;
    transient int size = 0;
    transient int modCount = 0;

    public int size() {
        return size;
    }

    public boolean add(E e) {
        Node<E> l = last;
        Node<E> newNode = new Node(last, e, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
        modCount++;
        return true;
    }

    public E get(int index) {
        checkIndex(index);
        if (index < (size >> 1)) {
            Node<E> f = first;
            for (int i = 0; i < index; i++) {
                f = f.next;
            }
            return f.item;
        } else {
            Node<E> l = last;
            for (int i = size - 1; i > index; i--) {
                l = l.pre;
            }
            return l.item;
        }
    }

    public boolean remove(Object o) {
        if (size <= 0) {
            throw new NoSuchElementException("size:" + size);
        }
        for (Node<E> f = first; f != last; f = f.next) {
            if (o == null) {
                if (f.item == null) {
                    unlink(f);
                    return true;
                }
            } else {
                if (o.equals(f.item)) {
                    unlink(f);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean remove(int index) {
        Node<E> x = node(index);
        unlink(x);
        return true;
    }

    private void unlink(Node<E> h) {
        Node<E> next = h.next;
        Node<E> pre = h.pre;
        if (pre == null) {
            first = next;
        } else {
            pre.next = next;
            h.pre = null;
        }
        if (next == null) {
            last = pre;
        } else {
            next.pre = pre;
            h.next = null;
        }
        h.item = null;
        size--;
        modCount++;
    }

    private Node<E> node(int index) {
        checkIndex(index);
        if (index < (size >> 1)) {
            Node<E> f = first;
            for (int i = 0; i < index; i++) {
                f = f.next;
            }
            return f;
        } else {
            Node<E> l = last;
            for (int i = size - 1; i > index; i--) {
                l = l.pre;
            }
            return l;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("index:" + index + ",size:" + size);
        ;
    }
}
