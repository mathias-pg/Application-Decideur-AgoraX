package modele;



import java.util.ArrayList;

public class Tree<T> implements TreeI<T>{

    private T data;
    private ArrayList<Tree<T>> children;
    private Tree<T> parent;

    public Tree(){}

    @SafeVarargs
    public Tree(T data, Tree<T>... childs) {
        this.data = data;
        this.children = new ArrayList<Tree<T>>();
        this.addChildren(childs);
        this.parent = null;
    }

    public T data(){
        return this.data;
    }

    public Tree<T> child(int n) {
        try{
            return this.children().get(n);
        }
        catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    private ArrayList<Tree<T>> children(){
        return this.children;
    }

    public int nbChildren(){
        return this.children().size();
    }

    public Tree<T> parent(){
        return this.parent;
    }

    @SafeVarargs
    public final void addChildren(Tree<T>... childs) {
        for (Tree<T> child : childs){
            if (child != null){
                child.parent = this;
                this.children.add(child);
            }
        }
    }

    /* Attention : la méthode suivante ne peut que modifier un enfant existant */
    public void setChild(int i, Tree<T> child) {
        this.children.set(i, child);
        if (child != null){
            child.parent = this;
        }
    }

    public void remove(){
        if (this.parent() == null){
            throw new IllegalArgumentException("The root cannot be removed.\n");
        }
        /* Merci à Mathieu pour le rapport de bug */
        for(int i = 0; i < this.parent().nbChildren(); i++){
           if (this.parent().child(i) == this){
                this.parent().children().remove(i);
                /* C'est la méthode remove de la classe Arraylist, pas un appel récursif */
           }
        }
    }

     /* Adapted from VasiliNovikov@StackOverflow */

    private void print(StringBuilder buffer, String prefix, String childrenPrefix) {
        buffer.append(prefix);
        buffer.append(this.data());
        buffer.append('\n');
        for (int i = 0; i < nbChildren(); i++) {
            Tree<T> next = this.child(i);
            if (i < nbChildren() -1) {
                next.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
            } else {
                next.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
            }
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
}