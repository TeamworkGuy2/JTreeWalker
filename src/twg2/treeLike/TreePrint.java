package twg2.treeLike;

import java.io.PrintStream;

import twg2.treeLike.parameters.IndexedTreeTraverseParameters;
import twg2.treeLike.simpleTree.SimpleTree;

/**
 * @author TeamworkGuy2
 * @since 2015-8-31
 */
public class TreePrint {

	public static <T> void printTree(SimpleTree<T> tree, PrintStream out) {
		printTree(IndexedTreeTraverseParameters.allNodes(tree, TreeTraversalOrder.PRE_ORDER, (t) -> t.hasChildren(), (t) -> t.getChildren()), out);
	}


	public static <T> void printTree(IndexedTreeTraverseParameters<T> params, PrintStream out) {
		StringBuilder indent = new StringBuilder();

		params.setStartSubtreeFunc((depth) -> {
			//out.println(indent.toString() + "{");
			indent(indent);
		});
		params.setEndSubtreeFunc((depth) -> {
			deindent(indent);
			//out.println(indent.toString() + "}");
		});

		params.setConsumerIndexed((T branch, int index, int size, int depth, T parentBranch) -> {
			out.println(indent.toString() + (branch instanceof SimpleTree ? ((SimpleTree<?>)branch).getData() : branch));
		});

		TreeTraverse.Indexed.traverse(params);
	}


	private static void indent(StringBuilder indentation) {
		indentation.append('\t');
	}


	private static void deindent(StringBuilder indentation) {
		indentation.setLength(indentation.length() - 1);
	}

}
