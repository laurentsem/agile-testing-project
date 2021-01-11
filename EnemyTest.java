package Tests;

import codingfactory.rpgconsole.enemy.Enemy;
import codingfactory.rpgconsole.hero.Hero;
import org.junit.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class EnemyTest {

    Enemy enemy;
    Hero hero;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println("Avant le démarrage");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        System.out.println("Après tous les tests");
    }

    @Before
    public void setUp() throws Exception {
        enemy = new Enemy("Dark Vador", 1);
        hero = new Hero("Yoda" );
        System.out.println("Avant un test");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Après un test");
    }

    @Test
    public void testEnemyTakeDamage() throws Exception {
        assertThat(enemy, hasProperty("hp"));
        enemy.takeDamage(10);
        assertThat(enemy, hasProperty("hp", is(5)));
        System.out.println("Après un test");
    }

    @Test
    public void testEnemyAttack() throws Exception {
        assertThat(enemy, hasProperty("atk"));
        enemy.attack(hero);

        assertThat(hero, hasProperty("hp", lessThanOrEqualTo(19)));
        assertThat(hero, hasProperty("hp", greaterThanOrEqualTo(17)));

        assertThat(enemy, hasProperty("atk", lessThanOrEqualTo(this.enemy.getAtk() +2)));
        assertThat(enemy, hasProperty("atk", greaterThanOrEqualTo(this.enemy.getAtk())));
        System.out.println("Après un test");
    }

    @Test
    public void testHeroProperties() throws Exception {
        assertThat(enemy, hasProperty("name"));
        assertThat(enemy, hasProperty("name", is("Dark Vador")));

        assertThat(enemy, hasProperty("hp"));
        assertThat(enemy, hasProperty("hp", is(15)));

        assertThat(enemy, hasProperty("atk"));
        assertThat(enemy, hasProperty("atk", is(1)));

        assertThat(enemy, hasProperty("level"));
        assertThat(enemy, hasProperty("level", is(1)));
    }

}
