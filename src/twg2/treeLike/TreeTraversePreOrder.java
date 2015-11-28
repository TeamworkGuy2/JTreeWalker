package twg2.treeLike;

import java.util.List;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.Predicate;

/** Static tree pre-order traversal functions
 * @author TeamworkGuy2
 * @since 2015-7-11
 */
public class TreeTraversePreOrder {

	public static <R> void traverseIndexedPreOrder(int index, int size, int depth, R parent, R tree, boolean skipRoot, boolean skipNullRoot, Predicate<R> hasChildren, Function<R, ? extends List<R>> childrenGetter,
			IndexedSubtreeConsumer<R> consumer, boolean consumeOnlyLeafNodes, IntConsumer startNodeFunc, IntConsumer endNodeFunc) {
		if(!hasChildren.test(tree)) {
			if(!skipRoot && (tree != null || !skipNullRoot)) {
				consumer.accept(tree, index, size, depth, parent);
			}
			// return early because no children
			return;
		}

		List<R> children = childrenGetter.apply(tree);
		int count = 0;

		int sizeI = children.size();
		// consume children in list indexed order
		while(count < sizeI) {
			// consume root element
			if(count == 0) {
				if(!consumeOnlyLeafNodes) {
					if(!skipRoot && (tree != null || !skipNullRoot)) {
						consumer.accept(tree, 0, sizeI, depth, parent);
					}
				}
				depth++;
				if(startNodeFunc != null) {
					startNodeFunc.accept(depth);
				}
			}

			R subtree = children.get(count);
			if(subtree != null) {
				traverseIndexedPreOrder(count, sizeI, depth, tree, subtree, false, false, hasChildren, childrenGetter, consumer, consumeOnlyLeafNodes, startNodeFunc, endNodeFunc);
			}

			count++;
		}

		if(count > 0) {
			if(endNodeFunc != null) {
				endNodeFunc.accept(depth);
			}
			depth--;
		}
	}


	public static <R> void traversePreOrder(int depth, R parent, R tree, boolean skipRoot, boolean skipNullRoot, Predicate<R> hasChildren, Function<R, ? extends Iterable<? extends R>> childrenGetter,
			SubtreeConsumer<R> consumer, boolean consumeOnlyLeafNodes, IntConsumer startNodeFunc, IntConsumer endNodeFunc) {
		if(!hasChildren.test(tree)) {
			if(!skipRoot && (tree != null || !skipNullRoot)) {
				consumer.accept(tree, depth, parent);
			}
			// return early because no children
			return;
		}

		Iterable<? extends R> children = childrenGetter.apply(tree);
		int count = 0;

		// consume children in iteration order
		for(R subtree : children) {
			// consume root element
			if(count == 0) {
				if(!consumeOnlyLeafNodes) {
					if(!skipRoot && (tree != null || !skipNullRoot)) {
						consumer.accept(tree, depth, parent);
					}
				}
				depth++;
				if(startNodeFunc != null) {
					startNodeFunc.accept(depth);
				}
			}

			if(subtree != null) {
				traversePreOrder(depth, tree, subtree, false, false, hasChildren, childrenGetter, consumer, consumeOnlyLeafNodes, startNodeFunc, endNodeFunc);
			}

			count++;
		}

		if(count > 0) {
			if(endNodeFunc != null) {
				endNodeFunc.accept(depth);
			}
			depth--;
		}
	}

}
