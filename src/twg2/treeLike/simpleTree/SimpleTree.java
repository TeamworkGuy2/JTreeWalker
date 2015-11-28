package twg2.treeLike.simpleTree;

import java.util.List;

import twg2.treeLike.IndexedTree;

/**
 * @author TeamworkGuy2
 * @since 2015-5-28
 */
public interface SimpleTree<T> extends IndexedTree<T, SimpleTree<T>> {


	@Override
	public SimpleTree<T> getParent();


	@Override
	public T getData();


	@Override
	public boolean hasChildren();


	@Override
	public List<SimpleTree<T>> getChildren();


	/**
	 * @param childData the data to store in the tree
	 * @return the child tree created to hold the new data reference
	 */
	public SimpleTree<T> addChild(T childData);


	/**
	 * @param childrenData a collection of child data to store in the tree
	 */
	public void addChildren(Iterable<? extends T> childrenData);


	public boolean removeChild(SimpleTree<T> subTree);


	public boolean removeChildren(Iterable<? extends SimpleTree<T>> subTree);

}
