package nl.han.ica.datastructures;

public class HANLinkedList<T> implements IHANLinkedList<T>{

    ListNode<T> headerNode = new ListNode<>();
    private int size = 0;

    @Override
    public void addFirst(T value) {
        ListNode<T> newNode = new ListNode<>();
        newNode.element = value;
        newNode.nextNode = headerNode.nextNode;
        headerNode.nextNode = newNode;
        size++;
    }

    @Override
    public void clear() {
        headerNode.nextNode = null;
        size = 0;
    }

    @Override
    public void insert(int index, T value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        if(index == 0) {
            addFirst(value);
        } else {
            ListNode<T> newNode = new ListNode<>();
            newNode.element = value;
            ListNode<T> neededNode = headerNode;
            for (int i = 0; i < index; i++) {
                neededNode = neededNode.nextNode;
            }
            newNode.nextNode = neededNode.nextNode;
            neededNode.nextNode = newNode;
            size++;
        }
    }

    @Override
    public void delete(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Position out of bounds: " + index);
        }

        ListNode<T> prevNode = headerNode;
        for (int i = 0; i < index; i++) {
            prevNode = prevNode.nextNode;
        }

        prevNode.nextNode = prevNode.nextNode.nextNode;
        size--;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Position out of bounds: " + index);
        }

        ListNode<T> currentNode = headerNode.nextNode;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.nextNode;
        }
        return currentNode.element;
    }

    @Override
    public void removeFirst() {
        if (size == 0) {
            throw new IllegalStateException("List is empty.");
        }
        headerNode.nextNode = headerNode.nextNode.nextNode;
        size--;
    }

    @Override
    public T getFirst() {
        if (size == 0) {
            throw new IllegalStateException("List is empty.");
        }
        return headerNode.nextNode.element;
    }

    @Override
    public int getSize() {
        return size;
    }
}
