package twg2.treeLike.parameters;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.Predicate;

import twg2.treeLike.TreeConsumer;
import twg2.treeLike.TreeTraversalOrder;

/**
 * @author TeamworkGuy2
 * @since 2015-8-31
 * @see AbstractTreeTraverseParameters
 */
public class KeyTreeTraverseParameters<K, V> extends AbstractTreeTraverseParameters<V, Map.Entry<K, V>, Iterable<? extends Map.Entry<K, V>>, TreeConsumer<Map.Entry<K, V>>> {
	// package-private
	TreeConsumer<Entry<K, V>> consumer;

	@Override
	public KeyTreeTraverseParameters<K, V> setTree(V tree) { super.setTree(tree); return this; }
	@Override
	public KeyTreeTraverseParameters<K, V> setOnlyVisitLeaves(boolean onlyVisitLeaves) { super.setOnlyVisitLeaves(onlyVisitLeaves); return this; }
	@Override
	public KeyTreeTraverseParameters<K, V> setSkipRoot(boolean skipRoot) { super.setSkipRoot(skipRoot); return this; }
	@Override
	public KeyTreeTraverseParameters<K, V> setSkipNullRoot(boolean skipNullRoot) { super.setSkipNullRoot(skipNullRoot); return this; }
	@Override
	public KeyTreeTraverseParameters<K, V> setHasChildren(Predicate<V> hasChildren) { super.setHasChildren(hasChildren); return this; }
	@Override
	public KeyTreeTraverseParameters<K, V> setChildrenGetter(Function<V, Iterable<? extends Map.Entry<K, V>>> childrenGetter) { super.setChildrenGetter(childrenGetter); return this; }
	@Override
	public KeyTreeTraverseParameters<K, V> setStartSubtreeFunc(IntConsumer startSubtreeFunc) { super.setStartSubtreeFunc(startSubtreeFunc); return this; }
	@Override
	public KeyTreeTraverseParameters<K, V> setEndSubtreeFunc(IntConsumer endSubtreeFunc) { super.setEndSubtreeFunc(endSubtreeFunc); return this; }


	@SuppressWarnings("unchecked")
	public KeyTreeTraverseParameters(V tree, boolean onlyVisitLeaves, TreeTraversalOrder traversalOrder, Predicate<V> hasChildren, Function<V, ? extends Iterable<? extends Map.Entry<? extends K, ? extends V>>> childrenGetter,
			TreeConsumer<Map.Entry<K, V>> consumer, IntConsumer startSubtreeFunc, IntConsumer endSubtreeFunc) {
		super(tree, onlyVisitLeaves, false, traversalOrder, hasChildren, (Function<V, Iterable<? extends Map.Entry<K, V>>>) childrenGetter, startSubtreeFunc, endSubtreeFunc);
		this.consumer = consumer;
	}


	@Override
	public TreeConsumer<Entry<K, V>> getConsumer() {
		return this.consumer;
	}


	@Override
	public KeyTreeTraverseParameters<K, V> setConsumer(TreeConsumer<Entry<K, V>> consumer) {
		this.consumer = consumer;
		return this;
	}


	public static <F, G> KeyTreeTraverseParameters<F, G> of(G tree, boolean onlyVisitLeaves, TreeTraversalOrder traversalOrder, Predicate<G> hasChildren,
			Function<G, ? extends Iterable<? extends Map.Entry<F, G>>> childrenGetter) {
		var params = new KeyTreeTraverseParameters<F, G>(tree, onlyVisitLeaves, traversalOrder, hasChildren, childrenGetter, null, null, null);
		return params;
	}


	public static <F, G> KeyTreeTraverseParameters<F, G> leafNodes(G tree, TreeTraversalOrder traversalOrder, Predicate<G> hasChildren,
			Function<G, ? extends Iterable<? extends Map.Entry<? extends F, ? extends G>>> childrenGetter) {
		var params = new KeyTreeTraverseParameters<F, G>(tree, true, traversalOrder, hasChildren, childrenGetter, null, null, null);
		return params;
	}


	public static <F, G> KeyTreeTraverseParameters<F, G> allNodes(G tree, TreeTraversalOrder traversalOrder, Predicate<G> hasChildren,
			Function<G, ? extends Iterable<? extends Map.Entry<? extends F, ? extends G>>> childrenGetter) {
		var params = new KeyTreeTraverseParameters<F, G>(tree, false, traversalOrder, hasChildren, childrenGetter, null, null, null);
		return params;
	}

}
