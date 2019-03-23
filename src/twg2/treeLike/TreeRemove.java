package twg2.treeLike;

import java.util.function.Function;
import java.util.function.Predicate;

import twg2.collections.interfaces.ListReadOnly;

/**
 * @author TeamworkGuy2
 * @since 2015-8-29
 */
public class TreeRemove {

	private TreeRemove() { throw new AssertionError("cannot instantiate static class TreeRemove"); }


	public static <R> void removeAllNodes(int depth, R parent, R tree, Predicate<R> hasChildren, Function<R, ListReadOnly<R>> childrenGetter,
			TreeConsumer<R> consumer, Remover<R, R> remover, boolean consumeOnlyLeafNodes) {
		if(!hasChildren.test(tree)) {
			consumer.accept(tree, depth, parent);
			if(parent != null) {
				remover.accept(parent, tree);
			}
			// return early because no children
			return;
		}

		ListReadOnly<R> children = childrenGetter.apply(tree);

		int sizeI = children.size();
		if(sizeI > 0) {
			depth++;
		}

		for(int i = sizeI - 1; i > -1; i--) {
			R subtree = children.get(i);
			if(subtree != null) {
				removeAllNodes(depth, tree, subtree, hasChildren, childrenGetter, consumer, remover, consumeOnlyLeafNodes);
			}
		}

		if(sizeI > 0) {
			if(!consumeOnlyLeafNodes) {
				consumer.accept(tree, depth - 1, parent);
				if(parent != null) {
					remover.accept(parent, tree);
				}
			}

			depth--;
		}
	}

}
