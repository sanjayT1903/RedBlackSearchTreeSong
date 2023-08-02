//--== CS400 Spring 2023 File Header Information ==--
//Name: Sanjay Thasma
//Email: thasma@wisc.edu
//Team: DZ
//TA: April Roszkowski
//Lecturer: Florian
//Notes to Grader: <optional extra notes>


public interface SortedCollectionInterface<T extends Comparable<T>>{

    public boolean insert(T data) throws NullPointerException, IllegalArgumentException;

    public boolean remove(T data) throws NullPointerException, IllegalArgumentException;

    public boolean contains(T data);

    public int size();

    public boolean isEmpty();

}