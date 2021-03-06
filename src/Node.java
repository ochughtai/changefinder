import java.util.*;

public class Node<T>{
    private T data = null;
    private List<Node<T>> children = new ArrayList<Node<T>>();
    private Node<T> parent = null;

    public Node(T data) {
        this.data = data;
    }

    public void addChild(Node<T>child) {
        child.setParent(this);
        this.children.add(child);
    }

    public void addChild(T data) {
        Node<T> newChild = new Node<>(data);
        newChild.setParent(this);
        children.add(newChild);
    }

    public void addChildren(List<Node<T>> children) {
        for(Node<T> t : children) {
            t.setParent(this);
        }
        this.children.addAll(children);
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private void setParent(Node<T>parent) {
        this.parent = parent;
    }

    public Node<T>getParent() {
        return parent;
    }
}