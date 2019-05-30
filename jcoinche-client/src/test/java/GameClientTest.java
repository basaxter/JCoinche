import lib.Game;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameClientTest {
    @Test
    public void carreauxColorTest(){
        if (Game.cardColor.values()[0] == Game.cardColor.CARREAUX) {
            System.out.println("Card Color Carreaux: OK");
        } else {
            fail("Card Color Carreaux is not OK");
        }
    }

    @Test
    public void picColorTest(){
        if (Game.cardColor.values()[1] == Game.cardColor.PIC) {
            System.out.println("Card Color Pic: OK");
        } else {
            fail("Card Color Pic is not OK");
        }
    }

    @Test
    public void trefleColorTest(){
        if (Game.cardColor.values()[2] == Game.cardColor.TREFLE) {
            System.out.println("Card Color Trefle: OK");
        } else {
            fail("Card Color Trefle is not OK");
        }
    }

    @Test
    public void coeurColorTest(){
        if (Game.cardColor.values()[3] == Game.cardColor.COEUR) {
            System.out.println("Card Color Coeur: OK");
        } else {
            fail("Card Color Coeur is not OK");
        }
    }

    @Test
    public void aceValueTest(){
        if (Game.cardValue.values()[0] == Game.cardValue.CARD_A) {
            System.out.println("Card Value Ace: OK");
        } else {
            fail("Card Value Ace is not OK");
        }
    }

    @Test
    public void kingValueTest(){
        if (Game.cardValue.values()[2] == Game.cardValue.CARD_R) {
            System.out.println("Card Value King: OK");
        } else {
            fail("Card Value King is not OK");
        }
    }

    @Test
    public void queenValueTest(){
        if (Game.cardValue.values()[3] == Game.cardValue.CARD_D) {
            System.out.println("Card Value Queen: OK");
        } else {
            fail("Card Value Queen is not OK");
        }
    }

    @Test
    public void valetValueTest(){
        if (Game.cardValue.values()[4] == Game.cardValue.CARD_V) {
            System.out.println("Card Value Valet: OK");
        } else {
            fail("Card Value Valet is not OK");
        }
    }

    @Test
    public void tenValueTest(){
        if (Game.cardValue.values()[1] == Game.cardValue.CARD_10) {
            System.out.println("Card Value Ten: OK");
        } else {
            fail("Card Value Ten is not OK");
        }
    }

    @Test
    public void nineValueTest(){
        if (Game.cardValue.values()[5] == Game.cardValue.CARD_9) {
            System.out.println("Card Value Nine: OK");
        } else {
            fail("Card Value Nine is not OK");
        }
    }

    @Test
    public void eightValueTest(){
        if (Game.cardValue.values()[6] == Game.cardValue.CARD_8) {
            System.out.println("Card Value Eight: OK");
        } else {
            fail("Card Value Eight is not OK");
        }
    }

    @Test
    public void sevenValueTest(){
        if (Game.cardValue.values()[7] == Game.cardValue.CARD_7) {
            System.out.println("Card Value Seven: OK");
        } else {
            fail("Card Value Seven is not OK");
        }
    }
}