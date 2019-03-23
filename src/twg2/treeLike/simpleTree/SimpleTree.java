package twg2.treeLike.simpleTree;

import twg2.collections.interfaces.ListReadOnly;
import twg2.treeLike.TreeLike;

/** Mutable {@link TreeLike}
 * @author TeamworkGuy2
 * @since 2015-5-28
 * @param <T> the type of data stored in this tree
 */
public interface SimpleTree<T> extends TreeLike<T, SimpleTree<T>> {


	@Override
	public SimpleTree<T> getParent();


	@Override
	public T getData();


	@Override
	public ListReadOnly<SimpleTree<T>> getChildren();


	@Override
	public boolean hasChildren();


	@Override
	public int size();


	/** Add a child data node to this tree
	 * @param childData the data to add as a child to this tree
	 * @return the child tree created to hold the new data
	 */
	public SimpleTree<T> addChild(T childData);


	/** Add a collection of data as child nodes to this tree
	 * @param childrenData a collection of child data to store in the tree
	 */
	public void addChildren(Iterable<? extends T> childrenData);


	/** Remove a child node from this tree
	 * @param subTree the child node to remove from this tree
	 * @return true if the child node was found and removed, false otherwise
	 */
	public boolean removeChild(SimpleTree<T> subTree);


	/** Remove a collection of child nodes from this tree
	 * @param subTrees the child nodes to remove from this tree
	 * @return true if all the child nodes were found and removed, false otherwise
	 */
	public boolean removeChildren(Iterable<? extends SimpleTree<T>> subTrees);

}
