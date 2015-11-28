package twg2.treeLike;

/** Enum of different tree traversal orders.
 * NOTE: IN_ORDER is not present because {@link TreeLike} does not require trees to be ordered and
 * supports multiple children per subtree, so the concept of traversing a tree in-order
 * (i.e. left subtree first, then root node, then right subtree) cannot be applied to 'TreeLike' tress
 * @author TeamworkGuy2
 * @since 2015-9-3
 */
public enum TreeTraversalOrder {
	/** The current node is consumed first, then the children, in order */
	PRE_ORDER,
	/** The children are consumed first, in order, then the current node */
	POST_ORDER;

}
