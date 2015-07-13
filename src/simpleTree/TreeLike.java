package simpleTree;

import java.util.Collection;

/**
 * @author TeamworkGuy2
 * @since 2015-5-28
 */
public interface TreeLike<T, R extends TreeLike<T, R>> {


	public T getParent();


	public T getData();


	public boolean hasChildren();


	public Collection<R> getChildren();

}
