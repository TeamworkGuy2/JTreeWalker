package twg2.treeLike;

import twg2.collections.interfaces.ListReadOnly;

/** A simple tree structure with a parent, data, and children.
 * @author TeamworkGuy2
 * @since 2015-5-28
 * @param <D> the type of data stored in this tree
 * @param <P> the parent/child type of this tree
 */
public interface TreeLike<D, P extends TreeLike<D, P>> {

	/** This tree's parent (possibly null)
	 */
	public P getParent();

	/** This tree node's data (possibly null)
	 */
	public D getData();

	/** The list of immediate children of this tree node (possibly null)
	 */
	public ListReadOnly<P> getChildren();

	/** Shortcut for {@code getChildren().size() > 0}
	 */
	public boolean hasChildren();

	/** Shortcut for {@code getChildren().size()}
	 */
	public int size();

}
