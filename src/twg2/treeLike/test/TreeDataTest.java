package twg2.treeLike.test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import twg2.collections.tuple.Tuples;
import twg2.collections.util.MapBuilder;
import twg2.treeLike.simpleTree.SimpleTree;

/**
 * @author TeamworkGuy2
 * @since 2015-7-1
 */
public class TreeDataTest {

	public static class RandomObjects {
		/*
		Tree layout:
		             A            Rocks               B
		     Chair      Stool    Granite           Starship
		indoor outdoor                          Battlecruiser
		                                    Enterprise   Hyperion
		                                     1701-D
		*/
		private static List<List<String>> randomObjsTagsByLevel = Arrays.asList(
				Arrays.asList("root"),
				Arrays.asList("A", "Rocks", "B"),
				Arrays.asList("Chair", "Stool", "Granite", "Starship"),
				Arrays.asList("indoor", "outdoor", "Battlecruiser"),
				Arrays.asList("Enterprise", "Hyperion"),
				Arrays.asList("1701-D")
		);

		private static Map<String, List<String>> randomObjsParentsByLeaf = MapBuilder.of(
				Tuples.of("1701-D", Arrays.asList("Enterprise", "Battlecruiser", "Starship", "B", "root")),
				Tuples.of("Hyperion", Arrays.asList("Battlecruiser", "Starship", "B", "root")),
				Tuples.of("indoor", Arrays.asList("Chair", "A", "root")),
				Tuples.of("outdoor", Arrays.asList("Chair", "A", "root")),
				Tuples.of("Stool", Arrays.asList("A", "root")),
				Tuples.of("Granite", Arrays.asList("Rocks", "root"))
		);


		public static List<List<String>> getTagsByLevel() {
			return randomObjsTagsByLevel;
		}


		public static Map<String, List<String>> getParentsByLeaf() {
			return randomObjsParentsByLeaf;
		}


		@SuppressWarnings("unused")
		static void addTreeTags(SimpleTree<String> rootTree) {
			/*
			Panel layout:
			             A            Rocks               B
			     Chair      Stool    Granite           Starship
			indoor outdoor                          Battlecruiser
			                                    Enterprise   Hyperion
			                                     1701-D
			*/

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

	}

}
