package twg2.treeLike.test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import twg2.collections.builder.MapBuilder;
import twg2.treeLike.simpleTree.SimpleKeyTree;
import twg2.treeLike.simpleTree.SimpleTree;
import twg2.treeLike.simpleTree.SimpleTreeImpl;
import twg2.tuple.Tuples;

/**
 * @author TeamworkGuy2
 * @since 2015-7-1
 */
public class TreeData {

	public static class RandomObjects {
		/*
		Tree layout:
		             A            Rocks               B
		     Chair      Stool    Granite           Starship
		indoor outdoor                          Battlecruiser
		                                    Enterprise   Hyperion
		                                     1701-D
		*/
		private static List<List<String>> tagsByLevel = Arrays.asList(
				Arrays.asList("root"),
				Arrays.asList("A", "Rocks", "B"),
				Arrays.asList("Chair", "Stool", "Granite", "Starship"),
				Arrays.asList("indoor", "outdoor", "Battlecruiser"),
				Arrays.asList("Enterprise", "Hyperion"),
				Arrays.asList("1701-D")
		);

		private static Map<String, List<String>> parentsByLeaf = MapBuilder.of(
				Tuples.of("1701-D", Arrays.asList("Enterprise", "Battlecruiser", "Starship", "B", "root")),
				Tuples.of("Hyperion", Arrays.asList("Battlecruiser", "Starship", "B", "root")),
				Tuples.of("indoor", Arrays.asList("Chair", "A", "root")),
				Tuples.of("outdoor", Arrays.asList("Chair", "A", "root")),
				Tuples.of("Stool", Arrays.asList("A", "root")),
				Tuples.of("Granite", Arrays.asList("Rocks", "root"))
		);

		private static List<String> postOrder = Arrays.asList(
				"indoor",
				"outdoor",
				"Chair",
				"Stool",
				"A",
				"Granite",
				"Rocks",
				"1701-D",
				"Enterprise",
				"Hyperion",
				"Battlecruiser",
				"Starship",
				"B"
		);

		private static List<String> preOrder = Arrays.asList(
				"A",
				"Chair",
				"indoor",
				"outdoor",
				"Stool",
				"Rocks",
				"Granite",
				"B",
				"Starship",
				"Battlecruiser",
				"Enterprise",
				"1701-D",
				"Hyperion"
		);


		public static List<List<String>> getTagsByLevel() {
			return tagsByLevel;
		}


		public static Map<String, List<String>> getParentsByLeaf() {
			return parentsByLeaf;
		}


		public static List<String> getPreOrderTags() {
			return preOrder;
		}


		public static List<String> getPostOrderTags() {
			return postOrder;
		}


		public static SimpleTree<String> createTreeTags() {
			SimpleTree<String> tree = new SimpleTreeImpl<String>(null);
			addTreeTags(tree);
			return tree;
		}


		@SuppressWarnings("unused")
		public static void addTreeTags(SimpleTree<String> rootTree) {
			SimpleTree<String> a = rootTree.addChild("A");
				SimpleTree<String> chair = a.addChild("Chair");
					SimpleTree<String> indoor = chair.addChild("indoor");
					SimpleTree<String> outdoor = chair.addChild("outdoor");
				SimpleTree<String> stool = a.addChild("Stool");

			SimpleTree<String> rocks = rootTree.addChild("Rocks");
				SimpleTree<String> granite = rocks.addChild("Granite");

			SimpleTree<String> b = rootTree.addChild("B");
				SimpleTree<String> starship = b.addChild("Starship");
					SimpleTree<String> battlecruiser = starship.addChild("Battlecruiser");
						SimpleTree<String> enterprise = battlecruiser.addChild("Enterprise");
							SimpleTree<String> _1701_D = enterprise.addChild("1701-D");
						SimpleTree<String> hyperion = battlecruiser.addChild("Hyperion");
		}


		@SuppressWarnings("unused")
		public static void addKeyTreeTags(SimpleKeyTree<String, String> rootTree) {
			SimpleKeyTree<String, String> a = rootTree.addChild("A", "A");
				SimpleKeyTree<String, String> chair = a.addChild("Chair", "Chair");
					SimpleKeyTree<String, String> indoor = chair.addChild("indoor", "indoor");
					SimpleKeyTree<String, String> outdoor = chair.addChild("outdoor", "outdoor");
				SimpleKeyTree<String, String> stool = a.addChild("Stool", "Stool");

			SimpleKeyTree<String, String> rocks = rootTree.addChild("Rocks", "Rocks");
				SimpleKeyTree<String, String> granite = rocks.addChild("Granite", "Granite");

			SimpleKeyTree<String, String> b = rootTree.addChild("B", "B");
				SimpleKeyTree<String, String> starship = b.addChild("Starship", "Starship");
					SimpleKeyTree<String, String> battlecruiser = starship.addChild("Battlecruiser", "Battlecruiser");
						SimpleKeyTree<String, String> enterprise = battlecruiser.addChild("Enterprise", "Enterprise");
							SimpleKeyTree<String, String> _1701_D = enterprise.addChild("1701-D", "1701-D");
						SimpleKeyTree<String, String> hyperion = battlecruiser.addChild("Hyperion", "Hyperion");
		}

	}

}
