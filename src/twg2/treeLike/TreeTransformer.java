package twg2.treeLike;

/**
 * @author TeamworkGuy2
 * @since 2015-8-29
 * @param <T> the original tree type
 * @param <R> the resulting tree after transformation
 */
@FunctionalInterface
public interface TreeTransformer<T, R> {

	/** Transform a tree node, given the node, it's original parent, and it's transformed parent
	 * @param branch the tree node to transform
	 * @param transformedParent the already transformed parent of this tree node
	 * @param originalParent the original parent of this tree node
	 * @return the {@code branch} transformed
	 */
	public R apply(T branch, R transformedParent, T originalParent);

}
