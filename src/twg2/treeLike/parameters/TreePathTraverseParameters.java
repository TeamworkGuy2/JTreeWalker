package twg2.treeLike.parameters;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.Predicate;

import lombok.val;
import twg2.treeLike.TreePathConsumer;

/**
 * @author TeamworkGuy2
 * @since 2015-9-1
 * @see AbstractTreeTraverseParameters
 */
public class TreePathTraverseParameters<T> extends AbstractTreeTraverseParameters<T, Collection<T>> {
	// package-private
	TreePathConsumer<T> consumer;

	@Override
	public TreePathTraverseParameters<T> setTree(T tree) { super.setTree(tree); return this; }
	@Override
	public TreePathTraverseParameters<T> setOnlyVisitLeaves(boolean onlyVisitLeaves) { super.setOnlyVisitLeaves(onlyVisitLeaves); return this; }
	@Override
	public TreePathTraverseParameters<T> setHasChildren(Predicate<T> hasChildren) { super.setHasChildren(hasChildren); return this; }
	@Override
	public TreePathTraverseParameters<T> setChildrenGetter(Function<T, Collection<T>> childrenGetter) { super.setChildrenGetter(childrenGetter); return this; }
	@Override
	public TreePathTraverseParameters<T> setStartSubtreeFunc(IntConsumer startSubtreeFunc) { super.setStartSubtreeFunc(startSubtreeFunc); return this; }
	@Override
	public TreePathTraverseParameters<T> setEndSubtreeFunc(IntConsumer endSubtreeFunc) { super.setEndSubtreeFunc(endSubtreeFunc); return this; }


	@SuppressWarnings("unchecked")
	public TreePathTraverseParameters(T tree, boolean onlyVisitLeaves, Predicate<T> hasChildren, Function<T, ? extends Collection<T>> childrenGetter,
			TreePathConsumer<T> consumer, IntConsumer startSubtreeFunc, IntConsumer endSubtreeFunc) {
		super(tree, onlyVisitLeaves, hasChildren, (Function<T, Collection<T>>) childrenGetter, startSubtreeFunc, endSubtreeFunc);
		this.consumer = consumer;
	}


	public TreePathConsumer<T> getConsumerTreePath() {
		return this.consumer;
	}


	public TreePathTraverseParameters<T> setConsumerTreePath(TreePathConsumer<T> consumer) {
		this.consumer = consumer;
		return this;
	}


	public static <_T> TreePathTraverseParameters<_T> of(_T tree, boolean onlyVisitLeaves, Predicate<_T> hasChildren, Function<_T, ? extends Collection<_T>> childrenGetter) {
		val params = new TreePathTraverseParameters<_T>(tree, onlyVisitLeaves, hasChildren, childrenGetter, null, null, null);
		return params;
	}


	public static <_T> TreePathTraverseParameters<_T> leafNodes(_T tree, Predicate<_T> hasChildren, Function<_T, ? extends Collection<_T>> childrenGetter) {
		val params = new TreePathTraverseParameters<_T>(tree, true, hasChildren, childrenGetter, null, null, null);
		return params;
	}


	public static <_T> TreePathTraverseParameters<_T> allNodes(_T tree, Predicate<_T> hasChildren, Function<_T, ? extends Collection<_T>> childrenGetter) {
		val params = new TreePathTraverseParameters<_T>(tree, false, hasChildren, childrenGetter, null, null, null);
		return params;
	}

}
