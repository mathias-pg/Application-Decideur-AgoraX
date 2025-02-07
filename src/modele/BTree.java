package modele;



import java.util.ArrayList;

public class BTree<T> extends Tree<T> implements BTreeI<T>{

    private T data;
    private BTree<T> left;
    private BTree<T> right;
    private BTree<T> parent;

    public BTree(){}

    @SafeVarargs
    public BTree(T data, BTree<T>... child) {
        this.data = data;
        if (child.length > 0){
            this.setLeft(child[0]);
        }
        if (child.length > 1){
            this.setRight(child[1]);
        }
        this.parent = null;
    }

    public T data(){
        return this.data;
    }

    public BTree<T> parent(){
        return this.parent;
    }

    public BTree<T> left() {
        return this.left;
    }

    public void setLeft(BTree<T> t) {
        this.left = t;
        if (t != null){
            this.left.parent = this;
        }
    }

    public BTree<T> right() {
        return this.right;
    }

    public void setRight(BTree<T> t) {
        this.right = t;
        if (t != null){
            this.right.parent = this;
        }
    }
    
    public void remove(){
        if (this.parent() == null){
            throw new IllegalArgumentException("The root cannot be removed.\n");
        }
        if (this.parent().left() == this){
            this.parent().setLeft(null);
        }
        if (this.parent().right() == this){
            this.parent().setRight(null);
        }
    }

     /* Adapted from VasiliNovikov@StackOverflow */

    private void print(StringBuilder buffer, String prefix, String childrenPrefix) {
        buffer.append(prefix);
        buffer.append(this.data());
        buffer.append('\n');
        if (this.left() != null && this.right() != null){
            this.left().print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
        }
        if (this.left() != null && this.right() == null){
            this.left().print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
        }
        if (this.right != null){
            this.right().print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
        }
        
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder(50);
        print(buffer, "", "");
        return buffer.toString();
    }

    public void display() {
        System.out.println(this.toString());
    }

    /* Pour la compatibilité avec Tree<T> */

    private ArrayList<BTree<T>> children(){
        ArrayList<BTree<T>> result = new ArrayList<>();
        if (this.left() != null){result.add (this.left());}
        if (this.right() != null){result.add (this.right());}
        return result;
    }

    public BTree<T> child(int n){
        try{
            return this.children().get(n);
        }
        catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    public void setChild(int i, BTree<T> child){
        if (i == 0){
            this.setLeft(child);
            return;
        }
        if (i == 1){
            this.setRight(child);
            return;
        }
        throw new IndexOutOfBoundsException();
    }
    
    @SafeVarargs
    public final void addChildren(BTree<T>... childs){
        if (childs.length > 0){
            this.setLeft(childs[0]);
            return;
        }
        if (childs.length > 1){
            this.setRight(childs[1]);
            return;
        }
    }

    public int nbChildren(){
        return this.children().size();
    }
}