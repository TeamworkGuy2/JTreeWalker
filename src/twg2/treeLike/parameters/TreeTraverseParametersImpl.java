package twg2.treeLike.parameters;

import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.Predicate;

import lombok.val;
import twg2.treeLike.SubtreeConsumer;
import twg2.treeLike.TreeTraversalOrder;

/**
 * @author TeamworkGuy2
 * @since 2015-8-31
 * @see AbstractTreeTraverseParameters
 */
public class TreeTraverseParametersImpl<T> extends AbstractTreeTraverseParameters<T, T, Iterable<T>, SubtreeConsumer<T>> {
	// package-private
	SubtreeConsumer<T> consumer;

	@Override
	public TreeTraverseParametersImpl<T> setTree(T tree) { super.setTree(tree); return this; }
	@Override
	public TreeTraverseParametersImpl<T> setOnlyVisitLeaves(boolean onlyVisitLeaves) { super.setOnlyVisitLeaves(onlyVisitLeaves); return this; }
	@Override
	public TreeTraverseParametersImpl<T> setSkipNullRoot(boolean skipNullRoot) { super.setSkipNullRoot(skipNullRoot); return this; }
	@Override
	public TreeTraverseParametersImpl<T> setHasChildren(Predicate<T> hasChildren) { super.setHasChildren(hasChildren); return this; }
	@Override
	public TreeTraverseParametersImpl<T> setChildrenGetter(Function<T, Iterable<T>> childrenGetter) { super.setChildrenGetter(childrenGetter); return this; }
	@Override
	public TreeTraverseParametersImpl<T> setStartSubtreeFunc(IntConsumer startSubtreeFunc) { super.setStartSubtreeFunc(startSubtreeFunc); return this; }
	@Override
	public TreeTraverseParametersImpl<T> setEndSubtreeFunc(IntConsumer endSubtreeFunc) { super.setEndSubtreeFunc(endSubtreeFunc); return this; }


	@SuppressWarnings("unchecked")
	public TreeTraverseParametersImpl(T tree, boolean onlyVisitLeaves, TreeTraversalOrder traversalOrder, Predicate<T> hasChildren, Function<T, ? extends Iterable<T>> childrenGetter,
			SubtreeConsumer<T> consumer, IntConsumer startSubtreeFunc, IntConsumer endSubtreeFunc) {
		super(tree, onlyVisitLeaves, false, traversalOrder, hasChildren, (Function<T, Iterable<T>>) childrenGetter, startSubtreeFunc, endSubtreeFunc);
		this.consumer = consumer;
	}


	@Override
	public SubtreeConsumer<T> getConsumer() {
		return this.consumer;
	}


	@Override
	public TreeTraverseParametersImpl<T> setConsumer(SubtreeConsumer<T> consumer) {
		this.consumer = consumer;
		return this;
	}


	public static <F> TreeTraverseParametersImpl<F> of(F tree, boolean onlyVisitLeaves, TreeTraversalOrder traversalOrder, Predicate<F> hasChildren, Function<F, ? extends Iterable<F>> childrenGetter) {
		val params = new TreeTraverseParametersImpl<F>(tree, onlyVisitLeaves, traversalOrder, hasChildren, childrenGetter, null, null, null);
		return params;
	}


	public static <F> TreeTraverseParametersImpl<F> leafNodes(F tree, TreeTraversalOrder traversalOrder, Predicate<F> hasChildren, Function<F, ? extends Iterable<F>> childrenGetter) {
		val params = new TreeTraverseParametersImpl<F>(tree, true, traversalOrder, hasChildren, childrenGetter, null, null, null);
		return params;
	}


	public static <F> TreeTraverseParametersImpl<F> allNodes(F tree, TreeTraversalOrder traversalOrder, Predicate<F> hasChildren, Function<F, ? extends Iterable<F>> childrenGetter) {
		val params = new TreeTraverseParametersImpl<F>(tree, false, traversalOrder, hasChildren, childrenGetter, null, null, null);
		return params;
	}

}
