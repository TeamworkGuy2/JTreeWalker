package twg2.treeLike;

import java.io.PrintStream;
import java.util.AbstractMap;
import java.util.Map;
import java.util.function.Function;

import twg2.treeLike.parameters.TreeTraverseParametersImpl;
import twg2.treeLike.simpleTree.SimpleKeyTree;
import twg2.treeLike.simpleTree.SimpleTree;

/**
 * @author TeamworkGuy2
 * @since 2015-8-31
 */
public class TreePrint {

	public static <T> void printTree(SimpleTree<T> tree, PrintStream out) {
		printTree(tree, null, out);
	}


	public static <T> void printTree(SimpleTree<T> tree, Function<T, String> toString, PrintStream out) {
		printTree(TreeTraverseParametersImpl.allNodes(tree, TreeTraversalOrder.PRE_ORDER,
				(t) -> t.hasChildren(),
				(t) -> t.getChildren()),
				(t) -> {
					T d = t.getData();
					return toString != null ? toString.apply(d) : (d != null ? d.toString() : null);
				}, out);
	}


	public static <K, T> void printTree(SimpleKeyTree<K, T> tree, PrintStream out) {
		printTree(tree, null, out);
	}


	public static <K, T> void printTree(SimpleKeyTree<K, T> tree, Function<T, String> toString, PrintStream out) {
		printTree(TreeTraverseParametersImpl.allNodes((Map.Entry<K, SimpleKeyTree<K, T>>)new AbstractMap.SimpleImmutableEntry<>(tree.getKey(), tree), TreeTraversalOrder.PRE_ORDER,
				(t) -> t.getValue().hasChildren(),
				(t) -> t.getValue().getChildren().entrySet()),
				null, out);
	}


	public static <T> void printTree(TreeTraverseParametersImpl<T> params, PrintStream out) {
		printTree(params, null, out);
	}


	public static <T> void printTree(TreeTraverseParametersImpl<T> params, Function<T, String> toString, PrintStream out) {
		StringBuilder indent = new StringBuilder();

		params.setStartSubtreeFunc((depth) -> {
			//out.println(indent.toString() + "{");
			indent(indent);
		});
		params.setEndSubtreeFunc((depth) -> {
			deindent(indent);
			//out.println(indent.toString() + "}");
		});

		params.setConsumer((TreeConsumer<T>)(T branch, int depth, T parentBranch) -> {
			out.println(indent.toString() + (toString != null ? toString.apply(branch) : branch));
		});

		TreeTraverse.traverse(params);
	}


	private static void indent(StringBuilder indentation) {
		indentation.append('\t');
	}


	private static void deindent(StringBuilder indentation) {
		indentation.setLength(indentation.length() - 1);
	}

}
