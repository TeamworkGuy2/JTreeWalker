package twg2.treeLike;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import twg2.treeLike.parameters.KeyTreeTraverseParameters;
import twg2.treeLike.parameters.TreeTraverseParametersImpl;

/**
 * @author TeamworkGuy2
 * @since 2015-9-6
 */
public class TreeFlatten {

	public static final <T, E> List<E> toList(T tree, boolean skipNullRoot, TreeTraversalOrder traversalOrder, Predicate<T> hasChildren, Function<T, Iterable<T>> childrenGetter,
			Function<T, E> transform, List<E> dst) {
		TreeTraverseParametersImpl<T> params = TreeTraverseParametersImpl.allNodes(tree, traversalOrder, hasChildren, childrenGetter)
			.setSkipNullRoot(skipNullRoot)
			.setConsumer((branch, depth, parentBranch) -> {
				E data = transform.apply(branch);
				dst.add(data);
			});

		TreeTraverse.traverse(params);

		return dst;
	}


	public static final <K, V, R, S> Map<R, S> toMap(V tree, boolean skipNullRoot, TreeTraversalOrder traversalOrder, Predicate<V> hasChildren,
			Function<V, Iterable<? extends Map.Entry<? extends K, ? extends V>>> childrenGetter, Function<Map.Entry<K, V>, Map.Entry<R, S>> transform, Map<R, S> dst) {
		Map<R, S> tmpDst = dst != null ? dst : new HashMap<>();

		KeyTreeTraverseParameters<K, V> params = KeyTreeTraverseParameters.allNodes(tree, traversalOrder, hasChildren, childrenGetter)
			.setSkipNullRoot(skipNullRoot)
			.setConsumer((branch, depth, parentBranch) -> {
				Map.Entry<R, S> entry = transform.apply(branch);
				if(skipNullRoot && (entry.getKey() == null || entry.getValue() == null)) {
					return;
				}
				tmpDst.put(entry.getKey(), entry.getValue());
			});

		TreeTraverse.traverse(params);

		return tmpDst;
	}

}
