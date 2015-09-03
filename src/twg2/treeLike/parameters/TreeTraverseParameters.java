package twg2.treeLike.parameters;

import java.util.Collection;
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
public class TreeTraverseParameters<T> extends AbstractTreeTraverseParameters<T, Collection<T>> {
	// package-private
	SubtreeConsumer<T> consumer;

	@Override
	public TreeTraverseParameters<T> setTree(T tree) { super.setTree(tree); return this; }
	@Override
	public TreeTraverseParameters<T> setOnlyVisitLeaves(boolean onlyVisitLeaves) { super.setOnlyVisitLeaves(onlyVisitLeaves); return this; }
	@Override
	public TreeTraverseParameters<T> setSkipNullRoot(boolean skipNullRoot) { super.setSkipNullRoot(skipNullRoot); return this; }
	@Override
	public TreeTraverseParameters<T> setHasChildren(Predicate<T> hasChildren) { super.setHasChildren(hasChildren); return this; }
	@Override
	public TreeTraverseParameters<T> setChildrenGetter(Function<T, Collection<T>> childrenGetter) { super.setChildrenGetter(childrenGetter); return this; }
	@Override
	public TreeTraverseParameters<T> setStartSubtreeFunc(IntConsumer startSubtreeFunc) { super.setStartSubtreeFunc(startSubtreeFunc); return this; }
	@Override
	public TreeTraverseParameters<T> setEndSubtreeFunc(IntConsumer endSubtreeFunc) { super.setEndSubtreeFunc(endSubtreeFunc); return this; }


	@SuppressWarnings("unchecked")
	public TreeTraverseParameters(T tree, boolean onlyVisitLeaves, TreeTraversalOrder traversalOrder, Predicate<T> hasChildren, Function<T, ? extends Collection<T>> childrenGetter,
			SubtreeConsumer<T> consumer, IntConsumer startSubtreeFunc, IntConsumer endSubtreeFunc) {
		super(tree, onlyVisitLeaves, false, traversalOrder, hasChildren, (Function<T, Collection<T>>) childrenGetter, startSubtreeFunc, endSubtreeFunc);
		this.consumer = consumer;
	}


	public SubtreeConsumer<T> getConsumer() {
		return this.consumer;
	}


	public TreeTraverseParameters<T> setConsumer(SubtreeConsumer<T> consumer) {
		this.consumer = consumer;
		return this;
	}


	public static <_T> TreeTraverseParameters<_T> of(_T tree, boolean onlyVisitLeaves, TreeTraversalOrder traversalOrder, Predicate<_T> hasChildren, Function<_T, ? extends Collection<_T>> childrenGetter) {
		val params = new TreeTraverseParameters<_T>(tree, onlyVisitLeaves, traversalOrder, hasChildren, childrenGetter, null, null, null);
		return params;
	}


	public static <_T> TreeTraverseParameters<_T> leafNodes(_T tree, TreeTraversalOrder traversalOrder, Predicate<_T> hasChildren, Function<_T, ? extends Collection<_T>> childrenGetter) {
		val params = new TreeTraverseParameters<_T>(tree, true, traversalOrder, hasChildren, childrenGetter, null, null, null);
		return params;
	}


	public static <_T> TreeTraverseParameters<_T> allNodes(_T tree, TreeTraversalOrder traversalOrder, Predicate<_T> hasChildren, Function<_T, ? extends Collection<_T>> childrenGetter) {
		val params = new TreeTraverseParameters<_T>(tree, false, traversalOrder, hasChildren, childrenGetter, null, null, null);
		return params;
	}

}
