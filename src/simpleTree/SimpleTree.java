package simpleTree;

import java.util.List;

/**
 * @author TeamworkGuy2
 * @since 2015-5-28
 */
public interface SimpleTree<T> extends TreeLike<T, SimpleTree<T>> {


	@Override
	public SimpleTree<T> getParent();


	@Override
	public T getData();


	@Override
	public boolean hasChildren();


	@Override
	public List<SimpleTree<T>> getChildren();


	/**
	 * @param childData the data to store in the child tree
	 * @return the child tree created to hold the new data reference
	 */
	public SimpleTree<T> addChild(T childData);


	public boolean removeChild(SimpleTree<T> subTree);

}
