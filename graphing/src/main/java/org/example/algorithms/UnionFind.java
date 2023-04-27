package org.example.algorithms;

/**
 * Holds the Union find data structure, works with ints, objects need separate mapping to int.
 *
 * @author David Nistor
 */
public class UnionFind {
    private int size;
    private int[] sizes;
    private int[] inWhoseGroup;
    private int numComponents;

    /**
     * Constructs a UnionFind object with the given size.
     *
     * @param size the input size
     */
    public UnionFind(int size) {
        if(size < 0) {
            throw new IllegalArgumentException("Can't have size smaller than zero...");
        }

        this.size = size;
        this.numComponents = size;
        sizes = new int[size];
        inWhoseGroup = new int[size];

        for(int i = 0; i < size;  i++) {
            inWhoseGroup[i] = i; //link to itself (self root)
            sizes[i] = 1; // each component is originally of size one
        }
    }

    /**
     * Finds the union the number belongs to.
     *
     * @param p the number
     * @return the {@code root} of the union the number belongs to
     */
    public int find(int p) {
        //find the root of the component set
        int root = p;

        while(root != inWhoseGroup[root]){
            root = inWhoseGroup[root];
        }
        //compress the path leading back to the root
        while(p != root) {
            int next = inWhoseGroup[p];
            inWhoseGroup[p] = root;
            p = next;
        }

        return root;
    }

    /**
     * Finds out if two numbers are in the same union, returning true iff they do.
     *
     * @param p the first number
     * @param q the second number
     * @return {@code true} <==> {@code p} and {@code q} are in the same union
     */
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    /**
     * Returns the size of the union that contains {@code p}.
     *
     * @param p the number
     * @return the size of the union of the root of {@code p}
     */
    public int componentSize(int p) {
        return sizes[find(p)];
    }

    /**
     * Returns the number of elements in this union find.
     *
     * @return the number of elements
     */
    public int size() {
        return size;
    }

    /**
     * Returns the number if components of this union find.
     *
     * @return the number of union groups
     */
    public int components() {
        return numComponents;
    }

    /**
     * Puts p and q in the same union/group.
     *
     * @param p the first number
     * @param q the second number
     */
    public void unify(int p, int q) {
        int root1 = find(p);
        int root2 = find(q);

        if(root1 == root2) {
            return;
        }
        //merge two components sets together
        //merge smaller component/set into the larger one
        if(sizes[root1] < sizes[root2]) {
            sizes[root2] += sizes[root1];
            inWhoseGroup[root1] = root2;
        } else {
            sizes[root1] += sizes[root2];
            inWhoseGroup[root2] = root1;
        }
        --numComponents;
    }
}