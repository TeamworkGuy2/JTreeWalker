package simpleTree;

import java.util.List;

/**
 * @author TeamworkGuy2
 * @since 2015-7-20
 */
@FunctionalInterface
public interface TreePathConsumer<T> {

	public void accept(T branch, int depth, List<T> parentBranches);

}
