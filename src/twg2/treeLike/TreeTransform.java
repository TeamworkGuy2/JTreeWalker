package twg2.treeLike;

import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.Predicate;

import twg2.collections.interfaces.ListReadOnly;
import twg2.treeLike.simpleTree.SimpleTree;
import twg2.treeLike.simpleTree.SimpleTreeImpl;

/**
 * @author TeamworkGuy2
 * @since 2015-8-29
 */
public class TreeTransform {

	private TreeTransform() { throw new AssertionError("cannot instantiate static class TreeTransform"); }


	public static <R, S> void traverseTransformTree(R tree, S treeTransformed, TreeTransformer<R, S> transformer,
			Predicate<R> hasChildren, Function<R, ListReadOnly<R>> childrenGetter, TreeConsumer<S> transformedConsumer) {
		traverseTransformTree(tree, treeTransformed, transformer, hasChildren, childrenGetter, transformedConsumer, null, null);
	}


	public static <R, S> void traverseTransformTree(R tree, S treeTransformed, TreeTransformer<R, S> transformer,
			Predicate<R> hasChildren, Function<R, ListReadOnly<R>> childrenGetter, TreeConsumer<S> transformedConsumer, IntConsumer startNodeFunc, IntConsumer endNodeFunc) {
		traverseTransformTree(0, null, tree, null, treeTransformed, transformer, hasChildren, childrenGetter, transformedConsumer, false, startNodeFunc, endNodeFunc);
	}


	public static <R, S> SimpleTree<S> transformSimpleTree(SimpleTree<R> tree, TreeTransformer<R, S> transformer,
			TreeConsumer<S> transformedConsumer) {
		SimpleTreeImpl<S> treeTransformed = new SimpleTreeImpl<>(null);
		transformSimpleTree0(0, null, tree, null, treeTransformed, transformer, transformedConsumer, false, null, null);
		return treeTransformed;
	}


	public static <R, S> SimpleTree<S> transformSimpleTree(SimpleTree<R> tree, S rootDataTransformed, TreeTransformer<R, S> transformer,
			TreeConsumer<S> transformedConsumer, IntConsumer startNodeFunc, IntConsumer endNodeFunc) {
		SimpleTreeImpl<S> treeTransformed = new SimpleTreeImpl<>(rootDataTransformed);
		transformSimpleTree0(0, null, tree, null, treeTransformed, transformer, transformedConsumer, false, startNodeFunc, endNodeFunc);
		return treeTransformed;
	}


	public static <R, S> SimpleTree<S> transformTree(R tree, TreeTransformer<R, S> transformer, Predicate<R> hasChildren,
			Function<R, ListReadOnly<R>> childrenGetter, TreeConsumer<S> transformedConsumer, IntConsumer startNodeFunc, IntConsumer endNodeFunc) {
		SimpleTreeImpl<S> treeTransformed = new SimpleTreeImpl<>(null); 
		transformTree0(0, null, tree, null, treeTransformed, transformer, hasChildren, childrenGetter, transformedConsumer, false, startNodeFunc, endNodeFunc);
		return treeTransformed;
	}


	public static <R, S> void transformTree0(int depth, R parent, R tree, SimpleTree<S> parentTransformed, SimpleTree<S> treeTransformed, TreeTransformer<R, S> transformer,
			Predicate<R> hasChildren, Function<R, ListReadOnly<R>> childrenGetter, TreeConsumer<S> consumer, boolean consumeOnlyLeafNodes, IntConsumer startNodeFunc, IntConsumer endNodeFunc) {
		if(!hasChildren.test(tree)) {
			consumer.accept(treeTransformed.getData(), depth, parentTransformed.getData());
			// return early because no children
			return;
		}

		ListReadOnly<R> children = childrenGetter.apply(tree);
		int count = 0;

		for(R subtree : children) {
			if(count == 0) {
				if(!consumeOnlyLeafNodes) {
					consumer.accept(treeTransformed.getData(), depth, parentTransformed.getData());
				}
				depth++;
				if(startNodeFunc != null) {
					startNodeFunc.accept(depth);
				}
			}

			S subDataTransformed = transformer.apply(subtree, treeTransformed.getData(), tree);
			SimpleTree<S> subtreeTransformed = treeTransformed.addChild(subDataTransformed);

			if(subtree != null) {
				transformTree0(depth, tree, subtree, treeTransformed, subtreeTransformed, transformer, hasChildren, childrenGetter, consumer, consumeOnlyLeafNodes, startNodeFunc, endNodeFunc);
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


	public static <R, S> void transformSimpleTree0(int depth, SimpleTree<R> parent, SimpleTree<R> tree, SimpleTree<S> parentTransformed, SimpleTree<S> treeTransformed,
			TreeTransformer<R, S> transformer, TreeConsumer<S> consumer, boolean consumeOnlyLeafNodes, IntConsumer startNodeFunc, IntConsumer endNodeFunc) {
		if(!tree.hasChildren()) {
			consumer.accept(treeTransformed.getData(), depth, parentTransformed.getData());
			// return early because no children
			return;
		}

		ListReadOnly<SimpleTree<R>> children = tree.getChildren();
		int count = children.size();

		if(count > 0) {
			if(!consumeOnlyLeafNodes) {
				consumer.accept(treeTransformed.getData(), depth, parentTransformed != null ? parentTransformed.getData() : null);
			}
			depth++;
			if(startNodeFunc != null) {
				startNodeFunc.accept(depth);
			}
		}

		for(int i = 0; i < count; i++) {
			SimpleTree<R> subtree = children.get(i);

			S subDataTransformed = transformer.apply(subtree.getData(), treeTransformed.getData(), tree.getData());
			SimpleTree<S> subtreeTransformed = treeTransformed.addChild(subDataTransformed);

			if(subtree != null) {
				transformSimpleTree0(depth, tree, subtree, treeTransformed, subtreeTransformed, transformer, consumer, consumeOnlyLeafNodes, startNodeFunc, endNodeFunc);
			}
		}

		if(count > 0) {
			if(endNodeFunc != null) {
				endNodeFunc.accept(depth);
			}
			depth--;
		}
	}


	public static <R, S> void traverseTransformTree(int depth, R parent, R tree, S parentTransformed, S treeTransformed, TreeTransformer<R, S> transformer,
			Predicate<R> hasChildren, Function<R, ListReadOnly<R>> childrenGetter, TreeConsumer<S> consumer, boolean consumeOnlyLeafNodes, IntConsumer startNodeFunc, IntConsumer endNodeFunc) {
		if(!hasChildren.test(tree)) {
			consumer.accept(treeTransformed, depth, parentTransformed);
			// return early because no children
			return;
		}

		ListReadOnly<R> children = childrenGetter.apply(tree);
		int count = 0;

		int sizeI = children.size();
		while(count < sizeI) {
			R subtree = children.get(count);

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
				traverseTransformTree(depth, tree, subtree, treeTransformed, subtreeTransformed, transformer, hasChildren, childrenGetter, consumer, consumeOnlyLeafNodes, startNodeFunc, endNodeFunc);
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
