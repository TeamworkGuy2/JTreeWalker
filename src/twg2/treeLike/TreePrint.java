package twg2.treeLike;

import java.io.PrintStream;
import java.util.AbstractMap;
import java.util.Map;

import twg2.treeLike.parameters.TreeTraverseParametersImpl;
import twg2.treeLike.simpleTree.SimpleKeyTree;
import twg2.treeLike.simpleTree.SimpleTree;

/**
 * @author TeamworkGuy2
 * @since 2015-8-31
 */
public class TreePrint {

	public static <T> void printTree(SimpleTree<T> tree, PrintStream out) {
		printTree(TreeTraverseParametersImpl.allNodes(tree, TreeTraversalOrder.PRE_ORDER, (t) -> t.hasChildren(), (t) -> t.getChildren()), out);
	}


	public static <K, T> void printTree(SimpleKeyTree<K, T> tree, PrintStream out) {
		printTree(TreeTraverseParametersImpl.allNodes((Map.Entry<K, SimpleKeyTree<K, T>>)new AbstractMap.SimpleImmutableEntry<>(tree.getKey(), tree), TreeTraversalOrder.PRE_ORDER,
				(t) -> t.getValue().hasChildren(),
				(t) -> t.getValue().getChildren().entrySet()), out);
	}


	public static <T> void printTree(TreeTraverseParametersImpl<T> params, PrintStream out) {
		StringBuilder indent = new StringBuilder();

		params.setStartSubtreeFunc((depth) -> {
			//out.println(indent.toString() + "{");
			indent(indent);
		});
		params.setEndSubtreeFunc((depth) -> {
			deindent(indent);
			//out.println(indent.toString() + "}");
		});

		params.setConsumer((SubtreeConsumer<T>)(T branch, int depth, T parentBranch) -> {
			out.println(indent.toString() + (branch instanceof SimpleTree ? ((SimpleTree<?>)branch).getData() : branch));
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
