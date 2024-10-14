package nl.han.ica.datastructures;

public class HANStack<T> implements IHANStack<T> {

    private HANLinkedList<T> linkedList = new HANLinkedList<>();

    @Override
    public void push(T value) {
        linkedList.addFirst(value);
    }

    @Override
    public T pop() {
        if (linkedList.getSize() == 0) {
            throw new IllegalStateException("Stack is empty.");
        }
        T topElement = linkedList.getFirst();
        linkedList.removeFirst();
        return topElement;
    }

    @Override
    public T peek() {
        if (linkedList.getSize() == 0) {
            throw new IllegalStateException("Stack is empty.");
        }
        return linkedList.getFirst();
    }
}

