package twg2.treeLike;

/**
 * @param <T> the tree branch element type
 * @author TeamworkGuy2
 * @since 2015-5-27
 */
@FunctionalInterface
public interface TreeConsumer<T> {

	public void accept(T branch, int depth, T parentBranch);

}
