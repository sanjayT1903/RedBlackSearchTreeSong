
import java.util.List;

public interface RedBlackTreeInterface<T extends Comparable<T>> extends SortedCollectionInterface<T> {


	

	

	
    /*
    constructor : this(), // a default constructor, the tree would arrange the
    element by default T.compareTo()
    this(Comparator<T> comparator) // a constructor that take one comparator as
    argument, the tree would arrange the element using the comparator
   */

  /**
   * find first element that matches the condition (return is 0) in the tree
   * Note: the predicate must use the same rule as the comparator
   * EX: if the tree is sorted by T.getYear(), then the predicate can only search
   * for element by year and CANNOT search for anything else
   *
   * @param predicate the lambda expression for searching elements
   * @return all elements
   */
  public T get(Comparable<T> predicate);
  
  public List<T> getList(List<T> list);
  

  /**
   * find all elements that match the condition (return is 0) in the tree
   * Note: the predicate must use the same rule as the comparator
   * EX: if the tree is sorted by T.getYear(), then the predicate can only search
   * for element by year and CANNOT search for anything else
   *
   * @param predicate the lambda expression for searching elements
   * @return all elements
   */
  public List<T> getAll(Comparable<T> predicate);

  /**
   * find the first element that matches the condition and remove it from the tree
   * Note: the predicate must use the same rule as the comparator
   * EX: if the tree is sorted by T.getYear(), then the predicate can only search
   * for element by year and CANNOT search for anything else
   *
   * @param value the value for searching elements
   * @return true if the element removed successfully
   */
  public boolean remove(T value);

}
