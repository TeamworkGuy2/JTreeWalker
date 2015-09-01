package twg2.treeLike;

import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.Predicate;

/**
 * @author TeamworkGuy2
 * @since 2015-8-29
 */
public class TreeTransform {

	private TreeTransform() { throw new AssertionError("cannot instantiate static class TreeTransform"); }


	public static <R, S> void transformTree(R tree, S treeTransformed, SubtreeTransformer<R, S> transformer, Predicate<R> hasChildren, Function<R, Collection<R>> childrenGetter, SubtreeConsumer<S> transformedConsumer) {
		transformTree(tree, treeTransformed, transformer, hasChildren, childrenGetter, transformedConsumer, null, null);
	}

	public static <R, S> void transformTree(R tree, S treeTransformed, SubtreeTransformer<R, S> transformer, Predicate<R> hasChildren, Function<R, Collection<R>> childrenGetter,
			SubtreeConsumer<S> transformedConsumer, IntConsumer startNodeFunc, IntConsumer endNodeFunc) {
		transformTree(0, null, tree, null, treeTransformed, transformer, hasChildren, childrenGetter, transformedConsumer, false, startNodeFunc, endNodeFunc);
	}


	public static <R, S> void transformTree(int depth, R parent, R tree, S parentTransformed, S treeTransformed, SubtreeTransformer<R, S> transformer,
			Predicate<R> hasChildren, Function<R, Collection<R>> childrenGetter, SubtreeConsumer<S> consumer, boolean consumeOnlyLeafNodes, IntConsumer startNodeFunc, IntConsumer endNodeFunc) {
		if(!hasChildren.test(tree)) {
			consumer.accept(treeTransformed, depth, parentTransformed);
			// return early because no children
			return;
		}

		Collection<R> children = childrenGetter.apply(tree);
		int count = 0;

		if(children instanceof RandomAccess && children instanceof List) {
			List<R> childrenList = (List<R>)children;
			int sizeI = childrenList.size();
			while(count < sizeI) {
				R subtree = childrenList.get(count);

				if(count == 0) {
					if(!consumeOnlyLeafNodes) {
						consumer.accept(treeTransformed, depth, parentTransformed);
					}
					depth++;
					if(startNodeFunc != null) {
						startNodeFunc.accept(depth);
					}
				}

				S subtreeTransformed = transformer.apply(subtree, treeTransformed, tree);

				if(subtree != null) {
					transformTree(depth, tree, subtree, treeTransformed, subtreeTransformed, transformer, hasChildren, childrenGetter, consumer, consumeOnlyLeafNodes, startNodeFunc, endNodeFunc);
				}

				count++;
			}
		}
		else {
			for(R subtree : children) {
				if(count == 0) {
					if(!consumeOnlyLeafNodes) {
						consumer.accept(treeTransformed, depth, parentTransformed);
					}
					depth++;
					if(startNodeFunc != null) {
						startNodeFunc.accept(depth);
					}
				}

				S subtreeTransformed = transformer.apply(subtree, treeTransformed, tree);

				if(subtree != null) {
					transformTree(depth, tree, subtree, treeTransformed, subtreeTransformed, transformer, hasChildren, childrenGetter, consumer, consumeOnlyLeafNodes, startNodeFunc, endNodeFunc);
				}

				count++;
			}
		}

		if(count > 0) {
			if(endNodeFunc != null) {
				endNodeFunc.accept(depth);
			}
			depth--;
		}
	}

}
