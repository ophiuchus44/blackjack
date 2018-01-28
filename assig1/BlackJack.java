
import java.util.*;

public class BlackJack
{
	public static void main(String [] args)
	{
		String rounds = args[0];
		String decks = args[1];
		String playerBank = args[2];
		int numRounds = Integer.parseInt(rounds);
		int numDecks = Integer.parseInt(decks);
		int startingPlayerBank = Integer.parseInt(playerBank);

		System.out.println("Starting BlackJack with " + rounds + " rounds and " + decks + " decks in the shoe");
		System.out.println("Players Starting Bank is: " + startingPlayerBank);
		
		RandIndexQueue<Card> shoeCards = new RandIndexQueue<Card>(52*numDecks);

// create deck of cards

		int count =0;
		while(count<numDecks){

			for(int i=0; i<4; i++){
				for (int a=0; a<13; a++){
					Card c = new Card(Card.Suits.values()[i], Card.Ranks.values()[a]);
					shoeCards.offer(c);
				}
			}
			count++;
		}
// perform 3 shuffles
		shoeCards.shuffle();
		shoeCards.shuffle();
		shoeCards.shuffle();

		Game game = new Game(numRounds, startingPlayerBank);

		game.playGame(shoeCards);

	}	

}